package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing;


import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class ItemPointDisplay extends GameComponent {
    private ItemDisplay itemDisplay;

    @Getter
    @Setter
    private float size = 1;

    @Getter
    @Setter
    private Color color = Color.WHITE;
    
    private ItemStack itemStack;
    private Material material;

    private float refreshTime = 0.1f;

    @Override
    public void onEnable() {
        material = Material.DIRT;
        itemStack =  new ItemStack(material,1);
    }

    public ItemDisplay getItemDisplay() {
        if (itemDisplay != null) {
            return itemDisplay;
        }


        itemDisplay = (ItemDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.ITEM_DISPLAY);
        itemDisplay.setGlowing(true);
        itemDisplay.setViewRange(size * 2);
        itemDisplay.setInterpolationDuration(20);
        itemDisplay.setInterpolationDelay(0);
        itemDisplay.setShadowStrength(0);

        itemDisplay.setBrightness(new Display.Brightness(15, 15));
        itemDisplay.setItemStack(new ItemStack(Material.DIAMOND_BLOCK,1));

        setRefreshRate(refreshTime);
        return itemDisplay;
    }

    @Override
    public void onUpdateAsync(double deltaTime) {
        
        if(!gameobject().meta().isVisible())
        {
            itemStack.setType(Material.AIR);
        }
        else
        {
            itemStack.setType(material);
        }
    }

    public void setWidth(float size) {
        transform().scale(size, size, size);
    }

    public void setRefreshRate(float time)
    {
        if(itemDisplay != null)
        {
            int rtime = (int)(time*20);
            itemDisplay.setInterpolationDuration(rtime);
        }
        this.refreshTime = time+0.1f;
    }


    @Override
    public void onRender()
    {
        waitForSeconds(refreshTime,"");
        var entity = getItemDisplay();
        var worldPosition = worldTransform();
        entity.teleport(worldPosition.toLocation());
        entity.setTransformation(worldPosition.toBukkitTransformation());

    }

    @Override
    public void onDestroy() {
        getItemDisplay().remove();
    }

}
