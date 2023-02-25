package util.packet;

import java.util.List;

import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import main.Main;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.world.level.block.state.IBlockData;
import util.gui.sign.SignGUI;
import util.packet.npc.ReadNPCPacket;

public class ReadPacket {
	public static void readPacket(Player player) {
		if(((CraftPlayer)player).getHandle().b.b.m.pipeline().get("PacketInjector") != null) return;
		((CraftPlayer)player).getHandle().b.b.m.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
			protected void decode(ChannelHandlerContext arg0, Packet<?> arg1, List<Object> arg2) throws Exception {
				arg2.add(arg1);
				readPacket(arg1, player);
			};
		});
	}
	public static void inject(Player p) {
		readPacket(p);
	}
	public static void uninject(Player p) {
		if(((CraftPlayer)p).getHandle().b.b.m.pipeline().get("PacketInjector") != null)
		((CraftPlayer)p).getHandle().b.b.m.pipeline().remove("PacketInjector");
	}
	public static void readPacket(Packet<?> packet, Player p) {
		if(packet instanceof PacketPlayInUseEntity) {
			ReadNPCPacket.npcPacket((PacketPlayInUseEntity) packet, p);
		}
		else if(packet instanceof PacketPlayInUpdateSign) {
			if(SignGUI.commands.containsKey(p)) {
				new BukkitRunnable() {
					@Override
					public void run() {
				        IBlockData data = CraftMagicNumbers.getBlock(p.getWorld().getBlockAt(0, 319, 0).getType()).m();
				        SendPacket.sendPacket(new PacketPlayOutBlockChange(new BlockPosition(0, 319, 0), data), p);
						SignGUI.commands.get(p).command((PacketPlayInUpdateSign)packet, p);
						SignGUI.commands.remove(p);
					}
				}.runTask(Main.main);
			}
		}
	}
}
