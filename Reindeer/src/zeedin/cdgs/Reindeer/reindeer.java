package zeedin.cdgs.Reindeer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.plugin.Plugin;


public class reindeer extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() 
	{
		Bukkit.getServer().getLogger().info(ChatColor.AQUA + "reindeer Successfully Enabled");
		getServer().getPluginManager().registerEvents(this, this);
		registerConfig();
		
	}
	
	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onDisable() { Bukkit.getServer().getLogger().info(ChatColor.AQUA + "This kills the server"); }

	
//Commands 
	
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("reindeer")) {
        	if ( sender instanceof Player) {
            sender.sendMessage(ChatColor.DARK_AQUA + "reindeer Version 1.1 -By Zeedin");
            sender.sendMessage(ChatColor.GRAY +  "X Power " + this.getConfig().getInt("X-Power"));
            sender.sendMessage(ChatColor.GRAY + "Y Power " + this.getConfig().getInt("Y-Power"));
            sender.sendMessage(ChatColor.GRAY + "Z Power " + this.getConfig().getInt("Z-Power"));
            sender.sendMessage(ChatColor.GRAY + "Region name " + this.getConfig().getString("Region"));
            }
        }
        
      if (label.equalsIgnoreCase("reindeer-set")) {   
    	if ( sender instanceof Player) {
    		
    		if(args[0].equalsIgnoreCase("x")) {
    		        		      
        		int num = Integer.parseInt(args[1]);
    			
    			this.getConfig();
    			this.getConfig().set("X-Power", num);
    			this.saveConfig();
        
        sender.sendMessage("X Power updated! It is now " + this.getConfig().getInt("X-Power"));
        
        }
    		
    		if(args[0].equalsIgnoreCase("y")) {
	            
        		int num = Integer.parseInt(args[1]);
  
    			this.getConfig();
    			this.getConfig().set("Y-Power", num);
    			this.saveConfig();
                
                sender.sendMessage("Y Power updated! It is now " + this.getConfig().getInt("Y-Power"));
                
                }
    		
    		if(args[0].equalsIgnoreCase("z")) {
    			
        		int num = Integer.parseInt(args[1]);
	            
    			this.getConfig();
    			this.getConfig().set("Z-Power", num);
    			this.saveConfig();
        
        sender.sendMessage("Z Power updated! It is now " + this.getConfig().getInt("Z-Power"));
        
        }
    		
    		if(args[0].equalsIgnoreCase("region")) {
	            
    			this.getConfig();
    			this.getConfig().set("Region", args[1]);
    			this.saveConfig();
                
                sender.sendMessage("Region updated! It is now " + this.getConfig().getString("Region"));
                
                }
    	}
    	
    	
    }
        
        
        return false;
    }
    
    
    
//End of commands
    
    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
     
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
     
        return (WorldGuardPlugin) plugin;
    }
    

    @EventHandler (priority=EventPriority.HIGHEST)
    public void onHorse(PlayerInteractEvent e)
{
        Player player = (Player) e.getPlayer();
        WorldGuardPlugin worldGuard = getWorldGuard();
        Location loc = player.getLocation();
             
        if (player.isInsideVehicle()) {
            if (player.getVehicle().getType() == EntityType.HORSE) {
                Horse horse = (Horse) player.getVehicle();
                
                ProtectedRegion region = worldGuard.getRegionManager(player.getWorld()).getRegion(getConfig().getString("Region"));
                com.sk89q.worldedit.Vector v = new com.sk89q.worldedit.Vector(loc.getX(), loc.getY(), loc.getZ());
                
                if (region.contains(v) || player.hasPermission("Reindeer.Override")) {
                	
                    if (player.hasPermission("Reindeer.Ride")) /* specifically for mit */ {
                    	
                int xvelocity = (getConfig().getInt("X-Power") + 1);
                int yvelocity = getConfig().getInt("Y-Power");
                int zvelocity = (getConfig().getInt("Z-Power") + 1);
                
                Vector look = player.getLocation().getDirection().normalize();
            	                             	
            	/* Player X Vector*/ double xvector = (look.getX() * xvelocity)/3 ; 
            	
            	/* Player z Vector*/ double zvector = (look.getZ() * zvelocity)/3 ;
            	
            	/* Player Y Vector double yvector = look.getZ() * 2 ;*/
            	         	

            	horse.setVelocity(new Vector(
            			/*x*/ xvector, 
            			/*y*/ yvelocity, 
            			/*z*/ zvector));
            	
                }
                    
                    else 
                    	player.sendMessage(ChatColor.RED+"You don't get Christmas :(");
            }
                       
        }
   }
	
}
      

@EventHandler (priority=EventPriority.HIGHEST)
public void onhurt(EntityDamageEvent e) {
	
    WorldGuardPlugin worldGuard = getWorldGuard();
	
    if(e.getEntityType().equals(EntityType.HORSE)){        	

        Location loc = e.getEntity().getLocation();

   
    ProtectedRegion region = worldGuard.getRegionManager(e.getEntity().getWorld()).getRegion(getConfig().getString("Region"));
    com.sk89q.worldedit.Vector v = new com.sk89q.worldedit.Vector(loc.getX(), loc.getY(), loc.getZ());
    if (region.contains(v)) {
    
            e.setDamage(0.0);
            e.setCancelled(true);
        }
        
    }
  }
}


