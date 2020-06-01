package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class represents a command which changes the color of the turtle
 * @author Andrej
 *
 */
public class ColorCommand implements Command {
	
	private Color color;
	
	/**
	 * Constructor for the ColorCommand
	 * @param color Color you wish to change the color to
	 */
	public ColorCommand(Color color) {
		super();
		this.color = Objects.requireNonNull(color);
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState();
		state.setColor(color);

	}

}
