package net.licks92.WirelessRedstone.Listeners;

import net.licks92.WirelessRedstone.WirelessRedstone;
import net.licks92.WirelessRedstone.Channel.WirelessChannel;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class WirelessPlayerListener implements Listener
{
	private final WirelessRedstone plugin;

	public WirelessPlayerListener(WirelessRedstone r_plugin)
	{
		plugin = r_plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		/*
		 * Check for updates and notify the admins.
		 */
		
		if(plugin.permissions.isWirelessAdmin(event.getPlayer()))
		{
			try
			{
				double newversion = plugin.updateCheck(plugin.currentversion);
				
				if(newversion > plugin.currentversion)
				{
					event.getPlayer().sendMessage("[WirelessRedstone] A new update has been released ! You can download it at http://dev.bukkit.org/server-mods/wireless-redstone/");
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Used for handling right click on a transmitter.
	 * If  a player interacts with a transmitter, it will turn on the channel for a given time.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event)
	{
		if(!(event.getClickedBlock().getState() instanceof Sign))
			return;
		
		Sign sign = (Sign)event.getClickedBlock().getState();
		
		if(!WirelessRedstone.WireBox.isTransmitter(sign.getLine(0)) || sign.getLine(1) == null || sign.getLine(1) == "")
			return;
		
		WirelessChannel channel = WirelessRedstone.config.getWirelessChannel(sign.getLine(1));
		
		channel.turnOn(100);
	}
}
