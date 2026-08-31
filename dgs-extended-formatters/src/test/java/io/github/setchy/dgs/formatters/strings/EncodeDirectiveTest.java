package io.github.setchy.dgs.formatters.strings;

import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EncodeDirectiveTest {

    EncodeDirective encodeDirective;

    @Mock
    GraphQLFieldDefinition field;
    @Mock
    GraphQLAppliedDirectiveArgument baseArgument;

    @Mock
    InputValueWithState nullArgumentValue;
    @Mock
    InputValueWithState base64ArgumentValue;

    @BeforeEach
    void setUp() {
        encodeDirective = new EncodeDirective();
    }

    private void stubAppliedDirective() {
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        when(field.getAppliedDirective(DirectiveConstants.ENCODE_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.ENCODE_DIRECTIVE_BASE_ARGUMENT_NAME)).thenReturn(baseArgument);
    }

    @Test
    @DisplayName("Will throw exception when base argument is missing")
    void testMissingBaseArgument() {
        stubAppliedDirective();
        when(baseArgument.getArgumentValue()).thenReturn(nullArgumentValue);

        GraphQLException thrown = assertThrows(GraphQLException.class, () ->
                encodeDirective.applyFormatting(field, "hello")
        );

        assertEquals("'encode' formatter directive missing required argument 'base'", thrown.getMessage());
    }

    @Test
    @DisplayName("Will throw exception when base argument is unsupported")
    void testUnsupportedBaseArgument() {
        stubAppliedDirective();
        when(baseArgument.getArgumentValue()).thenReturn(base64ArgumentValue);
        when(base64ArgumentValue.getValue()).thenReturn(StringValue.of("base32"));

        GraphQLException thrown = assertThrows(GraphQLException.class, () ->
                encodeDirective.applyFormatting(field, "hello")
        );

        assertEquals("'encode' formatter directive does not support base 'base32'", thrown.getMessage());
    }

    @ParameterizedTest(name = "[{index}] base={0}, input={1} -> {2}")
    @CsvSource({
            "base64, hello, aGVsbG8=",
            "base64, world, d29ybGQ=",
            "BASE64, hello, aGVsbG8="
    })
    @DisplayName("Will encode value using base64 (field, argument, and case-insensitive base)")
    void testEncode(String base, String input, String expected) {
        stubAppliedDirective();
        when(baseArgument.getArgumentValue()).thenReturn(base64ArgumentValue);
        when(base64ArgumentValue.getValue()).thenReturn(StringValue.of(base));

        assertEquals(expected, encodeDirective.applyFormatting(field, input));
    }

    @Test
    @DisplayName("Will pass through non-string values unchanged")
    void testNonStringValueUnchanged() {
        assertEquals(42, encodeDirective.format(field, 42));
    }
}
