package me.genix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SwapThread extends Thread {

	private long waitTime;
	public boolean running = true;
	
	public SwapThread(long waitTime) {
		this.waitTime = Math.max(waitTime, 10000);
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(waitTime - 10000L);
				
				for (int i = 10; i > 0; i--) {
					Bukkit.broadcastMessage(ChatColor.RED + "Swapping positions in " + i + " second" + (i > 1 ? "s" : ""));
					Thread.sleep(1000L);
				}
				
				List<Player> list = new ArrayList<Player>(Bukkit.getOnlinePlayers());
				PosSwap.instance.locations.clear();
				Collections.shuffle(list);
				int count = 0;
				for (Player player : list) {
					int index = count;
					index++;
					if (index > list.size() - 1) {
						index = 0;
					}
					PlayerLocation location = new PlayerLocation();
					location.player = player;
					location.location = list.get(index).getLocation();
					PosSwap.instance.locations.add(location);
					count++;
				}
				PosSwap.instance.setTask(new Runnable() {

					@Override
					public void run() {
						for (PlayerLocation location : PosSwap.instance.locations) {
							location.player.teleport(location.location);
						}
						PosSwap.instance.locations.clear();
					}
					
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class PlayerLocation {
	public Player player;
	public Location location;
}
