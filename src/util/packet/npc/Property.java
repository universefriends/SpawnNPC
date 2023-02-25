package util.packet.npc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import util.item.Items;
@SuppressWarnings("serial")
public class Property extends JSONObject{
	private boolean cape = true, jacket = true, leftsleeve = true, rightsleeve = true,
	leftpants = true, rightpants = true, hat = true,
	onfire = false, invisible = false, glowing = false, leftmainhand = false;
	private int leftvariant = -1, rightvariant = -1, pose = -1;
	private ItemStack head, chest, legs, feet, mainhand, offhand;
	private List<String> commands = new ArrayList<>();
	public List<String> getCommands() { return commands; }
	public void setCommands(List<String> commands) { this.commands = commands; }
	public Property(JSONObject o) {
		copy(o);
	}
	@SuppressWarnings("unchecked")
	public void copy(JSONObject o) {
		//FirstLine
		this.cape = (boolean) o.get("cape");
		this.jacket = (boolean) o.get("jacket");
		this.leftsleeve = (boolean) o.get("leftsleeve");
		this.rightsleeve = (boolean) o.get("rightsleeve");
		this.leftpants = (boolean) o.get("leftpants");
		this.rightpants = (boolean) o.get("rightpants");
		this.hat = (boolean) o.get("hat");
		//SecondLine
		this.onfire = (boolean) o.get("onfire");
		this.invisible = (boolean) o.get("invisible");
		this.glowing = (boolean) o.get("glowing");
		this.leftmainhand = (boolean) o.get("leftmainhand");
		this.pose = Integer.parseInt(o.get("pose").toString());
		this.leftvariant = Integer.parseInt(o.get("leftvariant").toString());
		this.rightvariant = Integer.parseInt(o.get("rightvariant").toString());
		//FinalLine
		this.head = o.get("head") != null ? Items.deserialize((JSONObject)o.get("head")) : null;
		this.chest = o.get("chest") != null ? Items.deserialize((JSONObject)o.get("chest")) : null;		
		this.legs = o.get("legs") != null ? Items.deserialize((JSONObject)o.get("legs")) : null;
		this.feet = o.get("feet") != null ? Items.deserialize((JSONObject)o.get("feet")) : null;		
		this.mainhand = o.get("mainhand") != null ? Items.deserialize((JSONObject)o.get("mainhand")) : null;
		this.offhand = o.get("offhand") != null ? Items.deserialize((JSONObject)o.get("offhand")) : null;
		this.commands = (ArrayList<String>)o.get("commands");
		save();
	}
	public Property() {
		save();
	}
	@SuppressWarnings("unchecked")
	public void save() {
		//FirstLine
		put("cape", cape);
		put("jacket", jacket);
		put("leftsleeve", leftsleeve);
		put("rightsleeve", rightsleeve);
		put("leftpants", leftpants);
		put("rightpants", rightpants);
		put("hat", hat);
		//SecondLine
		put("onfire", onfire);
		put("invisible", invisible);
		put("glowing", glowing);
		put("leftmainhand", leftmainhand);
		put("pose", pose);
		put("leftvariant", leftvariant);
		put("rightvariant", rightvariant);
		//FinalLine
		put("head", head != null ? Items.serialize(head) : null);
		put("chest", chest != null ? Items.serialize(chest) : null);
		put("legs", legs != null ? Items.serialize(legs) : null);
		put("feet", feet != null ? Items.serialize(feet) : null);
		put("mainhand", mainhand != null ? Items.serialize(mainhand) : null);
		put("offhand", offhand != null ? Items.serialize(offhand) : null);
		put("commands", commands);
		SpawnNPC.saveNPCS();
	}
	public int getPose() { return pose; }
	public void setPose(int pose) { this.pose = pose; save(); }
	public boolean isInvisible() { return invisible; }
	public void setInvisible(boolean invisible) { this.invisible = invisible; save(); }
	public boolean isOnfire() { return onfire; }
	public void setOnfire(boolean onfire) { this.onfire = onfire; save(); }
	public boolean isGlowing() { return glowing; }
	public void setGlowing(boolean glowing) { this.glowing = glowing; save(); }
	public boolean isCape() { return cape; }
	public void setCape(boolean cape) { this.cape = cape; save(); }
	public boolean isJacket() { return jacket; }
	public void setJacket(boolean jacket) { this.jacket = jacket; save(); }
	public boolean isLeftsleeve() { return leftsleeve; }
	public void setLeftsleeve(boolean leftsleeve) { this.leftsleeve = leftsleeve; save(); }
	public boolean isRightsleeve() { return rightsleeve; }
	public void setRightsleeve(boolean rightsleeve) { this.rightsleeve = rightsleeve; save(); }
	public boolean isLeftpants() { return leftpants; }
	public void setLeftpants(boolean leftpants) { this.leftpants = leftpants; save(); }
	public boolean isRightpants() { return rightpants; }
	public void setRightpants(boolean rightpants) { this.rightpants = rightpants; save(); }
	public boolean isHat() { return hat; }
	public void setHat(boolean hat) { this.hat = hat; save(); }
	public boolean isLeftmainhand() { return leftmainhand; }
	public void setLeftmainhand(boolean leftmainhand) { this.leftmainhand = leftmainhand; save(); }
	public ItemStack getHead() { return head; }
	public void setHead(ItemStack head) { this.head = head; save(); }
	public ItemStack getChest() { return chest; }
	public void setChest(ItemStack chest) { this.chest = chest; save(); }
	public ItemStack getLegs() { return legs; }
	public void setLegs(ItemStack leg) { this.legs = leg; save(); }
	public ItemStack getFeet() { return feet; }
	public void setFeet(ItemStack feet) { this.feet = feet; save(); }
	public ItemStack getMainhand() { return mainhand; }
	public void setMainhand(ItemStack mainhand) { this.mainhand = mainhand; save(); }
	public ItemStack getOffhand() { return offhand; }
	public void setOffhand(ItemStack offhand) { this.offhand = offhand; save(); }
	public int getLeftvariant() { return leftvariant; }
	public void setLeftvariant(int leftvariant) { this.leftvariant = leftvariant; save(); 	}
	public int getRightvariant() { return rightvariant; }
	public void setRightvariant(int rightvariant) { this.rightvariant = rightvariant; save(); }
	public byte get0() { return (byte)Integer.parseInt(getToString(glowing) + getToString(invisible) + "0000" + getToString(onfire), 2); }
	public byte get17() { return (byte)Integer.parseInt(getToString(hat) + getToString(rightpants) + getToString(leftpants) + 
			getToString(rightsleeve) + getToString(leftsleeve) + getToString(jacket) + getToString(cape), 2); }
	public void reset() {
		cape=true; jacket=true; leftsleeve=true; rightsleeve=true;
		leftpants=true; rightpants=true; hat=true;
		onfire=false; invisible=false; glowing=false; leftmainhand=false;
		head=null; chest=null; legs=null; feet=null; mainhand=null; offhand=null;
		leftvariant = -1; rightvariant = -1; pose = -1;
		commands.clear();
		save();
	};
	public void command(Player p) {
		for(String s : commands) {
			p.performCommand(s);
		}
	}
	public void add(String command) {
		commands.add(command);
		save();
	}
	public void remove(int index) {
		commands.remove(index);
		save();
	}
	public void removeAll() {
		commands.clear();
		save();
	}
	private static String getToString(boolean b) { return b?"1":"0"; }
}
