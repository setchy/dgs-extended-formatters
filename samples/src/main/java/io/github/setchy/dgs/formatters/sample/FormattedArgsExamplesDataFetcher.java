package io.github.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class FormattedArgsExamplesDataFetcher {

    @DgsData(parentType = "FormattedArgsExamples", field = "original")
    public String original(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "uppercased")
    public String uppercased(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "lowercased")
    public String lowercased(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "trimmed")
    public String trimmed(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "trimmedUppercased")
    public String trimmedUppercased(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "capitalized")
    public String capitalized(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "reversed")
    public String reversed(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "swapcased")
    public String swapcased(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "camelcased")
    public String camelcased(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "encoded")
    public String encoded(@InputArgument String arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "absoluteInt")
    public Integer absoluteInt(@InputArgument Integer arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "ceilingInt")
    public Integer ceilingInt(@InputArgument Integer arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "flooredInt")
    public Integer flooredInt(@InputArgument Integer arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "absoluteFloat")
    public Float absoluteFloat(@InputArgument Float arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "ceilingFloat")
    public Float ceilingFloat(@InputArgument Float arg) {
        return arg;
    }

    @DgsData(parentType = "FormattedArgsExamples", field = "flooredFloat")
    public Float flooredFloat(@InputArgument Float arg) {
        return arg;
    }
}
