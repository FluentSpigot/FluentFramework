package io.github.jwdeveloper.ff.core.validator.implementation.location;

import io.github.jwdeveloper.ff.core.validator.implementation.ValidatorBase;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.Objects;

public class LocationValidator extends ValidatorBase<Location, LocationValidator>
{
    public LocationValidator mustHasWorld(World world)
    {
        return mustComplyRule(e -> Objects.equals(e.getWorld(), world), "Location is on the different world");
    }

    public LocationValidator mustBeInRange(Location origin, int range)
    {
        return mustComplyRule(e ->  origin.distance(e) <= range, "Location is not in range");
    }
    public LocationValidator mustBeInSquare(Location origin, Vector leftDown, Vector rightTop)
    {
        throw new NotImplementedException("SIema");
    }
}
