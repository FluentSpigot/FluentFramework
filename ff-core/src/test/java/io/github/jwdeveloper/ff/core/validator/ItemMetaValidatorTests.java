package io.github.jwdeveloper.ff.core.validator;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ItemMetaValidatorTests
{
    private static ItemMeta itemMeta;

    @BeforeEach
    public void before() {
        MockBukkit.mock();
        var itemStack = new ItemStack(Material.DIAMOND,69);
        itemMeta = itemStack.getItemMeta();
    }

    @AfterEach
    public void after() {
        MockBukkit.unmock();
    }

    @Test
    public void ShouldValidate() {
        //Arrange
        //Act
        var result = FluentValidator
                .validators()
                .itemMeta()
                .mustBe(itemMeta)
                .validate(itemMeta);

        //Assert
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.hasContent());
        Assertions.assertFalse(result.hasMessage());
        Assertions.assertEquals(itemMeta, result.getContent());
    }


    @Test
    public void ShouldValidateCustomDataModel() {
        //Arrange
        itemMeta.setCustomModelData(2);

        //Act
        var result = FluentValidator
                .validators()
                .itemMeta()
                .mustHasCustomModelData(2)
                .validate(itemMeta);

        //Assert
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.hasContent());
        Assertions.assertFalse(result.hasMessage());
        Assertions.assertEquals(itemMeta, result.getContent());
    }

    @Test
    public void ShouldValidateName() {
        //Arrange
        itemMeta.setDisplayName("Custom Item");

        //Act
        var result = FluentValidator
                .validators()
                .itemMeta()
                .mustHasDisplayedName("Custom Item")
                .validate(itemMeta);

        //Assert
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.hasContent());
        Assertions.assertFalse(result.hasMessage());
        Assertions.assertEquals(itemMeta, result.getContent());
    }

    @Test
    public void ShouldValidateLore() {
        //Arrange
        itemMeta.setLore(List.of("Stats","Attack: 1"));

        //Act
        var result = FluentValidator
                .validators()
                .itemMeta()
                .mustHasLore("Stats","Attack: 1")
                .validate(itemMeta);

        //Assert
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.hasContent());
        Assertions.assertFalse(result.hasMessage());
        Assertions.assertEquals(itemMeta, result.getContent());
    }
}
