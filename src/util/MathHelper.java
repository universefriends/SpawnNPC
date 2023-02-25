package util;

public class MathHelper {
	public static int cycle(int target, int min, int max) {
		return target >= max?min:target+1;
	}
	public static int maskadder(int target, int add, int... hidemask) {
		int current = target+add;
		for(int i : hidemask) {
			if(current == i) current++;
		}
		return current;
	}
	public static int maskcycle(int target, int min, int max, int... hidemask) {
		return cycle(maskadder(target, 1, hidemask), min+1, max+1)-1;
	}
	public static int masksubstracter(int target, int sub, int... hidemask) {
		if(target < sub) throw new IllegalArgumentException("���� ���� Ÿ�� ������ Ů�ϴ�. (" + target + " < " + sub + ")");
		int current = target;
		for(int i = hidemask.length; i > 0; i--) {
			if(current == hidemask[i]) current--;
		}
		return current;
	}
	public static int maskindex(int target, int min, int... hidemask) throws IllegalArgumentException{
		if(target < min) throw new IllegalArgumentException("���� ���� Ÿ�� ������ Ů�ϴ�. (" + target + " < " + min + ")");
		if(hidemask.length >= 2 && equalsVar(hidemask)) throw new IllegalArgumentException("����ũ ���� ���� ���� �ߺ��Ǿ����ϴ�.");
		int index = target;
		for(int i : hidemask) {
			if(i <= min) throw new IllegalArgumentException("����ũ ���� Ÿ�� ������ ���ų� �۽��ϴ�. (" + i + " <= " + min + ")");
			if(target >= i) index--;
		}
		return index - min;
	}
	public static int[] bubblesortmin(int... arr) {
	    int[] arr2 = arr;
		for(int i = 0; i < arr2.length; i++) {
	        for(int j = 0 ; j < arr2.length - i - 1 ; j++) {
	            if(arr2[j] < arr2[j+1]) {
	                int temp = arr2[j+1];
	                arr2[j+1] = arr2[j];
	                arr2[j] = temp;
	            }
	        }
	    }
		return arr2;
	}
	public static int[] bubblesortmax(int... arr) {
		int[] arr2 = arr;
	    for(int i = 0; i < arr2.length; i++) {
	        for(int j = 0 ; j < arr2.length - i - 1 ; j++) {
	            if(arr2[j] > arr2[j+1]) {
	                int temp = arr2[j+1];
	                arr2[j+1] = arr2[j];
	                arr2[j] = temp;
	            }
	        }
	    }
	    return arr2;
	}
	public static boolean equalsVar(int... arr) {
		int[] arr2 = bubblesortmin(arr);
		for(int i = 0; i < arr2.length - 1; i++) {
			if(arr2[i] == arr2[i+1]) return true;
		}
		return false;
	}
}
