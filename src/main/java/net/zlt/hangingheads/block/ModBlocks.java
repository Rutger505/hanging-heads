package net.zlt.hangingheads.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.zlt.hangingheads.HangingHeads;

import java.util.function.Function;

public final class ModBlocks {
    private ModBlocks() {}

    public static final HangingSkullBlock SKELETON_HANGING_SKULL = register(
        "skeleton_hanging_skull",
        properties -> new HangingSkullBlock(SkullBlock.Types.SKELETON, properties),
        Blocks.SKELETON_SKULL
    );

    public static final WitherSkeletonHangingSkullBlock WITHER_SKELETON_HANGING_SKULL = register(
        "wither_skeleton_hanging_skull",
        WitherSkeletonHangingSkullBlock::new,
        Blocks.WITHER_SKELETON_SKULL
    );

    public static final HangingSkullBlock ZOMBIE_HANGING_HEAD = register(
        "zombie_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.ZOMBIE, properties),
        Blocks.ZOMBIE_HEAD
    );

    public static final HangingSkullBlock PLAYER_HANGING_HEAD = register(
        "player_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.PLAYER, properties),
        Blocks.PLAYER_HEAD
    );

    public static final HangingSkullBlock CREEPER_HANGING_HEAD = register(
        "creeper_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.CREEPER, properties),
        Blocks.CREEPER_HEAD
    );

    public static final HangingSkullBlock DRAGON_HANGING_HEAD = register(
        "dragon_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.DRAGON, properties),
        Blocks.DRAGON_HEAD
    );

    public static final HangingSkullBlock PIGLIN_HANGING_HEAD = register(
        "piglin_hanging_head",
        properties -> new HangingSkullBlock(SkullBlock.Types.PIGLIN, properties),
        Blocks.PIGLIN_HEAD
    );

    private static <B extends Block> B register(String name, Function<BlockBehaviour.Properties, B> factory, Block standingBlock) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(HangingHeads.ID, name));
        B block = factory.apply(hangingVariant(standingBlock).setId(key));
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    private static BlockBehaviour.Properties hangingVariant(Block standingBlock) {
        return BlockBehaviour.Properties.of()
            .strength(1.0f)
            .overrideLootTable(standingBlock.getLootTable())
            .overrideDescription(standingBlock.getDescriptionId())
            .pushReaction(PushReaction.DESTROY);
    }

    public static void register() {
        Item.BY_BLOCK.put(SKELETON_HANGING_SKULL, Items.SKELETON_SKULL);
        Item.BY_BLOCK.put(CREEPER_HANGING_HEAD, Items.CREEPER_HEAD);
        Item.BY_BLOCK.put(DRAGON_HANGING_HEAD, Items.DRAGON_HEAD);
        Item.BY_BLOCK.put(ZOMBIE_HANGING_HEAD, Items.ZOMBIE_HEAD);
        Item.BY_BLOCK.put(WITHER_SKELETON_HANGING_SKULL, Items.WITHER_SKELETON_SKULL);
        Item.BY_BLOCK.put(PLAYER_HANGING_HEAD, Items.PLAYER_HEAD);
        Item.BY_BLOCK.put(PIGLIN_HANGING_HEAD, Items.PIGLIN_HEAD);

        BlockEntityTypes.SKULL.addValidBlock(SKELETON_HANGING_SKULL);
        BlockEntityTypes.SKULL.addValidBlock(CREEPER_HANGING_HEAD);
        BlockEntityTypes.SKULL.addValidBlock(DRAGON_HANGING_HEAD);
        BlockEntityTypes.SKULL.addValidBlock(ZOMBIE_HANGING_HEAD);
        BlockEntityTypes.SKULL.addValidBlock(WITHER_SKELETON_HANGING_SKULL);
        BlockEntityTypes.SKULL.addValidBlock(PLAYER_HANGING_HEAD);
        BlockEntityTypes.SKULL.addValidBlock(PIGLIN_HANGING_HEAD);
    }
}
