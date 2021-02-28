package me.crafter.mc.cardconnect;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.crafter.mc.cardconnect.crafting.CraftingListener;
import me.crafter.mc.cardconnect.crafting.Init;
import me.crafter.mc.cardconnect.items.DeathDropListener;

public class CardConnect extends JavaPlugin {

	public final CommandExecutor cm = new CommandManager();
	private static Plugin plugin;

    public void onEnable() {
        plugin = this;
    	Init.init(plugin);
		getServer().getPluginManager().registerEvents(new CraftingListener(), this);
		getServer().getPluginManager().registerEvents(new DeathDropListener(), this);
        getCommand("card").setExecutor(cm);
    }

    public void onDisable() {
    }

    public static Plugin getInstance() {
    	return plugin;
    }
	
}
