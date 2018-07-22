package acctorni.otterplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import acctorni.otterplugin.command.DiscordCommand;
import acctorni.otterplugin.command.DynlinkCommand;
import acctorni.otterplugin.command.OGMSCommand;
import acctorni.otterplugin.command.OGMSPCommand;
import acctorni.otterplugin.command.OtterCommandManager;
import acctorni.otterplugin.command.ReloadCommand;
import acctorni.otterplugin.command.WorldEditCommand;

public class OtterPlugin extends JavaPlugin implements Listener {

	public static String disclink, dynlink = "";
	public static String discmessage, dynmessage = "";
	public static String prefix = "";
	public static boolean hideMe = false;
	public static List<String> permsToGrant;
	
	private ArrayList<Player> onlinePlayers = new ArrayList<Player>();
	private ArrayList<String> disallowedCommands = new ArrayList<String>();
	
	private ArrayList<Player> godPlayers = new ArrayList<>();
	private ArrayList<Player> playersToRemove = new ArrayList<>();
	
	
	static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

	@Override
	public void onEnable() {
		getLogger().log(Level.INFO, "[OtterPlugin] Enabling OtterPlugin");
		getServer().getPluginManager().registerEvents(this, this);
		OtterCommandManager.cleanupCommands();
		godPlayers.clear();
		
		// Registering all the commands to the Command Manager
		OtterCommandManager.addCommand(new DiscordCommand());
		OtterCommandManager.addCommand(new DynlinkCommand());
		OtterCommandManager.addCommand(new OGMSCommand());
		OtterCommandManager.addCommand(new OGMSPCommand());
		OtterCommandManager.addCommand(new ReloadCommand());
		OtterCommandManager.addCommand(new WorldEditCommand());
		
		disallowedCommands.add("pl");
		disallowedCommands.add("minecraft:msg");
		disallowedCommands.add("minecraft:w");
		disallowedCommands.add("minecraft:tell");
		
		this.saveDefaultConfig();
		
		// Gather all the configuration settings from the config file
		OtterPlugin.disclink = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("discord_link")) + "§r";
		OtterPlugin.discmessage = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("discord_message"));
		OtterPlugin.dynlink = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("dynmap_link")) + "§r";
		OtterPlugin.dynmessage = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("dynmap_message"));
		OtterPlugin.prefix = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("prefix")) + "§r";
		OtterPlugin.hideMe = this.getConfig().getBoolean("hiding_player");
		
		OtterPlugin.permsToGrant = this.getConfig().getStringList("granted_permissions");
		
		if (OtterPlugin.permsToGrant == null) {
			this.getLogger().log(Level.SEVERE, "Granted permissions in configuration file are invalid. Using empty list.");
			OtterPlugin.permsToGrant = new ArrayList<>();
		}
		
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			onlinePlayers.add(p);
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				
				playersToRemove.clear();
				
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					
					if (ess.getUser(p) != null && ess.getUser(p).isAfk() && !(godPlayers.contains(p))) {
						
						if (p.hasPermission("otterplugin.godafk")) {
							
							godPlayers.add(p);
							
							p.setInvulnerable(true);
		
							
						}
						
					}
					
					
				}
				
				for (Player p : godPlayers) {
					
					if (ess.getUser(p) != null && !(ess.getUser(p).isAfk())) {
						
						playersToRemove.add(p);
						p.setInvulnerable(false);
						
					}
					
				}
				
				for (Player p : playersToRemove) {
					
					godPlayers.remove(p);
					
				}
				
			}
		}, 60L, 60L);

	}

	@Override
	public void onDisable() {
		getLogger().log(Level.INFO, "[OtterPlugin] Disabling OtterPlugin");
		
		for (Player p : godPlayers) {
			
			if (p != null && p.isInvulnerable()) {
				
				p.setInvulnerable(false);
				
			}
			
		}
		
		godPlayers.clear();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		boolean response = OtterCommandManager.checkCommand(command.getName(), sender, args, this);
		return response;

	}
	
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
		
		Player ply = e.getPlayer();
		String msg = e.getMessage();
		msg = msg.split(" ")[0];
		
		// Anti-SocialSpy Bypass
		if (!ply.isOp()) {
			
			for (String s : disallowedCommands) {
				s = "/" + s;
				if (s.equalsIgnoreCase(msg)) {
					Util.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.", ply);
					e.setCancelled(true);
					return;
				}
			}
			
		}
		
	}

	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onPlayerJoin(PlayerJoinEvent e) {
		onlinePlayers.add(e.getPlayer());
		
		
		if (OtterPlugin.hideMe) {
			if (e.getPlayer().getUniqueId().equals(Util.uuid)) {
				e.setJoinMessage(null);
				e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "You've been auto-vanished.");
				Util.log(String.format(ChatColor.DARK_PURPLE + "Hiding (%s/%s)'s join message.", e.getPlayer().getUniqueId().toString(), e.getPlayer().getDisplayName()));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		
		try {
			
			Player p = (Player) e.getEntity();
			
			for (Player pl : godPlayers) {
				
				if (p == pl) {
					
					e.setCancelled(true);
					
				}
				
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		ArrayList<Player> playersToRemove = new ArrayList<Player>();
		for (Player p : onlinePlayers) {
			if (p.getUniqueId() == e.getPlayer().getUniqueId()) {
				playersToRemove.add(p);
			}
		}
		
		playersToRemove.clear();
		
		for (Player p : godPlayers) {
			
			if (e.getPlayer() == p) {
				
				p.setInvulnerable(false);
				playersToRemove.add(p);
				
			}
			
		}
		
		for (Player p : playersToRemove) {
			
			godPlayers.remove(p);
			
		}
		
		for (Player p : playersToRemove) {
			onlinePlayers.remove(p);
		}
		
		if (OtterPlugin.hideMe) {
			if (e.getPlayer().getUniqueId().equals(Util.uuid)) {
				e.setQuitMessage(null);
				Util.log(String.format(ChatColor.DARK_PURPLE + "Hiding (%s/%s)'s quit message.", e.getPlayer().getUniqueId().toString(), e.getPlayer().getDisplayName()));
			}
		}
			
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onHealthIncrease(EntityRegainHealthEvent e) {
		
		if (e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			if (godPlayers.contains(p)) {
				
				e.setCancelled(true);
				
			}
			
		}
		
	}
	
	public ArrayList<Player> getOnlinePlayers() {
		return onlinePlayers;
	}
	
	
}
