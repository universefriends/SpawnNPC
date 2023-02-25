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
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "NPC ����");
		npcguiedit(ep, inv);
		return inv;
	}
	public static void npcguiedit(EntityPlayer ep, Inventory inv) {
		inv.clear();
		inv.setItem(10, Items.getColorBool("���� ����", SpawnNPC.properties.get(ep).isCape(), "����", "����"));
		inv.setItem(11, Items.getColorBool("��Ŷ ����", SpawnNPC.properties.get(ep).isJacket(), "����", "����"));
		inv.setItem(12, Items.getColorBool("���� �Ҹ� ����", SpawnNPC.properties.get(ep).isLeftsleeve(), "����", "����"));
		inv.setItem(13, Items.getColorBool("������ �Ҹ� ����", SpawnNPC.properties.get(ep).isRightsleeve(), "����", "����"));
		inv.setItem(14, Items.getColorBool("���� ���� ����", SpawnNPC.properties.get(ep).isLeftpants(), "����", "����"));
		inv.setItem(15, Items.getColorBool("������ ���� ����", SpawnNPC.properties.get(ep).isRightpants(), "����", "����"));
		inv.setItem(16, Items.getColorBool("���� ����", SpawnNPC.properties.get(ep).isHat(), "����", "����"));
		inv.setItem(19, Items.getColorBool("�� ����", SpawnNPC.properties.get(ep).isOnfire(), "����", "����"));
		inv.setItem(20, Items.getColorBool("���� ����", SpawnNPC.properties.get(ep).isInvisible(), "����", "����"));
		inv.setItem(21, Items.getColorBool("�߱� ����", SpawnNPC.properties.get(ep).isGlowing(), "����", "����"));
		inv.setItem(22, Items.getColorBool("�ַ� ����ϴ� �� ����", SpawnNPC.properties.get(ep).isLeftmainhand(), "�޼�", "������"));
		inv.setItem(23, Items.getColorInt("���� ����", SpawnNPC.properties.get(ep).getPose(), "�⺻", "��ũ����", "���帮��", "����"));
		inv.setItem(24, Items.getColorInt("���� �޹��� ����", SpawnNPC.properties.get(ep).getLeftvariant(), "����", "������", "�Ķ���", "�ʷϻ�", "�ϴû�", "���"));
		inv.setItem(25, Items.getColorInt("������ �޹��� ����", SpawnNPC.properties.get(ep).getRightvariant(), "����", "������", "�Ķ���", "�ʷϻ�", "�ϴû�", "���"));
		inv.setItem(28, Items.getItem("�Ӹ� ������ ����", "Ŭ������ ������ ����", SpawnNPC.properties.get(ep).getHead()));
		inv.setItem(29, Items.getItem("���� ������ ����", "Ŭ������ ������ ����", SpawnNPC.properties.get(ep).getChest()));
		inv.setItem(30, Items.getItem("���� ������ ����", "Ŭ������ ������ ����", SpawnNPC.properties.get(ep).getLegs()));
		inv.setItem(31, Items.getItem("�Ź� ������ ����", "Ŭ������ ������ ����", SpawnNPC.properties.get(ep).getFeet()));
		inv.setItem(32, Items.getItem("�⺻ �� ������ ����", "Ŭ������ ������ ����", SpawnNPC.properties.get(ep).getMainhand()));
		inv.setItem(33, Items.getItem("���� �� ������ ����", "Ŭ������ ������ ����", SpawnNPC.properties.get(ep).getOffhand()));
		inv.setItem(34, Items.reset());
		inv.setItem(45, Items.getItemStack(Material.CHEST, "���� ����", "Ŭ������ ���� ���� â ����"));
		inv.setItem(49, Items.getItemStack(Material.COMMAND_BLOCK, "��� ����", "Ŭ������ ��� ���� â ����"));
		inv.setItem(52, Items.getItemStack(Material.NAME_TAG, "�̸� �ٲٱ�", "Ŭ������ �̸� �ٲٱ�"));
		inv.setItem(53, Items.getItemStack(Material.IRON_AXE, ChatColor.RED + "NPC ����", ChatColor.RED + "Ŭ������ NPC �����"));
	}
	public static Inventory propertiesMenu(String name) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "���� �޴�");
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
			inv.setItem(i, Items.getItemStack(Material.LIME_STAINED_GLASS_PANE, "���� #" + d, "��ȣ: " + (d - 'A' + 1)));
			i = MathHelper.maskadder(i, 1, 17, 18, 26, 27);
			d++;
		}
		inv.setItem(49, Items.getBack());
		if(((HashMap<String, JSONArray>)FileSystem.values.get("properties")).get(name).size() != 21) {
			inv.setItem(51, Items.getItemStack(Material.WRITABLE_BOOK, "�߰��ϱ�", "Ŭ������ ���� ����ϴ� ������ �߰�"));
		}
		if(!((HashMap<String, JSONArray>)FileSystem.values.get("properties")).get(name).isEmpty()) {
			inv.setItem(47, Items.getItemStack(Material.IRON_AXE, "��� �����", "Ŭ������ ��� ������ ����"));
		}
	}
	public static Inventory propertyMenu(int slot) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "���� ����");
		propertySet(inv, slot);
		return inv;
	}
	public static void propertySet(Inventory inv, int slot) {
		inv.setItem(10, Items.getItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "�ڷ� ����", "Ŭ������ �ڷ� �̵�"));
		inv.setItem(12, Items.getItemStack(Material.IRON_AXE, "���� ����", "Ŭ������ ���� ����"));
		inv.setItem(14, Items.getItemStack(Material.WRITABLE_BOOK, "���� ���� ����", "Ŭ������ ���� ���� ����"));
		inv.setItem(16, Items.getItemStack(Material.GREEN_STAINED_GLASS_PANE, "�� ���� ���", "Ŭ������ �� ���� ���"));
	}
	public static Inventory commandsMenu(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Ŀ�ǵ� �޴�");
		commandsSet(p, inv);
		return inv;
	}
	public static void commandsSet(Player p, Inventory inv) {
		inv.clear();
		int i = 10;
		char d = 'A';
		for(String s : SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).getCommands()) {
			inv.setItem(i, Items.getItemStack(Material.LIME_STAINED_GLASS_PANE, "Ŀ�ǵ� #" + d, "��ȣ: " + (d - 'A' + 1) + ", Ŀ�ǵ� : " + s));
			i = MathHelper.maskadder(i, 1, 17, 18, 26, 27);
			d++;
		}
		inv.setItem(49, Items.getBack());
		if(SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).getCommands().size() != 21) {
			inv.setItem(51, Items.getItemStack(Material.WRITABLE_BOOK, "�߰��ϱ�", "Ŭ������ Ŀ�ǵ带 �߰�"));
		}
		if(!SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).getCommands().isEmpty()) {
			inv.setItem(47, Items.getItemStack(Material.IRON_AXE, "��� �����", "Ŭ������ ��� Ŀ�ǵ带 ����"));
		}
	}
	public static Inventory commandMenu(int slot) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Ŀ�ǵ� ����");
		commandSet(inv);
		return inv;
	}
	public static void commandSet(Inventory inv) {
		inv.setItem(11, Items.getItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "�ڷ� ����", "Ŭ������ �ڷ� �̵�"));
		inv.setItem(15, Items.getItemStack(Material.IRON_AXE, "Ŀ�ǵ� ����", "Ŭ������ ���� ����"));
	}
	public static Inventory HeadMenu(EntityPlayer ep) {
		return abstractitemMenu("�Ӹ� ���� �޴�", SpawnNPC.properties.get(ep).getHead());
	}
	public static Inventory ChestMenu(EntityPlayer ep) {
		return abstractitemMenu("���� ���� �޴�", SpawnNPC.properties.get(ep).getChest());
	}
	public static Inventory LegsMenu(EntityPlayer ep) {
		return abstractitemMenu("���뽺 ���� �޴�", SpawnNPC.properties.get(ep).getLegs());
	}
	public static Inventory FeetMenu(EntityPlayer ep) {
		return abstractitemMenu("�Ź� ���� �޴�", SpawnNPC.properties.get(ep).getFeet());
	}
	public static Inventory MainhandMenu(EntityPlayer ep) {
		return abstractitemMenu("�ַ� ����ϴ� �� ���� �޴�", SpawnNPC.properties.get(ep).getMainhand());
	}
	public static Inventory OffhandMenu(EntityPlayer ep) {
		return abstractitemMenu("�ݴ��� �� ���� �޴�", SpawnNPC.properties.get(ep).getOffhand());
	}
	private static Inventory abstractitemMenu(String name, ItemStack i) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + name);
		itemSet(inv, i);
		return inv;
	}
	public static void itemSet(Inventory inv, ItemStack i) {
		inv.setItem(10, Items.getItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "�ڷ� ����", "Ŭ������ �ڷ� �̵�"));
		inv.setItem(13, Items.getItem("������� ������", "�ڽ��� �κ��丮�� Ŭ���ϸ� �� �������� ���Կ� ��� �����մϴ�!", i));
		inv.setItem(16, Items.getItemStack(Material.BARRIER, "������ �ʱ�ȭ", "Ŭ������ ���Կ� �������� ������ �ʱ�ȭ"));
	}
}
