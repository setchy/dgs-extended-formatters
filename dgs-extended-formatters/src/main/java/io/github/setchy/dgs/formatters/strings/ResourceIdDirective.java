package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.protobuf.OpaqueResourceIDProto;

import java.util.Objects;
import java.util.Optional;

import static java.util.Base64.getEncoder;

@DgsDirective(name = DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME)
public class ResourceIdDirective extends AbstractStringDirective {
    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        GraphQLAppliedDirective appliedDirective = field.getAppliedDirective(DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME);

        StringValue domain = Optional.ofNullable(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME))
                .map(directiveArgument -> directiveArgument.getArgumentValue())
                .map(argumentValue -> argumentValue.getValue())
                .filter(argValue -> argValue instanceof StringValue)
                .map(argValue -> (StringValue) argValue)
                .orElse(null);

        StringValue subdomain = Optional.ofNullable(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME))
                .map(directiveArgument -> directiveArgument.getArgumentValue())
                .map(argumentValue -> argumentValue.getValue())
                .filter(argValue -> argValue instanceof StringValue)
                .map(argValue -> (StringValue) argValue)
                .orElse(null);

        StringValue systemName = Optional.ofNullable(appliedDirective.getArgument(DirectiveConstants.RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME))
                .map(directiveArgument -> directiveArgument.getArgumentValue())
                .map(argumentValue -> argumentValue.getValue())
                .filter(argValue -> argValue instanceof StringValue)
                .map(argValue -> (StringValue) argValue)
                .orElse(null);

        if (Objects.isNull(domain)) {
            throw new GraphQLException(
                    String.format("'%s' formatter directive missing required argument '%s'",
                            DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME, DirectiveConstants.RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME
                    )
            );
        }

        if (Objects.isNull(subdomain)) {
            throw new GraphQLException(
                    String.format("'%s' formatter directive missing required argument '%s'",
                            DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME, DirectiveConstants.RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME
                    )
            );        }

        if (Objects.isNull(systemName)) {
            throw new GraphQLException(
                    String.format("'%s' formatter directive missing required argument '%s'",
                            DirectiveConstants.RESOURCE_ID_DIRECTIVE_NAME, DirectiveConstants.RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME
                    )
            );        }

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
