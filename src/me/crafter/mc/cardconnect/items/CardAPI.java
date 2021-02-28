package me.crafter.mc.cardconnect.items;

import org.bukkit.inventory.ItemStack;

public class CardAPI {
	
	public static boolean isCard(ItemStack itemStack) {
		return Card.isCard(itemStack);
	}
	
	public static Card getCard(ItemStack itemStack) {
		return Card.fromItemStack(itemStack);
	}

}
