package me.benfah.timber.mixin;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;

import me.benfah.timber.config.Config;
import me.benfah.timber.utils.LogUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(AxeItem.class)
public class AxeItemMixin extends MiningToolItem {
	
	
	protected AxeItemMixin() { super(0, 0, null, null, null); }

	
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		
		System.out.println("hi");
		if (BlockTags.LOGS.contains(state.getBlock()) && !miner.isSneaking()) {
			List<BlockPos> logPositions = new ArrayList<>();
			boolean hasLeaves = LogUtils.getLogPositions(pos, logPositions, world, state.getBlock(), true);
			int maxLogs = Math.min(stack.getMaxDamage() - stack.getDamage(), logPositions.size());
			
			if(hasLeaves)
			for (int i = 0; i < maxLogs; i++) {
				BlockPos logPos = logPositions.get(i);
				world.breakBlock(logPos, true);
			}
			if(!Config.getBoolean("options", "itemDamagePerBlock", true) || !hasLeaves)
			maxLogs = 0;
				
			stack.damage(maxLogs + 1, miner, (player) -> player.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
			return true;
		}

		return super.postMine(stack, world, state, pos, miner);
	}
	
	
	
	
	
}
