package com.dekankilic.cqrswithaxonserver.command.command;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class BaseCommand<T> {

    @TargetAggregateIdentifier // to let Axon know that the id field is the unique identifier of the AccountAggregate that the command targets
    private final T id; // id for axon to identify which instance of the aggregate that is applied on

    public BaseCommand(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
