package io.github.setchy.dgs.formatters.protobuf;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceIdDirectiveTest {

    ResourceIdDirective resourceIdDirective;

    @BeforeEach
    void setUp() {
        resourceIdDirective = new ResourceIdDirective();
    }

//    @Test
//    @DisplayName("Will abbreviate string")
//    void testFormatWithAbbreviation() {
//        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
//        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
//        GraphQLAppliedDirectiveArgument argument = mock(GraphQLAppliedDirectiveArgument.class);
//        InputValueWithState argumentValue = mock(InputValueWithState.class);
//        IntValue intValue = mock(IntValue.class);
//
//        when(field.getAppliedDirective(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)).thenReturn(appliedDirective);
//        when(appliedDirective.getArgument(DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME)).thenReturn(argument);
//        when(argument.getArgumentValue()).thenReturn(argumentValue);
//        when(argumentValue.getValue()).thenReturn(intValue);
//        when(intValue.getValue()).thenReturn(BigInteger.valueOf(10));
//
//        String result = abbreviateDirective.format(field, "This is a long value");
//
//        assertEquals("This is...", result);
//    }

    @Test
    @DisplayName("Will throw exception when domain argument is missing")
    void testMissingDomainArgument() {
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument domainArgument = mock(GraphQLAppliedDirectiveArgument.class);
        GraphQLAppliedDirectiveArgument subdomainArgument = mock(GraphQLAppliedDirectiveArgument.class);
        GraphQLAppliedDirectiveArgument systemNameArgument = mock(GraphQLAppliedDirectiveArgument.class);

        InputValueWithState nullArgumentValue = mock(InputValueWithState.class);
        StringValue nullValue = null;

        InputValueWithState someArgumentValue = mock(InputValueWithState.class);
        StringValue someValue = StringValue.of("Some value");

        when(field.getAppliedDirective(DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME)).thenReturn(appliedDirective);

        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME)).thenReturn(domainArgument);
        when(domainArgument.getArgumentValue()).thenReturn(nullArgumentValue);
        when(nullArgumentValue.getValue()).thenReturn(nullValue);

        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME)).thenReturn(subdomainArgument);
        when(subdomainArgument.getArgumentValue()).thenReturn(someArgumentValue);

        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME)).thenReturn(systemNameArgument);
        when(systemNameArgument.getArgumentValue()).thenReturn(someArgumentValue);
        when(someArgumentValue.getValue()).thenReturn(someValue);

        GraphQLException thrown = assertThrows(GraphQLException.class, () -> {
            resourceIdDirective.format(field, "This is a long value");
        });

        assertEquals("Domain is required in @resourceId", thrown.getMessage());
    }

    @Test
    @DisplayName("Will throw exception when subdomain argument is missing")
    void testMissingSubdomainArgument() {
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument domainArgument = mock(GraphQLAppliedDirectiveArgument.class);
        GraphQLAppliedDirectiveArgument subdomainArgument = mock(GraphQLAppliedDirectiveArgument.class);
        GraphQLAppliedDirectiveArgument systemNameArgument = mock(GraphQLAppliedDirectiveArgument.class);

        InputValueWithState nullArgumentValue = mock(InputValueWithState.class);
        StringValue nullValue = null;

        InputValueWithState someArgumentValue = mock(InputValueWithState.class);
        StringValue someValue = StringValue.of("Some value");

        when(field.getAppliedDirective(DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME)).thenReturn(appliedDirective);

        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME)).thenReturn(domainArgument);
        when(domainArgument.getArgumentValue()).thenReturn(someArgumentValue);

        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME)).thenReturn(subdomainArgument);
        when(subdomainArgument.getArgumentValue()).thenReturn(nullArgumentValue);
        when(nullArgumentValue.getValue()).thenReturn(nullValue);


        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME)).thenReturn(systemNameArgument);
        when(systemNameArgument.getArgumentValue()).thenReturn(someArgumentValue);
        when(someArgumentValue.getValue()).thenReturn(someValue);

        GraphQLException thrown = assertThrows(GraphQLException.class, () -> {
            resourceIdDirective.format(field, "This is a long value");
        });

        assertEquals("Subdomain is required in @resourceId", thrown.getMessage());
    }

    @Test
    @DisplayName("Will throw exception when systemName argument is missing")
    void testMissingSystemNameArgument() {
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument domainArgument = mock(GraphQLAppliedDirectiveArgument.class);
        GraphQLAppliedDirectiveArgument subdomainArgument = mock(GraphQLAppliedDirectiveArgument.class);
        GraphQLAppliedDirectiveArgument systemNameArgument = mock(GraphQLAppliedDirectiveArgument.class);

        InputValueWithState nullArgumentValue = mock(InputValueWithState.class);
        StringValue nullValue = null;

        InputValueWithState someArgumentValue = mock(InputValueWithState.class);
        StringValue someValue = StringValue.of("Some value");

        when(field.getAppliedDirective(DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME)).thenReturn(appliedDirective);

        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME)).thenReturn(domainArgument);
        when(domainArgument.getArgumentValue()).thenReturn(someArgumentValue);
        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME)).thenReturn(subdomainArgument);
        when(subdomainArgument.getArgumentValue()).thenReturn(someArgumentValue);
        when(someArgumentValue.getValue()).thenReturn(someValue);


        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME)).thenReturn(systemNameArgument);
        when(systemNameArgument.getArgumentValue()).thenReturn(nullArgumentValue);
        when(nullArgumentValue.getValue()).thenReturn(nullValue);


        GraphQLException thrown = assertThrows(GraphQLException.class, () -> {
            resourceIdDirective.format(field, "This is a long value");
        });

        assertEquals("systemName is required in @resourceId", thrown.getMessage());
    }

    @Test
    @DisplayName("Will return opaque resource identifier when all arguments provided")
    void testFormat() {
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument domainArgument = mock(GraphQLAppliedDirectiveArgument.class);
        GraphQLAppliedDirectiveArgument subdomainArgument = mock(GraphQLAppliedDirectiveArgument.class);
        GraphQLAppliedDirectiveArgument systemNameArgument = mock(GraphQLAppliedDirectiveArgument.class);

        InputValueWithState someArgumentValue = mock(InputValueWithState.class);
        StringValue someValue = StringValue.of("Some value");

        when(field.getAppliedDirective(DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME)).thenReturn(domainArgument);
        when(domainArgument.getArgumentValue()).thenReturn(someArgumentValue);
        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME)).thenReturn(subdomainArgument);
        when(subdomainArgument.getArgumentValue()).thenReturn(someArgumentValue);
        when(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME)).thenReturn(systemNameArgument);
        when(systemNameArgument.getArgumentValue()).thenReturn(someArgumentValue);
        when(someArgumentValue.getValue()).thenReturn(someValue);


        assertEquals("CgpTb21lIHZhbHVlEgpTb21lIHZhbHVlGgpTb21lIHZhbHVlIhRUaGlzIGlzIGEgbG9uZyB2YWx1ZQ==", resourceIdDirective.format(field, "This is a long value"));
    }
}
