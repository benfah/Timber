package me.benfah.timber.mixin;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.benfah.timber.config.Config;
import me.benfah.timber.utils.LogUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

	@Inject(method = "calcBlockBreakingDelta", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
	public void onCalcBlock(BlockState state, PlayerEntity player, BlockView world, BlockPos pos,
							CallbackInfoReturnable<Float> info) {
		float f = state.getHardness(world, pos);
		int i = player.isUsingEffectiveTool(state) ? 30 : 100;

		float logModifier = 1.0F;

		if (LogUtils.isLog(state.getBlock()) && !player.isSneaking()
				&& Config.getBoolean("options", "slowerChopping", true)
				&& player.getMainHandStack().getItem().getMiningSpeedMultiplier(player.getMainHandStack(), state) > 1F) {

			ArrayList<BlockPos> list = new ArrayList<>();
			boolean hasLeaves = LogUtils.getLogPositions(pos, list, player.world, state.getBlock(), true);

			if (hasLeaves)
				logModifier = Math.min(list.size(), 40);

		}
		info.setReturnValue(player.getBlockBreakingSpeed(state) / f / (float) i / logModifier);
	}

}