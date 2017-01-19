package me.waltster.ServerBase;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class RoyalsManager {
    private Main plugin;
    private ConfigManager config;
    public static final int UNDEF_STAT = -42;

    public RoyalsManager(Main instance, ConfigManager config) {
        this.plugin = instance;
        this.config = config;
    }

    public int getRoyals(Player p) {
        try {
            int stat = UNDEF_STAT;
            ResultSet rs = plugin.getDatabaseManager()
                    .query("SELECT * FROM `server_economy` WHERE `username`='" + p.getName() + "'")
                    .getResultSet();

            while (rs.next())
                stat = rs.getInt("royals");

            return stat;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return UNDEF_STAT;
        }
    }

    public void setRoyals(Player p, int value) {
        plugin.getDatabaseManager().query("UPDATE `server_economy` SET `royals`='" + value
                + "' WHERE `username`='" + p.getName() + "';");
    }

    public void incrementRoyals(Player p) {
        incrementRoyals(p, 1);
    }

    public void incrementRoyals(Player p, int amount) {
        if(p.hasPermission("network.sponsor.silver")){
            amount *= 2;
        }else if(p.hasPermission("network.sponsor.gold")){
            amount *= 3;
        }else if(p.hasPermission("network.sponsor.ruby")){
            amount *= 4;
        }
        
        setRoyals(p, getRoyals(p) + amount);
    }
}
