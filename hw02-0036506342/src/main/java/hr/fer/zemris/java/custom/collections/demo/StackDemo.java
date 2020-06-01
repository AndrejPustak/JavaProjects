package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {
	
	public static void nextElement(ObjectStack stack, String element) {
		Integer number;
		int num2;
		int num1;
		
		switch(element) {
		case "+":
			num2 = (Integer) stack.pop();
			num1 = (Integer) stack.pop();
			number = Integer.valueOf(num1 + num2);
			stack.push(number);
			break;
		case "-":
			num2 = (Integer) stack.pop();
			num1 = (Integer) stack.pop();
			number = Integer.valueOf(num1 - num2);
			stack.push(number);
			break;
		case "*":
			num2 = (Integer) stack.pop();
			num1 = (Integer) stack.pop();
			number = Integer.valueOf(num1 * num2);
			stack.push(number);
			break;
		case "/":
			num2 = (Integer) stack.pop();
			num1 = (Integer) stack.pop();
			number = Integer.valueOf(num1 / num2);
			stack.push(number);
			break;
		case "%":
			num2 = (Integer) stack.pop();
			num1 = (Integer) stack.pop();
			number = Integer.valueOf(num1 % num2);
			stack.push(number);
			break;
		default:
			number = Integer.parseInt(element);
			stack.push(number);
		}
	}
	
	public static void main(String[] args) {
		String line = args[0];
		String[] array = line.split("\\s+");
		
		ObjectStack stack = new ObjectStack();
		System.out.println(line);
		
		try {
			for(String element : array) {
				nextElement(stack, element);
			}
			
			if (stack.size() != 1) {
				System.out.println("Expression is not valid.");
				return;
			}
			
			System.out.println(stack.pop());
		}
		
		catch(NumberFormatException | EmptyStackException | ArithmeticException ex) {
			System.out.println("Expression is not valid");
		}
	}
}
