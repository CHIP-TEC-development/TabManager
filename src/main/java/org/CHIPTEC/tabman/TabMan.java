package org.CHIPTEC.tabman;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TabMan extends JavaPlugin {
	public static TabMan instance;
	
	@Deprecated
	File[] filesList = new File(this.getDataFolder().getPath() + "\\Commands").listFiles();

	public static TabMan getInstance() {
		return instance;
	}

	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdir();

		if (this.getConfig().getStringList("skip").isEmpty() || this.getConfig().getStringList("skip").size() == 0) {
			debug("The skip portion of the config is empty!");
		}
		for (Plugin p : Bukkit.getServer().getPluginManager().getPlugins()) {
			if (p.getName().equalsIgnoreCase("TabMan"))
				continue;
			for (String s : this.getConfig().getStringList("skip"))
				if (p.getName().equalsIgnoreCase(s)) {
					debug("Skipped " + s + " because it was in the config!");
					continue;
				}
			if (new File(this.getDataFolder().getPath() + File.separator + p.getName()).mkdirs()) {
				debug("Sucessfully created plugin folder " + p.getName());
			} else {
				if (new File(this.getDataFolder().getPath() + File.separator + p.getName()).exists()) {
					debug("Failed to sucessfully create plugin folder " + p.getName() + " because it was already there!");					
				} else {
					debug("Failed to sucessfully create plugin folder " + p.getName());
				}
			}
		}
	}

	public void onDisable() {
		instance = null;
	}

	/**
	 * @deprecated old cofig 
	 * @since Alpha
	 * @author RookieTEC9
	 * 
	 *         Gets the Files in "\\Commands" and does a Tab Executor for every
	 *         one.
	 * 
	 *         Has not been tested as of 06/22/17
	 */
	public void registerTabbers() {
		for (int i = 0; i < filesList.length; i++) {
			// HEHHEHE
		}
	}

	/**
	 * @since Alpha
	 * @author RookieTEC9s
	 * @return the type of debug.
	 * 
	 *         Debugs using 4 modes, configuarble in config.:
	 * 
	 *         - 0 : Does not display a message. 
	 *         - 1 : Displays a Logger message. 
	 *         - 2 : Displays a message to all OPs and the console : 
	 *         - 3 : Displays to all players on at the moment as well the console.
	 */
	public int debug(String s) {
		if (this.getConfig().getInt("debugMode") == 0) {
			return 0;
		}
		if (this.getConfig().getInt("debugMode") == 1) {
			Bukkit.getLogger().log(Level.INFO, "[TM] " + s);
			return 1;
		}
		if (this.getConfig().getInt("debugMode") == 2) {
			Bukkit.getLogger().log(Level.INFO, "[TM] " + s);
			for (OfflinePlayer p : Bukkit.getOperators()) {
				if (p.isOnline())
					((Player) p).sendMessage("§7[§fT§cM§7] " + s);
			}
			return 2;
		}
		if (this.getConfig().getInt("debugMode") == 3) {
			Bukkit.getLogger().log(Level.INFO, "[TM] " + s);
			for (Player p : Bukkit.getOnlinePlayers()) {
				((Player) p).sendMessage("§7[§fT§cM§7] " + s);
			}
			return 3;
		}
		return 0;
	}
}