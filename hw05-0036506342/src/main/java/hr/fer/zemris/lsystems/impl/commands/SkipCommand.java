package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This command is similar to the draw command except that it doesn't draw
 * the line, it just moves the turtle by a given step
 * @author Andrej
 *
 */
public class SkipCommand implements Command {

	private double step;
	
	/**
	 * Constructor for the SkipCommand
	 * @param step Step by which you wish to move the turtle
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D pos = state.getPositionVector();
		Vector2D ang = state.getAngleVector();
		
		Vector2D newPos = pos.translated(ang.scaled(step * state.getEffectiveLength()));
		
		state.setPositionVector(newPos);
	}

}
