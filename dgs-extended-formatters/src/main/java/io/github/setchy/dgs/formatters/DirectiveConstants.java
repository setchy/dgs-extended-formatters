package io.github.setchy.dgs.formatters;

/**
 * Central registry of directive names and directive-argument names used across this library's
 * schema directives, keeping SDL string literals and Java code in sync.
 */
public final class DirectiveConstants {

    /** SDL name of the {@code @abbreviate} directive. */
    public static final String ABBREVIATE_DIRECTIVE_NAME = "abbreviate";
    /** SDL name of the {@code @absolute} directive. */
    public static final String ABSOLUTE_DIRECTIVE_NAME = "absolute";
    /** Argument name for {@code @abbreviate}'s target width. */
    public static final String ABBREVIATE_DIRECTIVE_ARGUMENT_NAME = "width";
    /** SDL name of the {@code @camelcase} directive. */
    public static final String CAMELCASE_DIRECTIVE_NAME = "camelcase";
    /** SDL name of the {@code @capitalize} directive. */
    public static final String CAPITALIZE_DIRECTIVE_NAME = "capitalize";
    /** SDL name of the {@code @ceiling} directive. */
    public static final String CEILING_DIRECTIVE_NAME = "ceiling";
    /** SDL name of the {@code @encode} directive. */
    public static final String ENCODE_DIRECTIVE_NAME = "encode";
    /** Argument name for {@code @encode}'s target base (e.g. {@code base64}). */
    public static final String ENCODE_DIRECTIVE_BASE_ARGUMENT_NAME = "base";
    /** SDL name of the {@code @floor} directive. */
    public static final String FLOOR_DIRECTIVE_NAME = "floor";
    /** SDL name of the {@code @lowercase} directive. */
    public static final String LOWERCASE_DIRECTIVE_NAME = "lowercase";
    /** SDL name of the {@code @prefix} directive. */
    public static final String PREFIX_DIRECTIVE_NAME = "prefix";
    /** Argument name for {@code @prefix}'s prefix value. */
    public static final String PREFIX_DIRECTIVE_ARGUMENT_NAME = "with";
    /** SDL name of the {@code @resourceId} directive. */
    public static final String RESOURCE_ID_DIRECTIVE_NAME = "resourceId";
    /** Argument name for {@code @resourceId}'s domain component. */
    public static final String RESOURCE_ID_DIRECTIVE_DOMAIN_ARGUMENT_NAME = "domain";
    /** Argument name for {@code @resourceId}'s subdomain component. */
    public static final String RESOURCE_ID_DIRECTIVE_SUBDOMAIN_ARGUMENT_NAME = "subdomain";
    /** Argument name for {@code @resourceId}'s system name component. */
    public static final String RESOURCE_ID_DIRECTIVE_SYSTEMNAME_ARGUMENT_NAME = "systemName";
    /** SDL name of the {@code @reverse} directive. */
    public static final String REVERSE_DIRECTIVE_NAME = "reverse";
    /** SDL name of the {@code @suffix} directive. */
    public static final String SUFFIX_DIRECTIVE_NAME = "suffix";
    /** Argument name for {@code @suffix}'s suffix value. */
    public static final String SUFFIX_DIRECTIVE_ARGUMENT_NAME = "with";
    /** SDL name of the {@code @swapcase} directive. */
    public static final String SWAPCASE_DIRECTIVE_NAME = "swapcase";
    /** SDL name of the {@code @trim} directive. */
    public static final String TRIM_DIRECTIVE_NAME = "trim";
    /** SDL name of the {@code @uppercase} directive. */
    public static final String UPPERCASE_DIRECTIVE_NAME = "uppercase";

    private DirectiveConstants() {
    }

}
