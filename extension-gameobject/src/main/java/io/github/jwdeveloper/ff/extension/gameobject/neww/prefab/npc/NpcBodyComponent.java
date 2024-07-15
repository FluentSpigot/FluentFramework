package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.npc;


import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;


@Injection(lifeTime = LifeTime.TRANSIENT)
public class NpcBodyComponent extends GameComponent {

    public Map<String, Display> bodyParts;

    @Getter
    private ItemDisplay head;

    @Getter
    private BlockDisplay thors;

    @Getter
    private BlockDisplay leftHand;

    @Getter
    private BlockDisplay rightHand;

    @Getter
    private BlockDisplay leftLeg;

    @Getter
    private BlockDisplay rightLeg;


    @Override
    public void onInitialization(FluentApiSpigot api) {

        head = createHead();
        thors = createThors();
        leftLeg = createLeftLeg();
        rightLeg = createRightLeg();
        leftHand = createLeftHand();
        rightHand = createRightHand();

        bodyParts = new HashMap<>();
        bodyParts.put("head", head);
        bodyParts.put("thors", thors);
        bodyParts.put("left-hand", leftLeg);
        bodyParts.put("right-hand", rightLeg);
        bodyParts.put("left-leg", leftHand);
        bodyParts.put("right-leg", rightHand);

        for (var d : bodyParts.values()) {
            var res = TransformationUtility.create(d.getTransformation()).addTranslation(new Vector3f(-0.25f, 0, -0.10f));
            d.setTransformation(res.build());
        }
    }

    @Override
    public void onDestroy() {
        for(var body : bodyParts.values())
        {
            body.remove();
        }
    }

    @Override
    public void onRender() {
        var location = transform().position().clone().setY(transform().position().getY()).toLocation(transform().world());
        for (var part : bodyParts.values()) {
         //   part.teleport(location);
        }
    }

    private ItemDisplay createHead() {

        var head = (ItemDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.ITEM_DISPLAY);
        var itemStack = new ItemStack(Material.PLAYER_HEAD, 1);


        head.setItemStack(itemStack);


        var trans = TransformationUtility.create().setTranslation(0.25f, 1.9f, 0.10f).build();
        head.setTransformation(trans);

        head.setShadowRadius(0.5f);
        head.setShadowStrength(1f);
        return head;
    }

    private BlockDisplay createThors() {
        var thors = (BlockDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.BLOCK_DISPLAY);
        thors.setBlock(Material.ORANGE_GLAZED_TERRACOTTA.createBlockData());


        var trans = TransformationUtility.create()
                .setTranslation(0, 0.7f, 0)
                .setScale(0.5f, 0.7f, 0.25f)
                .build();

        thors.setTransformation(trans);

        return thors;
    }


    private BlockDisplay createLeftLeg() {
        var thors = (BlockDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.BLOCK_DISPLAY);
        thors.setBlock(Material.BLUE_SHULKER_BOX.createBlockData());


        var trans = TransformationUtility.create()
                .setScale(0.25f, 0.7f, 0.25f)
                .build();

        thors.setTransformation(trans);

        return thors;
    }

    private BlockDisplay createRightLeg() {
        var thors = (BlockDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.BLOCK_DISPLAY);
        thors.setBlock(Material.BLUE_SHULKER_BOX.createBlockData());
        var a = new Vector3f(0.25f, 0, 0);
        var b = new AxisAngle4f(0, 0, 0, 1);
        var c = new Vector3f(0.25f, 0.7f, 0.25f);
        var d = new AxisAngle4f(0, 0, 0, 1);
        var transfom = new Transformation(a, b, c, d);
        thors.setTransformation(transfom);
        return thors;
    }

    private BlockDisplay createLeftHand() {
        var thors = (BlockDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.BLOCK_DISPLAY);
        thors.setBlock(Material.TERRACOTTA.createBlockData());
        var a = new Vector3f(-0.25f, 0.7f, 0);
        var b = new AxisAngle4f(0, 0, 0, 1);
        var c = new Vector3f(0.25f, 0.7f, 0.25f);
        var d = new AxisAngle4f(0, 0, 0, 1);
        var transfom = new Transformation(a, b, c, d);
        thors.setTransformation(transfom);
        return thors;
    }

    private BlockDisplay createRightHand() {
        var thors = (BlockDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.BLOCK_DISPLAY);
        thors.setBlock(Material.TERRACOTTA.createBlockData());


        var result = TransformationUtility.create().setTranslation(0.50f, 1.4f, 0.25f).setScale(0.25f, 0.7f, 0.25f).build();
        thors.setTransformation(result);
        return thors;
    }


}
