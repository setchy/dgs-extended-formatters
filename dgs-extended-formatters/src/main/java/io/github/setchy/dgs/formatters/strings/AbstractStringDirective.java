package io.github.setchy.dgs.formatters.strings;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public abstract class AbstractStringDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
        GraphQLFieldDefinition field = env.getElement();
        GraphQLFieldsContainer fieldsContainer = env.getFieldsContainer();
        DataFetcher<?> originalDataFetcher = env.getFieldDataFetcher();

        // Build a data fetcher that transforms the given value to uppercase
        DataFetcher<?> dataFetcher =
            DataFetcherFactories.wrapDataFetcher(originalDataFetcher, ((dataFetchingEnvironment, value) -> {
                if (value instanceof String) {
                    return format(field, (String) value);
                }
                return value;
            }));

        env.getCodeRegistry()
            .dataFetcher(fieldsContainer, field, dataFetcher);
        return field;
    }

    public abstract String format(GraphQLFieldDefinition field, String value);

}