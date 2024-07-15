package immersive.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImmersiveBlockInstance
{
    private Block block;

    private Location location;

    private Inventory inventory;

    public ItemDisplay addDisplay(String identifier)
    {
        return null;
    }

    public ItemDisplay findDisplay(String identifer)
    {
        return null;
    }

    public List<ItemDisplay> getDisplays()
    {
        return List.of();
    }
}
