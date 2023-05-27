package io.github.jwdeveloper.ff.core.validator.implementation.player;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.validator.api.ValidationRule;
import io.github.jwdeveloper.ff.core.validator.implementation.item.ItemStackValidator;
import org.bukkit.entity.Player;

public class ItemOwnerRule  implements ValidationRule<Player> {

    private final ItemStackValidator itemStackValidator;

    public ItemOwnerRule(ItemStackValidator itemStackValidator) {
        this.itemStackValidator = itemStackValidator;
    }

    @Override
    public ActionResult<Player> validate(Player target) {
        for (var item : target.getInventory().getContents()) {
            var result = itemStackValidator.validate(item);
            if (result.isFailed()) {
                continue;
            }
            return ActionResult.success(target);
        }
        return ActionResult.failed(target, "Item not found in player inventory");
    }
}
