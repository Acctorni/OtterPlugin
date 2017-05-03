package acctorni.otterplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Util {

	public static void log(String msg) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	public static void sendMessage(String msg, Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
}
