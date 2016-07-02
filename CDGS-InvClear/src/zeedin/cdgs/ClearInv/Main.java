package zeedin.cdgs.ClearInv;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener{

	@Override
	public void onEnable() 
	{
		Bukkit.getServer().getLogger().info("[CDGS-ClearInv] Successfully Enabled");
		Bukkit.getServer().getLogger().info("[CDGS-ClearInv] By Zeedin");
		getServer().getPluginManager().registerEvents(this, this);

	}

	@Override
	public void onDisable() { Bukkit.getServer().getLogger().info(ChatColor.AQUA + "This kills the server"); }


	//Commands 

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("clearall")) {
			if ( sender instanceof Player) {
				Player player = (Player) sender;

				if(args.length == 0){

					Inventory inv = player.getInventory();
					inv.clear();

				}
			}
			if(args.length != 0){
				if(Bukkit.getServer().getPlayer(args[0]) != null) {

					Player player = this.getServer().getPlayer(args[0]);
					Inventory inv = player.getInventory();
					inv.clear();
				}
			}

		}
		return false;


	}

}

