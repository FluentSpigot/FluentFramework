package io.github.jwdeveloper.ff.extension.bai.blocks.api.data.drop;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FluentBlockDrops {

    @Getter
    private List<FluentBlockDrop> drops = new ArrayList<>();

    public void addDrop(FluentBlockDrop drop) {
        drops.add(drop);
    }


    public List<ItemStack> calculateDrop(ItemStack tool, Entity entity) {
        var random = new Random();
        var result = new ArrayList<ItemStack>();
        for (var drop : drops) {
            var number = random.nextInt(100) / 100.0f;
            if (number > drop.getProbability()) {
                continue;
            }
            result.add(drop.getItemStack());
        }
        return result;
    }

}
