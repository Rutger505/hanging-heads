package net.zlt.hangingheads;

import org.bukkit.block.Block;

import java.util.UUID;

public record BlockKey(UUID world, int x, int y, int z) {
    public static BlockKey of(Block block) {
        return new BlockKey(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
    }

    public int[] toArray() {
        return new int[] {x, y, z};
    }

    public static BlockKey fromArray(UUID world, int[] position) {
        return new BlockKey(world, position[0], position[1], position[2]);
    }
}
