package net.licks92.WirelessRedstone.Configuration;

import net.licks92.WirelessRedstone.WirelessRedstone;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationConverter {

    public ConfigurationConverter(WirelessRedstone plugin){
        FileConfiguration conf = plugin.getConfig();

        Integer confVersion = conf.getInt("ConfigVersion", 0);
        Boolean oldStorage = conf.getBoolean("UseSQL", false);

        try {
            if (confVersion == 0 && oldStorage) {
                conf.set("ConfigVersion", 1);

                if (conf.getBoolean("UseSQL")) {
                    conf.set("saveOption", "SQLITE");
                } else {
                    conf.set("saveOption", "YML");
                }

                conf.set("MySQL.host", "localhost");
                conf.set("MySQL.port", "3306");
                conf.set("MySQL.database", "WirelessRedstone");
                conf.set("MySQL.username", "root");
                conf.set("MySQL.password", "root");
                conf.set("gateLogic", "OR");

                conf.set("UseSQL", null);
                plugin.saveConfig();
            }

        } catch (Exception ex){
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[WirelessRedstone] Error while " +
                    "converting config");
        }
    }

}
