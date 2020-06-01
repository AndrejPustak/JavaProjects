package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface that represents a visitor for nodes
 * @author Andrej
 *
 */
public interface INodeVisitor {
	
	/**
	 * Method that is called when a text node is visited
	 * @param node visited text node
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Method that is called when a ForLoop node is visited
	 * @param node visited for loop node
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Method that is called when an echo node is visited
	 * @param node visited echo node
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Method that is called when a document node is visited
	 * @param node visited document node
	 */
	public void visitDocumentNode(DocumentNode node);
}