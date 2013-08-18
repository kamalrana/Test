/**
@author : kamal64
 */

package log4j_fetch;

import java.util.Random;

public class TestRandom {
	public static void main(String[] args) {
		Random r = new Random();
		int i=0,j = 0,k=0;
		for (int l = 0; l < 100; l++) {
			switch (r.nextInt(3)) {
			case 0:
				i++;
				break;

			case 1:
				j++;
				break;
			case 2:
				k++;
				break;
			default:
				break;
			}
		}
		System.out.println(i+"-"+j+"-"+k);
	}
}
