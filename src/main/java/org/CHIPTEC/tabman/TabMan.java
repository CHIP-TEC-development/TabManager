package org.CHIPTEC.tabman;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Level;

public class TabMan extends JavaPlugin {
    public static TabMan instance;
    Field bukkitCommandMap;
    Field bukkitSimpleMap;
    CommandMap commandMap;
    SimpleCommandMap simpleMap;

    public static TabMan getInstance() {
        return instance;
    }

    public void onEnable() {
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

            bukkitSimpleMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

        } catch (NoSuchFieldException e) {
            this.debug("NMS didn't work.");
        }

        try {
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            simpleMap = (SimpleCommandMap) bukkitSimpleMap.get(Bukkit.getServer());
        } catch (IllegalAccessException e) {
            this.debug("NMS didn't work. Error pt2");
            e.printStackTrace();
            System.out.print(e.getCause().getMessage());
        }
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

            for (Command cmd : simpleMap.getCommands()) /* ((PluginCommand) commandMap.getCommand(cmd.getName())).setExecutor(null); */ debug(cmd.getName());

        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * @author RookieTEC9
     * @since Alpha
     * @deprecated old cofig
     */
    public void registerTabbers() {
        //
    }

    /**
     * @return the type of debug.
     * <p>
     * Debugs using 4 modes, configuarble in config.:
     * <p>
     * - 0 : Does not display a message.
     * - 1 : Displays a Logger message.
     * - 2 : Displays a message to all OPs and the console :
     * - 3 : Displays to all players on at the moment as well the console.
     * @author RookieTEC9s
     * @since Alpha
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
                p.sendMessage("§7[§fT§cM§7] " + s);
            }
            return 3;
        }
        return 0;
    }
}