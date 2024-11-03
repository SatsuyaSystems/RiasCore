package de.satsuya.commands;

import org.bukkit.command.CommandExecutor;

public interface PluginCommand extends CommandExecutor {
    String getName();  // Used to dynamically register the command
}