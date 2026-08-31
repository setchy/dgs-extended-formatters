# DGS Extended Formatters

[![Build Status][build-badge]][build-workflow]
[![Latest Release][latest-release-badge]][latest-release]
[![Coverage][coverage-badge]][coverage]
[![Quality Gate Status][quality-badge]][quality]
[![Renovate enabled][renovate-badge]][renovate]
[![OSS License][license-badge]][license]

A set of [Netflix DGS][dgs-framework] Schema Directives for common response formatting use-cases.

## Getting started

> [!TIP]
> | Library | DGS   | Java (min) |
> |---------|-------|------------|
> | 3.x.x   | 12.x  | 17         |
> | 2.x.x   | 10.x  | 17         |
> | 1.x.x   | 9.x   | 17         |

```xml
<dependency>
    <groupId>io.github.setchy</groupId>
    <artifactId>dgs-extended-formatters</artifactId>
    <version>3.0.0</version>
</dependency>
```

## Usage

### String Formatters

The following schema directives support formatting `String` scalars

#### @abbreviate

Abbreviates a string using ellipses for a given width

- SDL: `directive @abbreviate(width: Int!) on FIELD_DEFINITION`

#### @camelcase

Converts the String into camelCase

- SDL: `directive @camelcase on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`

#### @capitalize

Capitalize the starting letter for each word in a String

- SDL: `directive @capitalize on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`

#### @encode

Encodes a string using the given base. Currently only `base64` is supported.

- SDL: `directive @encode(base: String!) on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @lowercase

Lowercase all characters in a String

- SDL: `directive @lowercase on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`

#### @prefix

Prepends a prefix to a String

- SDL: `directive @prefix(with: String!) on FIELD_DEFINITION`

#### @resourceId

Transforms a string into a base64 protobuf opaque ID. This takes in domain, subdomain, and systemName arguments
which will be encoded into the ID.

- SDL: `directive @resourceId(domain: String!, subdomain: String!, systemName: String!) on FIELD_DEFINITION`

#### @reverse

Reverse the characters in a String

- SDL: `directive @reverse on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`

#### @suffix

Appends a suffix to a String

- SDL: `directive @suffix(with: String!) on FIELD_DEFINITION`

### @swapcase

Invert the case of each character in a String

- SDL: `directive @swapcase on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`

#### @trim

Remove any leading or trailing whitespace

- SDL: `directive @trim on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`
- `INPUT_FIELD_DEFINITION` support: when applied to a field of a GraphQL input object type, the value is trimmed before any resolver observes it. This currently only supports input fields whose declared type is a scalar (e.g. `String`) directly; nested input object types and list-of-input-object fields are not yet supported and are left unchanged.

#### @uppercase

Uppercase each character in a String

- SDL: `directive @uppercase on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`

### Numeric Formatters

The following schema directives support formatting `Int` or `Float` scalars

#### @absolute

Returns the absolute value

- SDL: `directive @absolute on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`
- `INPUT_FIELD_DEFINITION` support: only works correctly for `Int` input fields. Applying `@absolute` to a `Float` input field does not currently transform the value - see the note under `@ceiling`/`@floor` below for why.

#### @ceiling

Returns the ceiling value

- SDL: `directive @ceiling on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`
- `INPUT_FIELD_DEFINITION` support: only meaningfully supported for `Int` input fields (where it is an identity no-op, consistent with its existing `FIELD_DEFINITION` behavior on `Int`). Applying `@ceiling` to a `Float` input field does not currently work: graphql-java's built-in `Float` scalar coercion produces a `java.lang.Double`, which this directive's implementation does not recognize as a `Float`, so the value passes through untransformed.

#### @floor

Returns the floor value

- SDL: `directive @floor on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION`
- `INPUT_FIELD_DEFINITION` support: same `Int`-only limitation as `@ceiling` above.

## Contributing

The `samples` module is a runnable Spring Boot / DGS application that demonstrates every directive in this library and is exercised by automated tests as part of CI (`mvn -B verify`). When adding a new schema field or directive to `samples`, add a corresponding query assertion in the relevant `*DataFetcherTest` class (e.g. `FormattedStringExamplesDataFetcherTest`, `FormattedArgsExamplesDataFetcherTest`) so a broken wiring or regression is caught by CI rather than only discovered by manually running the sample app.

## Release Process

Our release process involves

1. Run the [release workflow][release-workflow]
2. Enter the new version number to release
3. Draft a [new release][new-release], create or select the tag which matches the release version used in step 2
4. Publish the release notes

<!-- LINK LABELS -->
[dgs-framework]: https://github.com/Netflix/dgs-framework

[release-workflow]: https://github.com/setchy/dgs-extended-formatters/actions/workflows/release.yml
[new-release]: https://github.com/setchy/dgs-extended-formatters/releases/new

[renovate]: https://github.com/setchy/dgs-extended-formatters/issues/3
[renovate-badge]: https://img.shields.io/badge/renovate-enabled-brightgreen.svg?logo=renovate&logoColor=white

[license]: LICENSE
[license-badge]: https://img.shields.io/github/license/setchy/dgs-extended-formatters?logo=github

[build-badge]: https://img.shields.io/github/actions/workflow/status/setchy/dgs-extended-formatters/build.yml?logo=github
[build-workflow]: https://github.com/setchy/dgs-extended-formatters/actions/workflows/build.yml

[latest-release-badge]: https://img.shields.io/maven-central/v/io.github.setchy/dgs-extended-formatters?logo=sonatype
[latest-release]: https://central.sonatype.com/artifact/io.github.setchy/dgs-extended-formatters

[coverage-badge]: https://img.shields.io/sonar/coverage/setchy_dgs-extended-formatters?server=https%3A%2F%2Fsonarcloud.io&logo=sonarqubecloud
[coverage]: https://sonarcloud.io/summary/new_code?id=setchy_dgs-extended-formatters

[quality-badge]: https://img.shields.io/sonar/quality_gate/setchy_dgs-extended-formatters?server=https%3A%2F%2Fsonarcloud.io&logo=sonarqubecloud
[quality]: https://sonarcloud.io/summary/new_code?id=setchy_dgs-extended-formatters
