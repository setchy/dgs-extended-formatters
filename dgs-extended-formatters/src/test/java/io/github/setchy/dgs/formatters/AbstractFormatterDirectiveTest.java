package io.github.setchy.dgs.formatters;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLScalarType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static graphql.Scalars.GraphQLString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AbstractFormatterDirective}'s {@code onInputObjectField} mechanism and its
 * private {@code FormattingCoercing} wrapper, exercised directly (not via a full schema/query
 * round-trip) to get deterministic coverage of every {@link Coercing} method and both branches of
 * {@code onInputObjectField} (scalar vs. non-scalar declared type).
 */
@ExtendWith(MockitoExtension.class)
class AbstractFormatterDirectiveTest {

    /** A minimal concrete directive: uppercases Strings, passes everything else through unchanged. */
    private static final AbstractFormatterDirective UPPERCASING_DIRECTIVE = new AbstractFormatterDirective() {
        @Override
        public Object format(GraphQLFieldDefinition field, Object value) {
            if (value instanceof String stringValue) {
                return stringValue.toUpperCase(Locale.ROOT);
            }
            return value;
        }
    };

    @Mock
    graphql.schema.idl.SchemaDirectiveWiringEnvironment<GraphQLInputObjectField> env;

    @Test
    @DisplayName("onInputObjectField: non-scalar declared type (e.g. nested input object) is left unchanged")
    void onInputObjectFieldNonScalarTypeIsNoOp() {
        GraphQLInputObjectType nestedType = GraphQLInputObjectType.newInputObject()
                .name("Nested")
                .field(GraphQLInputObjectField.newInputObjectField().name("x").type(GraphQLString))
                .build();

        GraphQLInputObjectField field = GraphQLInputObjectField.newInputObjectField()
                .name("nestedField")
                .type(nestedType)
                .build();

        when(env.getElement()).thenReturn(field);

        GraphQLInputObjectField result = UPPERCASING_DIRECTIVE.onInputObjectField(env);

        assertSame(field, result, "Non-scalar input field types must be returned unchanged");
    }

    @Test
    @DisplayName("onInputObjectField: list-typed declared type is also left unchanged (non-scalar)")
    void onInputObjectFieldListTypeIsNoOp() {
        GraphQLInputObjectField field = GraphQLInputObjectField.newInputObjectField()
                .name("listField")
                .type(GraphQLList.list(GraphQLString))
                .build();

        when(env.getElement()).thenReturn(field);

        GraphQLInputObjectField result = UPPERCASING_DIRECTIVE.onInputObjectField(env);

        assertSame(field, result, "List-typed input field types must be returned unchanged");
    }

    @Test
    @DisplayName("onInputObjectField: scalar declared type is wrapped with a formatting Coercing")
    void onInputObjectFieldScalarTypeWrapsCoercing() {
        GraphQLInputObjectField field = GraphQLInputObjectField.newInputObjectField()
                .name("myField")
                .type(GraphQLString)
                .build();

        when(env.getElement()).thenReturn(field);

        GraphQLInputObjectField result = UPPERCASING_DIRECTIVE.onInputObjectField(env);

        assertInstanceOf(GraphQLScalarType.class, result.getType());
        GraphQLScalarType wrappedType = (GraphQLScalarType) result.getType();
        assertEquals("String_" + UPPERCASING_DIRECTIVE.getClass().getSimpleName() + "_myField", wrappedType.getName());
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("FormattingCoercing: parseValue applies formatting to the delegate-coerced value")
    void formattingCoercingParseValueAppliesFormatting() {
        Coercing<Object, Object> coercing = wrapAndGetCoercing();
        GraphQLContext ctx = GraphQLContext.getDefault();
        Locale locale = Locale.getDefault();

        Object result = coercing.parseValue("hello", ctx, locale);

        assertEquals("HELLO", result);
    }

    @Test
    @DisplayName("FormattingCoercing: parseLiteral applies formatting to the delegate-coerced value")
    void formattingCoercingParseLiteralAppliesFormatting() {
        Coercing<Object, Object> coercing = wrapAndGetCoercing();
        GraphQLContext ctx = GraphQLContext.getDefault();
        Locale locale = Locale.getDefault();

        Value<?> literal = new StringValue("world");
        Object result = coercing.parseLiteral(literal, CoercedVariables.emptyVariables(), ctx, locale);

        assertEquals("WORLD", result);
    }

    @Test
    @DisplayName("FormattingCoercing: serialize delegates to the original scalar's coercing unchanged")
    void formattingCoercingSerializeDelegatesUnchanged() {
        Coercing<Object, Object> coercing = wrapAndGetCoercing();
        GraphQLContext ctx = GraphQLContext.getDefault();
        Locale locale = Locale.getDefault();

        Object result = coercing.serialize("already formatted", ctx, locale);

        assertEquals("already formatted", result, "serialize (output coercion) must not apply input formatting");
    }

    @Test
    @DisplayName("FormattingCoercing: valueToLiteral delegates to the original scalar's coercing unchanged")
    void formattingCoercingValueToLiteralDelegatesUnchanged() {
        Coercing<Object, Object> coercing = wrapAndGetCoercing();
        GraphQLContext ctx = GraphQLContext.getDefault();
        Locale locale = Locale.getDefault();

        Value<?> literal = coercing.valueToLiteral("abc", ctx, locale);

        assertInstanceOf(StringValue.class, literal);
        assertEquals("abc", ((StringValue) literal).getValue());
    }

    @SuppressWarnings("unchecked")
    private Coercing<Object, Object> wrapAndGetCoercing() {
        GraphQLInputObjectField field = GraphQLInputObjectField.newInputObjectField()
                .name("myField")
                .type(GraphQLString)
                .build();

        lenient().when(env.getElement()).thenReturn(field);

        GraphQLInputObjectField result = UPPERCASING_DIRECTIVE.onInputObjectField(env);
        GraphQLScalarType wrappedType = (GraphQLScalarType) result.getType();
        return (Coercing<Object, Object>) wrappedType.getCoercing();
    }
}
