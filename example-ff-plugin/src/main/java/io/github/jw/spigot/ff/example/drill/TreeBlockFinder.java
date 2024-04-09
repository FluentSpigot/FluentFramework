package io.github.jw.spigot.ff.example.drill;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeBlockFinder {


    public List<Block> findTreeBlocks(Location startLocation, int maxDistance) {
        World world = startLocation.getWorld();
        if (world == null) return new ArrayList<>();

        List<Block> treeBlocks = new ArrayList<>();
        BlockIterator iterator = new BlockIterator(world, startLocation.toVector(), new Vector(0, 1, 0), 0, maxDistance);

        while (iterator.hasNext()) {
            Block block = iterator.next();
            FluentLogger.LOGGER.info(block);
            if (isLogOrLeaves(block)) {
                treeBlocks.add(block);
            }
        }

        return treeBlocks;
    }

    public Set<Block> findConnectedTreeBlocks(Block startBlock) {
        checkAdjacentBlocks(startBlock);
        return foundBlocks;
    }

    private Set<Block> foundBlocks = new HashSet<>();

    private void checkAdjacentBlocks(Block block) {
        if (block == null ||
                foundBlocks.contains(block) ||
                (!Tag.LOGS.isTagged(block.getType()) && !Tag.LEAVES.isTagged(block.getType()))) {
            return;
        }

        foundBlocks.add(block);

        // Check all six adjacent blocks (up, down, north, south, east, west)
        checkAdjacentBlocks(block.getRelative(1, 0, 0)); // East
        checkAdjacentBlocks(block.getRelative(-1, 0, 0)); // West
        checkAdjacentBlocks(block.getRelative(0, 1, 0)); // Up
        checkAdjacentBlocks(block.getRelative(0, -1, 0)); // Down
        checkAdjacentBlocks(block.getRelative(0, 0, 1)); // South
        checkAdjacentBlocks(block.getRelative(0, 0, -1)); // North
    }

    private boolean isLogOrLeaves(Block block) {

        if (block == null) {
            return false;
        }
        var mat = block.getType();
        if (Tag.LOGS.isTagged(mat)) {
            return true;
        }
        if (Tag.LEAVES.isTagged(mat)) {
            return true;
        }
        return false;
    }
}
