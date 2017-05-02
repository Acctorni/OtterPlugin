package acctorni.otterplugin.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import acctorni.otterplugin.OtterPlugin;


public class DynlinkCommand extends Command {

	public DynlinkCommand() {
		super("dynlink");
	}

	@Override
	boolean onCommandCall(CommandSender sender, String[] arguments, JavaPlugin pl) {
		String m = "[" + OtterPlugin.prefix + "] " + OtterPlugin.dynmessage + ": §r" + OtterPlugin.dynlink;
		sender.sendMessage(m);
		return true;
	}

}
