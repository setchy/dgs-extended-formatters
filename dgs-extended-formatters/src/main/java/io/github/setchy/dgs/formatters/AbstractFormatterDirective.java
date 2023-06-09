package io.github.setchy.dgs.formatters;

import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public abstract class AbstractFormatterDirective implements SchemaDirectiveWiring {

    protected static void throwGraphQLException(String directiveName, String argumentName) {
        throw new GraphQLException(
                String.format("'%s' formatter directive missing required argument '%s'",
                        directiveName, argumentName
                )
        );
    }

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
        GraphQLFieldDefinition field = env.getElement();
        GraphQLFieldsContainer fieldsContainer = env.getFieldsContainer();
        DataFetcher<?> originalDataFetcher = env.getFieldDataFetcher();

        DataFetcher<?> dataFetcher =
                DataFetcherFactories.wrapDataFetcher(originalDataFetcher, ((dataFetchingEnvironment, value) -> format(field, value)));

        env.getCodeRegistry()
                .dataFetcher(fieldsContainer, field, dataFetcher);
        return field;
    }

    public abstract Object format(GraphQLFieldDefinition field, Object value);
}