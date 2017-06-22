package org.CHIPTEC.tabman;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ChatColor;

public class MainConfig {
    private final String fileName;
    private final JavaPlugin plugin;
    private File configFile;
    private FileConfiguration fileConfiguration;

    public MainConfig(String fileName, JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        this.plugin = plugin;
        this.fileName = fileName;
        File dataFolder = plugin.getDataFolder();
        if (dataFolder == null) {
            throw new IllegalStateException();
        }
        this.configFile = new File(plugin.getDataFolder(), fileName);

        saveDefaultYaml();
    }

    public void reloadYaml() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defConfigStream = this.plugin.getResource(this.configFile.getPath().toString());
        if (defConfigStream == null) {
            return;
        }
        YamlConfiguration defConfig = new YamlConfiguration();
        byte[] contents;
        try {
            contents = ByteStreams.toByteArray(defConfigStream);
        } catch (IOException e) {
            TabMan.getInstance().getLogger().log(Level.SEVERE, "Unexpected failure reading config.yml", e);
            return;
        }
        String text = new String(contents, Charset.defaultCharset());
        if (!text.equals(new String(contents, Charsets.UTF_8))) {
        	TabMan.getInstance().getLogger()
                    .warning("Default system encoding may have misread config.yml from plugin jar");
        }
        try {
            defConfig.loadFromString(text);
        } catch (InvalidConfigurationException e) {
        	TabMan.getInstance().getLogger().log(Level.SEVERE, "Cannot load configuration from jar", e);
        }
        this.fileConfiguration.setDefaults(defConfig);
    }

    public FileConfiguration getYaml() {
        if (this.fileConfiguration == null) {
            reloadYaml();
        }
        return this.fileConfiguration;
    }

    public void saveYaml() {
        if ((this.fileConfiguration == null) || (this.configFile == null)) {
            return;
        }
        try {
            getYaml().save(this.configFile);
        } catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
        }
    }

    public void saveDefaultYaml() {
        if (!this.configFile.exists()) {
            Path file = this.configFile.toPath();
            try {
                Files.createFile(file, new FileAttribute[0]);
            } catch (FileAlreadyExistsException x) {
                Bukkit.getLogger().log(Level.INFO, "Tried to create a new Main Config, but it already existed! ");
            } catch (IOException x) {
                if (Bukkit.broadcast(ChatColor.DARK_RED + "AN ERROR OCCURED WHILE ATTEMPTING TO CREATE FILE: "
                        + this.configFile.getName(), "EnderPlugin.debug") == 0) {
                    Bukkit.getLogger().log(Level.SEVERE,
                            "AN ERROR OCCURED WHILE ATTEMPTING TO CREATE FILE: " + this.configFile.getName(), x);
                }
            }
        }
    }

    public void modifyYaml() {
        saveYaml();
        reloadYaml();
    }

    public String getFileName() {
        return this.fileName;
    }
}
