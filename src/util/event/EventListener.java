package util.event;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.simple.JSONArray;

import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import util.MathHelper;
import util.file.FileSystem;
import util.gui.Guis;
import util.gui.sign.SignGUI;
import util.item.Items;
import util.packet.Command;
import util.packet.ReadPacket;
import util.packet.npc.Property;
import util.packet.npc.SpawnNPC;

public class EventListener implements Listener{
	public static HashMap<Player, Integer> slot = new HashMap<>();
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR) && SpawnNPC.editnpc.containsKey(p) && !e.getInventory().equals(e.getClickedInventory())) {
			e.setCancelled(true);
			Property prop = SpawnNPC.properties.get(SpawnNPC.editnpc.get(p));
			if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "머리 슬롯 메뉴")) {
				SpawnNPC.guichange.add(p);
				prop.setHead(e.getCurrentItem().clone());
				p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
				SpawnNPC.setEquip(SpawnNPC.editnpc.get(p));
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "갑옷 슬롯 메뉴")) {
				SpawnNPC.guichange.add(p);
				prop.setChest(e.getCurrentItem().clone());
				p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
				SpawnNPC.setEquip(SpawnNPC.editnpc.get(p));
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "레깅스 슬롯 메뉴")) {
				SpawnNPC.guichange.add(p);
				prop.setLegs(e.getCurrentItem().clone());
				p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
				SpawnNPC.setEquip(SpawnNPC.editnpc.get(p));
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "신발 슬롯 메뉴")) {
				SpawnNPC.guichange.add(p);
				prop.setFeet(e.getCurrentItem().clone());
				p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
				SpawnNPC.setEquip(SpawnNPC.editnpc.get(p));
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "주로 사용하는 손 슬롯 메뉴")) {
				SpawnNPC.guichange.add(p);
				prop.setMainhand(e.getCurrentItem().clone());
				p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
				SpawnNPC.setEquip(SpawnNPC.editnpc.get(p));
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "반대편 손 슬롯 메뉴")) {
				SpawnNPC.guichange.add(p);
				prop.setOffhand(e.getCurrentItem().clone());
				p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
				SpawnNPC.setEquip(SpawnNPC.editnpc.get(p));
			}
			SpawnNPC.setVisible(SpawnNPC.editnpc.get(p), true);
		}
		if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR) && SpawnNPC.editnpc.containsKey(p) && e.getInventory().equals(e.getClickedInventory())) {
			e.setCancelled(true);
			Property prop = SpawnNPC.properties.get(SpawnNPC.editnpc.get(p));
			if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "NPC 설정")) {
				switch(e.getSlot()) {
					case 10:
						prop.setCape(!prop.isCape());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 11:
						prop.setJacket(!prop.isJacket());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 12:
						prop.setLeftsleeve(!prop.isLeftsleeve());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 13:
						prop.setRightsleeve(!prop.isRightsleeve());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 14:
						prop.setLeftpants(!prop.isLeftpants());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 15:
						prop.setRightpants(!prop.isRightpants());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 16:
						prop.setHat(!prop.isHat());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 19:
						prop.setOnfire(!prop.isOnfire());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 20:
						prop.setInvisible(!prop.isInvisible());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 21:
						prop.setGlowing(!prop.isGlowing());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 22:
						prop.setLeftmainhand(!prop.isLeftmainhand());
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 23:
						prop.setPose(MathHelper.cycle(prop.getPose(), -1, 2));
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 24:
						prop.setLeftvariant(MathHelper.cycle(prop.getLeftvariant(), -1, 4));
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 25:
						prop.setRightvariant(MathHelper.cycle(prop.getRightvariant(), -1, 4));
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 28:
						SpawnNPC.guichange.add(p);
						p.openInventory(Guis.HeadMenu(SpawnNPC.editnpc.get(p)));
						break;
					case 29:
						SpawnNPC.guichange.add(p);
						p.openInventory(Guis.ChestMenu(SpawnNPC.editnpc.get(p)));
						break;
					case 30:
						SpawnNPC.guichange.add(p);
						p.openInventory(Guis.LegsMenu(SpawnNPC.editnpc.get(p)));
						break;
					case 31:
						SpawnNPC.guichange.add(p);
						p.openInventory(Guis.FeetMenu(SpawnNPC.editnpc.get(p)));
						break;
					case 32:
						SpawnNPC.guichange.add(p);
						p.openInventory(Guis.MainhandMenu(SpawnNPC.editnpc.get(p)));
						break;
					case 33:
						SpawnNPC.guichange.add(p);
						p.openInventory(Guis.OffhandMenu(SpawnNPC.editnpc.get(p)));
						break;
					case 34:
						prop.reset();
						Guis.npcguiedit(SpawnNPC.editnpc.get(p), e.getClickedInventory());
						break;
					case 45:
						SpawnNPC.guichange.add(p);
						p.openInventory(Guis.propertiesMenu(p.getName()));
						break;
					case 49:
						SpawnNPC.guichange.add(p);
						p.openInventory(Guis.commandsMenu(p));
						break;
					case 52:
						SpawnNPC.guichange.add(p);
						p.closeInventory();
						SpawnNPC.rename(p, SpawnNPC.editnpc.get(p));
						break;
					case 53:
						SpawnNPC.guichange.add(p);
						p.closeInventory();
						SpawnNPC.removeNPC(SpawnNPC.editnpc.get(p));
						break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "설정 메뉴")) {
				switch(e.getSlot()) {
				case 10:case 11:case 12:case 13:case 14:case 15:case 16:
				case 19:case 20:case 21:case 22:case 23:case 24:case 25:
				case 28:case 29:case 30:case 31:case 32:case 33:case 34:
					SpawnNPC.guichange.add(p);
					int slot = MathHelper.maskindex(e.getSlot(), 10, 17, 18, 26, 27);
					EventListener.slot.put(p, slot);
					p.openInventory(Guis.propertyMenu(slot));
					break;
				case 47:
					FileSystem.clearArray((JSONArray)FileSystem.job("properties").get(p.getName()));
					Guis.propertiesSet(p.getName(), e.getClickedInventory());
					break;
				case 49:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				case 51:
					SpawnNPC.addSettings(p, prop);
					Guis.propertiesSet(p.getName(), e.getClickedInventory());
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "설정 관리")) {
			switch(e.getSlot()) {
				case 10:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.propertiesMenu(p.getName()));
					break;
				case 12:
					SpawnNPC.removeSettings(p, slot.get(p));
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.propertiesMenu(p.getName()));
					break;
				case 14:
					SpawnNPC.setSettings(p, slot.get(p), prop);
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.propertiesMenu(p.getName()));
					break;
				case 16:
					SpawnNPC.setSetting(p, slot.get(p));
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.propertiesMenu(p.getName()));
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "커맨드 메뉴")) {
				switch(e.getSlot()) {
				case 10:case 11:case 12:case 13:case 14:case 15:case 16:
				case 19:case 20:case 21:case 22:case 23:case 24:case 25:
				case 28:case 29:case 30:case 31:case 32:case 33:case 34:
					SpawnNPC.guichange.add(p);
					int slot = MathHelper.maskindex(e.getSlot(), 10, 17, 18, 26, 27);
					EventListener.slot.put(p, slot);
					p.openInventory(Guis.commandMenu(slot));
					break;
				case 47:
					SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).removeAll();
					Guis.commandsSet(p, p.getOpenInventory().getTopInventory());
					break;
				case 49:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				case 51:
					SpawnNPC.guichange.add(p);
					p.closeInventory();
					SignGUI.openSign(p, new Command<PacketPlayInUpdateSign>() {
						@Override
						public void command(PacketPlayInUpdateSign packet, Player p) {
							SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).add(packet.c()[0] + packet.c()[1] + packet.c()[2] + packet.c()[3]);
							p.openInventory(Guis.commandsMenu(p));
						}
					});
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "커맨드 관리")) {
			switch(e.getSlot()) {
				case 11:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.commandsMenu(p));
					break;
				case 15:
					SpawnNPC.properties.get(SpawnNPC.editnpc.get(p)).remove(slot.get(p));
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.commandsMenu(p));
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "머리 슬롯 메뉴")) {
			switch(e.getSlot()) {	
				case 10:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				case 16:
					SpawnNPC.guichange.add(p);
					prop.setHead(null);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "갑옷 슬롯 메뉴")) {
			switch(e.getSlot()) {	
				case 10:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				case 16:
					SpawnNPC.guichange.add(p);
					prop.setChest(null);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "레깅스 슬롯 메뉴")) {
			switch(e.getSlot()) {	
				case 10:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				case 16:
					SpawnNPC.guichange.add(p);
					prop.setLegs(null);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "신발 슬롯 메뉴")) {
			switch(e.getSlot()) {	
				case 10:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				case 16:
					SpawnNPC.guichange.add(p);
					prop.setFeet(null);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "주로 사용하는 손 슬롯 메뉴")) {
			switch(e.getSlot()) {	
				case 10:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				case 16:
					SpawnNPC.guichange.add(p);
					prop.setMainhand(null);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				}
			}else if(p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "반대편 손 슬롯 메뉴")) {
			switch(e.getSlot()) {	
				case 10:
					SpawnNPC.guichange.add(p);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				case 16:
					SpawnNPC.guichange.add(p);
					prop.setOffhand(null);
					p.openInventory(Guis.npcedit(SpawnNPC.editnpc.get(p)));
					break;
				}
			}
			SpawnNPC.setVisible(SpawnNPC.editnpc.get(p), true);
		}
	}
	@EventHandler
	public void onCloseInv(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		p.sendMessage("CLOSEINV: " + e.getView().getTitle());
		if(SpawnNPC.guichange.contains(p)) { SpawnNPC.guichange.remove(p); return; }
		if(SpawnNPC.editnpc.containsKey(p)) SpawnNPC.editnpc.remove(p);
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ReadPacket.inject(e.getPlayer());
		SpawnNPC.setVisible(SpawnNPC.npclist, true);
	}
	@EventHandler
	public void onItemUse(PlayerInteractEvent e) {
		if(e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Items.onItemUse(e.getItem(), e.getPlayer(), e.hasBlock());
		}
	}
	@EventHandler
	public void onWorldChanged(PlayerChangedWorldEvent e) {
		SpawnNPC.setVisible(e.getPlayer(), SpawnNPC.npclist, true);
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		ReadPacket.uninject(e.getPlayer());
		SpawnNPC.setVisible(SpawnNPC.npclist, false);
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setFormat("%1$s : %2$s");
	}
}
