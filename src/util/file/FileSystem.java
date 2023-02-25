package util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import main.Main;

public class FileSystem {
	public static JSONObject values = new JSONObject();
	private static File f() { return new File(Main.main.getDataFolder(), "data.json"); }
	public static JSONArray jar(String name) {
		return (JSONArray)values.get(name);
	}
	public static JSONObject job(String name) {
		return (JSONObject)values.get(name);
	}
	public static void setup() {
		try {
			if(!Main.main.getDataFolder().isDirectory()) { Main.main.getDataFolder().mkdir(); }
			if(!f().isFile()) { f().createNewFile(); return; }
			values = (JSONObject)new JSONParser().parse(new FileReader(f()));
		}catch(Exception e) { e.printStackTrace(); }
	}
	@SuppressWarnings("unchecked")
	public static JSONArray addArray(JSONArray k, Object v) {
		k.add(v);
		save();
		return k;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject putMap(Object k, Object vk, Object v) {
		((JSONObject)k).put(vk, v);
		save();
		return (JSONObject)k;
	}
	public static JSONArray clearArray(JSONArray k) {
		k.clear();
		save();
		return k;
	}
	public static JSONArray removeArray(JSONArray k, Object v) {
		k.remove(v);
		save();
		return k;
	}
	public static JSONArray removeArray(JSONArray k, int v) {
		k.remove(v);
		save();
		return k;
	}
	@SuppressWarnings("unchecked")
	public static JSONArray setArray(JSONArray k, int v, Object o) {
		k.set(v, o);
		save();
		return k;
	}
	public static JSONObject removeMap(Object k, Object vk) {
		((JSONObject)k).remove(vk);
		save();
		return (JSONObject)k;
	}
	@SuppressWarnings("unchecked")
	public static void save(Object key, Object value) {
		try {
			values.put(key, value);
			if(!Main.main.getDataFolder().isDirectory()) Main.main.getDataFolder().mkdir();
			if(!f().isFile()) f().createNewFile();
			FileWriter writer = new FileWriter(f());
			writer.write(values.toJSONString());
			writer.flush();
			writer.close();
			setup();
		}catch(Exception e) { e.printStackTrace(); }
	}
	public static void save() {
		try {
			if(!Main.main.getDataFolder().isDirectory()) Main.main.getDataFolder().mkdir();
			if(!f().isFile()) f().createNewFile();
			FileWriter writer = new FileWriter(f());
			writer.write(values.toJSONString());
			writer.flush();
			writer.close();
			setup();
		}catch(Exception e) { e.printStackTrace(); }
	}
	public static void copyFileStructure(File source, File target){
	    try {
	        ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
	        if(!ignore.contains(source.getName())) {
	            if(source.isDirectory()) {
	                if(!target.exists())
	                    if (!target.mkdirs())
	                        throw new IOException("Couldn't create world directory!");
	                String files[] = source.list();
	                for (String file : files) {
	                    File srcFile = new File(source, file);
	                    File destFile = new File(target, file);
	                    copyFileStructure(srcFile, destFile);
	                }
	            } else {
	                InputStream in = new FileInputStream(source);
	                OutputStream out = new FileOutputStream(target);
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = in.read(buffer)) > 0)
	                    out.write(buffer, 0, length);
	                in.close();
	                out.close();
	            }
	        }
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
}
