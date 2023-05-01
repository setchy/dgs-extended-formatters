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
class PrefixDirectiveTest {

    PrefixDirective prefixDirective;

    @Mock
    GraphQLFieldDefinition field;
    @Mock
    InputValueWithState withArgumentValue;

    @BeforeEach
    void setUp() {
        prefixDirective = new PrefixDirective();

        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument argument = mock(GraphQLAppliedDirectiveArgument.class);

        when(field.getAppliedDirective(DirectiveConstants.PREFIX_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.PREFIX_DIRECTIVE_ARGUMENT_NAME)).thenReturn(argument);
        when(argument.getArgumentValue()).thenReturn(withArgumentValue);
    }

    @Test
    @DisplayName("Will prepend prefix")
    void testFormatWithPrefix() {
        StringValue withStringValue = mock(StringValue.class);
        when(withArgumentValue.getValue()).thenReturn(withStringValue);
        when(withStringValue.getValue()).thenReturn("prefix: ");

        String result = prefixDirective.applyFormatting(field, TestUtils.SOME_STRING);

        assertEquals("prefix: " + TestUtils.SOME_STRING, result);
    }

    @Test
    @DisplayName("Will prepend empty prefix")
    void testFormatWithEmptyPrefix() {
        StringValue withStringValue = mock(StringValue.class);
        when(withArgumentValue.getValue()).thenReturn(withStringValue);
        when(withStringValue.getValue()).thenReturn("");

        String result = prefixDirective.applyFormatting(field, TestUtils.SOME_STRING);

        assertEquals(TestUtils.SOME_STRING, result);
    }

    @Test
    @DisplayName("Will throw exception when argument is missing")
    void testFormatWithMissingWidthArgument() {
        when(withArgumentValue.getValue()).thenReturn(null);

        GraphQLException thrown = assertThrows(GraphQLException.class, () ->
                prefixDirective.format(field, TestUtils.SOME_STRING)
        );

        assertEquals("Prefix formatter directive missing required argument", thrown.getMessage());
    }
}
