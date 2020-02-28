package me.benfah.timber.utils;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.util.math.BlockPos;

public class BlockPosList extends ArrayList<BlockPos>
{
	
	private final long lastModified;
	private final boolean hasLeaves;
	public BlockPosList(Collection<? extends BlockPos> c, long lastModified, boolean hasLeaves) {
		super(c);
		this.lastModified = lastModified;
		this.hasLeaves = hasLeaves;
	}

	public long getLastModified() {
		return lastModified;
	}

	public boolean hasLeaves() {
		return hasLeaves;
	}
	
	
	
	
}
