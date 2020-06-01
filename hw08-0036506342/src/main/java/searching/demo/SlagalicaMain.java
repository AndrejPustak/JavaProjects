package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

public class SlagalicaMain {
	
	public static void main(String[] args) {
		
		int[] polje = checkArgs(args);
		
		if(polje == null) {
			return;
		}
		
		Slagalica slagalica = new Slagalica(
				new KonfiguracijaSlagalice(polje)
				);
		Node<KonfiguracijaSlagalice> rješenje =
				SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if(rješenje==null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			SlagalicaViewer.display(rješenje);
		}
	}

	private static int[] checkArgs(String[] args) {
		if(args.length != 1) {
			System.out.println("Invalid argument number. Expected one.");
			return null;
		}
		
		if(args[0].length()!=9) {
			System.out.println("Invalid configuration length. Expected 9 numbers.");
			return null;
		}
		
		int[] polje = new int[9];
		
		for(int i = 0; i< 9; i++) {
			try {
				polje[i] = Integer.parseInt(args[0].substring(i, i+1));
			}
			catch (NumberFormatException e) {
				System.out.println("Expected numbers in the configuration");
				return null;
			}
		}
		
		boolean containsAll = true;
		
		for(int i = 0; i < 9; i++) {
			boolean contains = false;
			for(int j = 0; j < 9; j++) {
				if (polje[j] == i) {
					contains = true;
					break;
				}
			}
			if(!contains) {
				containsAll = false;
				break;
			}
		}
		
		if(!containsAll) {
			System.out.println("Configuration does not contain all numbers from 0-8");
			return null;
		}
		
		return polje;
		
	}
	
}
