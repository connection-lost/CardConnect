package me.crafter.mc.cardconnect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.crafter.mc.cardconnect.items.Card;
import me.crafter.mc.cardconnect.items.CardAPI;
import me.crafter.mc.cardconnect.items.CardType;
import net.md_5.bungee.api.ChatColor;

public class CommandManager implements CommandExecutor, TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			switch (command.getName()) {
			case "card":
				if (args.length == 0 ) {
					sender.sendMessage(ChatColor.BLUE + "[CardConnect] " + ChatColor.YELLOW + "Plugin by connection_lost.");
				} else {
					switch (args[0]) {
					case "createnaive":
						if (args.length != 3) {
							player.sendMessage("/" + label + " " + args[1] + " <id> <level>");
						}
						try {
							int id = Integer.parseInt(args[1]);
							int level = Integer.parseInt(args[2]);
							Card card = Card.createNaiveCard(CardType.valueFrom(id), level);
							player.getInventory().addItem(card.getItemStack());
							player.sendMessage("You get: " + card.toString());
						} catch (Exception ex) {
							player.sendMessage("/" + label + " " + args[1] + " <id> <level>");
						}
						
						break;
					case "check":
						if (CardAPI.isCard(player.getInventory().getItemInMainHand())) {
							player.sendMessage("Card info:");
							player.sendMessage(CardAPI.getCard(player.getInventory().getItemInMainHand()).toString());
						} else {
							player.sendMessage("No card detected.");
						}
						break;
					}
				}
				break;
			}
		} else {
			sender.sendMessage(ChatColor.BLUE + "[CardConnect] " + ChatColor.YELLOW + "Plugin by connection_lost.");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (command.getName()) {
		case "card":
			if (args.length == 1) {
				List<String> completion = new ArrayList<String>();
				completion.add("createnaive");
				completion.add("check");
				return completion;
			}
		}
		return null;
	}

}
