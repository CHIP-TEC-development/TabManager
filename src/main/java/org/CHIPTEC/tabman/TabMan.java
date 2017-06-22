package org.CHIPTEC.tabman;

import java.io.File;

import org.bukkit.Bukkit;
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

	}

	public void onDisable() {
		instance = null;
	}

	/**
	 * @author RookieTEC9
	 * 
	 * Gets the Files in "\\Commands" and does a Tab Executor for every one.
	 */
	public void registerTabbers() {
		for (int i = 0; i < filesList.length; i++) {
			Bukkit.getServer().getPluginCommand(String.valueOf(filesList[i]).replace(".yml", "")).getTabCompleter();
		}
	}

}