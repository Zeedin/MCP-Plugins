package zeedin.cdgs.CDGSQuests;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class QuestClaim {

	main plugin;

	public QuestClaim(main plugin) { this.plugin = plugin; }

	public void exeClaimCMD(Player player, String[] args)
	{
		String Quest = args[1];
		String Splayer =  player.getName();
		UUID playerUUID = Bukkit.getServer().getPlayer(Splayer).getUniqueId();
		File Data = new File("plugins/CDGS_Quests/data/"+ playerUUID +"-Qdata.yml");
		File QData = new File("plugins/CDGS_Quests/"+ Quest +".yml");

		if (Data.exists()) {
			if (QData.exists()) {

				FileConfiguration QEData = YamlConfiguration.loadConfiguration(QData);
				FileConfiguration PData = YamlConfiguration.loadConfiguration(Data);

				player.sendMessage(ChatColor.DARK_GRAY+" ------------------["+ChatColor.DARK_PURPLE+" Quest Progress "+ChatColor.DARK_GRAY+"]------------------" );
				player.sendMessage(ChatColor.DARK_PURPLE+""+ ChatColor.ITALIC +"Quest: " + ChatColor.GRAY + QEData.getString("Name"));
				player.sendMessage(ChatColor.DARK_PURPLE+""+ ChatColor.ITALIC +"Info: " + ChatColor.GRAY + QEData.getString("Description"));
				player.sendMessage(" ");

				int Questcount = 0;
				int QuestDone = 0;
					
				for(String key : QEData.getConfigurationSection("Requirements").getKeys(false)) {

					for(String subkey : QEData.getConfigurationSection("Requirements." + key).getKeys(false)) {
						Questcount = Questcount +1;	

						if (PData.getInt(key + "." + subkey) >= (QEData.getInt("Requirements."+ key +"." + subkey)) ){
							QuestDone = QuestDone +1;
						} 					
					}
				}

				if(Questcount <= QuestDone){
					player.sendMessage(ChatColor.DARK_PURPLE +"All Quest requirements completed!");
					
					// Removes the Requirements amount from the players data
					for(String key : QEData.getConfigurationSection("Requirements").getKeys(false)) {

						for(String subkey : QEData.getConfigurationSection("Requirements." + key).getKeys(false)) {
							

							if (PData.getInt(key + "." + subkey) >= (QEData.getInt("Requirements."+ key +"." + subkey)) ){
								PData.set((key + "." + subkey), PData.getInt(key + "." + subkey) - QEData.getInt("Requirements."+ key +"." + subkey));
								try {
									PData.save(Data);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							} 					
						}
					}
					// Starts the Rewards spawning process 
					player.sendMessage(ChatColor.GRAY +"Rewards:");

					for(String key : QEData.getConfigurationSection("Rewards").getKeys(false)) {
						for(String subkey : QEData.getConfigurationSection("Rewards." + key).getKeys(false)) {
							
							String item = subkey.toUpperCase();
							int Amount = QEData.getInt("Rewards."+key+"."+subkey+".Amount");
							ItemStack IGive = new ItemStack(org.bukkit.Material.getMaterial(item), Amount);
							PlayerInventory inventory = player.getInventory();
							inventory.addItem(IGive);
							player.sendMessage(ChatColor.GRAY +" - "+subkey+" ("+ Amount+")");
						}
					}

				}
				else {
					player.sendMessage(ChatColor.GRAY + String.valueOf(QuestDone)+ " / " + String.valueOf(Questcount)+ " Quest requirements completed");
					player.sendMessage(ChatColor.GRAY+"" + ChatColor.ITALIC + "Do '/quest status "+Quest+"' to see your progress");}
			}

			else {player.sendMessage(ChatColor.DARK_RED+"No Quest by that name");}	

		}
		else {player.sendMessage("Sorry Your don't have any quests. do /quest start to start a quest");}			
	}
}
