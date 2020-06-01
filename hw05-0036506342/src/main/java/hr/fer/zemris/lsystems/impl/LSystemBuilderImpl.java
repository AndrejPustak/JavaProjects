package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * This class is an implementation of LSystemBuilder. It holds all the necessary
 * information about a single Lindenmayer system which is used to generate and draw
 * a single fractal.
 * 
 * @author Andrej
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	/**
	 * Default unit length of the drawing
	 */
	private final static double DEFAULT_UNIT_LENGTH = 0.1;
	/**
	 * Default unit length degree scaler of the drawing
	 */
	private final static double DEFAULT_UNIT_LENGTH_DEGREE_SCALER = 1;
	/**
	 * Default starting angle of the turtle
	 */
	private final static double DEFAULT_ANGLE = 0;
	/**
	 * Default axiom
	 */
	private final static String DEFAULT_AXIOM = "";
	
	private Dictionary<Character, String> productions;
	private Dictionary<Character, Command> actions;
	
	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;
	
	/**
	 * Default constructor for the LSystemBuilderImpl
	 */
	public LSystemBuilderImpl() {
		unitLength = DEFAULT_UNIT_LENGTH;
		unitLengthDegreeScaler = DEFAULT_UNIT_LENGTH_DEGREE_SCALER;
		origin = new Vector2D(0, 0);
		angle = DEFAULT_ANGLE;
		axiom = DEFAULT_AXIOM;
		
		productions = new Dictionary<>();
		actions = new Dictionary<>();
	}
	
	/**
	 * This class is an implementation of LSystem. It is used to generate and draw a Lindenmayer
	 * system which it gets form LSystemBuilder
	 * @author Andrej
	 *
	 */
	public static class MyLSystem implements LSystem {
		
		/**
		 * Default color for the drawing
		 */
		private final static Color DEFAULT_COLOR = Color.BLACK;
		
		/**
		 * Builder which holds all the necessary information for the LSystem
		 */
		private LSystemBuilderImpl builder;
		
		/**
		 * Constructor for the LSystem
		 * @param builder LSystemBuilder which holds the necessary information about the LSystem you want to draw
		 */
		public MyLSystem(LSystemBuilderImpl builder) {
			this.builder = builder;
		}
		
		/**
		 * This method draws the the Lindenmayer system
		 * @param level Level of the lindermayer system you wish to draw
		 * @param painter object which draws the lines
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context context = new Context();
			
			//Add first state
			Vector2D angleVector = new Vector2D(1, 0);
			angleVector.rotate(Math.toRadians(builder.angle));
			double effectiveLen = builder.unitLength * (Math.pow(builder.unitLengthDegreeScaler, level));
			TurtleState state = new TurtleState(builder.origin, angleVector, DEFAULT_COLOR, effectiveLen);
			context.pushState(state);
			
			String pattern = generate(level);
			
			for(char c : pattern.toCharArray()) {
				Command com = builder.actions.get(c);
				if (com != null) {
					com.execute(context, painter);
				}
			}
			
			
		}
		
		/**
		 * This method generates the pattern which is used to draw the LSystm
		 * @param level Level of the LSystem you wish to draw
		 */
		@Override
		public String generate(int level) {
			String pattern = builder.axiom;
			for(int i = 0; i < level; i++) {
				StringBuilder sb = new StringBuilder("");
				char[] array = pattern.toCharArray();
				for(char c : array) {
					if(builder.productions.get(c) != null) {
						sb.append(builder.productions.get(c));
					} else {
						sb.append(c);
					}
				}
				pattern = sb.toString();
			}
			
			return pattern;
		}
	}
	
	/**
	 * This method returns the new MyLSystem object
	 */
	@Override
	public LSystem build() {
		return new MyLSystem(this);
	}
	
	
	/**
	 * This method is used to configure the lsystem from text file
	 * @param lines Array of the lines from the text file
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for (String line : lines) {
			String[] array = line.split("\\s+");
			
			switch(array[0]) {
				case "origin":
					setOrigin(Double.parseDouble(array[1]), Double.parseDouble(array[2]));
					break;
				case "angle":
					setAngle(Double.parseDouble(array[1]));
					break;
				case "unitLength":
					setUnitLength(Double.parseDouble(array[1]));
					break;
				case "unitLengthDegreeScaler":
					String s = "";
					for(int i = 1; i< array.length ; i++) {
						s += array[i];
					}
					String[] array2 = s.split("/");
					setUnitLengthDegreeScaler(Double.parseDouble(array2[0]) / Double.parseDouble(array2[1]));
					break;
				case "command":
					if (array.length == 3) {
						registerCommand(array[1].charAt(0), array[2]);
					} else if(array.length == 4){
						registerCommand(array[1].charAt(0), array[2] + " " + array[3]);
					}
					
					break;
				case "axiom":
					setAxiom(array[1]);
					break;
				case "production":
					registerProduction(array[1].charAt(0), array[2]);
					break;
				case "":
					break;
				default:
					break;
			}
		}
		
		return this;
	}
	
	/**
	 * THis method registers one command to this LSystemBuilder
	 * @param symbol symbol which represents this command
	 * @param action action which the command is supposed to do
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		String[] array = action.split("\\s+");
		Command com = null;
		switch(array[0]) {
			case "draw":
				com = new DrawCommand(Double.parseDouble(array[1]));
				break;
			case "skip":
				com = new SkipCommand(Double.parseDouble(array[1]));
				break;
			case "scale":
				com = new ScaleCommand(Double.parseDouble(array[1]));
				break;
			case "rotate":
				com = new RotateCommand(Double.parseDouble(array[1]));
				break;
			case "push":
				com = new PushCommand();
				break;
			case "pop":
				com = new PopCommand();
				break;
			case "color":
				int r = Integer.valueOf(array[1].substring(0,2), 16);
				int g = Integer.valueOf(array[1].substring(2,4), 16);
				int b = Integer.valueOf(array[1].substring(4,6), 16);
				com = new ColorCommand(new Color(r, g, b));
				break;
			default:
				break;
		}
		if (com != null) {
			actions.put(symbol, com);
		}
		return this;
	}
	
	/**
	 * This method is used to register a single production to this LSystemBuilder
	 * @param symbol symbol of the production
	 * @param result of the production
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}
	
	/**
	 * Setter for the starting angle of the turtle
	 * @param angle of the turtle in degrees 
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}
	
	/**
	 * Setter fort he starting axiom
	 * @param starting axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}
	
	/**
	 * Setter for the starting origin or the turtle
	 * @param x x coordinate of the turtle
	 * @param y y coordinate of the turtle
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}
	
	/**
	 * Setter for the unit length
	 * @param unitLength unit length
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}
	
	/**
	 * Setter for the unit length degree scaler
	 * @param unitLengthDegreeScaler unit length degree scaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
