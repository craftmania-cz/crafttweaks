package cz.craftmania.crafttweaks.crafttweaks.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    public static void info(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[CraftTweaks] " + ChatColor.WHITE + s);
    }

    public static void danger(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[CraftTweaks] " + ChatColor.RED + s);
    }

    public static void success(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[CraftTweaks] " + ChatColor.GREEN + s);
    }
}
