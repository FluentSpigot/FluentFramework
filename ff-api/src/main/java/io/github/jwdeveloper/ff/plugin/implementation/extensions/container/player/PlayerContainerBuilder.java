package io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player;
import io.github.jwdeveloper.dependance.injector.api.containers.Container;
import io.github.jwdeveloper.dependance.injector.api.events.events.OnRegistrationEvent;
import io.github.jwdeveloper.dependance.injector.api.models.RegistrationInfo;
import java.util.HashSet;
import java.util.Set;

public class PlayerContainerBuilder {
    Set<RegistrationInfo> playerScopedClasses = new HashSet<RegistrationInfo>();

    public boolean handleRegistrationEvent(OnRegistrationEvent event) {
        if (!event.input().isAnnotationPresent(PlayerScope.class)) {
            return true;
        }
        playerScopedClasses.add(event.registrationInfo());
        return false;
    }

    public PlayerContainer build(Container mainContainer)
    {

        var playerContainer = new PlayerContainer(mainContainer, playerScopedClasses);

        return playerContainer;
    }
}
