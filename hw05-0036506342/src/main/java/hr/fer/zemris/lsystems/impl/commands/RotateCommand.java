package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This command is used to rotate the turtle by an angle given in the constructor.
 * THe angle is in degrees
 * @author Andrej
 *
 */
public class RotateCommand implements Command {
	
	private double angle;

	/**
	 * Constructor for the RotateCommand
	 * @param angle angle by which you wish to rotate the turtle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setAngleVector(state.getAngleVector().rotated(Math.toRadians(angle)));
	}

}
