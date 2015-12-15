package prog05;

import java.util.Stack;

import prog02.GUI;
import prog02.UserInterface;

public class Parser {
	static final String OPERATORS = "()+-*/u^";
	static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3, 4 };
	String input = null;
	int index = 0;
	Stack<Character> operatorStack = new Stack<Character>();
	Stack<Double> numberStack = new Stack<Double>();
	static UserInterface ui = new GUI();

	boolean atEndOfInput() {
		while (index < input.length() && Character.isWhitespace(input.charAt(index)))
			index++;
		return index == input.length();
	}

	boolean isNumber() {
		return Character.isDigit(input.charAt(index));
	}

	double getNumber() {
		int index2 = index;
		while (index2 < input.length() && (Character.isDigit(input.charAt(index2)) || input.charAt(index2) == '.'))
			index2++;
		double d = 0;
		try {
			d = Double.parseDouble(input.substring(index, index2));
		} catch (Exception e) {
			System.out.println(e);
		}
		index = index2;
		return d;
	}

	char getOperator() {
		char op = input.charAt(index++);
		if (OPERATORS.indexOf(op) == -1) {
			System.out.println("Operator " + op + " not recognized.");
		}
		return op;
	}

	String numberStackToString() {
		String s = "numberStack: ";
		Stack<Double> helperStack = new Stack<Double>();

		while (!numberStack.empty()) {
			helperStack.push(numberStack.pop());
		}
		while (!helperStack.empty()) {
			s = s + " " + numberStack.push(helperStack.pop());
		}

		return s;
	}

	String operatorStackToString() {
		String s = "operatorStack: ";
		Stack<Character> helperStack = new Stack<Character>();
		while (!operatorStack.empty()) {
			helperStack.push(operatorStack.pop());
		}
		while (!helperStack.empty()) {
			s = s + " " + operatorStack.push(helperStack.pop());
		}
		return s;
	}

	void displayStacks() {
		ui.sendMessage(numberStackToString() + "\n" + operatorStackToString());
	}

	int precedence(char op) {
		return PRECEDENCE[OPERATORS.indexOf(op)];
	}

	double evaluateOperator(double a, char op, double b) {
		switch (op) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			return a / b;
		case '^':
			return Math.pow(a, b);
		}
		System.out.println("Unknown operator " + op);
		return 0;
	}

	void evaluateOperator() {
		char op = operatorStack.pop();
		if (op == 'u') {
			numberStack.push(-(numberStack.pop()));
		} else {
			double b = numberStack.pop();
			double a = numberStack.pop();
			numberStack.push(evaluateOperator(a, op, b));
		}
		displayStacks();
	}

	void processOperator(char op) {
		if ((op == '(') || (op == 'u')) {
			operatorStack.push(op);
		} else if (op == ')') {
			while (operatorStack.peek() != '(') {
				evaluateOperator();
			}
			operatorStack.pop();
		} else {
			while ((!operatorStack.empty()) && precedence(operatorStack.peek()) >= precedence(op)) {
				evaluateOperator();
			}
			operatorStack.push(op);
		}

	}

	double evaluate(String expr) {
		input = expr;
		index = 0;
		while (!operatorStack.empty()) {
			operatorStack.pop();
		}
		while (!numberStack.empty()) {
			numberStack.pop();
		}
		boolean previous = false;
		while (!atEndOfInput()) {
			if (isNumber()) {
				numberStack.push(getNumber());
				displayStacks();
				previous = true;
			} else {
				char op = getOperator();
				if ((op == '-') && (!previous)) {
					processOperator('u');
				} else {
					processOperator(op);
				}

				displayStacks();
				previous = op == ')';
			}
		}
		while (!operatorStack.empty()) {
			evaluateOperator();
		}
		return numberStack.pop();

	}

	public static void main(String[] args) {
		Parser parser = new Parser();

		while (true) {
			String line = ui.getInfo("Enter arithmetic expression or cancel.");
			if (line == null)
				return;

			try {
				double result = parser.evaluate(line);
				ui.sendMessage(line + " = " + result);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
