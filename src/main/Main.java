package main;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
	public static void main(String[] args) {
		/*for(int i=1; i<=4; i++) {
			int m = 0;
			for(int j=i; j<9; j++) {
				System.out.print(m+++""+j+" ");
			}
			System.out.println();
		}
		for(int i=1; i<=4; i++) {
			int m = 0;
			for(int j=i; j<9; j++) {
				System.out.print(j+""+m+++" ");
			}
			System.out.println();
		}
		*/
		for(int i=9-5; i<9; i++) {
			int m=0;
			for(int j=i; j>=0; j--) {
				System.out.print(m+++""+j+" ");
			}
			System.out.println();
		}
		
		for(int i=1; i<=4; i++) {
			int m=8;
			for(int j=i; j<=8; j++) {
				System.out.print(j+""+m--+" ");
			}System.out.println();
		}

	}
}
