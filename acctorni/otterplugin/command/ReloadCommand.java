package acctorni.otterplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import acctorni.otterplugin.OtterPlugin;


public class ReloadCommand extends Command {

	public ReloadCommand() {
		super("orl");
	}

	@Override
	boolean onCommandCall(CommandSender sender, String[] arguments, OtterPlugin pl) {
		if (sender.isOp()) {
			pl.reloadConfig();

			OtterPlugin.disclink = ChatColor.translateAlternateColorCodes('~', pl.getConfig().getString("discord_link")) + "§r";
			OtterPlugin.discmessage = ChatColor.translateAlternateColorCodes('~', pl.getConfig().getString("discord_message"));
			OtterPlugin.dynlink = ChatColor.translateAlternateColorCodes('~', pl.getConfig().getString("dynmap_link")) + "§r";
			OtterPlugin.dynmessage = ChatColor.translateAlternateColorCodes('~', pl.getConfig().getString("dynmap_message"));
			OtterPlugin.prefix = ChatColor.translateAlternateColorCodes('~', pl.getConfig().getString("prefix")) + "§r";

			sender.sendMessage("[§oOtterCraft§r] Reload complete.");
		} else {
			sender.sendMessage("[§oOtterCraft§r] §4You do not have permission to use this command.");
		}
		return true;
	}

}
