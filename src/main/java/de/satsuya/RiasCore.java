package de.satsuya;

import de.satsuya.commands.AssignWorkerRoleCommand;
import de.satsuya.commands.RemoveWorkerRoleCommand;
import de.satsuya.listeners.PlayerMoveListener;
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


    // Leader-to-worker mapping
    public static final Map<String, String> roleMapping = new HashMap<>() {{
        put("zunftmeister_farmer", "farmer");
        put("zunftmeister_builder", "builder");
        put("zunftmeister_miner", "miner");
        put("zunftmeister_schmied", "schmied");
        put("zunftmeister_wachen", "wache");
        put("zunftmeister_holzfäller", "holzfäller");
        put("zunftmeister_sammler", "Sammler");
    }};

    @Override
    public void onEnable() {
        this.init();

        // List of commands to register
        CommandLoader commandLoader = new CommandLoader(this, Arrays.asList(
                new FreezeCommand(freezeManager),
                new AssignWorkerRoleCommand(),
                new RemoveWorkerRoleCommand()
                // Add more commands here as needed
        ));
        commandLoader.registerCommands();

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(freezeManager), this);

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
            luckPerms = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(LuckPerms.class)).getProvider();
        }catch (Exception e) {
            this.getLogger().severe(e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
