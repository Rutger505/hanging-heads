package net.zlt.hangingheads.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WitherSkeletonHangingSkullBlock extends HangingSkullBlock {
    public static final MapCodec<WitherSkeletonHangingSkullBlock> CODEC = simpleCodec(WitherSkeletonHangingSkullBlock::new);

    public WitherSkeletonHangingSkullBlock(Properties properties) {
        super(SkullBlock.Types.WITHER_SKELETON, properties);
    }

    @Override
    public MapCodec<WitherSkeletonHangingSkullBlock> codec() {
        return CODEC;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        WitherSkullBlock.checkSpawn(level, pos);
    }
}
