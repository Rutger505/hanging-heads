package net.zlt.hangingheads.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.zlt.hangingheads.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractSkullBlock.class)
public class AbstractSkullBlockMixin {
    @ModifyExpressionValue(method = "getTicker", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z", ordinal = 1))
    private boolean hangingHeads$isDragonHead(boolean isDragonWallHead, @Local(argsOnly = true) BlockState state) {
        return isDragonWallHead || state.is(ModBlocks.DRAGON_HANGING_HEAD);
    }

    @ModifyExpressionValue(method = "getTicker", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z", ordinal = 3))
    private boolean hangingHeads$isPiglinHead(boolean isPiglinWallHead, @Local(argsOnly = true) BlockState state) {
        return isPiglinWallHead || state.is(ModBlocks.PIGLIN_HANGING_HEAD);
    }
}
