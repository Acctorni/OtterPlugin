package acctorni.otterplugin.command;

import org.bukkit.command.CommandSender;

import acctorni.otterplugin.OtterPlugin;


public abstract class Command {
	
	protected String name = "";
	
	public Command(String name) {
		this.name = name;
	}
	
	abstract boolean onCommandCall(CommandSender sender, String[] arguments, OtterPlugin pl);

	public String getName() {
		return this.name;
	}
	
}
