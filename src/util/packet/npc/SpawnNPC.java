package util.packet.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;

import main.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EnumItemSlot;
import util.GetValue;
import util.file.FileSystem;
import util.gui.sign.SignGUI;
import util.item.Items;
import util.packet.Command;
import util.packet.SendPacket;
import util.skin.GetSkin;

public class SpawnNPC {
	public static HashMap<EntityPlayer, String> getName = new HashMap<>();
	public static HashMap<EntityPlayer, String> getNickName = new HashMap<>();
	public static List<Player> guichange = new ArrayList<>();
	public static HashMap<EntityPlayer, Property> properties = new HashMap<>();
	public static List<EntityPlayer> npclist = new ArrayList<>();
	public static HashMap<Integer, EntityPlayer> npcs = new HashMap<>();
	public static HashMap<Player, EntityPlayer> editnpc = new HashMap<>();
	public static List<EntityPlayer> autoangle = new ArrayList<>();
	public static HashMap<EntityPlayer, Float> angle = new HashMap<>();
	public static HashMap<EntityPlayer, Float> pitch = new HashMap<>();
	public static HashMap<EntityPlayer, Command<PacketPlayInUseEntity>> command = new HashMap<>();
	public static EntityPlayer spawnNPC(String nickname, String name, Command<PacketPlayInUseEntity> command, Location l, boolean isNameVisiblity, float yaw, float pitch) {
		if(Bukkit.getWorld(l.getWorld().getName()) == null) {
			new WorldCreator(l.getWorld().getName()).createWorld();
		}
		MinecraftServer ms = ((CraftServer)Bukkit.getServer()).getServer();
		WorldServer ws = ((CraftWorld)l.getWorld()).getHandle();
		GameProfile profile = new GameProfile(UUID.randomUUID(), nickname);
		String[] skin = GetSkin.getSkin(name);
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", skin[0], skin[1]));
		EntityPlayer npc = new EntityPlayer(ms, ws, profile, null);
		npcs.put(npc.getBukkitEntity().getEntityId(), npc);
		npclist.add(npc);
		properties.put(npc, new Property());
		getName.put(npc, name);
		getNickName.put(npc, nickname);
		angle.put(npc, yaw);
		SpawnNPC.pitch.put(npc, pitch);
		SpawnNPC.command.put(npc, command);
		setLocation(npc, l);
		setVisible(npc, true);
		return npc;
	}
	public static EntityPlayer spawnNPC(String nickname, String name, Command<PacketPlayInUseEntity> command, Location l, boolean isNameVisiblity) {
		if(Bukkit.getWorld(l.getWorld().getName()) == null) {
			new WorldCreator(l.getWorld().getName()).createWorld();
		}
		MinecraftServer ms = ((CraftServer)Bukkit.getServer()).getServer();
		WorldServer ws = ((CraftWorld)l.getWorld()).getHandle();
		GameProfile profile = new GameProfile(UUID.randomUUID(), nickname);
		String[] skin = GetSkin.getSkin(name);
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", skin[0], skin[1]));
		EntityPlayer npc = new EntityPlayer(ms, ws, profile, null);
		npcs.put(npc.ae(), npc);
		npclist.add(npc);
		properties.put(npc, new Property());
		getName.put(npc, name);
		getNickName.put(npc, nickname);
		SpawnNPC.command.put(npc, command);
		setLocation(npc, l);
		setVisible(npc, true);
		autoangle.add(npc);
		return npc;
	}
	public static void rename(Player p, EntityPlayer ep) {
		SignGUI.openSign(p, new Command<PacketPlayInUpdateSign>() {
			@Override
			public void command(PacketPlayInUpdateSign packet, Player p) {
				if(packet.c()[0].length() <= 15 && packet.c()[0].length() > 0 && packet.c()[1].length() <= 15 && packet.c()[1].length() > 0){
					packet.c()[0].replace("&", "§");
					packet.c()[1].replace("&", "§");
					if(packet.c()[2].length() == 0 || packet.c()[3].length() == 0)
						rename(ep, packet.c()[0], packet.c()[1]);
					else try {
						rename(ep, packet.c()[0], packet.c()[1], Float.parseFloat(packet.c()[2]), Float.parseFloat(packet.c()[3]));
					}catch (Exception e) {
						p.sendMessage(ChatColor.RED + "3, 4번 줄에는 숫자만 들어가야합니다!");
					}
				}
			}
		});
	}
	public static void rename(EntityPlayer npc, String s, String ss) {
		GameProfile profile = npc.fy();
		String[] skin = GetSkin.getSkin(ss);
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", skin[0], skin[1]));
		npc.displayName = ss;
		SendPacket.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		setVisible(npc, false);
		setVisible(npc, true);
		setAutoAngle(npc, true);
	}
	public static void rename(EntityPlayer npc, String s, String ss, float i, float ii) {
		rename(npc,s,ss);
		setAngle(npc, ii, i);
	}
	public static void setLocation(EntityPlayer ep, Location l) {
		ep.g(l.getX(), l.getY(), l.getZ());
	}
	public static void setAngle(EntityPlayer ep, float yaw, float pitch) {
		setAutoAngle(ep, false);
		angle.put(ep, yaw);
		SpawnNPC.pitch.put(ep, pitch);
	}
	public static void setAutoAngle(EntityPlayer ep, boolean b) {
		if(autoangle.contains(ep) && !b) autoangle.remove(ep);
		if(!autoangle.contains(ep) && b) autoangle.add(ep);
	}
	public static void setVisible(Player p, EntityPlayer ep, boolean b) {
		if(ep != null)
		if(p.getWorld().equals(ep.getBukkitEntity().getWorld()))
		if(b) {
			SendPacket.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.a, ep), p);
			SendPacket.sendPacket(new PacketPlayOutNamedEntitySpawn(ep), p);
			ep.ai().b(DataWatcherRegistry.a.a(17), (byte)
				(properties.get(ep)).get17());
			ep.ai().b(DataWatcherRegistry.a.a(0), properties.get(ep).get0());
			if(properties.get(ep).getLeftvariant() != -1) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.a("Variant", properties.get(ep).getLeftvariant());
				ep.ai().b(DataWatcherRegistry.q.a(19), nbt);
			}
			ep.ai().b(DataWatcherRegistry.t.a(6),
					switch(properties.get(ep).getPose()) {
					case -1 -> EntityPose.a;
					case 0 -> EntityPose.f;
					case 1 -> EntityPose.b;
					default -> EntityPose.c;});
			if(properties.get(ep).getLeftvariant() != -1) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.a("Variant", properties.get(ep).getRightvariant());
				ep.ai().b(DataWatcherRegistry.q.a(20), nbt);
			}
			SendPacket.sendPacket(new PacketPlayOutEntityMetadata(ep.ae(), ep.ai(), true));
			saveNPCS();
		}else {
			SendPacket.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.e, ep), p);
			SendPacket.sendPacket(new PacketPlayOutEntityDestroy(ep.ae()), p);
		}
	}
	public static void setVisible(EntityPlayer ep, boolean b) {
		for(Player p : Bukkit.getOnlinePlayers())
		setVisible(p, ep, b);
	}
	public static void setVisible(Player p, List<EntityPlayer> epp, boolean b) {
		if(!epp.isEmpty())
		setVisible(epp, b);
	}
	public static void setVisible(List<EntityPlayer> epp, boolean b) {
		if(!epp.isEmpty())
		for(EntityPlayer ep : epp)
		setVisible(ep, b);
	}
	static {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, new Runnable() {
			@Override
			public void run() {
				for(EntityPlayer ep : npcs.values())
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(ep.getBukkitEntity().getWorld().equals(p.getWorld()))
					if(autoangle.contains(ep)) {
						Vector height = ep.getBukkitEntity().getEyeLocation().subtract(p.getEyeLocation()).toVector().normalize();
						byte pitch = (byte) (MathHelper.d((Math.toDegrees(Math.atan((height.getY())) + 270) * 256.0F) / 360.0F) + 8);
						float degrees = (float) Math.toDegrees(Math.atan2(height.getZ(), height.getX()) - Math.PI / 2);
						byte angle = (byte) MathHelper.d(((degrees + (properties.get(ep).getPose() == 2 ? 0 : 180)) * 256.0F) / 360.0F);
						SendPacket.sendPacket(new PacketPlayOutEntityHeadRotation(ep, angle), p);
						SendPacket.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(ep.ae(), angle, pitch, true), p);
					}else {
						byte pitch = (byte) ((SpawnNPC.pitch.get(ep) * 256.0F) / 360.0F);
						byte angle = (byte) ((SpawnNPC.angle.get(ep) * 256.0F) / 360.0F);
						SendPacket.sendPacket(new PacketPlayOutEntityHeadRotation(ep, angle), p);
						SendPacket.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(ep.ae(), angle, pitch, true), p);
					}
				}
			}
		}, 1, 1);
	}
	private static List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> getProp(EntityPlayer ep){
		List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> list =
			new ArrayList<>();
		if(properties.get(ep).getMainhand() != null) list.add(new Pair<>(EnumItemSlot.a, Items.toNMS(properties.get(ep).getMainhand())));
		if(properties.get(ep).getOffhand() != null) list.add(new Pair<>(EnumItemSlot.b, Items.toNMS(properties.get(ep).getOffhand())));
		if(properties.get(ep).getFeet() != null) list.add(new Pair<>(EnumItemSlot.c, Items.toNMS(properties.get(ep).getFeet())));
		if(properties.get(ep).getLegs() != null) list.add(new Pair<>(EnumItemSlot.d, Items.toNMS(properties.get(ep).getLegs())));
		if(properties.get(ep).getChest() != null) list.add(new Pair<>(EnumItemSlot.e, Items.toNMS(properties.get(ep).getChest())));
		if(properties.get(ep).getHead() != null) list.add(new Pair<>(EnumItemSlot.f, Items.toNMS(properties.get(ep).getHead())));
		return list;
	}
	public static void setEquip(EntityPlayer ep) {
		SendPacket.sendPacket(new PacketPlayOutEntityEquipment(ep.ae(), getProp(ep)));
	}
	public static void setMainHand(EntityPlayer ep, ItemStack s) {
		properties.get(ep).setMainhand(s);
		setEquip(ep);
	}
	public static void setOffHand(EntityPlayer ep, ItemStack s) {
		properties.get(ep).setOffhand(s);
		SendPacket.sendPacket(new PacketPlayOutEntityEquipment(ep.ae(), getProp(ep)));
	}
	public static void setArmorHelmet(EntityPlayer ep, ItemStack s) {
		properties.get(ep).setHead(s);
		SendPacket.sendPacket(new PacketPlayOutEntityEquipment(ep.ae(), getProp(ep)));
	}
	public static void setArmorChestPlate(EntityPlayer ep, ItemStack s) {
		properties.get(ep).setChest(s);
		SendPacket.sendPacket(new PacketPlayOutEntityEquipment(ep.ae(), getProp(ep)));
	}
	public static void setArmorLeggings(EntityPlayer ep, ItemStack s) {
		properties.get(ep).setLegs(s);
		SendPacket.sendPacket(new PacketPlayOutEntityEquipment(ep.ae(), getProp(ep)));
	}
	public static void setArmorBoots(EntityPlayer ep, ItemStack s) {
		properties.get(ep).setFeet(s);
		SendPacket.sendPacket(new PacketPlayOutEntityEquipment(ep.ae(), getProp(ep)));
	}
	public static void addSettings(Player p, Property prop) {
		FileSystem.addArray((JSONArray)FileSystem.job("properties").get(p.getName()), prop);
	}
	public static void removeSettings(Player p, int index) {
		FileSystem.removeArray((JSONArray)FileSystem.job("properties").get(p.getName()), index);
	}
	public static void setSettings(Player p, int index, Property prop) {
		FileSystem.setArray((JSONArray)FileSystem.job("properties").get(p.getName()), index, prop);
	}
	private static void setSetting(EntityPlayer ep, JSONObject o) {
		properties.put(ep, new Property(o));
	}
	public static void setSetting(Player p, int index) {
		setSetting(editnpc.get(p), (JSONObject)((JSONArray)FileSystem.job("properties").get(p.getName())).get(index));
	}
	public static void removeNPC(EntityPlayer ep) {
		npclist.remove(ep);
		npcs.remove(ep.ae());
		setVisible(ep, false);
	}
	@SuppressWarnings("unchecked")
	public static void saveNPCS() {
		JSONArray ja = new JSONArray();
		for(EntityPlayer ep : npclist) {
			JSONObject o = new JSONObject();
			o.put("nickname", getNickName.get(ep));
			o.put("property", properties.get(ep));
			o.put("name", getName.get(ep));
			o.put("x", ep.getBukkitEntity().getLocation().getX());
			o.put("y", ep.getBukkitEntity().getLocation().getY());
			o.put("z", ep.getBukkitEntity().getLocation().getZ());
			o.put("world", ep.getBukkitEntity().getLocation().getWorld().getName());
			if(angle.containsKey(ep) && pitch.containsKey(ep)) {
				o.put("yaw", angle.get(ep));
				o.put("pitch", pitch.get(ep));
			}
			ja.add(o);
		}
		FileSystem.save("npclist", ja);
	}
	@SuppressWarnings("unchecked")
	public static void setupNPCS() {
		if(!FileSystem.values.containsKey("npclist")) return;
		for(JSONObject obb : (List<JSONObject>)FileSystem.jar("npclist")) {
			if(obb.containsKey("x") && obb.containsKey("y") && obb.containsKey("z") && obb.containsKey("x") && obb.containsKey("world") && obb.containsKey("name") && obb.containsKey("nickname") && obb.containsKey("property")) {
			if(obb.containsKey("yaw") && obb.containsKey("pitch")) {
				properties.put(spawnNPC((String)obb.get("nickname"), (String)obb.get("name"), new Command<PacketPlayInUseEntity>() {
					@Override
					public void command(PacketPlayInUseEntity packet, Player p) {
						for(String command : SpawnNPC.properties.get(SpawnNPC.npcs.get((int)GetValue.getValue(packet, "a"))).getCommands()) {
							p.performCommand(command);
						}
					}
				}, new Location(Bukkit.getWorld((String)obb.get("world")) != null ? Bukkit.getWorld((String)obb.get("world")) : Bukkit.createWorld(new WorldCreator((String)obb.get("world"))), (double)obb.get("x"), (double)obb.get("y"), (double)obb.get("z")), true, (float)obb.get("yaw"), (float)obb.get("pitch")),
				new Property((JSONObject)obb.get("property")));
			}else {
				properties.put(spawnNPC((String)obb.get("nickname"), (String)obb.get("name"), new Command<PacketPlayInUseEntity>() {
					@Override
					public void command(PacketPlayInUseEntity packet, Player p) {
						for(String command : SpawnNPC.properties.get(SpawnNPC.npcs.get((int)GetValue.getValue(packet, "a"))).getCommands()) {
							p.performCommand(command);
						}
					}
				}, new Location(Bukkit.getWorld((String)obb.get("world")) != null ? Bukkit.getWorld((String)obb.get("world")) : Bukkit.createWorld(new WorldCreator((String)obb.get("world"))), (double)obb.get("x"), (double)obb.get("y"), (double)obb.get("z")), true),
				new Property((JSONObject)obb.get("property")));
			}
			}
		}
	}
}
