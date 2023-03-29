package io.github.setchy.dgs.formatters.strings;

import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CamelcaseDirectiveTest {

    CamelcaseDirective camelcaseDirective;
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        camelcaseDirective = new CamelcaseDirective();
        field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);

        when(field.getAppliedDirective(DirectiveConstants.CAMELCASE_DIRECTIVE_NAME)).thenReturn(appliedDirective);
    }

    @Test
    @DisplayName("Should camelcase string")
    void testCamelcase() {
        assertEquals("someStringThatHasMixedCase",
                camelcaseDirective.format(field, TestUtils.SOME_STRING));
    }
}
