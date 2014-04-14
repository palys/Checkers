package Utils;

import java.util.Arrays;

public class Utils {
	
	public static int[] range(int start, int end) {
		int step = 0;
		
		if (end > start) {
			step = 1;
		} else {
			step = -1;
		}
		
		int steps = Math.abs(end - start);
		
		int[] array = new int[steps];
		
		for (int i = start, j = 0; i != end; i+= step, j++) {
			array[j] = i;
		}
		
		return array;
	}
	
	public static int[] rangeWithEnd(int start, int end) {
		if (end > start) {
			return range(start, end + 1);
		} else {
			return range(start, end - 1);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(rangeWithEnd(2,5)));
	}
}
