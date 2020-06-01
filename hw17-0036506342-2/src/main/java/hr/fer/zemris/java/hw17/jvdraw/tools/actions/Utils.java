package hr.fer.zemris.java.hw17.jvdraw.tools.actions;

import java.nio.file.Path;

/**
 * Utility class
 * @author Andrej
 *
 */
public class Utils {
	public static String getFileExtension(Path path){
		String pathName = path.toString();
		int i = pathName.lastIndexOf('.');
		if(i == -1) return "";
		
		return pathName.substring(i + 1);
	}
}
