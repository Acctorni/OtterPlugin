package acctorni.otterplugin.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import acctorni.otterplugin.OtterPlugin;


public class VanishCommand extends Command {

	ArrayList<Player> vanishedPlayers = new ArrayList<Player>();
	
	public VanishCommand() {
		super("v");
	}

	@Override
	boolean onCommandCall(CommandSender sender, String[] arguments, OtterPlugin pl) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("This is a player specific command, sorry.");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (sender.hasPermission("otterplugin.v") || sender.isOp()) {
			
			if (vanishedPlayers.contains(p)) {
				p.sendMessage(ChatColor.DARK_AQUA + "You have unvanished from the other players.");
				vanishedPlayers.remove(p);
				
				for (Player ply : pl.getOnlinePlayers()) {
					if (!(ply.hasPermission("otterplugin.v")))
						ply.showPlayer(p);
				}
				
			} else {
				p.sendMessage(ChatColor.DARK_AQUA + "You have vanished from the other players.");
				vanishedPlayers.add(p);
				
				for (Player ply : pl.getOnlinePlayers()) {
					if (!(ply.hasPermission("otterplugin.v")))
						ply.hidePlayer(p);
				}
			}
		}
		
		return true;
	}
	
	public void clearPlayerList() {
		vanishedPlayers.clear();
	}
	
	public ArrayList<Player> getVanishedPlayers() {
		return vanishedPlayers;
	}

}
