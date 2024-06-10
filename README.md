# dgs-extended-formatters

[![Build Status][build-badge]][build-workflow]
[![Latest Release][latest-release-badge]][latest-release]
[![Coverage][coverage-badge]][coverage]
[![Quality Gate Status][quality-badge]][quality]
[![Renovate enabled][renovate-badge]][renovate]
[![OSS License][license-badge]][license]

A set of [DGS][dgs-framework] Schema Directives for common response formatting use-cases.

## Getting started

```xml
<dependency>
    <groupId>io.github.setchy</groupId>
    <artifactId>dgs-extended-formatters</artifactId>
    <version>1.0.13</version>
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

- SDL: `directive @camelcase on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @capitalize

Capitalize the starting letter for each word in a String

- SDL: `directive @capitalize on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @lowercase

Lowercase all characters in a String

- SDL: `directive @lowercase on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @prefix

Prepends a prefix to a String

- SDL: `directive @prefix(with: String!) on FIELD_DEFINITION`

#### @resourceId

Transforms a string into a base64 protobuf opaque ID. This takes in domain, subdomain, and systemName arguments
which will be encoded into the ID.

- SDL: `directive @resourceId(domain: String!, subdomain: String!, systemName: String!) on FIELD_DEFINITION`

#### @reverse

Reverse the characters in a String

- SDL: `directive @reverse on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @suffix

Appends a suffix to a String

- SDL: `directive @suffix(with: String!) on FIELD_DEFINITION`

### @swapcase

Invert the case of each character in a String

- SDL: `directive @swapcase on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @trim

Remove any leading or trailing whitespace

- SDL: `directive @trim on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @uppercase

Uppercase each character in a String

- SDL: `directive @uppercase on FIELD_DEFINITION | ARGUMENT_DEFINITION`

### Numeric Formatters

The following schema directives support formatting `Int` or `Float` scalars

#### @absolute

Returns the absolute value

- SDL: `directive @absolute on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @ceiling

Returns the ceiling value

- SDL: `directive @ceiling on FIELD_DEFINITION | ARGUMENT_DEFINITION`

#### @floor

Returns the floor value

- SDL: `directive @floor on FIELD_DEFINITION | ARGUMENT_DEFINITION`

## Release Process

Our release process involves

1. Run the [release workflow][release-workflow]
2. Enter the new version number to release
3. Draft a [new release][new release], create a tag that matches the release version from above and publish

<!-- LINK LABELS -->
[dgs-framework]: https://github.com/Netflix/dgs-framework

[release-workflow]: https://github.com/setchy/dgs-extended-formatters/actions/workflows/release.yml
[new-release]: https://github.com/setchy/dgs-extended-formatters/releases/new

[renovate]: https://renovatebot.com/
[renovate-badge]: https://img.shields.io/badge/renovate-enabled-brightgreen.svg

[license]: LICENSE
[license-badge]: https://img.shields.io/github/license/setchy/dgs-extended-formatters

[build-badge]: https://github.com/setchy/dgs-extended-formatters/actions/workflows/build.yml/badge.svg
[build-workflow]: https://github.com/setchy/dgs-extended-formatters/actions/workflows/master.yml

[latest-release-badge]: https://maven-badges.herokuapp.com/maven-central/io.github.setchy/dgs-extended-formatters/badge.svg?color=blue
[latest-release]: https://maven-badges.herokuapp.com/maven-central/io.github.setchy/dgs-extended-formatters/

[coverage-badge]: https://sonarcloud.io/api/project_badges/measure?project=setchy_dgs-extended-formatters&metric=coverage
[coverage]: https://sonarcloud.io/summary/new_code?id=setchy_dgs-extended-formatters

[quality-badge]: https://sonarcloud.io/api/project_badges/measure?project=setchy_dgs-extended-formatters&metric=alert_status
[quality]: https://sonarcloud.io/summary/new_code?id=setchy_dgs-extended-formatters
