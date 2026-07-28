package net.zlt.hangingheads.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zlt.hangingheads.HangingHeads;

public final class ModBlocks {
    private ModBlocks() {}

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(HangingHeads.ID);

    public static final DeferredBlock<HangingSkullBlock> SKELETON_HANGING_SKULL = BLOCKS.register(
        "skeleton_hanging_skull",
        () -> new HangingSkullBlock(
            SkullBlock.Types.SKELETON,
            BlockBehaviour.Properties.of()
                .strength(1.0f)
                .lootFrom(() -> Blocks.SKELETON_SKULL)
                .pushReaction(PushReaction.DESTROY)
        )
    );

    public static final DeferredBlock<WitherSkeletonHangingSkullBlock> WITHER_SKELETON_HANGING_SKULL = BLOCKS.register(
        "wither_skeleton_hanging_skull",
        () -> new WitherSkeletonHangingSkullBlock(
            BlockBehaviour.Properties.of()
                .strength(1.0f)
                .lootFrom(() -> Blocks.WITHER_SKELETON_SKULL)
                .pushReaction(PushReaction.DESTROY)
        )
    );

    public static final DeferredBlock<HangingSkullBlock> ZOMBIE_HANGING_HEAD = BLOCKS.register(
        "zombie_hanging_head",
        () -> new HangingSkullBlock(
            SkullBlock.Types.ZOMBIE,
            BlockBehaviour.Properties.of()
                .strength(1.0f)
                .lootFrom(() -> Blocks.ZOMBIE_HEAD)
                .pushReaction(PushReaction.DESTROY)
        )
    );

    public static final DeferredBlock<HangingSkullBlock> PLAYER_HANGING_HEAD = BLOCKS.register(
        "player_hanging_head",
        () -> new HangingSkullBlock(
            SkullBlock.Types.PLAYER,
            BlockBehaviour.Properties.of()
                .strength(1.0f)
                .lootFrom(() -> Blocks.PLAYER_HEAD)
                .pushReaction(PushReaction.DESTROY)
        )
    );

    public static final DeferredBlock<HangingSkullBlock> CREEPER_HANGING_HEAD = BLOCKS.register(
        "creeper_hanging_head",
        () -> new HangingSkullBlock(
            SkullBlock.Types.CREEPER,
            BlockBehaviour.Properties.of()
                .strength(1.0f)
                .lootFrom(() -> Blocks.CREEPER_HEAD)
                .pushReaction(PushReaction.DESTROY)
        )
    );

    public static final DeferredBlock<HangingSkullBlock> DRAGON_HANGING_HEAD = BLOCKS.register(
        "dragon_hanging_head",
        () -> new HangingSkullBlock(
            SkullBlock.Types.DRAGON,
            BlockBehaviour.Properties.of()
                .strength(1.0f)
                .lootFrom(() -> Blocks.DRAGON_HEAD)
                .pushReaction(PushReaction.DESTROY)
        )
    );

    public static final DeferredBlock<HangingSkullBlock> PIGLIN_HANGING_HEAD = BLOCKS.register(
        "piglin_hanging_head",
        () -> new HangingSkullBlock(
            SkullBlock.Types.PIGLIN,
            BlockBehaviour.Properties.of()
                .strength(1.0f)
                .lootFrom(() -> Blocks.PIGLIN_HEAD)
                .pushReaction(PushReaction.DESTROY)
        )
    );

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        modEventBus.addListener(ModBlocks::commonSetup);
        modEventBus.addListener(ModBlocks::onBlockEntityTypeAddBlocks);
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Item.BY_BLOCK.put(SKELETON_HANGING_SKULL.get(), Items.SKELETON_SKULL);
            Item.BY_BLOCK.put(CREEPER_HANGING_HEAD.get(), Items.CREEPER_HEAD);
            Item.BY_BLOCK.put(DRAGON_HANGING_HEAD.get(), Items.DRAGON_HEAD);
            Item.BY_BLOCK.put(ZOMBIE_HANGING_HEAD.get(), Items.ZOMBIE_HEAD);
            Item.BY_BLOCK.put(WITHER_SKELETON_HANGING_SKULL.get(), Items.WITHER_SKELETON_SKULL);
            Item.BY_BLOCK.put(PLAYER_HANGING_HEAD.get(), Items.PLAYER_HEAD);
            Item.BY_BLOCK.put(PIGLIN_HANGING_HEAD.get(), Items.PIGLIN_HEAD);
        });
    }

    public static void onBlockEntityTypeAddBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SKULL, SKELETON_HANGING_SKULL.get());
        event.modify(BlockEntityType.SKULL, CREEPER_HANGING_HEAD.get());
        event.modify(BlockEntityType.SKULL, DRAGON_HANGING_HEAD.get());
        event.modify(BlockEntityType.SKULL, ZOMBIE_HANGING_HEAD.get());
        event.modify(BlockEntityType.SKULL, WITHER_SKELETON_HANGING_SKULL.get());
        event.modify(BlockEntityType.SKULL, PLAYER_HANGING_HEAD.get());
        event.modify(BlockEntityType.SKULL, PIGLIN_HANGING_HEAD.get());
    }
}
