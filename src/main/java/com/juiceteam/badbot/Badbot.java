package com.juiceteam.badbot;

import com.juiceteam.badbot.commands.SpawnCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Badbot extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        getCommand("spawnbot").setExecutor(new SpawnCommand());
    }
}