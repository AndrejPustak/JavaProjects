package hr.fer.zemris.java.hw17.jvdraw.tools.actions;

import hr.fer.zemris.java.hw17.jvdraw.geometrical.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.Line;

/**
 * Class representing a GeometricalObjectVisitor used to save the objects to a document.
 * @author Andrej
 *
 */
public class SaveVisitor implements GeometricalObjectVisitor{
	
	StringBuilder sb;
	
	public SaveVisitor() {
		sb = new StringBuilder();
	}
	
	@Override
	public void visit(Line line) {
		sb.append("LINE ");
		sb.append(line.getStartPoint().x + " " + line.getStartPoint().y + " ");
		sb.append(line.getEndPoint().x + " " + line.getEndPoint().y + " ");
		sb.append(line.getColor().getRed() + " " + line.getColor().getGreen() + " " + line.getColor().getBlue());
		sb.append("\r\n");
	}

	@Override
	public void visit(Circle circle) {
		sb.append("CIRCLE ");
		sb.append(circle.getCenter().x + " " + circle.getCenter().y + " ");
		sb.append(circle.getRadius() + " ");
		sb.append(circle.getColor().getRed() + " " + circle.getColor().getGreen() + " " + circle.getColor().getBlue());
		sb.append("\r\n");
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		sb.append("FCIRCLE ");
		sb.append(filledCircle.getCenter().x + " " + filledCircle.getCenter().y + " ");
		sb.append(filledCircle.getRadius() + " ");
		sb.append(filledCircle.getFgColor().getRed() + " " + filledCircle.getFgColor().getGreen() + " " + filledCircle.getFgColor().getBlue() + " ");
		sb.append(filledCircle.getBgColor().getRed() + " " + filledCircle.getBgColor().getGreen() + " " + filledCircle.getBgColor().getBlue());
		sb.append("\r\n");
	}
	
	public String getResultString() {
		return sb.toString();
	}
	
}
