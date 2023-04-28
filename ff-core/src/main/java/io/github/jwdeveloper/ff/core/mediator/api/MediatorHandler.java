package io.github.jwdeveloper.ff.core.mediator.api;

public interface MediatorHandler<Input, Output>
{
    public Output handle(Input request);
}
