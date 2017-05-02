package acctorni.otterplugin.command;

import org.bukkit.command.CommandSender;

import acctorni.otterplugin.OtterPlugin;


public class DynlinkCommand extends Command {

	public DynlinkCommand() {
		super("dynlink");
	}

	@Override
	boolean onCommandCall(CommandSender sender, String[] arguments, OtterPlugin pl) {
		String m = "[" + OtterPlugin.prefix + "] " + OtterPlugin.dynmessage + ": §r" + OtterPlugin.dynlink;
		sender.sendMessage(m);
		return true;
	}

}
