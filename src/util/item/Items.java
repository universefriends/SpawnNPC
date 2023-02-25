package util.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import util.GetValue;
import util.gui.sign.SignGUI;
import util.packet.Command;
import util.packet.npc.SpawnNPC;
@SuppressWarnings("deprecation")
public class Items {
	public static net.minecraft.world.item.ItemStack toNMS(ItemStack i){
		return CraftItemStack.asNMSCopy(i);
	}
	public static void onItemUse(ItemStack i, Player p, boolean b) {
		if(i.equals(getNPCSpawner())) {
			SignGUI.openSign(p, new Command<PacketPlayInUpdateSign>() {
				@Override
				public void command(PacketPlayInUpdateSign packet, Player p) {
					if(packet.c()[0].length() <= 15 && packet.c()[0].length() > 0 && packet.c()[1].length() <= 15 && packet.c()[1].length() > 0){
						packet.c()[0].replace("&", "§");
						packet.c()[1].replace("&", "§");
						if(packet.c()[2].length() == 0 || packet.c()[3].length() == 0)
						SpawnNPC.spawnNPC(packet.c()[0], packet.c()[1], new Command<PacketPlayInUseEntity>() {
							@Override
							public void command(PacketPlayInUseEntity packet, Player p) {
								for(String command : SpawnNPC.properties.get(SpawnNPC.npcs.get((int)GetValue.getValue(packet, "a"))).getCommands()) {
									p.performCommand(command);
								}
							}
						}, p.getLocation(), true);
						else try {
							SpawnNPC.spawnNPC(packet.c()[0], packet.c()[1], new Command<PacketPlayInUseEntity>() {
								@Override
								public void command(PacketPlayInUseEntity packet, Player p) {
									for(String command : SpawnNPC.properties.get(SpawnNPC.npcs.get((int)GetValue.getValue(packet, "a"))).getCommands()) {
										p.performCommand(command);
									}
								}
							}, p.getLocation(), true, Integer.parseInt(packet.c()[2]), Integer.parseInt(packet.c()[3]));
						}catch (Exception e) {
							p.sendMessage(ChatColor.RED + "3, 4번 줄에는 숫자만 들어가야합니다!");
						}
					}
				}
			});
		}
	}
	public static ItemStack getMenu() {
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta compass = item.getItemMeta();
		compass.setDisplayName(ChatColor.GREEN + "메뉴");
		item.setItemMeta(compass);
		return item;
	}
	public static ItemStack getBack() {
		return getItemStack(Material.FEATHER, ChatColor.RED + "뒤로 가기", "클릭으로 뒤로 이동");
	}
	public static ItemStack getNPCSpawner() {
		ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "NPC 생성기");
		itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "" + ChatColor.BOLD + "이 아이템을 들고 클릭을 하면 NPC를 만들수 있습니다."));
		item.setItemMeta(itemmeta);
		return item;
	}
	public static ItemStack getNPCEditor() {
		ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "NPC 수정");
		itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "" + ChatColor.BOLD + "이 아이템을 들고 NPC 클릭을 하면 수정할수 있습니다."));
		item.setItemMeta(itemmeta);
		return item;
	}
	public static ItemStack getItemStack(Material type, String name, String lore) {
		ItemStack item = new ItemStack(type);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + name);
		itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "" + ChatColor.BOLD + lore));
		item.setItemMeta(itemmeta);
		return item;
	}
	public static ItemStack reset() {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "초기화");
		itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "" + ChatColor.BOLD + "클릭으로 초기화"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public static ItemStack getColorBool(String name, boolean b, String lT, String lF) {
		ItemStack item = new ItemStack(b?Material.LIME_STAINED_GLASS_PANE:Material.RED_STAINED_GLASS_PANE);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName((b?ChatColor.GREEN:ChatColor.RED) + "" + ChatColor.BOLD + name);
		itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "" + ChatColor.BOLD + "클릭으로 다음으로",
				(b?ChatColor.GREEN + "> ":ChatColor.YELLOW + "- ") + ChatColor.BOLD + lT,
				(!b?ChatColor.GREEN + "> ":ChatColor.YELLOW + "- ") + ChatColor.BOLD + lF));
		item.setItemMeta(itemmeta);
		return item;
	}
	public static ItemStack getColorInt(String name, int i, String Fm, String... l) {
		ItemStack item = new ItemStack(i != -1?Material.LIME_STAINED_GLASS_PANE:Material.RED_STAINED_GLASS_PANE);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName((i != -1?ChatColor.GREEN:ChatColor.RED) + "" + ChatColor.BOLD + name);
		List<String> array = new ArrayList<>();
		array.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "클릭으로 다음으로");
		array.add((i == -1?ChatColor.GREEN + "> ":ChatColor.YELLOW + "- ") + ChatColor.BOLD + Fm);
		int i23=0;
		for(String s : l) {
			array.add((i23++ == i?ChatColor.GREEN + "> ":ChatColor.YELLOW + "- ") + ChatColor.BOLD + s);
		}
		itemmeta.setLore(array);
		item.setItemMeta(itemmeta);
		return item;
	}
	public static ItemStack getItem(String label, String lore, ItemStack i) {
		boolean b = i == null || i.getType() == Material.AIR;
		ItemStack I = b ? new ItemStack(Material.BARRIER, 1) : i;
		ItemMeta m = I.getItemMeta();
		m.setDisplayName("" + (b?ChatColor.RED:ChatColor.GREEN) + ChatColor.BOLD + label);
		m.setLore(Arrays.asList(ChatColor.YELLOW + "" + ChatColor.BOLD + lore));
		I.setItemMeta(m);
		return I;
	}
	public static ItemStack getHead(String s) {
		ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) stack.getItemMeta();
		meta.setOwner(s);
		stack.setItemMeta(meta);
		return stack;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject serialize(ItemStack i) {
		JSONObject JsonObject = new JSONObject();
		JsonObject.put("id", i.getType().toString().toLowerCase());
		JsonObject.put("count", i.getAmount());
		if (i.hasItemMeta()) {
			JsonObject.put("tag", i.getItemMeta().getAsString());
		}
		return JsonObject;
	}
	public static ItemStack deserialize(JSONObject json) {
		String id = json.get("id").toString();
		int count = Integer.parseInt(json.get("count").toString());
		String meta;
		if(json.containsKey("tag")) {
		meta = json.get("tag").toString();
		meta = meta.replace("\\\"", "\"");
		} else meta = "";
		ItemStack item = Bukkit.getItemFactory().createItemStack(id + meta);
		item.setAmount(count);
		return item;
	}
}

