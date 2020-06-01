package hr.fer.zemris.java.hw17.trazilica.commands;

import hr.fer.zemris.java.hw17.trazilica.Command;
import hr.fer.zemris.java.hw17.trazilica.Konzola;

/**
 * This command is used to exit the application.
 * @author Andrej
 *
 */
public class ExitCommand implements Command{

	@Override
	public void execute(String params, Konzola console) {
		if(params.length() != 0) {
			System.out.println("Unexpected parameters for results command");
			return;
		}
		
		console.setExit(true);
	}

}
