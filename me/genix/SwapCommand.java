package me.genix;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class SwapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!arg0.isOp()) {
			arg0.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			return false;
		}
		if (arg3.length > 0) {
			switch (arg3[0].toLowerCase()) {
				case "start":
					try {
						long time = Long.parseLong(arg3[1]);
						if (PosSwap.instance.thread != null) {
							PosSwap.instance.thread.running = false;
							PosSwap.instance.thread.stop();
						}
						PosSwap.instance.thread = new SwapThread(time * 1000);
						PosSwap.instance.thread.start();
						
						Bukkit.broadcastMessage(ChatColor.RED + "[ANNOUNCEMENT] " + ChatColor.WHITE + "PosSwap has started!");
					} catch (Exception e) {
						sendUsage(arg0);
					}
					break;
				case "stop":
					if (PosSwap.instance.thread != null && PosSwap.instance.thread.running) {
						PosSwap.instance.thread.running = false;
						PosSwap.instance.thread.stop();
						arg0.sendMessage(ChatColor.GREEN + "Successfully stopped the Position Swapper.");
					} else {
						arg0.sendMessage(ChatColor.RED + "Position Swapper is not running.");
					}
					break;
				default:
					sendUsage(arg0);
					return false;
			}
			return true;
		} else {
			sendUsage(arg0);
			return false;
		}
	}

	private void sendUsage(CommandSender arg0) {
		arg0.sendMessage(ChatColor.BLUE + "Usage: ");
		arg0.sendMessage(ChatColor.GREEN + "/PosSwap start 60" + ChatColor.GRAY + " (seconds)");
		arg0.sendMessage(ChatColor.GREEN + "/PosSwap stop");
	}
}
