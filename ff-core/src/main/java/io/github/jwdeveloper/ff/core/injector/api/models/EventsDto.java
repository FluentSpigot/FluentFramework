package io.github.jwdeveloper.ff.core.injector.api.models;


import io.github.jwdeveloper.ff.core.injector.api.events.ContainerEvents;

import java.util.List;

public record EventsDto(List<ContainerEvents> events)

{

}
