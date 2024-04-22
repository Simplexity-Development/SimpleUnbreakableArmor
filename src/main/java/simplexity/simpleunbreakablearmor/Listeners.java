package simplexity.simpleunbreakablearmor;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.persistence.PersistentDataType;

public class Listeners implements Listener {

    public static final NamespacedKey key = new NamespacedKey("simpleunbreakablearmor", "old_data");

    @EventHandler
    public void onBreak(PlayerItemBreakEvent event) {
        ItemStack item = event.getBrokenItem();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta.getPersistentDataContainer().has(key)) {
            event.getPlayer().getInventory().addItem(event.getBrokenItem().clone());
            return;
        }
        byte[] serializedItem = item.serializeAsBytes();
        ItemStack replacementItem = new ItemStack(item.getType());
        ItemMeta replacementMeta = replacementItem.getItemMeta();
        ((Damageable) replacementMeta).setDamage(((Damageable) itemMeta).getDamage());
        if (((Repairable) itemMeta).hasRepairCost()) ((Repairable) replacementMeta).setRepairCost((((Repairable) itemMeta).getRepairCost()));
        replacementMeta.displayName(item.displayName().append(MiniMessage.miniMessage().deserialize(" <gray><bold>BROKEN</bold></gray>")));
        replacementMeta.getPersistentDataContainer().set(key, PersistentDataType.BYTE_ARRAY, serializedItem);
        // TODO: Add Custom Item Data Support
        replacementItem.setItemMeta(replacementMeta);
        event.getPlayer().getInventory().addItem(replacementItem);
    }

    public static ItemStack restoreBrokenItem(ItemStack itemStack) {
        if (itemStack == null) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if (!meta.getPersistentDataContainer().has(key)) return null;
        byte[] restoreItemArray = meta.getPersistentDataContainer().get(key, PersistentDataType.BYTE_ARRAY);
        ItemStack restoreItem = ItemStack.deserializeBytes(restoreItemArray);
        itemStack.subtract();
        return restoreItem;

    }

}
