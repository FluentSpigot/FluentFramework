package io.github.jwdeveloper.ff.core.validator.implementation.item;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.validator.api.ValidatorFactory;
import io.github.jwdeveloper.ff.core.validator.implementation.ValidatorBase;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;


public class ItemStackValidator extends ValidatorBase<ItemStack, ItemStackValidator>
{
    private final ValidatorFactory factory;

    public ItemStackValidator(ValidatorFactory factory)
    {
        this.factory = factory;
    }

    public ItemStackValidator mustHasItemMeta(Consumer<ItemMetaValidator> validator)
    {
        var metaValidator = factory.itemMeta();
        validator.accept(metaValidator);
        return mustComplyRule(target ->
        {
            var result = metaValidator.validate(target.getItemMeta());
            return result.isSuccess()? ActionResult.success() :  ActionResult.failed(target, result.getMessage());
        });
    }
    public ItemStackValidator mustHasAmount(int amount)
    {
        return mustComplyRule(e -> e.getAmount() == amount, "Different Amount");
    }

    public ItemStackValidator mustHasMaterial(Material material)
    {
        return mustComplyRule(e -> e.getType() == material, "Different Material");
    }

    public ItemStackValidator mustHasTranslationKey(String translationKey)
    {
        return mustComplyRule(e -> e.getTranslationKey().equals(translationKey), "Different Translation key");
    }

    public ItemStackValidator mustHasMaterialType(MaterialType materialType)
    {
        return mustComplyRule(e ->
        {
            var type= e.getType();
            return switch (materialType)
            {
                case AIR -> type.isAir();
                case ITEM -> type.isItem();
                case BLOCK -> type.isBlock();
                case BURNABLE -> type.isBurnable();
                case EDITABLE -> type.isEdible();
                case GRAWITABLE -> type.hasGravity();
            };
        }, "Different Material type");
    }
}
