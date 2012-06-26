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
	public static CybsEnergy p;
	private static GenericTexture energyBar;
	private static GenericTexture iconJump;
	private static GenericTexture iconSprint;
	private static String baseUrl = "https://dl.dropbox.com/u/56755498/Server/CybsStuff/EnergyBar/EnergyBar_";
	private static String baseUrl2 = "https://dl.dropbox.com/u/56755498/Server/CybsStuff/EnergyBar/";

	public PlayerGui(CybsEnergy plugin, SpoutPlayer player) {
        p = plugin;
        energyBar = new GenericTexture();
		energyBar.setUrl(baseUrl + CybsEnergy.getEnergyManager().getEnergy(player) + ".png");
		energyBar.setX(120).setY(190);
		energyBar.setHeight(11).setWidth(87);
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
	
	/*public static void initializePlayerGui(SpoutPlayer player) {
		energyBar = new GenericTexture();
		energyBar.setUrl(baseUrl + CybsEnergy.getEnergyManager().getEnergy(player) + ".png");
		energyBar.setX(120).setY(190);
		energyBar.setHeight(11).setWidth(87);
		energyBar.setPriority(RenderPriority.Lowest);
		iconJump = new GenericTexture();
		iconJump.setUrl(baseUrl2 + "Jump.png");
		iconJump.setX(100).setY(215);
		iconJump.setHeight(20).setWidth(20);
		iconJump.setPriority(RenderPriority.Lowest);
		iconJump.setVisible(false);
		iconSprint = new GenericTexture();
		iconSprint.setUrl(baseUrl2 + "Sprint.png");
		iconSprint.setX(100).setY(215);
		iconSprint.setHeight(20).setWidth(20);
		iconSprint.setPriority(RenderPriority.Lowest);
		iconSprint.setVisible(false);
		player.getMainScreen().attachWidget(p, energyBar);
		player.getMainScreen().attachWidget(p, iconJump);
		player.getMainScreen().attachWidget(p, iconSprint);
		player.getMainScreen().attachWidget(p, iconSwimming);
		player.getMainScreen().setDirty(true);
		player.getMainScreen().getArmorBar().setY(player.getMainScreen().getArmorBar().getY() - 11);
    }*/
	
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