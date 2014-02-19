package me.shawshark.automessage;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

	public main m;
	
	public Commands(main m) {
		this.m = m;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lable, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("automessage")) {
			if(args.length < 1) {
				s.sendMessage(ChatColor.GOLD + "AutoMessage - Commands");
				s.sendMessage(ChatColor.GREEN + "/automessage reload - Reload the config.");
				return true;
			} else {
				if(args[0].equalsIgnoreCase("reload")) {
					m.reloadConfig();
					
					m.task.cancel();
					m.messages.clear();
					m.loadMessages();
					m.repeatingTask();
					s.sendMessage(ChatColor.GOLD + "AutoMessage: " + ChatColor.GREEN + "Reloaded config!");
					
					return true;
				}
			}
		}
		return false;
	}
}
