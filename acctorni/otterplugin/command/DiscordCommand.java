package acctorni.otterplugin.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import acctorni.otterplugin.OtterPlugin;


public class DiscordCommand extends Command {

	public DiscordCommand() {
		super("discord");
	}

	@Override
	public boolean onCommandCall(CommandSender sender, String[] arguments, JavaPlugin pl) {
		String m = "[" + OtterPlugin.prefix + "] " + OtterPlugin.discmessage + ": §r" + OtterPlugin.disclink;
		sender.sendMessage(m);

		return true;
	}

}
