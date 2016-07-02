package zeedin.cdgs.CDGSQuests;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobEvents implements Listener{

	main plugin;

	@EventHandler
	public void MobKilled(EntityDeathEvent e) {
		
		Entity deadEnt = e.getEntity();
		Entity killer = e.getEntity().getKiller();
		
		if (killer instanceof Player && deadEnt instanceof Entity) {
						
			String player =  e.getEntity().getKiller().getName();
			UUID playerUUID = Bukkit.getServer().getPlayer(player).getUniqueId();
	
			File Data = new File("plugins/CDGS_Quests/data/"+ playerUUID +"-Qdata.yml");
			
			if (Data.exists()) {
				FileConfiguration EData = YamlConfiguration.loadConfiguration(Data);
												
				if (EData.contains("CheckFor."+deadEnt.getName())) {	
					String path = "Kills."+deadEnt.getName();  
					EData.set(path, EData.getInt(path) +1);
					try {
						EData.save(Data);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					//killer.sendMessage(ChatColor.GOLD + "Data saved!"); //Debug remove when done
					//killer.sendMessage(ChatColor.DARK_AQUA + "Mob Killed saved to: " + ChatColor.GRAY + Data + " as " + EData.getInt(path)); //Debug remove when done
				}
				
			}		
		}
	}
}

