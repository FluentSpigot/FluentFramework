package io.github.jwdeveloper.ff.core.validator;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerValidatorTests {

    private static Player player;
    private static ServerMock server;

    @BeforeEach
    public void before() {
        server = MockBukkit.mock();
        player = server.addPlayer("Mike");
    }

    @AfterEach
    public void after() {
        MockBukkit.unmock();
    }

    @Test
    public void ShouldValidatePlayer() {
        //Arrange
        //Act
        var result = FluentValidator
                .validators()
                .player()
                .mustHasName("Mike")
                .validate(player);


        //Assert
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.hasContent());
        Assertions.assertFalse(result.hasMessage());
        Assertions.assertEquals(player, result.getContent());
    }

    @Test
    public void ShouldValidatePlayerPermissions()
    {
        //Arrange
        var plugin = MockBukkit.createMockPlugin();
        var attachment = player.addAttachment(plugin);
        attachment.setPermission("hello.world", true);

        //Act
        var result = FluentValidator
                .validators()
                .player()
                .mustHasPermission("hello.world")
                .validate(player);

        //Assert
        Assertions.assertTrue(result.isSuccess());
    }


    @Test
    public void ShouldValidatePlayerItem()
    {
        //Arrange
        var item = new ItemStack(Material.DIAMOND, 2);
        player.getInventory().setItem(5, item);

        //Act
        var result = FluentValidator
                .validators()
                .player()
                .mustHasItem(item)
                .validate(player);

        //Assert
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    public void ShouldHasItemInLeftHand()
    {
        //Arrange
        var item = new ItemStack(Material.DIAMOND, 2);
        player.getInventory().setItemInOffHand(item);

        //Act
        var result = FluentValidator
                .validators()
                .player()
                .mustHasItemInLeftHand(item)
                .validate(player);

        //Assert
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    public void ShouldHasItemInRightHand()
    {
        //Arrange
        var item = new ItemStack(Material.DIAMOND, 2);
        player.getInventory().setItemInMainHand(item);

        //Act
        var result = FluentValidator
                .validators()
                .player()
                .mustHasItemInRightHand(item)
                .validate(player);

        //Assert
        Assertions.assertTrue(result.isSuccess());
    }



}