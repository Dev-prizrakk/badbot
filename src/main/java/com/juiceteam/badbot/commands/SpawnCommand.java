package com.juiceteam.badbot.commands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

public class SpawnCommand  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawnbot")) {
            if (sender.hasPermission("test.spawnbot")) {
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /spawnbot <type> <radius> <time>");
                    return true;
                }
                EntityType type = EntityType.valueOf(args[0].toUpperCase());
                int radius = Integer.parseInt(args[1]);
                int time = Integer.parseInt(args[2]);
                Location loc = getRandomLocation(radius);
                LivingEntity bot = null;
                switch (type) {
                    case ZOMBIE:
                        bot = spawnZombie(loc);
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "Invalid bot type.");
                        return true;
                }
                sender.sendMessage(ChatColor.GREEN + "Spawned a " + type.toString() + " bot at x=" + loc.getBlockX() + ", y=" + loc.getBlockY() + ", z=" + loc.getBlockZ());
                bot.setRemoveWhenFarAway(false);
                LivingEntity finalBot = bot;
                Bukkit.getScheduler().runTaskLater((Plugin) this, () -> finalBot.remove(), time * 20L);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
        }
        return false;
    }

    private Location getRandomLocation(int radius) {
        int x = (int) (Math.random() * (radius * 2)) - radius;
        int z = (int) (Math.random() * (radius * 2)) - radius;
        return new Location(Bukkit.getWorld("world"), x, 64, z);
    }

    private LivingEntity spawnZombie(Location loc) {
        LivingEntity zombie = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        zombie.setCustomName(ChatColor.RED + "My Zombie");
        zombie.setCustomNameVisible(true);
        zombie.setHealth(50);
        return zombie;
    }
}