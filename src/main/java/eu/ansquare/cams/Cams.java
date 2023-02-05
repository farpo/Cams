package eu.ansquare.cams;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.*;

public class Cams extends JavaPlugin implements Listener {
    ItemManager im;
    CamPlacer placer;
    static Cams instance;
    GuiManager gm;

    List<Player> camUsers;
    Map<Player, Location> originLocs;
    Map<Player, String> camIdStrings;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("camplacer")){
            im.givePlacer((Player) sender);
            return true;
        }
        if(label.equalsIgnoreCase("cams")){
            im.giveViewer((Player) sender);
            return true;
        }
        return false;
    }

    @Override
    public void onEnable(){
        instance = this;
        im = new ItemManager();
        placer = new CamPlacer();
        getServer().getPluginManager().registerEvents(new PlayerViewHandler(), this);
        getServer().getPluginManager().registerEvents(this, this);
        camUsers = new ArrayList<>();
        gm = new GuiManager();
        getServer().getPluginManager().registerEvents(gm, this);
        originLocs = new HashMap<>();
        camIdStrings = new HashMap<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.hasItemMeta()){
            im = new ItemManager();
            if(im.getItemType(item).equalsIgnoreCase("place")){
                placer.placeCam(player);
            }
            if(im.getItemType(item).equalsIgnoreCase("view")){
                gm.ShowCameraGui(player);
            }
        }
    }

}
