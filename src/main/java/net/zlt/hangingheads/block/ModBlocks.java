package net.zlt.hangingheads.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
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

    public static final DeferredBlock<HangingSkullBlock> SKELETON_HANGING_SKULL = BLOCKS.registerBlock(
        "skeleton_hanging_skull",
        properties -> new HangingSkullBlock(SkullBlock.Types.SKELETON, properties),
        () -> hangingVariant(Blocks.SKELETON_SKULL)
    );

    public static final DeferredBlock<WitherSkeletonHangingSkullBlock> WITHER_SKELETON_HANGING_SKULL = BLOCKS.registerBlock(
        "wither_skeleton_hanging_skull",
        WitherSkeletonHangingSkullBlock::new,
        () -> hangingVariant(Blocks.WITHER_SKELETON_SKULL)
    );

    public static final DeferredBlock<HangingSkullBlock> ZOMBIE_HANGING_HEAD = BLOCKS.registerBlock(
        "zombie_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.ZOMBIE, properties),
        () -> hangingVariant(Blocks.ZOMBIE_HEAD)
    );

    public static final DeferredBlock<HangingSkullBlock> PLAYER_HANGING_HEAD = BLOCKS.registerBlock(
        "player_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.PLAYER, properties),
        () -> hangingVariant(Blocks.PLAYER_HEAD)
    );

    public static final DeferredBlock<HangingSkullBlock> CREEPER_HANGING_HEAD = BLOCKS.registerBlock(
        "creeper_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.CREEPER, properties),
        () -> hangingVariant(Blocks.CREEPER_HEAD)
    );

    public static final DeferredBlock<HangingSkullBlock> DRAGON_HANGING_HEAD = BLOCKS.registerBlock(
        "dragon_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.DRAGON, properties),
        () -> hangingVariant(Blocks.DRAGON_HEAD)
    );

    public static final DeferredBlock<HangingSkullBlock> PIGLIN_HANGING_HEAD = BLOCKS.registerBlock(
        "piglin_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.PIGLIN, properties),
        () -> hangingVariant(Blocks.PIGLIN_HEAD)
    );

    private static BlockBehaviour.Properties hangingVariant(Block standingBlock) {
        return BlockBehaviour.Properties.of()
            .strength(1.0f)
            .overrideLootTable(standingBlock.getLootTable())
            .overrideDescription(standingBlock.getDescriptionId())
            .pushReaction(PushReaction.DESTROY);
    }

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
        event.modify(
            BlockEntityTypes.SKULL,
            SKELETON_HANGING_SKULL.get(),
            CREEPER_HANGING_HEAD.get(),
            DRAGON_HANGING_HEAD.get(),
            ZOMBIE_HANGING_HEAD.get(),
            WITHER_SKELETON_HANGING_SKULL.get(),
            PLAYER_HANGING_HEAD.get(),
            PIGLIN_HANGING_HEAD.get()
        );
    }
}
