package acctorni.otterplugin.command;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class OGMSPCommand extends Command {

	public OGMSPCommand() {
		super("ogmsp");
	}

	@Override
	boolean onCommandCall(CommandSender sender, String[] arguments, JavaPlugin pl) {
		if (sender.hasPermission("otterplugin.gmsp") || sender.isOp()) {
			Player p = (Player) sender;
			p.setGameMode(GameMode.SPECTATOR);
		} else {
			sender.sendMessage("[OtterPlugin] §4You cannot use this command.");
		}
		return true;
	}

}
