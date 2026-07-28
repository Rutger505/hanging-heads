package net.zlt.hangingheads.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.zlt.hangingheads.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(WitherSkullBlock.class)
public abstract class WitherSkullBlockMixin {
    @ModifyExpressionValue(method = "checkSpawn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/SkullBlockEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 1))
    private static boolean hangingHeads$checkWitherSpawn(boolean isWitherSkeletonWallSkull, @Local BlockState blockState) {
        return isWitherSkeletonWallSkull || blockState.is(ModBlocks.WITHER_SKELETON_HANGING_SKULL);
    }

    @ModifyArg(method = "getOrCreateWitherFull", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/pattern/BlockInWorld;hasState(Ljava/util/function/Predicate;)Ljava/util/function/Predicate;"))
    private static Predicate<BlockState> hangingHeads$getOrCreateWitherFull(Predicate<BlockState> state) {
        return state.or(BlockStatePredicate.forBlock(ModBlocks.WITHER_SKELETON_HANGING_SKULL.get()));
    }
}
