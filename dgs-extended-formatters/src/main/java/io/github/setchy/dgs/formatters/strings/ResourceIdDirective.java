package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.protobuf.OpaqueResourceIDProto;

import static java.util.Base64.getEncoder;

@DgsDirective(name = DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME)
public class ResourceIdDirective extends AbstractStringDirective {
    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        GraphQLAppliedDirective appliedDirective = field.getAppliedDirective(DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME);

        StringValue domain = (StringValue) appliedDirective
                .getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME)
                .getArgumentValue()
                .getValue();

        StringValue subdomain = (StringValue) appliedDirective
                .getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME)
                .getArgumentValue()
                .getValue();

        StringValue systemName = (StringValue) appliedDirective
                .getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME)
                .getArgumentValue()
                .getValue();

        if (domain == null) {
            throw new GraphQLException("Domain argument is required in @resourceId");
        }

        if (subdomain == null) {
            throw new GraphQLException("Subdomain argument is required in @resourceId");
        }

        if (systemName == null) {
            throw new GraphQLException("systemName argument is required in @resourceId");
        }

        return createOpaqueResourceID(domain.getValue(), subdomain.getValue(), systemName.getValue(), value);
    }

    private String createOpaqueResourceID(String domain, String subdomain, String systemName,
                                                String systemID) {

        OpaqueResourceIDProto.OpaqueResourceID opaqueResourceIDProto =
                OpaqueResourceIDProto.OpaqueResourceID.newBuilder()
                        .setDomain(domain)
                        .setSubdomain(subdomain)
                        .setSystemName(systemName)
                        .setSystemID(systemID)
                        .build();

        byte[] bytesProto = opaqueResourceIDProto.toByteArray();

        return getEncoder().encodeToString(bytesProto);
    }
}
