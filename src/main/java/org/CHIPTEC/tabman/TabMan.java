package org.CHIPTEC.tabman;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TabMan extends JavaPlugin {
	public static TabMan instance;
	File[] filesList = new File(this.getDataFolder().getPath() + "\\Commands").listFiles();

	public static TabMan getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdir();

		for (Plugin p : Bukkit.getServer().getPluginManager().getPlugins()) {
			if (new File(this.getDataFolder().getPath() + p.getName()).mkdirs()) {
				System.out.print("[TM]" + "Sucessfully created plugin folder " + p.getName());
			} else {
				System.out.print("[TM]" + "Failed to sucessfully create plugin folder " + p.getName());
			}
		}

	}

	public void onDisable() {
		instance = null;
	}

	/**
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

}