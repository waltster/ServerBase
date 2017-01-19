package me.waltster.ServerBase;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.nicknamer.api.NickNamerAPI;

import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener{
    private Main plugin;
    
    public PlayerListener(Main plugin){
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event){
        plugin.getDatabaseManager().query("INSERT IGNORE INTO `server_economy` (`username`, `royals`) VALUES ('" + event.getPlayer().getName() + "', '0');");
        
        Player p = event.getPlayer();
        
        if(p.isOp()){
            p.sendMessage(ChatColor.GOLD + "You have " + ChatColor.WHITE + plugin.getRoyalsManager().getRoyals(p) + ChatColor.GOLD + " Royals");
        }else if(p.hasPermission("network.staff.developer")){
            NickNamerAPI.getNickManager().setNick(p.getUniqueId(), ChatColor.BLACK + "S " + ChatColor.WHITE + p.getName());
        }else if(p.hasPermission("network.staff.admin")){
            NickNamerAPI.getNickManager().setNick(p.getUniqueId(), ChatColor.BLACK + "S " + ChatColor.WHITE + p.getName());
        }else if(p.hasPermission("network.sponsor.silver")){
            NickNamerAPI.getNickManager().setNick(p.getUniqueId(), ChatColor.GRAY + "S " + ChatColor.WHITE + p.getName());
        }else if(p.hasPermission("network.sponsor.gold")){
            NickNamerAPI.getNickManager().setNick(p.getUniqueId(), ChatColor.GOLD + "G " + ChatColor.WHITE + p.getName());
        }else if(p.hasPermission("network.sponsor.ruby")){
            NickNamerAPI.getNickManager().setNick(p.getUniqueId(), ChatColor.RED + "R " + ChatColor.WHITE + p.getName());
        }else if(p.hasPermission("network.sponsor.sponsor")){
            NickNamerAPI.getNickManager().setNick(p.getUniqueId(), ChatColor.GOLD + "S " + ChatColor.WHITE + p.getName());
        }
        
        p.sendMessage(ChatColor.GOLD + "You have " + ChatColor.WHITE + plugin.getRoyalsManager().getRoyals(p) + ChatColor.GOLD + " Royals");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();
        
        String s = event.getMessage();
        String msg = s;
        
        if(!ChatColor.stripColor(s).startsWith("[")){
            s = p.getName() + ": " + s;
        }
        
        event.setCancelled(true);
        
        if(p.hasPermission("network.staff.admin")){
            msg = ChatColor.WHITE + "[" + ChatColor.RED + "Admin" + ChatColor.WHITE + "] " + s;
        }else if(p.hasPermission("network.staff.developer")){
            msg = ChatColor.WHITE + "[" + ChatColor.DARK_BLUE + "Developer" + ChatColor.WHITE + "] " + s;
        }else if(p.hasPermission("network.sponsor.silver")){
            msg = ChatColor.WHITE + "[" + ChatColor.GRAY + "Silver" + ChatColor.WHITE + "] " + s;
        }else if(p.hasPermission("network.sponsor.gold")){
            msg = ChatColor.WHITE + "[" + ChatColor.GOLD + "Gold" + ChatColor.WHITE + "] " + s;
        }else if(p.hasPermission("network.sponsor.ruby")){
            msg = ChatColor.WHITE + "[" + ChatColor.RED + "Ruby" + ChatColor.WHITE + "]" + s;
        }else if(p.hasPermission("network.sponsor.sponsor")){
            msg = ChatColor.WHITE + "[" + ChatColor.GOLD + "Sponsor" + ChatColor.WHITE + "]" + s;
        }
        
        Bukkit.broadcastMessage(msg);
    }
}
