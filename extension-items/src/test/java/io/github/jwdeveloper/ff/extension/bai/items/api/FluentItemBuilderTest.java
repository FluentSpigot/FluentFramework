package io.github.jwdeveloper.ff.extension.bai.items.api;

import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryViewMock;
import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.extension.bai.BlocksAndItemsFramework;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

public class FluentItemBuilderTest extends FluentApiTest {
    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {
        fluentApiBuilder.useExtension(BlocksAndItemsFramework.use(fluentItemApiSettings ->
        {

        }));
    }

    @Test
    public void setupFakeItems() throws InvalidConfigurationException {
        var content = loadResourceAsStream(getClass(), "config.yml");
        var fluentItemsApi = getContainer().findInjection(FluentItemApi.class);


        var config = new YamlConfiguration();
        config.loadFromString(content);

        fluentItemsApi.addItems(config)
                .register()
                .forEach(fluentItem ->
                {
                    fluentItem.toItemStack();
                    getFluentApiMock().logger().info(fluentItem.getSchema().toString());
                });

        var backpack = getContainer().findInjection(FluentItemApi.class).findFluentItem("backpack").get();
        // TestCustomItemCrafting(backpack.toItemStack());
        TestBackPackCrafting();
    }


    private void TestCustomItemCrafting(ItemStack backpack) {
        runCraftEvent((matrix) ->
        {
            matrix[0] = null;
            matrix[1] = new ItemStack(Material.SLIME_BALL);
            matrix[2] = null;

            matrix[3] = new ItemStack(Material.SLIME_BALL);
            matrix[4] = backpack;
            matrix[5] = new ItemStack(Material.SLIME_BALL);

            matrix[6] = null;
            matrix[7] = new ItemStack(Material.SLIME_BALL);
            matrix[8] = null;
        });
    }

    private void TestBackPackCrafting() {
        runCraftEvent((matrix) ->
        {
            matrix[0] = null;
            matrix[1] = new ItemStack(Material.LEATHER);
            matrix[2] = null;

            matrix[3] = new ItemStack(Material.LEATHER);
            matrix[4] = new ItemStack(Material.STRING);
            matrix[5] = new ItemStack(Material.LEATHER);

            matrix[6] = null;
            matrix[7] = new ItemStack(Material.LEATHER);
            matrix[8] = null;
        });
    }

    public void runCraftEvent(Consumer<ItemStack[]> giveCrafting) {
        var crafting = new CraftingInventoryMock(getPlayer(), 9, InventoryType.CRAFTING);
        var matrix = new ItemStack[9];
        giveCrafting.accept(matrix);
        crafting.setMatrix(matrix);
        invokeEvent(new PrepareItemCraftEvent(crafting, new PlayerInventoryViewMock(getPlayer(), crafting), false));
    }


    public class CraftingInventoryMock extends InventoryMock implements CraftingInventory {

        private ItemStack[] matrix;

        public CraftingInventoryMock(@Nullable InventoryHolder holder, int size, @NotNull InventoryType type) {
            super(holder, size, type);
        }

        @Nullable
        @Override
        public ItemStack getResult() {
            return null;
        }

        @NotNull
        @Override
        public ItemStack[] getMatrix() {
            return matrix;
        }

        @Override
        public void setResult(@Nullable ItemStack newResult) {

        }

        @Override
        public void setMatrix(@NotNull ItemStack[] contents) {
            this.matrix = contents;
        }

        @Nullable
        @Override
        public Recipe getRecipe() {
            return null;
        }
    }


}