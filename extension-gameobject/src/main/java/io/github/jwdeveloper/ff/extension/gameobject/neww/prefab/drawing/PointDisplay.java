package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing;


import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class PointDisplay extends GameComponent {
    private BlockDisplay blockDisplay;

    private TextDisplay textDisplay;

    @Getter
    @Setter
    private float size = 1;

    @Getter
    @Setter
    private Color color = Color.WHITE;


    public BlockDisplay getBlockDisplay() {
        if (blockDisplay != null) {
            return blockDisplay;
        }
        blockDisplay = (BlockDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.BLOCK_DISPLAY);
        blockDisplay.setGlowing(true);
        blockDisplay.setViewRange(size * 2);
        blockDisplay.setInterpolationDuration(5);
        blockDisplay.setInterpolationDelay(1);
        blockDisplay.setShadowStrength(0);

        blockDisplay.setBrightness(new Display.Brightness(15, 15));


        setColor(color);
        return blockDisplay;
    }

    public void setColor(Color color) {
        var material = LineDisplayFactory.getColorMaterial(color);
        var entity = getBlockDisplay();
        entity.setGlowColorOverride(color);
        entity.setBlock(material.createBlockData());
    }


    public void setWidth(float size) {
        transform().scale(size, size, size);

        //  transform().translation(-size/2, -size/2, -size/2);
    }


    @Override
    public void onRender() {
        var entity = getBlockDisplay();

        var worldPosition = worldTransform();
        entity.teleport(worldPosition.toLocation());
        entity.setTransformation(worldPosition.toBukkitTransformation());

    }

    @Override
    public void onDestroy() {
        getBlockDisplay().remove();
        textDisplay.remove();
    }

}
