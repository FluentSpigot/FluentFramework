package io.github.jwdeveloper.ff.extension.gameobject.neww.impl.events;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.GameObject;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameObjectImpl;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
public class GameObjectAttatcheEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private boolean isRegistration;

    private GameObject gameObject;

    private Location location;

    public boolean isUnregistration()
    {
        return !isRegistration;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
