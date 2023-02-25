package util.packet;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.network.protocol.Packet;

public class SendPacket {
	public static void sendPacket(Packet<?> packet, Player player) {
		((CraftPlayer)player).getHandle().b.a(packet);
	}
	public static void sendPacket(Packet<?> packet) {
		for(Player player : Bukkit.getOnlinePlayers())
		((CraftPlayer)player).getHandle().b.a(packet);
	}
}
