package me.benfah.timber;

import me.benfah.timber.config.Config;
import net.fabricmc.api.ModInitializer;

public class TimberMod implements ModInitializer {
	
	public static String MOD_ID = "timber";
	
	@Override
	public void onInitialize() {
		Config.load();
		initOptions();
		Config.save();
	}
	
	public void initOptions()
	{
		Config.getBoolean("options", "itemDamagePerBlock", true);
		Config.getBoolean("options", "slowerChopping", true);
	}
	
}
