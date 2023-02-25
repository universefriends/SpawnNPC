package util.gui.sign;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.block.CraftSign;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.state.IBlockData;
import util.packet.Command;
import util.packet.SendPacket;

public class SignGUI {
	public static HashMap<Player, Command<PacketPlayInUpdateSign>> commands = new HashMap<>();
	public static void openSign(Player p, Command<PacketPlayInUpdateSign> commnad, String... args) {
		BlockPosition blockPosition = new BlockPosition(0, 319, 0);
		IBlockData data = CraftMagicNumbers.getBlock(Material.OAK_SIGN).m();
		SendPacket.sendPacket(new PacketPlayOutBlockChange(blockPosition, data));
		IChatBaseComponent[] components = CraftSign.sanitizeLines(args);
		TileEntitySign sign = new TileEntitySign(blockPosition, data);
		System.arraycopy(components, 0, sign.d, 0, sign.d.length);
		SendPacket.sendPacket(sign.c(), p);
		SendPacket.sendPacket(new PacketPlayOutOpenSignEditor(blockPosition), p);
		commands.put(p, commnad);
	}
}
