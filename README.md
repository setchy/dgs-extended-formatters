# dgs-extended-formatters
An experimental set of [DGS](https://github.com/Netflix/dgs-framework) Schema Directives for common response formatting use-cases

## Getting started
_Note: this is not yet published_
```xml
<dependency>
    <groupId>io.github.setchy>
    <artifact>dgs-extended-formatters</artifact>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## String Formatters
The following schema directives suppport formatting `String` scalars

### @abbreviate
Abbreviates a string using ellipses for a given width

- SDL: `directive @abbreviate(width: Int!) on FIELD_DEFINITION`


### @camelcase
Converts the String into camelCase

- SDL: `directive @camelcase on FIELD_DEFINITION`

### @capitalize

Capitalize the starting letter for each word in a String 

- SDL: `directive @capitalize on FIELD_DEFINITION`


### @lowercase

Lowercase all characters in a String 


- SDL: `directive @lowercase on FIELD_DEFINITION`


### @reverse

Reverse the characters in a String

- SDL: `directive @reverse on FIELD_DEFINITION`

### @swapcase

Invert the case of each character in a String

- SDL: `directive @swapcase on FIELD_DEFINITION`


### @trim

Remove any leading or trailing whitespace

- SDL: `directive @trim on FIELD_DEFINITION`

### @uppercase

Uppercase each character in a String

- SDL: `directive @uppercase on FIELD_DEFINITION`
