package acctorni.otterplugin.command;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import acctorni.otterplugin.OtterPlugin;
import acctorni.otterplugin.Util;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;

public class WorldEditCommand extends Command {

	LuckPermsApi api = LuckPerms.getApi();
	
	public WorldEditCommand() {
		super("owe");
		
	}

	@Override
	boolean onCommandCall(CommandSender sender, String[] arguments, OtterPlugin pl) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("This is a player specific command, sorry.");
			return true;
		}
		
		Player p = (Player) sender;	
		
		if (sender.hasPermission("otterplugin.we") || sender.isOp()) {
			
			if (arguments.length <= 0) {
				Util.sendMessage("§c[§eOtterPlugin§c]§f This command requires an argument, please refer to the help page.", p);
				return true;
			} else {
				
				if (OtterPlugin.permsToGrant.isEmpty()) {
					Bukkit.getLogger().log(Level.WARNING, "No permissions in the configuration file.");
					Util.sendMessage("§c[§eOtterPlugin§c]§c Please contact your server administrators, the permissions to grant in the configuration file are empty.", p);
					return true;
				}
				
				Player ply = Bukkit.getPlayerExact(arguments[0]);
				
				if (ply == null) {
					Util.sendMessage("§c[§eOtterPlugin§c]§c The user is either not online, or an invalid target.", p);
				}
				
				User user = api.getUserSafe(p.getUniqueId()).get();
				Node node = api.getNodeFactory().newBuilder(OtterPlugin.permsToGrant.get(0)).build();
				boolean toggle = !(user.hasPermission(node).asBoolean());
				
				for (String s : OtterPlugin.permsToGrant) {
					node = api.getNodeFactory().newBuilder(s).build();
					if (toggle) {
						
						user.setTransientPermission(node);
					} else {
						user.unsetTransientPermission(node);
					}
				}
				user.refreshCachedData();
				
				String z = toggle ? " has been granted permissions" : " has lost granted permissions";
				Util.sendMessage("§c[§eOtterPlugin§c]§f " + ply.getName() + z, p);
			}
			
		}
		return true;
	}

}
