package acctorni.otterplugin.command;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import acctorni.otterplugin.OtterPlugin;


public class OGMSCommand extends Command {

	public OGMSCommand() {
		super("ogms");
	}

	@Override
	boolean onCommandCall(CommandSender sender, String[] arguments, OtterPlugin pl) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("This is a player specific command, sorry.");
			return true;
		}
		
		if (sender.hasPermission("otterplugin.gms") || sender.isOp()) {
			Player p = (Player) sender;
			p.setGameMode(GameMode.SURVIVAL);
		} else {
			sender.sendMessage("[OtterPlugin] §4You cannot use this command.");
		}
		return true;
	}

}
