package acctorni.otterplugin.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public abstract class Command {
	
	protected String name = "";
	
	public Command(String name) {
		this.name = name;
	}
	
	abstract boolean onCommandCall(CommandSender sender, String[] arguments, JavaPlugin pl);

	public String getName() {
		return this.name;
	}
	
}
