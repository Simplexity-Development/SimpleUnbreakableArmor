package simplexity.simpleunbreakablearmor;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemStack restoreItem = Listeners.restoreBrokenItem(item);
        if (restoreItem == null) return true;
        player.getInventory().setItemInMainHand(restoreItem);
        return true;
    }
}
