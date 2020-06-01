package hr.fer.zemris.java.hw17.trazilica.commands;

import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw17.trazilica.Command;
import hr.fer.zemris.java.hw17.trazilica.Document;
import hr.fer.zemris.java.hw17.trazilica.Konzola;

/**
 * THis command is used to print the content of a specific result
 * @author Andrej
 *
 */
public class TypeCommand implements Command{

	@Override
	public void execute(String params, Konzola console) {
		
		if(params.length() == 0) {
			System.out.println("No type given");
			return;
		}
		
		int index;
		try {
			index = Integer.parseInt(params.strip());
		} catch (NumberFormatException e) {
			System.out.println("Unvalid number given for type");
			return;
		}
		
		List<Entry<Document, Double>> results = console.getQueryResult();
		
		if(results == null) {
			System.out.println("No stored results");
			return;
		}
		
		if(index < 0 || index >= results.size()) {
			System.out.println("Invalid index given for type");
			return;
		}
		
		Util.printSpecificResult(results, index);
		
	}

}
