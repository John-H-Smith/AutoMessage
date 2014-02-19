package me.shawshark.automessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class main extends JavaPlugin {
	
	public List<messages> messages = new ArrayList<messages>();
	public BukkitTask task;
	int timer;
	int count;
	
	public void onEnable() {
		saveDefaultConfig();
		saveConfig();
		
		getCommand("automessage").setExecutor(new Commands(this));
		
		timer = getConfig().getInt("timer");
		
		loadMessages();
		repeatingTask();
		
	}
	
	public void onDisable() {
		messages.clear();
	}
	
	public void loadMessages() {
		count = 0;
		FileConfiguration c = getConfig();
		
		for( String s : c.getStringList("messages")) {
			String[] messages = s.split(",,");
			int id = Integer.parseInt(messages[0]);
			String message = messages[1];
			
			this.messages.add(new messages(message, id));
			count++;
		}
		System.out.println("<AutoMessage> Loaded " + count + " messages!");
	}
	
	public void repeatingTask() {
		task = new BukkitRunnable() {
			
			@Override
			public void run() {
				int messageSize = messages.size();
				Random rand = new Random();
				int randomNumber = rand.nextInt(messageSize);
				
				for(messages m : messages) {
					if(m.id == randomNumber) {
						Bukkit.broadcastMessage(Colour(m.message));
						return;
					}
				}
			}
		}.runTaskTimer(this, 20 * timer, 20 * timer);
	}
	
	public String Colour(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
