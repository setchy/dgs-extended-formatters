package io.github.setchy.dgs.formatters.strings;

import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuffixDirectiveTest {

    SuffixDirective suffixDirective;

    @Mock
    GraphQLFieldDefinition field;
    @Mock
    InputValueWithState withArgumentValue;

    @BeforeEach
    void setUp() {
        suffixDirective = new SuffixDirective();

        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument argument = mock(GraphQLAppliedDirectiveArgument.class);

        when(field.getAppliedDirective(DirectiveConstants.SUFFIX_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.SUFFIX_DIRECTIVE_ARGUMENT_NAME)).thenReturn(argument);
        when(argument.getArgumentValue()).thenReturn(withArgumentValue);
    }

    @Test
    @DisplayName("Will append suffix")
    void testFormatWithSuffix() {
        StringValue withStringValue = mock(StringValue.class);
        when(withArgumentValue.getValue()).thenReturn(withStringValue);
        when(withStringValue.getValue()).thenReturn("- suffix");

        String result = suffixDirective.applyFormatting(field, TestUtils.SOME_STRING);

        assertEquals(TestUtils.SOME_STRING + "- suffix", result);
    }

    @Test
    @DisplayName("Will append empty suffix")
    void testFormatWithEmptySuffix() {
        StringValue withStringValue = mock(StringValue.class);
        when(withArgumentValue.getValue()).thenReturn(withStringValue);
        when(withStringValue.getValue()).thenReturn("");

        String result = suffixDirective.applyFormatting(field, TestUtils.SOME_STRING);

        assertEquals(TestUtils.SOME_STRING, result);
    }

    @Test
    @DisplayName("Will throw exception when argument is missing")
    void testFormatWithMissingWidthArgument() {
        when(withArgumentValue.getValue()).thenReturn(null);

        GraphQLException thrown = assertThrows(GraphQLException.class, () ->
                suffixDirective.format(field, TestUtils.SOME_STRING)
        );

        assertEquals("Suffix formatter directive missing required argument", thrown.getMessage());
    }
}
