package io.github.setchy.dgs.formatters;

import graphql.schema.*;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public abstract class AbstractFormatterDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
        return registerDataFetcher(env);
    }

    @Override
    public GraphQLArgument onArgument(SchemaDirectiveWiringEnvironment<GraphQLArgument> env) {
        return registerDataFetcher(env);
    }

    private <T extends GraphQLDirectiveContainer> T registerDataFetcher(SchemaDirectiveWiringEnvironment<T> env) {
        GraphQLFieldDefinition field = env.getFieldDefinition();
        GraphQLFieldsContainer fieldsContainer = env.getFieldsContainer();
        DataFetcher<?> originalDataFetcher = env.getFieldDataFetcher();

        DataFetcher<?> dataFetcher =
                DataFetcherFactories.wrapDataFetcher(originalDataFetcher, ((dataFetchingEnvironment, value) -> format(field, value)));

        env.getCodeRegistry().dataFetcher((GraphQLObjectType) fieldsContainer, field, dataFetcher);
        return env.getElement();
    }

    public abstract Object format(GraphQLFieldDefinition field, Object value);
}