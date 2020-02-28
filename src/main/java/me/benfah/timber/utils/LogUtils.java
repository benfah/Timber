package me.benfah.timber.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LogUtils {
	
	public static Map<BlockPos, BlockPosList> TREE_MAP = new HashMap<>();
	
	public static boolean getLogPositions(BlockPos pos, List<BlockPos> list, World world, Block block,
			boolean putIntoTreeMap) {
		if (putIntoTreeMap) {
			BlockPosList oldList = TREE_MAP.get(pos);
			if (oldList != null && oldList.getLastModified() >= world.getTime() + 20) {
				list.addAll(oldList);
				return oldList.hasLeaves();
			} else
				TREE_MAP.remove(pos);
		}
		
		int minX = pos.getX() - 1;
		int minZ = pos.getZ() - 1;
		int minY = pos.getY();

		int maxX = pos.getX() + 1;
		int maxZ = pos.getZ() + 1;
		int maxY = pos.getY() + 1;
		
		boolean hasLeaves = false;
		
		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				for (int y = minY; y <= maxY; y++) {
					
					BlockPos newPos = new BlockPos(x, y, z);
					
					if(areLeaves(world.getBlockState(newPos).getBlock()) && !hasLeaves)
					hasLeaves = true;
					
					if (world.getBlockState(newPos).getBlock().equals(block) && !list.contains(newPos)) {
						list.add(newPos);
						
						boolean hasBranchLeaves = getLogPositions(newPos, list, world, block, false);
						if(!hasLeaves)
						hasLeaves = hasBranchLeaves;
					}
				}
			}
		}
			
		
		if(putIntoTreeMap)
		TREE_MAP.put(pos, new BlockPosList(list, world.getTime(), hasLeaves));
		return hasLeaves;

	}
	
	public static boolean isLog(Block b)
	{
		return BlockTags.LOGS.contains(b) || Blocks.WARPED_STEM.equals(b) || Blocks.CRIMSON_STEM.equals(b) || Blocks.SHROOMLIGHT.equals(b);
	}
	
	public static boolean areLeaves(Block b)
	{
		return BlockTags.LEAVES.contains(b) || BlockTags.WART_BLOCKS.contains(b);
	}
	
}
