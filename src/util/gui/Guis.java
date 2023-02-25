package util.gui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.level.EntityPlayer;
import util.MathHelper;
import util.file.FileSystem;
import util.item.Items;
import util.packet.npc.SpawnNPC;

public class Guis {
	public static Inventory npcedit(EntityPlayer ep) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "NPC 설정");
		npcguiedit(ep, inv);
		return inv;
	}
	public static void npcguiedit(EntityPlayer ep, Inventory inv) {
		inv.clear();
		inv.setItem(10, Items.getColorBool("망토 설정", SpawnNPC.properties.get(ep).isCape(), "켜짐", "꺼짐"));
		inv.setItem(11, Items.getColorBool("재킷 설정", SpawnNPC.properties.get(ep).isJacket(), "켜짐", "꺼짐"));
		inv.setItem(12, Items.getColorBool("왼쪽 소매 설정", SpawnNPC.properties.get(ep).isLeftsleeve(), "켜짐", "꺼짐"));
		inv.setItem(13, Items.getColorBool("오른쪽 소매 설정", SpawnNPC.properties.get(ep).isRightsleeve(), "켜짐", "꺼짐"));
		inv.setItem(14, Items.getColorBool("왼쪽 바지 설정", SpawnNPC.properties.get(ep).isLeftpants(), "켜짐", "꺼짐"));
		inv.setItem(15, Items.getColorBool("오른쪽 바지 설정", SpawnNPC.properties.get(ep).isRightpants(), "켜짐", "꺼짐"));
		inv.setItem(16, Items.getColorBool("모자 설정", SpawnNPC.properties.get(ep).isHat(), "켜짐", "꺼짐"));
		inv.setItem(19, Items.getColorBool("불 설정", SpawnNPC.properties.get(ep).isOnfire(), "켜짐", "꺼짐"));
		inv.setItem(20, Items.getColorBool("투명 설정", SpawnNPC.properties.get(ep).isInvisible(), "켜짐", "꺼짐"));
		inv.setItem(21, Items.getColorBool("발광 설정", SpawnNPC.properties.get(ep).isGlowing(), "켜짐", "꺼짐"));
		inv.setItem(22, Items.getColorBool("주로 사용하는 손 설정", SpawnNPC.properties.get(ep).isLeftmainhand(), "왼손", "오른손"));
		inv.setItem(23, Items.getColorInt("포즈 설정", SpawnNPC.properties.get(ep).getPose(), "기본", "웅크리기", "엎드리기", "눕기"));
		inv.setItem(24, Items.getColorInt("왼쪽 앵무새 설정", SpawnNPC.properties.get(ep).getLeftvariant(), "꺼짐", "빨간색", "파란색", "초록색", "하늘색", "흰색"));
		inv.setItem(25, Items.getColorInt("오른쪽 앵무새 설정", SpawnNPC.properties.get(ep).getRightvariant(), "꺼짐", "빨간색", "파란색", "초록색", "하늘색", "흰색"));
		inv.setItem(28, Items.getItem("머리 아이템 슬롯", "클릭으로 아이템 설정", SpawnNPC.properties.get(ep).getHead()));
		inv.setItem(29, Items.getItem("갑옷 아이템 슬롯", "클릭으로 아이템 설정", SpawnNPC.properties.get(ep).getChest()));
		inv.setItem(30, Items.getItem("바지 아이템 슬롯", "클릭으로 아이템 설정", SpawnNPC.properties.get(ep).getLegs()));
		inv.setItem(31, Items.getItem("신발 아이템 슬롯", "클릭으로 아이템 설정", SpawnNPC.properties.get(ep).getFeet()));
		inv.setItem(32, Items.getItem("기본 손 아이템 슬롯", "클릭으로 아이템 설정", SpawnNPC.properties.get(ep).getMainhand()));
		inv.setItem(33, Items.getItem("보조 손 아이템 슬롯", "클릭으로 아이템 설정", SpawnNPC.properties.get(ep).getOffhand()));
		inv.setItem(34, Items.reset());
		inv.setItem(45, Items.getItemStack(Material.CHEST, "세팅 설정", "클릭으로 세팅 설정 창 가기"));
		inv.setItem(49, Items.getItemStack(Material.COMMAND_BLOCK, "명령 설정", "클릭으로 명령 설정 창 가기"));
		inv.setItem(52, Items.getItemStack(Material.NAME_TAG, "이름 바꾸기", "클릭으로 이름 바꾸기"));
		inv.setItem(53, Items.getItemStack(Material.IRON_AXE, ChatColor.RED + "NPC 제거", ChatColor.RED + "클릭으로 NPC 지우기"));
	}
	public static Inventory propertiesMenu(String name) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "설정 메뉴");
		propertiesSet(name, inv);
		return inv;
	}
	@SuppressWarnings({"unchecked", "unused"})
	public static void propertiesSet(String name, Inventory inv) {
		inv.clear();
		int i = 10;
		char d = 'A';
		if(!FileSystem.values.containsKey("properties")) {
			JSONObject o = new JSONObject();
			o.put(name, new JSONArray());
			FileSystem.save("properties", o);
		}
		for(Object s : (JSONArray)((JSONObject)FileSystem.values.get("properties")).get(name)) {
			inv.setItem(i, Items.getItemStack(Material.LIME_STAINED_GLASS_PANE, "설정 #" + d, "번호: " + (d - 'A' + 1)));
			i = MathHelper.maskadder(i, 1, 17, 18, 26, 27);
			d++;
		}
		inv.setItem(49, Items.getBack());
		if(((HashMap<String, JSONArray>)FileSystem.values.get("properties")).get(name).size() != 21) {
			inv.setItem(51, Items.getItemStack(Material.WRITABLE_BOOK, "추가하기", "클릭으로 지금 사용하는 설정을 추가"));
		}
		if(!((HashMap<String, JSONArray>)FileSystem.values.get("properties")).get(name).isEmpty()) {
			inv.setItem(47, Items.getItemStack(Material.IRON_AXE, "모두 지우기", "클릭으로 모든 설정을 제거"));
		}
	}
	public static Inventory propertyMenu(int slot) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "설정 관리");
		propertySet(inv, slot);
		return inv;
	}
	public static void propertySet(Inventory inv, int slot) {
		inv.setItem(10, Items.getItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "뒤로 가기", "클릭으로 뒤로 이동"));
		inv.setItem(12, Items.getItemStack(Material.IRON_AXE, "설정 삭제", "클릭으로 설정 삭제"));
		inv.setItem(14, Items.getItemStack(Material.WRITABLE_BOOK, "설정 덮어 쓰기", "클릭으로 설정 덮어 쓰기"));
		inv.setItem(16, Items.getItemStack(Material.GREEN_STAINED_GLASS_PANE, "이 설정 사용", "클릭으로 이 설정 사용"));
	}
	public static Inventory commandsMenu(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "커맨드 메뉴");
		commandsSet(p, inv);
		return inv;
	}
	public static void commandsSet(Player p, Inventory inv) {
		inv.clear();
		int i = 10;
		char d = 'A';
		for(String s : SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).getCommands()) {
			inv.setItem(i, Items.getItemStack(Material.LIME_STAINED_GLASS_PANE, "커맨드 #" + d, "번호: " + (d - 'A' + 1) + ", 커맨드 : " + s));
			i = MathHelper.maskadder(i, 1, 17, 18, 26, 27);
			d++;
		}
		inv.setItem(49, Items.getBack());
		if(SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).getCommands().size() != 21) {
			inv.setItem(51, Items.getItemStack(Material.WRITABLE_BOOK, "추가하기", "클릭으로 커맨드를 추가"));
		}
		if(!SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).getCommands().isEmpty()) {
			inv.setItem(47, Items.getItemStack(Material.IRON_AXE, "모두 지우기", "클릭으로 모든 커맨드를 제거"));
		}
	}
	public static Inventory commandMenu(int slot) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "커맨드 관리");
		commandSet(inv);
		return inv;
	}
	public static void commandSet(Inventory inv) {
		inv.setItem(11, Items.getItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "뒤로 가기", "클릭으로 뒤로 이동"));
		inv.setItem(15, Items.getItemStack(Material.IRON_AXE, "커맨드 삭제", "클릭으로 설정 삭제"));
	}
	public static Inventory HeadMenu(EntityPlayer ep) {
		return abstractitemMenu("머리 슬롯 메뉴", SpawnNPC.properties.get(ep).getHead());
	}
	public static Inventory ChestMenu(EntityPlayer ep) {
		return abstractitemMenu("갑옷 슬롯 메뉴", SpawnNPC.properties.get(ep).getChest());
	}
	public static Inventory LegsMenu(EntityPlayer ep) {
		return abstractitemMenu("레깅스 슬롯 메뉴", SpawnNPC.properties.get(ep).getLegs());
	}
	public static Inventory FeetMenu(EntityPlayer ep) {
		return abstractitemMenu("신발 슬롯 메뉴", SpawnNPC.properties.get(ep).getFeet());
	}
	public static Inventory MainhandMenu(EntityPlayer ep) {
		return abstractitemMenu("주로 사용하는 손 슬롯 메뉴", SpawnNPC.properties.get(ep).getMainhand());
	}
	public static Inventory OffhandMenu(EntityPlayer ep) {
		return abstractitemMenu("반대편 손 슬롯 메뉴", SpawnNPC.properties.get(ep).getOffhand());
	}
	private static Inventory abstractitemMenu(String name, ItemStack i) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + name);
		itemSet(inv, i);
		return inv;
	}
	public static void itemSet(Inventory inv, ItemStack i) {
		inv.setItem(10, Items.getItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "뒤로 가기", "클릭으로 뒤로 이동"));
		inv.setItem(13, Items.getItem("사용중인 아이템", "자신의 인벤토리를 클릭하면 그 아이템을 슬롯에 대신 장착합니다!", i));
		inv.setItem(16, Items.getItemStack(Material.BARRIER, "아이템 초기화", "클릭으로 슬롯에 장착중인 아이템 초기화"));
	}
}
