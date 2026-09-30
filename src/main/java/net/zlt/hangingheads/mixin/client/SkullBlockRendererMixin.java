package net.zlt.hangingheads.mixin.client;

import com.mojang.math.Transformation;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.blockentity.state.SkullBlockRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.zlt.hangingheads.block.HangingSkullBlock;
import net.zlt.hangingheads.block.ModBlocks;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/SkullBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/SkullBlockRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", at = @At("TAIL"))
    private void hangingHeads$raiseHangingSkull(SkullBlockEntity blockEntity, SkullBlockRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress, CallbackInfo ci) {
        Block block = blockEntity.getBlockState().getBlock();
        if (!(block instanceof HangingSkullBlock)) {
            return;
        }

        float y = block == ModBlocks.DRAGON_HANGING_HEAD ? 0.25f : 0.5f;
        state.transformation = new Transformation(new Matrix4f().translation(0.0f, y, 0.0f).mul(state.transformation.getMatrix()));
    }
}
