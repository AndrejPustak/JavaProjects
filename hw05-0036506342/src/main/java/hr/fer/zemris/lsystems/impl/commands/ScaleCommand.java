package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This command is used to scale the effective length by a value given in the constructor
 * @author Andrej
 *
 */
public class ScaleCommand implements Command {
	
	private double factor;
	
	/**
	 * Constructor for the ScaleCommand
	 * @param factor factor by which you wish to scale the effective length
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {

		TurtleState state = ctx.getCurrentState();
		state.setEffectiveLength(state.getEffectiveLength() * factor);

	}

}
