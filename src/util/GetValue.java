package util;

import java.lang.reflect.Field;

public class GetValue {
	public static Object getValue(Object instance, String pathname) {
		try{
			Field f = instance.getClass().getDeclaredField(pathname);
			f.setAccessible(true);
			Object result = f.get(instance);
			f.setAccessible(false);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static boolean containsValue(Object instance, String pathname) {
		try{
			Field f = instance.getClass().getDeclaredField(pathname);
			f.setAccessible(true);
			f.get(instance);
			f.setAccessible(false);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
