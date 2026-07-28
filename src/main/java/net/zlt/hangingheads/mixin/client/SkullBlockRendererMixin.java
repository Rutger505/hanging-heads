package net.zlt.hangingheads.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.zlt.hangingheads.block.HangingSkullBlock;
import net.zlt.hangingheads.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin {
    @Unique
    @Nullable
    private static Block hangingHeads$block = null;

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/SkullBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;renderSkull(Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/SkullModelBase;Lnet/minecraft/client/renderer/RenderType;)V"))
    private void hangingHeads$grabSkullBlock(SkullBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, CallbackInfo ci) {
        hangingHeads$block = blockEntity.getBlockState().getBlock();
    }

    @ModifyArg(method = "renderSkull", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0), index = 1)
    private static float hangingHeads$renderSkull(float y) {
        if (hangingHeads$block == ModBlocks.DRAGON_HANGING_HEAD.get()) {
            y = 0.25f;
        } else if (hangingHeads$block instanceof HangingSkullBlock) {
            y = 0.5f;
        }
        hangingHeads$block = null;
        return y;
    }
}
