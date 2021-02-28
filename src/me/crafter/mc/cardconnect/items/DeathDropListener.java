package me.crafter.mc.cardconnect.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.crafter.mc.cardconnect.CardConnect;
import me.crafter.mc.revapi.RevFormat;
import net.md_5.bungee.api.ChatColor;

public class DeathDropListener implements Listener {
	
	// TODO use better way
	Map<Player, List<ItemStack>> dropKeep = new HashMap<Player, List<ItemStack>>();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		// Get saved items
		List<ItemStack> drops = event.getDrops();
		List<ItemStack> saved = new ArrayList<ItemStack>();
		for (ItemStack itemStack : drops) {
			if (BrokenAPI.hasDurability(itemStack)) {
				saved.add(itemStack);
			}
		}
		
		// Record saved items
		if (!saved.isEmpty()) {
			dropKeep.put(player, new ArrayList<ItemStack>());
			for (ItemStack itemStack : saved) {
				drops.remove(itemStack);
				BrokenAPI.dealDamage(itemStack, 1);
				dropKeep.get(player).add(itemStack);
			}
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		if (dropKeep.containsKey(player)) {
			final List<ItemStack> saved = dropKeep.remove(player);
			Bukkit.getScheduler().runTaskLater(CardConnect.getInstance(), new Runnable() {
				@Override
				public void run() {
					List<String> itemNames = new ArrayList<String>();
					for (ItemStack itemStack : saved) {
						itemNames.add(getItemName(itemStack) + ChatColor.RESET);
						player.getInventory().addItem(itemStack);
					}
					player.sendMessage(RevFormat.formatMessageFull("CardConnect", "以下物品受到掉落保护:"));
					player.sendMessage(Strings.join(itemNames, ' '));
				}
			}, 7L);
		}
	}
	
	private String getItemName(ItemStack itemStack) {
		if (itemStack.hasItemMeta()) {
			if (itemStack.getItemMeta().hasDisplayName()) {
				return itemStack.getItemMeta().getDisplayName();
			}
		}
		return itemStack.getType().getTranslationKey();
	}
	
}
