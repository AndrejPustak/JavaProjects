package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartScriptEngineDemo1 {
	public static void main(String[] args) {
		String documentBody = readFromDisk("webroot/scripts/osnovni.smscr");
		if(documentBody == null) return;
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	private static String readFromDisk(String name) {
		String text = null;
		try {
			text = Files.readString(Paths.get(name));
		} catch (IOException e) {
			System.out.println("Unnable to read from file");
		}
		return text;
	}
}
