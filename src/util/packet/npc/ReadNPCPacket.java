package util.packet.npc;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import main.Main;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import util.GetValue;
import util.gui.Guis;
import util.item.Items;

public class ReadNPCPacket {
	public static HashMap<Player, Integer> clicks = new HashMap<>();
	public static void npcPacket(PacketPlayInUseEntity packet, Player p) {
		int id = (int) GetValue.getValue(packet, "a");
		if(SpawnNPC.npcs.containsKey(id)) {
			if(GetValue.containsValue(GetValue.getValue(packet, "b"), "a")) {
				clicks.put(p, (clicks.containsKey(p) ? clicks.get(p) : 0) + 1);
				if(clicks.get(p) >= 4) {
					clicks.put(p, 0);
					new BukkitRunnable() {
						@Override
						public void run() {
							if(p.getInventory().getItemInMainHand().equals(Items.getNPCEditor())) {
								SpawnNPC.editnpc.put(p, SpawnNPC.npcs.get(id));
								p.openInventory(Guis.npcedit(SpawnNPC.npcs.get(id)));
							}else SpawnNPC.command.get(SpawnNPC.npcs.get(id)).command(packet, p);
						}
					}.runTask(Main.main);
				}
			}else {
				new BukkitRunnable() {
					@Override
					public void run() {
						if(p.getInventory().getItemInMainHand().equals(Items.getNPCEditor())) {
							SpawnNPC.editnpc.put(p, SpawnNPC.npcs.get(id));
							p.openInventory(Guis.npcedit(SpawnNPC.npcs.get(id)));
						}else SpawnNPC.command.get(SpawnNPC.npcs.get(id)).command(packet, p);					
					}
				}.runTask(Main.main);
			}
		}
	}
}
