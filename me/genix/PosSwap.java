package me.genix;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PosSwap extends JavaPlugin {

	public SwapThread thread;
	public List<PlayerLocation> locations = new ArrayList<PlayerLocation>();
	
	public static PosSwap instance;
	
	@Override
	public void onEnable() {
		instance = this;
		SwapCommand command = new SwapCommand();
		getCommand("PosSwap").setExecutor(command);
	}
	
	@Override
	public void onDisable() {
		if (thread != null) {
			thread.running = false;
			thread.stop();
		}
		instance = null;
	}
	
	public void setTask(Runnable runnable) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, runnable, 0, 0);
	}
}
