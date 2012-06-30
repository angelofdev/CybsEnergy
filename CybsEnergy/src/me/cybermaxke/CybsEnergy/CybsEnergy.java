package me.cybermaxke.CybsEnergy;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.FileManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.cybermaxke.CybsEnergy.EnergyManager;
import me.cybermaxke.CybsEnergy.PlayerListener;

public class CybsEnergy extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	private final PluginManager pm = Bukkit.getPluginManager();
	private final PlayerListener playerListener = new PlayerListener(this);
	private static String pTitle = "[CybsEnergy] ";
	private static EnergyManager energyManager;
	private static ScreenManager screenManager;
	
	public double fasterSprintMultiplier;
	public double higherJumpMultiplier;
	public int higherJumpEnergyReq;
	public long ticksRegain;
	public boolean playerGuiLeft;

	public static EnergyManager getEnergyManager()
	{
		return energyManager;
	}
	
	public static ScreenManager getScreenManager()
	{
		return screenManager;
	}
	  
	public void onEnable() 
	{
		if (!pm.isPluginEnabled(pm.getPlugin("Spout"))) {
			pm.disablePlugin(this);
			log.warning(pTitle + "Can't find Spout, plugin disabled!");
			return;
		}
		setupConfig();
		loadConfig();
		preCacheEnergyBarTex();
		pm.registerEvents(playerListener, this);
		energyManager = new EnergyManager(this);
		screenManager = new ScreenManager(this);
		startEnergyLoop();
		startEnergyLoop2();
		log.info(pTitle + "is enabled.");
	}
	
	public void onDisable() 
	{
		log.info(pTitle + "is disabled.");
	}
	
	private void setupConfig() {
		FileConfiguration c = getConfig();
		c.addDefault("FasterSprint.Multiplier", "1.7");
		c.addDefault("HigherJump.Multiplier", "1.7");
		c.addDefault("HigherJump.EnergyReq", "3");
		c.addDefault("TicksBetweenRegain", "25");
		c.addDefault("PlayerGui.LeftSide", "true");
		c.options().copyDefaults(true);
		saveConfig();
	}
	
	private void loadConfig() {
		FileConfiguration c = getConfig();
		fasterSprintMultiplier = c.getDouble("FasterSprint.Multiplier", 1.7);
		higherJumpMultiplier = c.getDouble("HigherJump.Multiplier", 1.7);
		higherJumpEnergyReq = c.getInt("HigherJump.EnergyReq", 3);
		ticksRegain = c.getLong("TicksBetweenRegain", 25);
		playerGuiLeft = c.getBoolean("PlayerGui.LeftSide", true);
	}
	
	private String energyBaseUrl = "https://dl.dropbox.com/u/56755498/Server/CybsStuff/EnergyBar/EnergyBar_";
	private String iconBaseUrl = "https://dl.dropbox.com/u/56755498/Server/CybsStuff/EnergyBar/EnergyBar_";
	private void preCacheEnergyBarTex() {
		FileManager fm = SpoutManager.getFileManager();
		fm.addToPreLoginCache(this, energyBaseUrl + "0.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "1.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "2.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "3.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "4.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "5.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "6.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "7.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "8.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "9.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "10.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "11.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "12.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "13.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "14.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "15.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "16.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "17.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "18.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "19.png");
		fm.addToPreLoginCache(this, energyBaseUrl + "20.png");
		fm.addToPreLoginCache(this, iconBaseUrl + "Sprint.png");
		fm.addToPreLoginCache(this, iconBaseUrl + "Jump.png");
	}
	
	public void startEnergyLoop() {
	    getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable()
	    {
	    	public void run() {
	    		for (Player players : Bukkit.getOnlinePlayers())
	    			if (getEnergyManager().getEnergy(players) < 20) {	
	    				if (players.getGameMode() == GameMode.SURVIVAL) {
	    					if (PlayerListener.isSprinting(players)) return;
	    					else if (PlayerListener.isJumpCharged(players)) return;
	    					getEnergyManager().setEnergy(players, getEnergyManager().getEnergy(players) + 1);
	    					CybsEnergy.getScreenManager().getPlayerGui((SpoutPlayer) players).updateEnergyBar((SpoutPlayer) players);
	    				}
	    			}
	    		}
	    	}
	    , 0L, ticksRegain);
	}
	
	public void startEnergyLoop2() {
	    getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable()
	    {
	    	public void run() {
	    		for (Player players : Bukkit.getOnlinePlayers())
	    			if (getEnergyManager().getEnergy(players) > 0) {	
	    				if (players.getGameMode() == GameMode.SURVIVAL) {    				
	    					if (PlayerListener.isSprinting(players)) {
	    						getEnergyManager().setEnergy(players, getEnergyManager().getEnergy(players) - 1);
	    						CybsEnergy.getScreenManager().getPlayerGui((SpoutPlayer) players).updateEnergyBar((SpoutPlayer) players);
	    					}
	    				}
	    			}	    			
	    		}
	    	}
	    , 0L, 10L);
	}
}
