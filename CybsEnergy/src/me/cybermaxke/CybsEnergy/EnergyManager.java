package me.cybermaxke.CybsEnergy;

import java.util.HashMap;

import org.bukkit.entity.Player;
import me.cybermaxke.CybsEnergy.CybsEnergy;

public class EnergyManager 
{
	public CybsEnergy p;
	private HashMap<String, Integer> energy = new HashMap<String, Integer>();
	
	public EnergyManager(CybsEnergy plugin)
  	{
        p = plugin;
    }
	
  	public void setEnergy(Player player, int amount)
  	{
  		energy.put(player.getName(), Integer.valueOf(amount));
  	}

  	public int getEnergy(Player player)
  	{
	  	int amount = ((Integer)energy.get(player.getName())).intValue();
    	return amount;
  	}
}