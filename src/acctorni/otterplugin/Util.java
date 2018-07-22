package acctorni.otterplugin;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Util {
	
	public static UUID uuid = UUID.fromString("fd37d758-b0dd-42ff-9b71-d674afec23cf");

	public static void log(String msg) {
		Bukkit.getConsoleSender().sendMessage("[OtterPlugin] " + ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	public static void sendMessage(String msg, Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
}
