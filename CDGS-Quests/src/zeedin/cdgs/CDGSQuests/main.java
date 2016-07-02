package zeedin.cdgs.CDGSQuests;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin {

	public static main plugin;


	@Override
	public void onEnable() 
	{
		plugin = this;
		registerConfig();

		File userfiles;
		try {
			userfiles = new File(getDataFolder() + File.separator + "players");
			if(!userfiles.exists()){
				userfiles.mkdirs();
			}
		} catch(SecurityException e) {
			// do something...
			return;
		}
		File userdata;
		try {
			userdata = new File(getDataFolder() + File.separator + "data");
			if(!userdata.exists()){
				userdata.mkdirs();
			}
		} catch(SecurityException e) {
			// do something...
			return;
		}
		
		// Register Multiple Classes
		

		getCommand("quest").setExecutor(new Commands(this));
		registerEvents(this, new MobEvents());

		// System Critical End
		Bukkit.getServer().getLogger().info("[CDGS-Quests] Successfully Enabled");
	}
	@Override
	public void onDisable() { 
		plugin = null;//To stop memory leeks
		Bukkit.getServer().getLogger().info("[CDGS-Quests] Successfully Disabled"); 

	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	// System Critical End

	// Debug items
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("quest-test")) {
			if ( sender instanceof Player) {
				sender.sendMessage(ChatColor.DARK_AQUA + "CDGS Quests Version 1.0 -By Zeedin");

			}
			return true;
		}
		return false;
	}
	// End of Debug 



	//Much eaisier then registering events in 10 diffirent methods
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	//To access the plugin variable from other classes
	public static Plugin getPlugin() {
		return plugin;
	}
} 



