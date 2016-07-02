package zeedin.cdgs.Elytras;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener{

	@Override
	public void onEnable() 
	{
		Bukkit.getServer().getLogger().info("[CDGS-Elytras] Successfully Enabled");
		Bukkit.getServer().getLogger().info("[CDGS-Elytras] By Zeedin");
		getServer().getPluginManager().registerEvents(this, this);
		registerConfig();
	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("Elytras")) {
			if ( sender instanceof Player) {
				if(args.length == 0){
					Player player = (Player) sender;
					List<String> Worlds = getConfig().getStringList("Disabled-Worlds");
					player.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD +"CDGS_Elytras"+ChatColor.DARK_GRAY+"]"+ChatColor.GRAY+ " Worlds with Elytras disabled "+Worlds );
					player.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD +"CDGS_Elytras"+ChatColor.DARK_GRAY+"]"+ChatColor.GRAY+ " Version: 1.0, By Zeedin ");
				}
			}
			if(args.length != 0){
				if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("CDGS-Elytras.Admin")) {
					reloadConfig();
					List<String> Worlds = getConfig().getStringList("Disabled-Worlds");
					sender.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD +"CDGS_Elytras"+ChatColor.DARK_GRAY+"]"+ChatColor.GRAY+ " Reloaded ");
					sender.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD +"CDGS_Elytras"+ChatColor.DARK_GRAY+"]"+ChatColor.GRAY+ " Worlds with Elytras disabled "+Worlds );
				}
				else sender.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD +"CDGS_Elytras"+ChatColor.DARK_GRAY+"]"+ChatColor.GRAY+ " You don't have permission for this command");{}
			}
		}
		return false;
	}
	@EventHandler
	public void onGlide(EntityToggleGlideEvent event) {
		Player player = (Player) event.getEntity();
		if(player.getGameMode() != GameMode.CREATIVE){
			if(player.isGliding()== false){
				List<String> Worlds = getConfig().getStringList("Disabled-Worlds");
				String PW = player.getWorld().getName();

				if ( Worlds.contains(PW) ){

					player.sendMessage(ChatColor.DARK_RED + "You have been shot down! "+ChatColor.GRAY+ ""+ ChatColor.ITALIC +"(Elytras disabled in this world)");
					ItemStack item = player.getInventory().getChestplate();
					player.getInventory().setChestplate(null);
					player.getInventory().addItem(item);
				}
			}
		}
	}
}

