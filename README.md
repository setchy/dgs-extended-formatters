# dgs-extended-formatters

[![Build Status](https://github.com/setchy/dgs-extended-formatters/actions/workflows/build.yml/badge.svg)](https://github.com/setchy/dgs-extended-formatters/actions/workflows/master.yml)
[![Latest Release](https://maven-badges.herokuapp.com/maven-central/io.github.setchy/dgs-extended-formatters/badge.svg?color=blue)](https://maven-badges.herokuapp.com/maven-central/io.github.setchy/dgs-extended-formatters/)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=setchy_dgs-extended-formatters&metric=coverage)](https://sonarcloud.io/summary/new_code?id=setchy_dgs-extended-formatters)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=setchy_dgs-extended-formatters&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=setchy_dgs-extended-formatters)

A set of [DGS](https://github.com/Netflix/dgs-framework) Schema Directives for common response formatting use-cases.

## Getting started

```xml
<dependency>
    <groupId>io.github.setchy</groupId>
    <artifactId>dgs-extended-formatters</artifactId>
    <version>1.0.10</version>
</dependency>
```

## String Formatters

The following schema directives support formatting `String` scalars

### @abbreviate

Abbreviates a string using ellipses for a given width

- SDL: `directive @abbreviate(width: Int!) on FIELD_DEFINITION`

### @camelcase

Converts the String into camelCase

- SDL: `directive @camelcase on FIELD_DEFINITION | ARGUMENT_DEFINITION`

### @capitalize

Capitalize the starting letter for each word in a String

- SDL: `directive @capitalize on FIELD_DEFINITION | ARGUMENT_DEFINITION`

### @lowercase

Lowercase all characters in a String

- SDL: `directive @lowercase on FIELD_DEFINITION | ARGUMENT_DEFINITION`

### @prefix

Prepends a prefix to a String

- SDL: `directive @prefix(with: String!) on FIELD_DEFINITION`

### @resourceId

Transforms a string into a base64 protobuf opaque ID. This takes in domain, subdomain, and systemName arguments
which will be encoded into the ID.

- SDL: `directive @resourceId(domain: String!, subdomain: String!, systemName: String!) on FIELD_DEFINITION`

### @reverse

Reverse the characters in a String

- SDL: `directive @reverse on FIELD_DEFINITION | ARGUMENT_DEFINITION`

### @suffix

Appends a suffix to a String

- SDL: `directive @suffix(with: String!) on FIELD_DEFINITION`

### @swapcase

Invert the case of each character in a String

- SDL: `directive @swapcase on FIELD_DEFINITION | ARGUMENT_DEFINITION`

### @trim

Remove any leading or trailing whitespace

- SDL: `directive @trim on FIELD_DEFINITION | ARGUMENT_DEFINITION`

### @uppercase

Uppercase each character in a String

- SDL: `directive @uppercase on FIELD_DEFINITION | ARGUMENT_DEFINITION`

## Numeric Formatters

The following schema directives support formatting `Int` or `Float` scalars

### @absolute

Returns the absolute value

- SDL: `directive @absolute on FIELD_DEFINITION`

### @ceiling

Returns the ceiling value

- SDL: `directive @ceiling on FIELD_DEFINITION`

### @floor

Returns the floor value

- SDL: `directive @floor on FIELD_DEFINITION`
