package de.satsuya;

import de.satsuya.commands.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Level;

public class CommandLoader {

    private final JavaPlugin plugin;
    private final List<PluginCommand> commands;

    public CommandLoader(JavaPlugin plugin, List<PluginCommand> commands) {
        this.plugin = plugin;
        this.commands = commands;
    }

    public void registerCommands() {
        for (PluginCommand command : commands) {
            String commandName = command.getName();
            try {
                plugin.getCommand(commandName).setExecutor(command);
                plugin.getLogger().log(Level.INFO, "Registered command: /" + commandName);
            } catch (NullPointerException e) {
                plugin.getLogger().log(Level.WARNING, "Command /" + commandName + " is not defined in plugin.yml");
            }
        }
    }
}