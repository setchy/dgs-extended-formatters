package io.github.setchy.dgs.formatters.numeric;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public abstract class AbstractNumericDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
        GraphQLFieldDefinition field = env.getElement();
        GraphQLFieldsContainer fieldsContainer = env.getFieldsContainer();
        DataFetcher<?> originalDataFetcher = env.getFieldDataFetcher();

        DataFetcher<?> dataFetcher =
                DataFetcherFactories.wrapDataFetcher(originalDataFetcher, ((dataFetchingEnvironment, value) -> {
                    if (value instanceof Integer intValue) {
                        return format(field, intValue);
                    } else if (value instanceof Float floatValue) {
                        return format(field, floatValue);
                    }
                    return value;
                }));

        env.getCodeRegistry()
                .dataFetcher(fieldsContainer, field, dataFetcher);
        return field;
    }


    public abstract Integer format(GraphQLFieldDefinition field, Integer value);

    public abstract Float format(GraphQLFieldDefinition field, Float value);


}