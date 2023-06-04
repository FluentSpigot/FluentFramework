package io.github.jwdeveloper.ff.core.validator;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import io.github.jwdeveloper.ff.core.validator.implementation.item.MaterialType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemStackValidatorTests
{
    private static ItemStack itemStack;

    @BeforeEach
    public void before() {
       MockBukkit.mock();
        itemStack = new ItemStack(Material.DIAMOND,69);
    }

    @AfterEach
    public void after() {
        MockBukkit.unmock();
    }


    @Test
    public void ShouldValidatePlayer() {
        //Arrange
        //Act
        var result = FluentValidatorApi
                .validators()
                .itemStack()
                .mustBe(itemStack)
                .validate(itemStack);

        //Assert
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.hasContent());
        Assertions.assertFalse(result.hasMessage());
        Assertions.assertEquals(itemStack, result.getContent());
    }

    @Test
    public void ShouldHasAmount() {
        //Arrange
        //Act
        var result = FluentValidatorApi
                .validators()
                .itemStack()
                .mustHasAmount(69)
                .validate(itemStack);

        //Assert
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.hasContent());
        Assertions.assertFalse(result.hasMessage());
        Assertions.assertEquals(itemStack, result.getContent());
    }

    @Test
    public void ShouldHasMaterialType() {
        //Arrange
        //Act
        var result = FluentValidatorApi
                .validators()
                .itemStack()
                .mustHasMaterialType(MaterialType.ITEM)
                .validate(itemStack);

        //Assert
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.hasContent());
        Assertions.assertFalse(result.hasMessage());
        Assertions.assertEquals(itemStack, result.getContent());
    }

}
