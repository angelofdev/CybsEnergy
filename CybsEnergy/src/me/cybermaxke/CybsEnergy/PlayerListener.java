package me.cybermaxke.CybsEnergy;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PlayerListener implements Listener
{	
	public static CybsEnergy p;
	public PlayerListener(CybsEnergy plugin) {
        p = plugin;
    }
    
    private static Set<String> isSprinting = new HashSet<String>();
    public static boolean isSprinting(Player player) {
    	if (isSprinting.contains(player.getName())) return true;
    	else return false;
    }
    
    public static void setSprinting(Player player, boolean sprinting) {   	
    	if (sprinting) isSprinting.add(player.getName());
    	else isSprinting.remove(player.getName());
    }
    
    private static Set<String> isJumpCharged = new HashSet<String>();
    public static boolean isJumpCharged(Player player) {
    	if (isJumpCharged.contains(player.getName())) return true;
    	else return false;
    }
    
    public static void setJumpCharged(Player player, boolean jumpCharged) {   	
    	if (jumpCharged) isJumpCharged.add(player.getName());
    	else isJumpCharged.remove(player.getName());
    }
    
    /*
    @EventHandler(priority=EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
    	SpoutPlayer player = (SpoutPlayer) event.getPlayer();
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Material btype = event.getClickedBlock().getType();
			if (btype.equals(Material.WOODEN_DOOR) || btype.equals(Material.TRAP_DOOR) || btype.equals(Material.IRON_DOOR)) return;
		}
    	if (player.getItemInHand() == null) return;
    	if (player.getItemInHand().getType() == Material.STICK) {
    		String msg2 = "false";
    		String msg3 = "false";
    		if (isSprinting(player)) msg2 = "true";
    		if (isJumpCharged(player)) msg3 = "true";
    		player.sendMessage("Is Sprinting:" + msg2);
    		player.sendMessage("Is Jump Charged: " + msg3);
    	}
    }
    */
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
    	SpoutPlayer player = (SpoutPlayer) event.getPlayer();
    	CybsEnergy.getScreenManager().setPlayerGui(player, new PlayerGui(p, player));
    	if (player.getGameMode() == GameMode.CREATIVE) {
    		CybsEnergy.getScreenManager().getPlayerGui(player).showEnergyBar(player, false);
    	}
    }
    
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
    	Player player = event.getPlayer();
    	CybsEnergy.getEnergyManager().setEnergy(player, 20);
    }
    
	@EventHandler(priority=EventPriority.HIGH)
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
    	SpoutPlayer player = (SpoutPlayer) event.getPlayer();
    	if (event.getNewGameMode() == GameMode.CREATIVE) {
    		CybsEnergy.getScreenManager().getPlayerGui(player);
    		CybsEnergy.getScreenManager().getPlayerGui(player).showEnergyBar(player, false);
    		CybsEnergy.getEnergyManager().setEnergy(player, 20);
    	} else {
    		CybsEnergy.getScreenManager().getPlayerGui(player).showEnergyBar(player, true);
    	}
    }
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
    	SpoutPlayer player = (SpoutPlayer) event.getPlayer();
    	CybsEnergy.getEnergyManager().setEnergy(player, 20);
    	CybsEnergy.getScreenManager().getPlayerGui(player).updateEnergyBar(player);
    }
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event) {
    	SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		if (!(player.isSprinting())) {
    		if (CybsEnergy.getEnergyManager().getEnergy(player) < 4) {
    			player.setWalkingMultiplier(0.8);
    			player.setAirSpeedMultiplier(0.8);
    		} else {
    			player.setWalkingMultiplier(1);
    			player.setAirSpeedMultiplier(1);
    		}
    	} else {
    		if (CybsEnergy.getEnergyManager().getEnergy(player) == 0) {
    			player.setSprinting(false);
    		}
    	}
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
    	Player pl = event.getPlayer();
    	SpoutPlayer player = (SpoutPlayer) pl;
    	if (!(player.isSprinting())) {
    		if (CybsEnergy.getEnergyManager().getEnergy(player) >= 4) {
    			player.setWalkingMultiplier(1.7);
    			setSprinting(player, true);
    			CybsEnergy.getScreenManager().getPlayerGui(player).showSprintIcon(player, true);
    			CybsEnergy.getScreenManager().getPlayerGui(player).showJumpIcon(player, false);
    			return;
    		} else event.setCancelled(true);
    	}
    	setSprinting(player, false);
    	player.setWalkingMultiplier(1.0);
    	CybsEnergy.getScreenManager().getPlayerGui(player).showSprintIcon(player, false);
    }
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
    	Player pl = event.getPlayer();
    	SpoutPlayer player = (SpoutPlayer) pl;
    	if (!(player.isSneaking())) {
    		setJumpCharged(pl, true);
    		CybsEnergy.getScreenManager().getPlayerGui(player).showJumpIcon(player, true);
    		CybsEnergy.getScreenManager().getPlayerGui(player).showSprintIcon(player, false);
    		if(CybsEnergy.getEnergyManager().getEnergy(player) >= 3) {
    			player.setJumpingMultiplier(1.7);
    		}
    	} else {
    		setJumpCharged(player, false);
    		player.setJumpingMultiplier(1.0);
    		CybsEnergy.getScreenManager().getPlayerGui(player).showJumpIcon(player, false);
    	}	
    }
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onKeyPressedEvent(KeyPressedEvent event) {
    	SpoutPlayer player = event.getPlayer();
    	String keypressed = event.getKey().name();
    	if (keypressed.equals("KEY_SPACE")) {
    		if (isJumpCharged(player) && CybsEnergy.getEnergyManager().getEnergy(player) >= 3) {
    			setJumpCharged(player, false);
    			CybsEnergy.getEnergyManager().setEnergy(player, CybsEnergy.getEnergyManager().getEnergy(player) - 3);
    			CybsEnergy.getScreenManager().getPlayerGui(player).updateEnergyBar(player);
    		}
    	}
    }
}