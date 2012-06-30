package me.cybermaxke.CybsEnergy;

import java.util.HashMap;

import org.getspout.spoutapi.player.SpoutPlayer;

public class ScreenManager
{
	public CybsEnergy p;
	private HashMap<String, PlayerGui> gui = new HashMap<String, PlayerGui>();
	
	public ScreenManager(CybsEnergy plugin)
  	{
        	p = plugin;
   	}
	
	public PlayerGui getPlayerGui(SpoutPlayer player)
	{
	   	 return (PlayerGui) gui.get(player.getName());
	}

	public void setPlayerGui(SpoutPlayer player, PlayerGui playerGui)
	{
		if (p.playerGuiLeft) {
			player.getMainScreen().getArmorBar().setY(player.getMainScreen().getArmorBar().getY() - 11);
		} else {
			player.getMainScreen().getBubbleBar().setY(player.getMainScreen().getBubbleBar().getY() - 11);
		}
		player.getMainScreen().attachWidget(p, playerGui);
		player.getMainScreen().setDirty(true);
	   	gui.put(player.getName(), playerGui);
	    	getPlayerGui(player).showJumpIcon(player, false);
	    	getPlayerGui(player).showSprintIcon(player, false);
	}
}