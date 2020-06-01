package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This class represents a draw command which draws one line. The length of the line
 * is calculate by multiplying effective length of the vector by step which is given 
 * in the constructor of the command
 * @author Andrej
 *
 */
public class DrawCommand implements Command {
	
	private double step;
	
	/**
	 * Constructor for the draw command
	 * @param step number which represents how long the line will be
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D pos = state.getPositionVector();
		Vector2D ang = state.getAngleVector();
		
		Vector2D newPos = pos.translated(ang.scaled(step * state.getEffectiveLength()));
		
		painter.drawLine(pos.getX(), pos.getY(),
						newPos.getX(), newPos.getY(), 
						state.getColor(), 1f);
		
		state.setPositionVector(newPos);
	}

}
