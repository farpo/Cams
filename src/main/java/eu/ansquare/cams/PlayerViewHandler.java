package eu.ansquare.cams;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;


public class PlayerViewHandler implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(Cams.instance.camUsers.contains(e.getPlayer())){
            Audience audience = e.getPlayer();
            audience.sendActionBar(Component.text("Viewing camera " + Cams.instance.camIdStrings.get(e.getPlayer())));
            if(e.hasChangedPosition()){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent e) {
        if(Cams.instance.camUsers.contains(e.getPlayer())){
            Cams.instance.camUsers.remove(e.getPlayer());
            e.getPlayer().teleport(Cams.instance.originLocs.get(e.getPlayer()));
            Cams.instance.originLocs.remove(e.getPlayer());
            Cams.instance.camIdStrings.remove(e.getPlayer());
            e.getPlayer().setGameMode(GameMode.CREATIVE);
        }
    }
}
