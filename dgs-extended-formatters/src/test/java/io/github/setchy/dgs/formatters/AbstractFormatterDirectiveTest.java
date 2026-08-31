package io.github.setchy.dgs.formatters;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.FloatValue;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLScalarType;
import io.github.setchy.dgs.formatters.numeric.AbsoluteDirective;
import io.github.setchy.dgs.formatters.numeric.AbstractNumericDirective;
import io.github.setchy.dgs.formatters.numeric.CeilingDirective;
import io.github.setchy.dgs.formatters.numeric.FloorDirective;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.stream.Stream;

import static graphql.Scalars.GraphQLFloat;
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

    @SuppressWarnings("unchecked")
    @ParameterizedTest(name = "[{index}] {0}: parseValue({1}) -> {2}")
    @MethodSource("numericDirectiveParseValueCases")
    @DisplayName("onInputObjectField: numeric directives on a Float input field correctly transform a Double-coerced value (parseValue)")
    void onInputObjectFieldNumericDirectiveOnFloatFieldTransformsDoubleCoercedValueViaParseValue(
            AbstractNumericDirective directive, Double input, Float expected) {
        GraphQLInputObjectField field = GraphQLInputObjectField.newInputObjectField()
                .name("myFloatField")
                .type(GraphQLFloat)
                .build();

        lenient().when(env.getElement()).thenReturn(field);

        GraphQLInputObjectField result = directive.onInputObjectField(env);
        GraphQLScalarType wrappedType = (GraphQLScalarType) result.getType();
        Coercing<Object, Object> coercing = (Coercing<Object, Object>) wrappedType.getCoercing();

        GraphQLContext ctx = GraphQLContext.getDefault();
        Locale locale = Locale.getDefault();

        // graphql-java's built-in Float scalar coerces raw input to java.lang.Double, not Float -
        // this proves that Double value is still correctly transformed by each numeric directive.
        Object result1 = coercing.parseValue(input, ctx, locale);

        assertEquals(expected, result1);
    }

    @SuppressWarnings("unchecked")
    @ParameterizedTest(name = "[{index}] {0}: parseLiteral({1}) -> {2}")
    @MethodSource("numericDirectiveParseValueCases")
    @DisplayName("onInputObjectField: numeric directives on a Float input field correctly transform a literal Float value (parseLiteral)")
    void onInputObjectFieldNumericDirectiveOnFloatFieldTransformsLiteralValueViaParseLiteral(
            AbstractNumericDirective directive, Double input, Float expected) {
        GraphQLInputObjectField field = GraphQLInputObjectField.newInputObjectField()
                .name("myFloatField")
                .type(GraphQLFloat)
                .build();

        lenient().when(env.getElement()).thenReturn(field);

        GraphQLInputObjectField result = directive.onInputObjectField(env);
        GraphQLScalarType wrappedType = (GraphQLScalarType) result.getType();
        Coercing<Object, Object> coercing = (Coercing<Object, Object>) wrappedType.getCoercing();

        GraphQLContext ctx = GraphQLContext.getDefault();
        Locale locale = Locale.getDefault();

        Value<?> literal = FloatValue.newFloatValue(BigDecimal.valueOf(input)).build();
        Object result1 = coercing.parseLiteral(literal, CoercedVariables.emptyVariables(), ctx, locale);

        assertEquals(expected, result1);
    }

    private static Stream<Arguments> numericDirectiveParseValueCases() {
        return Stream.of(
                Arguments.of(new AbsoluteDirective(), -2.5, 2.5f),
                Arguments.of(new CeilingDirective(), 2.1, 3.0f),
                Arguments.of(new FloorDirective(), 2.9, 2.0f),
                // Whole-number edge cases: ceiling/floor should be identity no-ops on whole
                // numbers (consistent with CeilingDirectiveTest/FloorDirectiveTest's existing
                // "WithWholeNumber" cases), and this must also hold true via the Double-coerced
                // INPUT_FIELD_DEFINITION path, not just when called directly with a Float.
                Arguments.of(new AbsoluteDirective(), 10.0, 10.0f),
                Arguments.of(new CeilingDirective(), 10.0, 10.0f),
                Arguments.of(new FloorDirective(), 10.0, 10.0f),
                Arguments.of(new CeilingDirective(), -10.0, -10.0f),
                Arguments.of(new FloorDirective(), -10.0, -10.0f)
        );
    }
}
