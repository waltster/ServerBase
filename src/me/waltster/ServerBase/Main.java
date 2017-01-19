package me.waltster.ServerBase;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    private static RoyalsManager rManager;
    private static DatabaseManager dManager;
    private static ConfigManager cManager;
    
    @Override
    public void onEnable(){
        this.getLogger().info("Setting up ServerBase");
        this.cManager = new ConfigManager(this);
        this.dManager = new DatabaseManager("host", 3306, "database", "username", "password", this);
        this.rManager = new RoyalsManager(this, cManager);
        
        this.getLogger().info("Setting up event listeners");
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        
        this.getCommand("royals").setExecutor(new CommandRoyals(this));
        
        dManager.query("CREATE TABLE IF NOT EXISTS `server_economy` ( `username` varchar(16) NOT NULL, `royals` int(16) NOT NULL, "
                + "UNIQUE KEY `username` (`username`) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
    }
    
    public DatabaseManager getDatabaseManager(){
        return dManager;
    }
    
    public static RoyalsManager getRoyalsManager(){
        return rManager;
    }
}
