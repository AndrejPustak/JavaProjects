package hr.fer.zemris.java.hw17.trazilica.commands;

import hr.fer.zemris.java.hw17.trazilica.Command;
import hr.fer.zemris.java.hw17.trazilica.Konzola;

/**
 * THis command is used to print all the results
 * @author Andrej
 *
 */
public class ResultsCommand implements Command{

	@Override
	public void execute(String params, Konzola console) {
		if(params.length() != 0) {
			System.out.println("Unexpected parameters for results command");
			return;
		}
		
		Util.printResults(console.getQueryResult(), true);
	}

}
