package de.satsuya;

import de.satsuya.commands.AssignWorkerRoleCommand;
import de.satsuya.commands.RemoveWorkerRoleCommand;
import de.satsuya.commands.ServerStatusCommand;
import de.satsuya.listeners.PlayerFishListener;
import de.satsuya.listeners.PlayerJoinListener;
import de.satsuya.listeners.PlayerMoveListener;
import de.satsuya.managers.ServerManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import de.satsuya.managers.FreezeManager;
import de.satsuya.commands.FreezeCommand;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class RiasCore extends JavaPlugin {
    public static Plugin Instance;
    public static LuckPerms luckPerms;

    private FreezeManager freezeManager;
    private ServerManager serverManager;


    // Leader-to-worker mapping
    public static final Map<String, String> roleMapping = new HashMap<>() {{
        put("zunftmeister_farmer", "farmer");
        put("zunftmeister_builder", "builder");
        put("zunftmeister_miner", "miner");
        put("zunftmeister_schmied", "schmied");
        put("zunftmeister_wachen", "wache");
        put("zunftmeister_holzfäller", "holzfäller");
        put("zunftmeister_sammler", "Sammler");
        put("zunftmeister_fischer", "fischer");
    }};

    @Override
    public void onEnable() {
        this.init();

        // List of commands to register
        CommandLoader commandLoader = new CommandLoader(this, Arrays.asList(
                new FreezeCommand(freezeManager),
                new AssignWorkerRoleCommand(),
                new RemoveWorkerRoleCommand(),
                new ServerStatusCommand(serverManager)
                // Add more commands here as needed
        ));
        commandLoader.registerCommands();

        // Register events
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(freezeManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFishListener(), this);

        this.getLogger().info("Everything is ready to rock! <3");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Bye bye!");
    }

    private void init() {
        try {
            Instance = this;
            freezeManager = new FreezeManager();
            serverManager = new ServerManager();
            luckPerms = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(LuckPerms.class)).getProvider();
        }catch (Exception e) {
            this.getLogger().severe(e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
