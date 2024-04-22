package simplexity.simpleunbreakablearmor;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleUnbreakableArmor extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("restore").setExecutor(new Command());
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
