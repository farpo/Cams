package eu.ansquare.cams;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemManager {
    public void givePlacer(Player player){
        NamespacedKey key = new NamespacedKey("cams", "item-type");
        ItemStack item = new ItemStack(Material.IRON_NUGGET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "place");
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }
    public void giveViewer(Player player){
        NamespacedKey key = new NamespacedKey("cams", "item-type");
        ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "view");
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }
    public String getItemType(ItemStack item){
        NamespacedKey key = new NamespacedKey("cams", "item-type");
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)){
            String valueString = container.get(key, PersistentDataType.STRING);
            return valueString;
        }
        else {
            return "not";
        }
    }
}
