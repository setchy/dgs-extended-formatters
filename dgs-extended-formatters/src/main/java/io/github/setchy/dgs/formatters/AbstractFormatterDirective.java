package io.github.setchy.dgs.formatters;

import graphql.schema.*;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

/**
 * Base {@link SchemaDirectiveWiring} implementation shared by every schema directive in this
 * library. Subclasses only need to implement {@link #format(GraphQLFieldDefinition, Object)};
 * this class handles wiring that formatting into {@code FIELD_DEFINITION}, {@code ARGUMENT_DEFINITION},
 * and {@code INPUT_FIELD_DEFINITION} locations.
 */
public abstract class AbstractFormatterDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
        return registerDataFetcher(env);
    }

    @Override
    public GraphQLArgument onArgument(SchemaDirectiveWiringEnvironment<GraphQLArgument> env) {
        return registerDataFetcher(env);
    }

    /**
     * Applies this directive's formatting to an {@code INPUT_FIELD_DEFINITION} location.
     * <p>
     * Unlike {@link #onField} / {@link #onArgument}, there is no {@link DataFetcher} to wrap for
     * an input object field - values flow into resolvers via graphql-java's input coercion
     * ({@link Coercing#parseValue} / {@link Coercing#parseLiteral}), not field fetching. This
     * implementation wraps the field's declared scalar type with a per-field {@link Coercing}
     * decorator that applies {@link #format(GraphQLFieldDefinition, Object)} to the coerced value.
     * <p>
     * The wrapped type is scoped to this single field only (via {@link GraphQLInputObjectField#transform});
     * it does not mutate or replace the shared {@link GraphQLScalarType} instance, so sibling fields
     * that declare the same underlying type but are not themselves annotated with this directive are
     * unaffected.
     * <p>
     * If the field's declared type is not a direct {@link GraphQLScalarType} (for example a nested
     * {@link GraphQLInputObjectType}, a {@link GraphQLList}, or a {@link GraphQLNonNull} wrapper around
     * one of these), this is a safe no-op: the field is returned unchanged. Supporting those cases is
     * currently out of scope.
     *
     * @param env the wiring element for the annotated input object field
     * @return the (possibly transformed) input object field
     */
    @Override
    public GraphQLInputObjectField onInputObjectField(SchemaDirectiveWiringEnvironment<GraphQLInputObjectField> env) {
        GraphQLInputObjectField field = env.getElement();
        GraphQLInputType originalType = field.getType();

        if (!(originalType instanceof GraphQLScalarType originalScalar)) {
            return field;
        }

        GraphQLScalarType wrappedType = GraphQLScalarType.newScalar()
                .name(originalScalar.getName() + "_" + getClass().getSimpleName() + "_" + field.getName())
                .description(originalScalar.getDescription())
                .coercing(new FormattingCoercing(originalScalar.getCoercing()))
                .build();

        return field.transform(builder -> builder.type(wrappedType));
    }

    private <T extends GraphQLDirectiveContainer> T registerDataFetcher(SchemaDirectiveWiringEnvironment<T> env) {
        GraphQLFieldDefinition field = env.getFieldDefinition();
        GraphQLFieldsContainer fieldsContainer = env.getFieldsContainer();
        DataFetcher<?> originalDataFetcher = env.getFieldDataFetcher();

        DataFetcher<?> dataFetcher =
                DataFetcherFactories.wrapDataFetcher(originalDataFetcher, ((dataFetchingEnvironment, value) -> format(field, value)));

        env.getCodeRegistry().dataFetcher((GraphQLObjectType) fieldsContainer, field, dataFetcher);
        return env.getElement();
    }

    /**
     * Formats a single value according to this directive's rules.
     *
     * @param field the field the directive was applied to (or containing the annotated argument);
     *              {@code null} when invoked for an {@code INPUT_FIELD_DEFINITION} location
     * @param value the value to format
     * @return the formatted value, or the original value unchanged if this directive does not apply to its type
     */
    public abstract Object format(GraphQLFieldDefinition field, Object value);

    /**
     * A {@link Coercing} decorator that delegates result/literal coercion to an original scalar's
     * {@link Coercing}, then post-processes the parsed value through this directive's
     * {@link AbstractFormatterDirective#format(GraphQLFieldDefinition, Object)} for input coercion
     * ({@code parseValue} / {@code parseLiteral}).
     */
    @SuppressWarnings("unchecked")
    private final class FormattingCoercing implements Coercing<Object, Object> {
        private final Coercing<Object, Object> delegate;

        FormattingCoercing(Coercing<?, ?> delegate) {
            this.delegate = (Coercing<Object, Object>) delegate;
        }

        @Override
        public Object serialize(Object dataFetcherResult, graphql.GraphQLContext graphQLContext, java.util.Locale locale) throws CoercingSerializeException {
            return delegate.serialize(dataFetcherResult, graphQLContext, locale);
        }

        @Override
        public Object parseValue(Object input, graphql.GraphQLContext graphQLContext, java.util.Locale locale) throws CoercingParseValueException {
            return formatInputValue(delegate.parseValue(input, graphQLContext, locale));
        }

        @Override
        public Object parseLiteral(graphql.language.Value<?> input, graphql.execution.CoercedVariables variables, graphql.GraphQLContext graphQLContext, java.util.Locale locale) throws CoercingParseLiteralException {
            return formatInputValue(delegate.parseLiteral(input, variables, graphQLContext, locale));
        }

        @Override
        public graphql.language.Value<?> valueToLiteral(Object input, graphql.GraphQLContext graphQLContext, java.util.Locale locale) {
            return delegate.valueToLiteral(input, graphQLContext, locale);
        }

        private Object formatInputValue(Object parsedValue) {
            return format(null, parsedValue);
        }
    }
}
