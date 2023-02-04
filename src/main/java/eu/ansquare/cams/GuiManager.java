package eu.ansquare.cams;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiManager implements Listener {
    private Inventory inv;
    List<Integer> camList;

    public void ShowCameraGui(Player player){
        inv = Bukkit.createInventory(null, 54, Component.text("Cameras"));
        putCameras();
        player.openInventory(inv);
    }
    void putCameras(){
        camList = new ArrayList<>();
        Set<String> cameraStringSet =  Cams.instance.getConfig().getConfigurationSection("CamStorage").getKeys(false);
        int idx = 0;
        for(String cameraString : cameraStringSet){
           camList.add(idx, Integer.parseInt(cameraString));
           idx++;
        }
        for(int i = 0; i < camList.size(); i++){
            ItemStack is = new ItemStack(Material.BLACK_WOOL, camList.get(i));
            inv.setItem(i, is);
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(inv)) {
            int slot = e.getSlot();
            if(e.getInventory().getItem(slot) != null){
                if(!Cams.instance.camUsers.contains(e.getWhoClicked())){
                tpPlayer((Player) e.getWhoClicked(), camList.get(slot).toString());
            }}
            e.setCancelled(true);
        }
    }
    void tpPlayer(Player player, String camId){
        Cams.instance.camUsers.add(player);
        Cams.instance.originLocs.put(player, player.getLocation());
        Cams.instance.camIdStrings.put(player, camId);
        Vector vector =  Cams.instance.getConfig().getVector("CamStorage."+camId+".location");
        String worldName =  Cams.instance.getConfig().getString("CamStorage."+camId+".world");
        World world = Bukkit.getWorld(worldName);
        Location location =  new Location(world, vector.getX(), vector.getY(), vector.getZ());
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(location);
        Audience audience = player;
        audience.sendActionBar(Component.text("Viewing camera " + camId));
    }
}
