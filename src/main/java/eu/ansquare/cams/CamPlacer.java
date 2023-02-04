package eu.ansquare.cams;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.Set;

public class CamPlacer {
    public void placeCam(Player player){
        Location camLoc = player.getLocation();
        int nextId = 1;
        if (Cams.instance.getConfig().getConfigurationSection("CamStorage") != null) {
            Set<String> cameras = Cams.instance.getConfig().getConfigurationSection("CamStorage").getKeys(false);
            for (String idstring : cameras) {
                int id = Integer.parseInt(idstring);
                if (id >= nextId) {
                    nextId = id + 1;
                }
            }
        }
        World camWorld = player.getWorld();
        Cams.instance.getConfig().set("CamStorage."+nextId+".location", camLoc.toVector());
        Cams.instance.getConfig().set("CamStorage."+nextId+".world", camWorld.getName());
        Cams.instance.saveConfig();
    }
}
