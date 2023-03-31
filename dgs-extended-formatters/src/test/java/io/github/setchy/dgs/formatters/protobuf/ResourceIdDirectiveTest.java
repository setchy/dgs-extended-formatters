package io.github.setchy.dgs.formatters.protobuf;

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
class ResourceIdDirectiveTest {

    ResourceIdDirective resourceIdDirective;

    @Mock
    GraphQLFieldDefinition field;
    @Mock
    GraphQLAppliedDirectiveArgument domainArgument;
    @Mock GraphQLAppliedDirectiveArgument subdomainArgument;
    @Mock
    GraphQLAppliedDirectiveArgument systemNameArgument;

    @Mock
    InputValueWithState nullArgumentValue;
    @Mock
    InputValueWithState nonNullArgumentValue;

    @BeforeEach
    void setUp() {
        resourceIdDirective = new ResourceIdDirective();

        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        StringValue nonNullValue = StringValue.of(TestUtils.SOME_STRING_ARG);

        when(field.getAppliedDirective(DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME)).thenReturn(domainArgument);
        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME)).thenReturn(subdomainArgument);
        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME)).thenReturn(systemNameArgument);
        when(nonNullArgumentValue.getValue()).thenReturn(nonNullValue);
    }


    @Test
    @DisplayName("Will throw exception when domain argument is missing")
    void testMissingDomainArgument() {
        when(domainArgument.getArgumentValue()).thenReturn(nullArgumentValue);
        when(subdomainArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);
        when(systemNameArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);

        GraphQLException thrown = assertThrows(GraphQLException.class, () ->
            resourceIdDirective.format(field, TestUtils.SOME_STRING)
        );

        assertEquals("Domain is required in @resourceId", thrown.getMessage());
    }

    @Test
    @DisplayName("Will throw exception when subdomain argument is missing")
    void testMissingSubdomainArgument() {
        when(domainArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);
        when(subdomainArgument.getArgumentValue()).thenReturn(nullArgumentValue);
        when(systemNameArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);

        GraphQLException thrown = assertThrows(GraphQLException.class, () ->
            resourceIdDirective.format(field, TestUtils.SOME_STRING)
        );

        assertEquals("Subdomain is required in @resourceId", thrown.getMessage());
    }

    @Test
    @DisplayName("Will throw exception when systemName argument is missing")
    void testMissingSystemNameArgument() {
        when(domainArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);
        when(subdomainArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);
        when(systemNameArgument.getArgumentValue()).thenReturn(nullArgumentValue);

        GraphQLException thrown = assertThrows(GraphQLException.class, () ->
            resourceIdDirective.format(field, TestUtils.SOME_STRING)
        );

        assertEquals("systemName is required in @resourceId", thrown.getMessage());
    }

    @Test
    @DisplayName("Will return opaque resource identifier when all arguments provided")
    void testFormat() {
        when(domainArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);
        when(subdomainArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);
        when(systemNameArgument.getArgumentValue()).thenReturn(nonNullArgumentValue);

        assertEquals("CgpTdHJpbmcgQXJnEgpTdHJpbmcgQXJnGgpTdHJpbmcgQXJnIiMgIFNvbWUgc3RyaW5nIFRoYXQgaGFzIE1JWEVEIGNhc2UgIA==",
                resourceIdDirective.format(field, TestUtils.SOME_STRING));
    }
}
