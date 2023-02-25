package main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.level.EntityPlayer;
import util.event.EventListener;
import util.file.FileSystem;
import util.item.Items;
import util.packet.ReadPacket;
import util.packet.npc.SpawnNPC;
public class Main extends JavaPlugin{
	public static Main main;
	public static HashMap<Player, String> game = new HashMap<>();
	public void onEnable() {
		main = this;
		FileSystem.setup();
		SpawnNPC.setupNPCS();
		Bukkit.getPluginCommand("npckit").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender arg0, org.bukkit.command.Command arg1, String l, String[] args) {
				if(arg0.isOp() && arg0 instanceof Player) {
					((Player)arg0).getInventory().addItem(Items.getNPCSpawner(), Items.getNPCEditor());
				}
				return false;
			}
		});
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
	}
	@Override
	public synchronized void onDisable() {
		for(EntityPlayer ep : SpawnNPC.npclist)
		SpawnNPC.removeNPC(ep);
		for(Player p : Bukkit.getOnlinePlayers())
		ReadPacket.uninject(p);
	}
}
