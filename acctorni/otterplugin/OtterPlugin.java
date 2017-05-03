package acctorni.otterplugin;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import acctorni.otterplugin.command.DiscordCommand;
import acctorni.otterplugin.command.DynlinkCommand;
import acctorni.otterplugin.command.OGMSCommand;
import acctorni.otterplugin.command.OGMSPCommand;
import acctorni.otterplugin.command.OtterCommandManager;
import acctorni.otterplugin.command.ReloadCommand;
import acctorni.otterplugin.command.VanishCommand;

public class OtterPlugin extends JavaPlugin implements Listener {

	public static String disclink, dynlink = "";
	public static String discmessage, dynmessage = "";
	public static String prefix = "";
	
	private ArrayList<Player> onlinePlayers = new ArrayList<Player>();
	private VanishCommand v = new VanishCommand();

	@Override
	public void onEnable() {
		getLogger().log(Level.INFO, "[OtterPlugin] Enabling OtterPlugin");
		getServer().getPluginManager().registerEvents(this, this);
		OtterCommandManager.cleanupCommands();
		v.clearPlayerList();
		
		// Registering all the commands to the Command Manager
		OtterCommandManager.addCommand(new DiscordCommand());
		OtterCommandManager.addCommand(new DynlinkCommand());
		OtterCommandManager.addCommand(new OGMSCommand());
		OtterCommandManager.addCommand(new OGMSPCommand());
		OtterCommandManager.addCommand(new ReloadCommand());
		OtterCommandManager.addCommand(v);

		this.saveDefaultConfig();
		
		// Gather all the configuration settings from the config file
		OtterPlugin.disclink = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("discord_link")) + "§r";
		OtterPlugin.discmessage = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("discord_message"));
		OtterPlugin.dynlink = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("dynmap_link")) + "§r";
		OtterPlugin.dynmessage = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("dynmap_message"));
		OtterPlugin.prefix = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("prefix")) + "§r";
		
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			onlinePlayers.add(p);

	}

	@Override
	public void onDisable() {
		getLogger().log(Level.INFO, "[OtterPlugin] Disabling OtterPlugin");
		
		for (Player p : v.getVanishedPlayers()) {
			p.sendMessage(ChatColor.RED + "A reload has forced you to unvanish!");
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		boolean response = OtterCommandManager.checkCommand(command.getName(), sender, args, this);
		return response;

	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		String message = e.getMessage();
		String user = e.getPlayer().getName();
		Player pUser = e.getPlayer();
		
		// Anti-SocialSpy-Bypass
		if (message.substring(0, 14).equalsIgnoreCase("/minecraft:msg")) {
			e.setCancelled(true);
			pUser.sendMessage("Unknown command. Type \"/help\" for help.");
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		onlinePlayers.add(e.getPlayer());
		
		for (Player p : v.getVanishedPlayers()) {
			e.getPlayer().hidePlayer(p);
		}
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		for (Player p : onlinePlayers) {
			if (p.getUniqueId() == e.getPlayer().getUniqueId()) {
				onlinePlayers.remove(p);
			}
		}
	}
	
	public ArrayList<Player> getOnlinePlayers() {
		return onlinePlayers;
	}
	
}
