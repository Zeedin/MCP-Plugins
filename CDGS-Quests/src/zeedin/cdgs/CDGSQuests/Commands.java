package zeedin.cdgs.CDGSQuests;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands  implements CommandExecutor {

	QuestClaim QClaim;
	QuestStatus QStatus;
	QuestStart QStart;
	main plugin;


	public Commands(main passedPlugin) {

		
		QClaim = new QuestClaim(passedPlugin);
		QStatus = new QuestStatus(passedPlugin);
		QStart = new QuestStart(passedPlugin);
		this.plugin = passedPlugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ( sender instanceof Player) 
		{
			Player DPlayer = (Player) sender;

			if(args[0].equalsIgnoreCase("status")) {QStatus.exeClaimCMD(DPlayer, args); } //Opens New Class
			if(args[0].equalsIgnoreCase("claim")) {QClaim.exeClaimCMD(DPlayer, args); } //Opens New Class
			if(args[0].equalsIgnoreCase("start")) {QStart.exeClaimCMD(DPlayer, args); } //Opens New Class
		}
		return false;
	}
}
