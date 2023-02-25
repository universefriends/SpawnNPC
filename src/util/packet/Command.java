package util.packet;

import org.bukkit.entity.Player;

import net.minecraft.network.protocol.Packet;

public interface Command<T extends Packet<?>> {
	public void command(T packet, Player p);
}
