package net.zlt.hangingheads;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.GameMode;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class HangingHeadListener implements Listener {
    private final HangingHeads heads;

    public HangingHeadListener(HangingHeads heads) {
        this.heads = heads;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Block clicked = event.getClickedBlock();
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK
            || event.getBlockFace() != BlockFace.DOWN
            || clicked == null
            || item == null
            || !Tag.ITEMS_SKULLS.isTagged(item.getType())
            || event.useItemInHand() == Event.Result.DENY
            || player.getGameMode() == GameMode.ADVENTURE
            || clicked.getType().isInteractable() && !player.isSneaking()) {
            return;
        }

        Block target = clicked.getRelative(BlockFace.DOWN);
        if (!target.isEmpty()) {
            return;
        }

        event.setCancelled(true);
        if (heads.isOccupied(target)) {
            return;
        }

        EquipmentSlot hand = event.getHand();
        BlockPlaceEvent placeEvent = new BlockPlaceEvent(target, target.getState(), clicked, item, player, true, hand);
        if (!placeEvent.callEvent() || !placeEvent.canBuild()) {
            return;
        }

        heads.place(target, item, player.getYaw());
        player.swingHand(hand);
        if (player.getGameMode() != GameMode.CREATIVE) {
            ItemStack inHand = player.getInventory().getItem(hand);
            inHand.subtract();
            player.getInventory().setItem(hand, inHand);
        }
    }

    // Paper fires this pre-cancelled for interaction entities, so cancelled events must be handled too.
    @EventHandler
    public void onBreak(PrePlayerAttackEntityEvent event) {
        if (!(event.getAttacked() instanceof Interaction hitbox)) {
            return;
        }
        BlockKey key = heads.keyOf(hitbox);
        Player player = event.getPlayer();
        if (key == null || player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        event.setCancelled(true);
        Block block = hitbox.getLocation().getBlock();
        BlockBreakEvent breakEvent = new BlockBreakEvent(block, player);
        if (!breakEvent.callEvent()) {
            return;
        }
        heads.remove(key, breakEvent.isDropItems() && player.getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockPlacedIntoHead(BlockPlaceEvent event) {
        if (event instanceof BlockMultiPlaceEvent multiPlace) {
            for (BlockState state : multiPlace.getReplacedBlockStates()) {
                if (heads.isOccupied(state.getBlock())) {
                    event.setCancelled(true);
                    return;
                }
            }
        } else if (heads.isOccupied(event.getBlockPlaced())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBucketEmptiedIntoHead(PlayerBucketEmptyEvent event) {
        if (heads.isOccupied(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onFluidFlowIntoHead(BlockFromToEvent event) {
        if (heads.isOccupied(event.getToBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        breakPushedInto(event.getBlock(), event.getBlocks(), event.getDirection());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPistonRetract(BlockPistonRetractEvent event) {
        breakPushedInto(null, event.getBlocks(), event.getDirection());
    }

    private void breakPushedInto(Block piston, List<Block> moved, BlockFace direction) {
        if (piston != null) {
            breakIfOccupied(piston.getRelative(direction));
        }
        for (Block block : moved) {
            breakIfOccupied(block.getRelative(direction));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFallingBlockLand(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock && !event.getTo().isAir()) {
            breakIfOccupied(event.getBlock());
        }
    }

    private void breakIfOccupied(Block block) {
        if (heads.isOccupied(block)) {
            heads.remove(BlockKey.of(block), true);
        }
    }

    @EventHandler
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        heads.track(event.getEntities());
    }

    @EventHandler
    public void onEntitiesUnload(EntitiesUnloadEvent event) {
        heads.untrack(event.getEntities());
    }
}
