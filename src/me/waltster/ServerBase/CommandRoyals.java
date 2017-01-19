package me.waltster.ServerBase;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRoyals implements CommandExecutor{
    private Main main;
    
    public CommandRoyals(Main main){
        this.main = main;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + "/royals <set/get/give/take> [player] [amount]");
                return true;
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("get")){
                    sender.sendMessage(ChatColor.GOLD + "You have " + ChatColor.WHITE + main.getRoyalsManager().getRoyals((Player)sender) + ChatColor.GOLD + " Royals");
                    return true;
                }else{
                    sender.sendMessage(ChatColor.RED + "/royals <set/get/give/take> [player] [amount]");
                    return true;
                }
            }else if(args.length == 3){
                if(args[0].equalsIgnoreCase("set")){
                    if(sender.hasPermission("network.royals.set") || sender.isOp()){
                        Player p = Bukkit.getPlayer(args[1]);
                        int amount = Integer.parseInt(args[2]);
                        
                        if(p == null){
                            sender.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        
                        main.getRoyalsManager().setRoyals(p, amount);
                        sender.sendMessage(ChatColor.GREEN + "Set " + p.getName() + "'s Royals to " + ChatColor.WHITE + amount);
                        
                        return true;
                    }else{
                        sender.sendMessage(ChatColor.RED + "You don't have permission for that");
                    }
                }else if(args[0].equalsIgnoreCase("give")){
                    if(sender.hasPermission("network.royals.give") || sender.isOp()){
                        Player p = Bukkit.getPlayer(args[1]);
                        int amount = Integer.parseInt(args[2]);
                        
                        if(p == null){
                            sender.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        
                        main.getRoyalsManager().incrementRoyals(p, amount);
                        sender.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.WHITE + amount + ChatColor.GREEN + " Royals to " + p.getName());
                    }else{
                        sender.sendMessage(ChatColor.RED + "You don't have permission");
                    }
                }else if(args[0].equalsIgnoreCase("take")){
                    if(sender.hasPermission("network.royals.take")){
                        Player p = Bukkit.getPlayer(args[1]);
                        int amount = Integer.parseInt(args[2]);
                        
                        if(p == null){
                            sender.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        
                        main.getRoyalsManager().incrementRoyals(p, -amount);
                        sender.sendMessage(ChatColor.GREEN + "Took " + ChatColor.WHITE + amount + ChatColor.GREEN + " Royals from " + p.getName());
                    }else{
                        sender.sendMessage(ChatColor.RED + "You don't have permission");
                    }
                }
            }
        }
        
        return false;
    }
}