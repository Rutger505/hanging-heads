package net.zlt.hangingheads.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.zlt.hangingheads.block.ModBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StandingAndWallBlockItem.class)
public abstract class StandingAndWallBlockItemMixin extends BlockItem {
    private StandingAndWallBlockItemMixin(Block block, Properties properties) {
        super(block, properties);
    }

    @Shadow
    @Final
    protected Block wallBlock;

    @Shadow
    protected abstract boolean canPlace(LevelReader level, BlockState state, BlockPos pos);

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void hangingHeads$getSkullPlacementState(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        Block standingBlock = getBlock();
        Block hangingBlock;
        if (standingBlock == Blocks.SKELETON_SKULL) {
            hangingBlock = ModBlocks.SKELETON_HANGING_SKULL;
        } else if (standingBlock == Blocks.CREEPER_HEAD) {
            hangingBlock = ModBlocks.CREEPER_HANGING_HEAD;
        } else if (standingBlock == Blocks.DRAGON_HEAD) {
            hangingBlock = ModBlocks.DRAGON_HANGING_HEAD;
        } else if (standingBlock == Blocks.ZOMBIE_HEAD) {
            hangingBlock = ModBlocks.ZOMBIE_HANGING_HEAD;
        } else if (standingBlock == Blocks.WITHER_SKELETON_SKULL) {
            hangingBlock = ModBlocks.WITHER_SKELETON_HANGING_SKULL;
        } else if (standingBlock == Blocks.PLAYER_HEAD) {
            hangingBlock = ModBlocks.PLAYER_HANGING_HEAD;
        } else if (standingBlock == Blocks.PIGLIN_HEAD) {
            hangingBlock = ModBlocks.PIGLIN_HANGING_HEAD;
        } else {
            return;
        }

        BlockState wallBlockState = wallBlock.getStateForPlacement(context);
        BlockState standingBlockState = null;
        LevelReader levelReader = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        for (Direction direction : context.getNearestLookingDirections()) {
            BlockState blockState = direction == Direction.DOWN ? getBlock().getStateForPlacement(context) : direction == Direction.UP ? hangingBlock.getStateForPlacement(context) : wallBlockState;
            if (blockState != null && canPlace(levelReader, blockState, blockPos)) {
                standingBlockState = blockState;
                break;
            }
        }

        cir.setReturnValue(standingBlockState != null && levelReader.isUnobstructed(standingBlockState, blockPos, CollisionContext.empty()) ? standingBlockState : null);
    }
}
