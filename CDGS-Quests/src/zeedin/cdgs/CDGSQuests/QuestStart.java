package zeedin.cdgs.CDGSQuests;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class QuestStart {
	main plugin;

	public QuestStart(main plugin) { this.plugin = plugin; }

	public void exeClaimCMD(Player player, String[] args)
	{
		String CMDQuest = args[1];
		String Splayer =  player.getName();
		UUID playerUUID = Bukkit.getServer().getPlayer(Splayer).getUniqueId();
		File QuestFile = new File("plugins/CDGS_Quests/"+ CMDQuest +".yml");
		File PlayerQuestFile = new File("plugins/CDGS_Quests/data/"+ playerUUID +"-Qdata.yml");
		File PlayerFile = new File("plugins/CDGS_Quests/players/"+ playerUUID +"-player.yml");

		if (!PlayerFile.exists()) {
			try {
				PlayerFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				player.sendMessage(ChatColor.DARK_RED + "ERROR could not create player quest info, go yell at zeedin");
			}
		} 
		if (!PlayerQuestFile.exists()) {
			try {
				PlayerQuestFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				player.sendMessage(ChatColor.DARK_RED + "ERROR could not create player quest data, go yell at zeedin");
			}
		} 
		FileConfiguration PQData = YamlConfiguration.loadConfiguration(PlayerQuestFile);
		FileConfiguration PFile = YamlConfiguration.loadConfiguration(PlayerFile);
		FileConfiguration Quest = YamlConfiguration.loadConfiguration(QuestFile);

		if (!PFile.contains("Quests-Open")) {
			PFile.set("Quests-Open.Open", 0);
			try {
				PFile.save(PlayerFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (QuestFile.exists()) {
			for(String key : Quest.getConfigurationSection("Requirements").getKeys(false)) {
				for(String subkey : Quest.getConfigurationSection("Requirements." + key).getKeys(false)) {
					if (!PQData.contains(key+"."+subkey)){
						if(PQData.contains("CheckFor."+subkey)){
							PQData.set("CheckFor."+subkey, PQData.getInt("CheckFor."+subkey) +1);
						}
						else{PQData.set("CheckFor."+subkey, 1);}
						PQData.set(key+"."+subkey, 0);
						try {
							PQData.save(PlayerQuestFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}


			player.sendMessage(ChatColor.DARK_RED+"Found Quest: "+CMDQuest);}
		else {player.sendMessage(ChatColor.DARK_RED+"Error could not fine the Quest: "+CMDQuest);}
	}
}