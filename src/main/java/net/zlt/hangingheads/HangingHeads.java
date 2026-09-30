package net.zlt.hangingheads;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class HangingHeads {
    private final NamespacedKey positionKey;
    private final Map<BlockKey, UUID> displays = new HashMap<>();
    private final Map<BlockKey, UUID> hitboxes = new HashMap<>();

    public HangingHeads(Plugin plugin) {
        this.positionKey = new NamespacedKey(plugin, "position");
    }

    public boolean isOccupied(Block block) {
        return displays.containsKey(BlockKey.of(block));
    }

    public BlockKey keyOf(Entity entity) {
        int[] position = entity.getPersistentDataContainer().get(positionKey, PersistentDataType.INTEGER_ARRAY);
        return position == null ? null : BlockKey.fromArray(entity.getWorld().getUID(), position);
    }

    public void track(Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            BlockKey key = keyOf(entity);
            if (key == null) {
                continue;
            }
            if (entity instanceof ItemDisplay) {
                displays.put(key, entity.getUniqueId());
            } else if (entity instanceof Interaction) {
                hitboxes.put(key, entity.getUniqueId());
            }
        }
    }

    public void untrack(Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            BlockKey key = keyOf(entity);
            if (key != null) {
                displays.remove(key, entity.getUniqueId());
                hitboxes.remove(key, entity.getUniqueId());
            }
        }
    }

    public void place(Block block, ItemStack item, float playerYaw) {
        BlockKey key = BlockKey.of(block);
        World world = block.getWorld();
        Location center = block.getLocation().add(0.5, 0.5, 0.5);
        center.setYaw(Math.floorMod(Math.round(playerYaw / 22.5f), 16) * 22.5f);
        // Dragon heads are modelled lower than other skulls, so they need less lift to touch the ceiling.
        float lift = item.getType() == Material.DRAGON_HEAD ? 0.25f : 0.5f;

        ItemDisplay display = world.spawn(center, ItemDisplay.class, entity -> {
            entity.setItemStack(item.asOne());
            entity.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.NONE);
            entity.setTransformation(new Transformation(new Vector3f(0, lift, 0), new AxisAngle4f(), new Vector3f(1, 1, 1), new AxisAngle4f()));
            entity.getPersistentDataContainer().set(positionKey, PersistentDataType.INTEGER_ARRAY, key.toArray());
        });
        Interaction hitbox = world.spawn(center, Interaction.class, entity -> {
            entity.setInteractionWidth(item.getType() == Material.PIGLIN_HEAD ? 0.625f : 0.5f);
            entity.setInteractionHeight(0.5f);
            entity.getPersistentDataContainer().set(positionKey, PersistentDataType.INTEGER_ARRAY, key.toArray());
        });

        displays.put(key, display.getUniqueId());
        hitboxes.put(key, hitbox.getUniqueId());
        world.playSound(center, item.getType().createBlockData().getSoundGroup().getPlaceSound(), 1.0f, 0.8f);
    }

    public void remove(BlockKey key, boolean drop) {
        UUID displayId = displays.remove(key);
        UUID hitboxId = hitboxes.remove(key);
        if (hitboxId != null && Bukkit.getEntity(hitboxId) instanceof Interaction hitbox) {
            hitbox.remove();
        }
        if (displayId == null || !(Bukkit.getEntity(displayId) instanceof ItemDisplay display)) {
            return;
        }

        ItemStack item = display.getItemStack();
        Location center = display.getLocation();
        display.remove();
        if (item.isEmpty()) {
            return;
        }

        BlockData blockData = item.getType().createBlockData();
        center.getWorld().spawnParticle(Particle.BLOCK, center, 16, 0.15, 0.15, 0.15, blockData);
        center.getWorld().playSound(center, blockData.getSoundGroup().getBreakSound(), 1.0f, 0.8f);
        if (drop) {
            center.getWorld().dropItemNaturally(center, item);
        }
    }
}
