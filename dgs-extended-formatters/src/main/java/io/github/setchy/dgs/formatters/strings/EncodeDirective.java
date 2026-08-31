package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.exception.ExceptionUtils;

import java.util.Objects;
import java.util.Optional;

import static java.util.Base64.getEncoder;

@DgsDirective(name = DirectiveConstants.ENCODE_DIRECTIVE_NAME)
public class EncodeDirective extends AbstractStringDirective {

    private static final String BASE64 = "base64";

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        GraphQLAppliedDirective appliedDirective = resolveAppliedDirective(field);

        StringValue base = Optional.ofNullable(appliedDirective)
                .map(directive -> directive.getArgument(DirectiveConstants.ENCODE_DIRECTIVE_BASE_ARGUMENT_NAME))
                .map(GraphQLAppliedDirectiveArgument::getArgumentValue)
                .map(InputValueWithState::getValue)
                .filter(StringValue.class::isInstance)
                .map(StringValue.class::cast)
                .orElse(null);

        if (Objects.isNull(base)) {
            throw new GraphQLException(
                    ExceptionUtils.formatExceptionMessage(DirectiveConstants.ENCODE_DIRECTIVE_NAME,
                            DirectiveConstants.ENCODE_DIRECTIVE_BASE_ARGUMENT_NAME));
        }

        return encode(base.getValue(), value);
    }

    /**
     * The {@code @encode} directive can be applied either to a FIELD_DEFINITION (in which case
     * the applied directive lives on the field itself) or to an ARGUMENT_DEFINITION (in which
     * case the applied directive lives on one of the field's arguments, not the field). Both
     * {@code onField} and {@code onArgument} wiring in {@link io.github.setchy.dgs.formatters.AbstractFormatterDirective}
     * ultimately call {@code format}/{@code applyFormatting} with only the containing field, so
     * this checks the field first and falls back to scanning its arguments.
     */
    private GraphQLAppliedDirective resolveAppliedDirective(GraphQLFieldDefinition field) {
        GraphQLAppliedDirective onField = field.getAppliedDirective(DirectiveConstants.ENCODE_DIRECTIVE_NAME);
        if (Objects.nonNull(onField)) {
            return onField;
        }

        return field.getArguments().stream()
                .map(argument -> argument.getAppliedDirective(DirectiveConstants.ENCODE_DIRECTIVE_NAME))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private String encode(String base, String value) {
        if (!BASE64.equalsIgnoreCase(base)) {
            throw new GraphQLException(
                    String.format("'%s' formatter directive does not support base '%s'",
                            DirectiveConstants.ENCODE_DIRECTIVE_NAME, base));
        }

        return getEncoder().encodeToString(value.getBytes());
    }
}
