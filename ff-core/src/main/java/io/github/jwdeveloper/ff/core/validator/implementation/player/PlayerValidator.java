package io.github.jwdeveloper.ff.core.validator.implementation.player;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.validator.api.FluentValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.entity.EntityValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.item.ItemStackValidator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PlayerValidator extends EntityValidator<Player, PlayerValidator> {
    private final FluentValidator factory;

    public PlayerValidator(FluentValidator factory) {
        super(factory);
        this.factory = factory;
    }

    public PlayerValidator mustHasPlayedFirstTime() {
        return mustComplyRule(e -> !e.hasPlayedBefore(), "Player is not Flying");
    }
    public PlayerValidator mustHasPlayedBefore() {
        return mustComplyRule(Player::hasPlayedBefore, "Player is not Flying");
    }
    public PlayerValidator mustBeFlying() {
        return mustComplyRule(Player::isFlying, "Player is not Flying");
    }
    public PlayerValidator mustBeSleeping() {
        return mustComplyRule(Player::isSleeping, "Player is not Sleeping");
    }
    public PlayerValidator mustBeBanned() {
        return mustComplyRule(Player::isBanned, "Player is not banned");
    }

    public PlayerValidator mustNotBeBanned() {
        return mustComplyRule(e -> !e.isBanned(), "Player is banned");
    }

    public PlayerValidator mustBeOnline() {
        return mustComplyRule(Player::isOnline, "Player is not Online");
    }

    public PlayerValidator mustNotBeOnline() {
        return mustComplyRule(e -> !e.isOnline(), "Player is  Online");
    }
    public PlayerValidator mustBeOp() {
        return mustComplyRule(Player::isOp, "Player is not Op");
    }

    public PlayerValidator mustHasName(String name) {
        return mustComplyRule(e -> e.getName().equals(name), "Player name is different then "+name);
    }

    public PlayerValidator mustNotHasName(String name) {
        return mustComplyRule(e -> !e.getName().equals(name), "Player name should not be equal to"+name);
    }

    public PlayerValidator mustHasPermission(String... permissions) {
        return mustComplyRule(new PermissionRule(Arrays.stream(permissions).toList()));
    }

    public PlayerValidator mustHasPermission(List<String> permissions)
    {
        return mustComplyRule(new PermissionRule(permissions));
    }

    public PlayerValidator mustHasItemInRightHand(Consumer<ItemStackValidator> consumer) {
        var itemValidator = factory.itemStack();
        consumer.accept(itemValidator);
        return mustComplyRule(player -> ActionResult.cast(itemValidator.validate(player.getInventory().getItemInMainHand()), player));
    }

    public PlayerValidator mustHasItemInRightHand(ItemStack itemStack) {
        var itemValidator = factory.itemStack().mustBe(itemStack);
        return mustComplyRule(player -> ActionResult.cast(itemValidator.validate(player.getInventory().getItemInMainHand()), player));
    }

    public PlayerValidator mustHasItemInLeftHand(Consumer<ItemStackValidator> consumer) {
        var itemValidator = factory.itemStack();
        consumer.accept(itemValidator);
        return mustComplyRule(player -> ActionResult.cast(itemValidator.validate(player.getInventory().getItemInOffHand()), player));
    }

    public PlayerValidator mustHasItemInLeftHand(ItemStack itemStack) {
        var itemValidator = factory.itemStack().mustBe(itemStack);
        return mustComplyRule(player -> ActionResult.cast(itemValidator.validate(player.getInventory().getItemInOffHand()), player));
    }

    public PlayerValidator mustHasItem(Consumer<ItemStackValidator> consumer) {
        var itemValidator = factory.itemStack();
        consumer.accept(itemValidator);
        return mustComplyRule(new ItemOwnerRule(itemValidator));
    }

    public PlayerValidator mustHasItem(ItemStack itemStack) {
        var itemValidator = factory.itemStack().mustBe(itemStack);
        return mustComplyRule(new ItemOwnerRule(itemValidator));

    }

}
