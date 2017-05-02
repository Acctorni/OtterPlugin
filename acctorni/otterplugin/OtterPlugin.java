package acctorni.otterplugin;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import acctorni.otterplugin.command.DiscordCommand;
import acctorni.otterplugin.command.DynlinkCommand;
import acctorni.otterplugin.command.OGMSCommand;
import acctorni.otterplugin.command.OGMSPCommand;
import acctorni.otterplugin.command.OtterCommandManager;
import acctorni.otterplugin.command.ReloadCommand;

public class OtterPlugin extends JavaPlugin {

	public static String disclink, dynlink = "";
	public static String discmessage, dynmessage = "";
	public static String prefix = "";

	@Override
	public void onEnable() {
		getLogger().log(Level.INFO, "[OtterPlugin] Enabling OtterPlugin");
		OtterCommandManager.cleanupCommands();
		
		// Registering all the commands to the Command Manager
		OtterCommandManager.addCommand(new DiscordCommand());
		OtterCommandManager.addCommand(new DynlinkCommand());
		OtterCommandManager.addCommand(new OGMSCommand());
		OtterCommandManager.addCommand(new OGMSPCommand());
		OtterCommandManager.addCommand(new ReloadCommand());

		this.saveDefaultConfig();
		
		// Gather all the configuration settings from the config file
		OtterPlugin.disclink = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("discord_link")) + "§r";
		OtterPlugin.discmessage = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("discord_message"));
		OtterPlugin.dynlink = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("dynmap_link")) + "§r";
		OtterPlugin.dynmessage = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("dynmap_message"));
		OtterPlugin.prefix = ChatColor.translateAlternateColorCodes('~', this.getConfig().getString("prefix")) + "§r";

	}

	@Override
	public void onDisable() {
		getLogger().log(Level.INFO, "[OtterPlugin] Disabling OtterPlugin");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		boolean response = OtterCommandManager.checkCommand(command.getName(), sender, args, this);
		return response;

	}

}
