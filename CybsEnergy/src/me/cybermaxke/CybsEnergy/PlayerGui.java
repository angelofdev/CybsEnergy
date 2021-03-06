package me.cybermaxke.CybsEnergy;

import me.cybermaxke.CybsEnergy.CybsEnergy;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PlayerGui extends GenericContainer
{
	public CybsEnergy p;
	private GenericTexture energyBar;
	private GenericTexture iconJump;
	private GenericTexture iconSprint;
	private String baseUrl = "https://dl.dropbox.com/u/56755498/Server/CybsStuff/EnergyBar/EnergyBar_";
	private String baseUrl2 = "https://dl.dropbox.com/u/56755498/Server/CybsStuff/EnergyBar/";

	public PlayerGui(CybsEnergy plugin, SpoutPlayer player) {
        	p = plugin;
        	energyBar = new GenericTexture();
        	if (p.playerGuiLeft) {
			energyBar.setX(120);
		} else {
			energyBar.setX(220);
			baseUrl = baseUrl2 + "Right/EnergyBar_";
		}
		energyBar.setUrl(baseUrl + CybsEnergy.getEnergyManager().getEnergy(player) + ".png");
		energyBar.setHeight(11).setWidth(87).setY(190);
		energyBar.setPriority(RenderPriority.Lowest);
		iconJump = new GenericTexture();
		iconJump.setUrl(baseUrl2 + "Jump.png");
		iconJump.setX(100).setY(215);
		iconJump.setHeight(20).setWidth(20);
		iconJump.setPriority(RenderPriority.Lowest);
		iconSprint = new GenericTexture();
		iconSprint.setUrl(baseUrl2 + "Sprint.png");
		iconSprint.setX(100).setY(215);
		iconSprint.setHeight(20).setWidth(20);
		iconSprint.setPriority(RenderPriority.Lowest);
		addChildren(new Widget[] {energyBar, iconJump, iconSprint});
	    	setWidth(0).setHeight(0);
    	}
	
	
	public void updateEnergyBar(SpoutPlayer player) {
		PlayerGui.energyBar.setUrl(baseUrl + CybsEnergy.getEnergyManager().getEnergy(player) + ".png");
		PlayerGui.energyBar.setDirty(true);
        	player.getMainScreen().setDirty(true);
    	}
	
	public void showEnergyBar(SpoutPlayer player, boolean show) {
		energyBar.setVisible(show);
       	 	player.getMainScreen().setDirty(true);
   	 }
	
	public void showJumpIcon(SpoutPlayer player, boolean show) {
		PlayerGui.iconJump.setVisible(show);
        	player.getMainScreen().setDirty(true);
    	}
	
	public void showSprintIcon(SpoutPlayer player, boolean show) {
		SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
		iconSprint.setVisible(show);
        	sPlayer.getMainScreen().setDirty(true);
    	}
}