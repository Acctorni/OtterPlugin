package acctorni.otterplugin.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class OtterCommandManager {
	
	private static ArrayList<Command> commands = new ArrayList<Command>();
	
	public static void addCommand(Command c) {
		commands.add(c);
	}
	
	public static void cleanupCommands() {
		commands.clear();
	}
	
	public static boolean checkCommand(String c, CommandSender sender, String[] arguments, JavaPlugin pl) {
		boolean response = false;
		for (Command com : commands) {
//			sender.sendMessage("Checking command, " + com.getName() + " against " + c);
			if (com.getName().equalsIgnoreCase(c)) {
				response = com.onCommandCall(sender, arguments, pl);
			}
		}
		return response;
	}
	
}