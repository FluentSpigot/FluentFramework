package io.github.jwdeveloper.ff.core.validator.implementation.entity;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.validator.api.ValidatorFactory;
import io.github.jwdeveloper.ff.core.validator.implementation.ValidatorBase;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EntityValidator<ENTITY extends Entity, SubClass extends EntityValidator> extends ValidatorBase<ENTITY, SubClass> {
    private final ValidatorFactory validatorFactory;

    public EntityValidator(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    public SubClass mustBeAtLocation(Location location) {
        return mustComplyRule(entity -> validatorFactory
                .location()
                .mustBe(location)
                .validate(entity.getLocation())
                .cast(entity));

    }

    public SubClass mustBeAtLocationRange(Location location, int range) {
        return mustComplyRule(entity -> validatorFactory
                .location()
                .mustBeInRange(location, range)
                .validate(entity.getLocation())
                .cast(entity));
    }

    public SubClass mustBeInSquare(Location origin, Vector leftDown, Vector rightTop) {
        return mustComplyRule(entity -> validatorFactory
                .location()
                .mustBeInSquare(origin, leftDown, rightTop)
                .validate(entity.getLocation())
                .cast(entity));
    }

    public SubClass mustBeAtBlock(Block block) {
        return mustComplyRule(entity -> validatorFactory
                .block()
                .mustBe(block)
                .validate(entity.getLocation().getBlock())
                .cast(entity));
    }

    public SubClass mustHasPassenger(Entity passenger) {
        return mustComplyRule(e -> e.getPassengers().contains(passenger), "has not passenger " + passenger.getName());
    }

    public SubClass mustHasPassengerCount(int count) {
        return mustComplyRule(e -> e.getPassengers().size() == count, "has different passenger count");
    }


}
