package org.mries.enderbag.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("give","recipe");
        } else if (args.length == 2) {
            if (args[1] == "give") {
                List<String> returnList = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    returnList.add(player.getName());
                }
                return returnList;
            }
            else {
                return null;
            }
        }
        return null;
    }
}