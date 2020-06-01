package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Representation of an engine that takes in a document node parsed by the
 * SmartScripParser is able to execute the parsed code.
 * @author Andrej
 *
 */
public class SmartScriptEngine {
	
	/**
	 * Reference to the document node
	 */
	private DocumentNode documentNode;
	
	/**
	 * Reference to the request's context
	 */
	private RequestContext requestContext;
	
	/**
	 * Object multistack
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("Unable to write nodes' request's output stream.");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			multistack.push(node.getVariable().asText(), new ValueWrapper(node.getStartExpression().asText()));
			
			while(multistack.peek(node.getVariable().asText()).numCompare(node.getEndExpression().asText()) <= 0 ){
				for(int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
				
				multistack.peek(node.getVariable().asText()).add(node.getStepExpression().asText());
			}
			
			multistack.pop(node.getVariable().asText());
			
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			
			Stack<Object> stack = new Stack<>();
			
			Element[] elements = node.getElements();
			for(Element element : elements) {
				if(element instanceof ElementVariable) {
					stack.push(multistack.peek(((ElementVariable) element).getName()).getValue());
				} else if(element instanceof ElementOperator) {
					Object object = stack.pop();
					ValueWrapper wrapper = new ValueWrapper(stack.pop());
					
					if(((ElementOperator) element).getSymbol().equals("+")) {
						wrapper.add(object);
					}
					if(((ElementOperator) element).getSymbol().equals("-")) {
						wrapper.subtract(object);
					}
					if(((ElementOperator) element).getSymbol().equals("*")) {
						wrapper.multiply(object);
					}
					if(((ElementOperator) element).getSymbol().equals("/")) {
						wrapper.divide(object);
					}
					
					stack.push(wrapper.getValue());
				} else if(element instanceof ElementFunction) {
					String elementName = ((ElementFunction) element).getName();
					
					if(elementName.equals("sin")) {
						double result = Math.sin(Math.toRadians(Double.valueOf(stack.pop().toString())));
						stack.push(result);
					}
					if(elementName.equals("decfmt")) {
						DecimalFormat df = new DecimalFormat((String) stack.pop());
						Object x = stack.pop();
						stack.push(df.format(x));
					}
					if(elementName.equals("dup")) {
						Object x = stack.pop();
						stack.push(x);
						stack.push(x);
					}
					if(elementName.equals("swap")) {
						Object x = stack.pop();
						Object y = stack.pop();
						stack.push(x);
						stack.push(y);
					}
					if(elementName.equals("setMimeType")) {
						Object x = stack.pop();
						requestContext.setMimeType((String) x);
					}
					if(elementName.equals("paramGet")) {
						Object dv = stack.pop();
						Object name = stack.pop();
						String value = requestContext.getParameter((String) name);
						if(value == null) {
							stack.push(dv);
						}else {
							stack.push(value);
						}
					}
					if(elementName.equals("pparamGet")) {
						Object dv = stack.pop();
						Object name = stack.pop();
						String value = requestContext.getPersistentParameter((String) name);
						if(value == null) {
							stack.push(dv);
						}else {
							stack.push(value);
						}
					}
					if(elementName.equals("pparamSet")) {
						Object name = stack.pop();
						Object value = stack.pop();
						requestContext.setPersistentParameter((String) name,String.valueOf(value));
					}
					if(elementName.equals("pparamDel")) {
						Object name = stack.pop();
						requestContext.removePersistentParameter((String) name);
					}
					if(elementName.equals("tparamGet")) {
						Object dv = stack.pop();
						Object name = stack.pop();
						String value = requestContext.getTemporaryParameter((String) name);
						if(value == null) {
							stack.push(dv);
						}else {
							stack.push(value);
						}
					}
					if(elementName.equals("tparamSet")) {
						Object name = stack.pop();
						Object value = stack.pop();
						requestContext.setTemporaryParameter((String) name,String.valueOf(value));
					}
					if(elementName.equals("tparamDel")) {
						Object name = stack.pop();
						requestContext.removeTemporaryParameter((String) name);
					}
					
				} else if(element instanceof ElementString) {
					stack.push(((ElementString) element).getValue());
				} else if(element instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) element).getValue());
				} else if(element instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) element).getValue());
				} else {
					System.out.println("Unknown element type");
				}
				
			}
			
			stack = reverseStack(stack);
			while(!stack.isEmpty()) {
				try {
					requestContext.write(stack.pop().toString());
				} catch (IOException e) {
					System.out.println("Unable to write to rc");
				}
			}
			
			
		}
		
		/**
		 * This method takes in a stack and returns a stack with
		 * reversed order of elements.
		 * @param stack stack you wish to reverse
		 * @return
		 */
		private Stack<Object> reverseStack(Stack<Object> stack) {
			Stack<Object> result = new Stack<Object>();
			while(!stack.isEmpty()) {
				result.push(stack.pop());
			}
			
			return result;
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
		
	};
	
	/**
	 * Constructor for the SmarScripEngine
	 * @param documentNode reference to the document node
	 * @param requestContext request's context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Method that executes the code.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
