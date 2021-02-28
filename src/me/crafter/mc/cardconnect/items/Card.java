package me.crafter.mc.cardconnect.items;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import me.crafter.mc.cardconnect.CardConnect;
import net.md_5.bungee.api.ChatColor;

public class Card {
	
	static Material CardMaterial = Material.PAPER;
	static NamespacedKey KeyMeta = new NamespacedKey(CardConnect.getInstance(), "CardConnect.card");
	static NamespacedKey KeyId = new NamespacedKey(CardConnect.getInstance(), "CardConnect.card.id");
	static NamespacedKey KeyLevel = new NamespacedKey(CardConnect.getInstance(), "CardConnect.card.level");
	
	private ItemStack itemStack;
	private CardType cardType;
	private int level;
	
	private Card(CardType cardType) {
		this.cardType = cardType;
		this.itemStack = new ItemStack(Material.PAPER);
	}
	
	private Card(ItemStack itemStack, CardType cardType, int level) {
		this.itemStack = itemStack;
		this.cardType = cardType;
		this.level = level;
	}
	
	public static Card fromItemStack(ItemStack itemStack) {
		if (!isCard(itemStack)) return null;
		PersistentDataContainer data = itemStack.getItemMeta().getPersistentDataContainer();
		int id = data.get(KeyId, PersistentDataType.INTEGER);
		int level = data.get(KeyLevel, PersistentDataType.INTEGER);
		CardType cardType = CardType.valueFrom(id);
		return new Card(itemStack, cardType, level);
	}
	
	public static Card createNaiveCard(CardType cardType, int level) {
		ItemStack itemStack = new ItemStack(CardMaterial);
		GenericItem.markItem(itemStack, true);
		ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(CardMaterial);
		int id = cardType.getValue();
		
		// Display and lore
		itemMeta.setDisplayName(ChatColor.GOLD + cardType.name() + " card");
		List<String> lore = new LinkedList<String>();
		lore.add(ChatColor.WHITE + "This is a naive card.");
		lore.add(ChatColor.WHITE + cardType.name() + " | lv. " + level + " | id. " + cardType.getValue());
		itemMeta.setLore(lore);
		
		// Data
		PersistentDataContainer data = itemMeta.getPersistentDataContainer();
		data.set(KeyMeta, PersistentDataType.INTEGER, 1);
		data.set(KeyId, PersistentDataType.INTEGER, id);
		data.set(KeyLevel, PersistentDataType.INTEGER, level);
		
		// Model data
		itemMeta.setCustomModelData(id);
		
		itemStack.setItemMeta(itemMeta);
		return new Card(itemStack, cardType, level);
	}

	public ItemStack getItemStack() { return itemStack; }
	public CardType getType() { return cardType; }
	public int getId() { return cardType.getValue(); }
	public int getLevel() { return level; }
	public int getAmount() { return itemStack.getAmount(); }
	
	public boolean isSimilar(Card card) {
		return card.getLevel() == this.getLevel() && card.getType() == this.getType();
	}
	
	@Override
	public String toString() {
		return "Card{Type=" + cardType.name() + ", Level=" + level + "}";
	}
	
	public static boolean isCard(ItemStack itemStack) {
		if (itemStack == null) return false;
		if (itemStack.getType() != CardMaterial) return false;
		if (!itemStack.hasItemMeta()) return false;
		if (!itemStack.getItemMeta().getPersistentDataContainer().getKeys().contains(KeyMeta)) return false;
		return true;
	}

}
