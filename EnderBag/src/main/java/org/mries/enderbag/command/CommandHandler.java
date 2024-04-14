package org.mries.enderbag.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mries.enderbag.util.ItemManager;

public class CommandHandler implements CommandExecutor {
    private ItemManager itemManager = null;

    public CommandHandler(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (sender.hasPermission("enderbag.command")) {
                    itemManager.openInventory((Player) sender);
                    return true;
                } else {
                    sender.sendMessage("§cYou do not have permission to do that");
                }
            } else {
                sender.sendMessage("§cThis command must be executed by a player");
            }
            return false;
        }
        if (args[0].equalsIgnoreCase("give")) {
            // No target was specified
            if (args.length == 1) {
                if (sender instanceof Player) {
                    return giveBag(sender, (Player) sender);
                } else {
                    sender.sendMessage("§cA player must be specified");
                    return false;
                }
            }
            // A target was specified
            else {
                Player requestedPlayer = Bukkit.getPlayer(args[1]);
                if (requestedPlayer == null) {
                    sender.sendMessage("§cNo player found with ID: " + args[1]);
                    return false;
                }
                return giveBag(sender, requestedPlayer);
            }
        }
        return false;
    }

    private boolean giveBag(CommandSender sender, Player target) {
        ItemStack stack = new ItemStack(Material.EMERALD, 1);
        itemManager.updateItemStack(stack);
        if (sender == target) {
            if (sender.hasPermission("enderbag.give.self")) {
                ((Player) sender).getInventory().addItem(stack);
                return true;
            } else {
                sender.sendMessage("§cYou do not have permission to do that");
                return false;
            }
        } else {
            if (sender instanceof Player) {
                if (sender.hasPermission("enderbag.give.others")) {
                    target.getInventory().addItem(stack);
                    return true;
                } else {
                    sender.sendMessage("§cYou do not have permission to do that");
                    return false;
                }
            } else {
                target.getInventory().addItem(stack);
                return true;
            }
        }
    }
}