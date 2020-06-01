package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Program that writes the document tree to the System.out
 * @author Andrej
 *
 */
public class TreeWriter {
	
	/**
	 * MEthod from which the program starts
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("THis program accepts 1 command line argument");
			return;
		}
		
		Path path = Paths.get(args[0]);
		
		String text;
		try {
			text = Files.readString(path);
		} catch (IOException e) {
			System.out.println("Unable to read from the document");
			return;
		}
		
		SmartScriptParser parser = new SmartScriptParser(text);
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
		
		
	}
	
	/**
	 * Implementation of INodeVisitor that every time it visits a node writes
	 * the node to the System.out
	 * @author Andrej
	 *
	 */
	public static class WriterVisitor implements INodeVisitor{
		
		@Override
		public void visitTextNode(TextNode node) {
			System.out.printf(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.printf(node.toString());
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.printf("{$ END $}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.printf(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
		
	}
	
}
