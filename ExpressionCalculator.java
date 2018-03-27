//Geoffrey Balshaw, Rachel Corey White, Jonathan Reese

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ExpressionCalculator implements Calculator, KeyListener, ActionListener {

	String enteredExpression;
	String enteredX;

	JFrame calcFrame = new JFrame();
	JPanel ioPanel = new JPanel();
	JPanel northPanel = new JPanel();
	JPanel southPanel = new JPanel();

	JLabel inputLabel = new JLabel("Input");
	JTextField inputTextField = new JTextField();
	JLabel xLabel = new JLabel("X = ");
	JTextField xTextField = new JTextField();

	JButton recallButton = new JButton("Recall");
	JTextField errorTextField = new JTextField();

	JLabel outputLabel = new JLabel("Log");
	JTextArea outputTextArea = new JTextArea();
	JScrollPane outputScrollPane = new JScrollPane(outputTextArea);

	public ExpressionCalculator() {
		// TODO Auto-generated constructor stub
		calcFrame.getContentPane().add(ioPanel, "Center");
		calcFrame.getContentPane().add(northPanel, "North");
		calcFrame.getContentPane().add(southPanel, "South");

		calcFrame.setTitle("Expression Caclulator");
		ioPanel.setLayout(new GridLayout(1, 2));
		ioPanel.add(inputTextField);
		ioPanel.add(outputScrollPane);

		outputTextArea.setEditable(false);
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		outputLabel.setHorizontalAlignment(JLabel.CENTER);

		northPanel.setLayout(new GridLayout(1, 2));
		northPanel.add(inputLabel);
		northPanel.add(outputLabel);

		southPanel.setLayout(new GridLayout(1, 4));
		southPanel.add(xLabel);
		southPanel.add(xTextField);
		southPanel.add(recallButton);
		southPanel.add(errorTextField);

		errorTextField.setEditable(false);
		errorTextField.setBackground(Color.WHITE);
		errorTextField.setSelectedTextColor(Color.BLACK);

		inputTextField.addKeyListener(this);
		xTextField.addKeyListener(this);
		recallButton.addActionListener(this);

		calcFrame.setSize(500, 500);
		calcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calcFrame.setVisible(true);
	}

	@Override
	public double calculate(String expression, String x) throws Exception {
		System.out.println("Jonathan Reese, Rachel Corey White, Geoffrey Balshaw");
		enteredExpression = expression;
		String smallExpression = null;
		expression = operandSubstitution(expression, x);
	//	while (expression.contains("^") || expression.contains("r") ||expression.contains( "*") || expression.contains("/") || expression.contains("+") || expression.contains("-") ) { //LOOP HERE FOR EACH PART
			// as long as there are still operations to be done, evaluate the expression
			
			if (expression.contains("(")) {
				System.out.println("expression to be handled is " + expression);
				expression = handleParenthesis(expression);
				
			}
			else
			{
				smallExpression = expression;
			}
			smallExpression = evaluateComplexExpression(expression);
			smallExpression = smallExpression.replace('u', '-');
			System.out.println(smallExpression);
	//	}
			return Double.parseDouble(smallExpression); //TEMP RETURN VALUE
		
	}

	private String handleParenthesis(String expression) throws Exception {
		// TODO Find the innermost parenthesis set and returns its section of the expression
		
		//SHOULD RETURN AN ERROR IF NOT AN EQUAL # OF PARENTHESIS?
		
		System.out.println("Entering handleParenthesis");
		int leftParenIndex =0; // = expression.lastIndexOf("(");
		int rightParenIndex =0; // = expression.indexOf(")");
		int parenCount =0;				
		
		int i=0;		
		int[] countarray = new int[expression.length()];
		
		for(i=0; i < expression.length(); i++) {
			if(expression.charAt(i) == '(') parenCount++;
			if(expression.charAt(i) == ')') parenCount--;
			countarray[i] = parenCount;			
		}
		
		if(countarray[expression.length() -1] !=0) {
			System.out.println("Uneven left and right parenthesis.");
			throw new IllegalArgumentException("Uneven left and right parenthesis.");
		}
		
		int max = 0;
		
		for(i=0; i < countarray.length; i++) {
			if (countarray[i] > max) max = countarray[i];
		}
			
			
		for (i=0; i < countarray.length; i++) {
			if (countarray[i] == max) {
				leftParenIndex = i;
				break;
			}
		}

		
//		String afterParen = expression.substring(leftParenIndex+1);
		rightParenIndex = expression.indexOf(')', leftParenIndex);
		
		String leftOfParen = expression.substring(0, leftParenIndex);
		
//		if(leftParenIndex != 0) {
//			String leftOfParen = expression.substring(0, leftParenIndex-1);
//		}
//		else {
//			String leftOfParen = null;
//		}
		
		String rightOfParen = expression.substring(rightParenIndex+1);		
		String innerExpression = expression.substring(leftParenIndex +1, rightParenIndex);
		
		String intermedExpression = evaluateComplexExpression(innerExpression);		
		
		String finalExpression = leftOfParen + intermedExpression + rightOfParen;
		
		System.out.println("finalExpression is " + finalExpression);
		
		if(finalExpression.contains("(")) finalExpression = handleParenthesis(finalExpression);
		
		return finalExpression;
	}

	private String evaluateComplexExpression(String complexExpression) throws Exception {
		// TODO Handle complex expressions, calling evaluateSimpleExpression when 2 are left
		System.out.println("Entering complex eval");
		String simpleExpress = " ";
		String simpleExpressVal = " ";
		int beforeOperator = ' ';
		int afterOperator = ' ';
		int currentOperator = ' ';
		int i;
		
		
		
		System.out.println("complexExpression is " + complexExpression);
		while(complexExpression.contains("r") || complexExpression.contains("^") || complexExpression.contains("*") || complexExpression.contains("/") || complexExpression.contains("+") || complexExpression.contains("-")) {
					
			if(complexExpression.charAt(0) == '-') { complexExpression = 'u' + complexExpression.substring(1); }
			
				if(complexExpression.contains("r") && complexExpression.contains("^")) { 
					if(complexExpression.indexOf("r") < complexExpression.indexOf("^")) { currentOperator = complexExpression.indexOf("r"); }
					else { currentOperator = complexExpression.indexOf("^"); }
				}
				
				else if(complexExpression.contains("r")) { currentOperator = complexExpression.indexOf("r"); }
				else if(complexExpression.contains("^")) { currentOperator = complexExpression.indexOf("^"); }
				
				else if(complexExpression.contains("*") && complexExpression.contains("/")) { 
					if(complexExpression.indexOf("*") < complexExpression.indexOf("/")) { currentOperator = complexExpression.indexOf("*"); }
					else { currentOperator = complexExpression.indexOf("/"); }
				}
				
				else if(complexExpression.contains("*")) { currentOperator = complexExpression.indexOf("*"); }
				else if(complexExpression.contains("/")) { currentOperator = complexExpression.indexOf("/"); }
				
				else if(complexExpression.contains("+") && complexExpression.contains("-")) { 
					if(complexExpression.indexOf("+") < complexExpression.indexOf("-")) { currentOperator = complexExpression.indexOf("+"); }
					else { currentOperator = complexExpression.indexOf("-"); }
				}
				
				else if(complexExpression.contains("+")) { currentOperator = complexExpression.indexOf("+"); }
				else if(complexExpression.contains("-")) { currentOperator = complexExpression.indexOf("-"); }
				
				System.out.println("index of currentOperator is " + currentOperator);
				
				for(i = currentOperator-1; i >= 0; i--) {
					if((complexExpression.charAt(i) == 'r') || (complexExpression.charAt(i) == '^') || (complexExpression.charAt(i) == '*') || (complexExpression.charAt(i) == '/') || (complexExpression.charAt(i) == '+') || (complexExpression.charAt(i) == '-')) {
						beforeOperator = i;
						break;
					}
					else { beforeOperator = -1; }
				}
				
				System.out.println("index of beforeOperator is " + beforeOperator);

				for(i = currentOperator+1; i < complexExpression.length(); i++) {
					if((complexExpression.charAt(i) == 'r') || (complexExpression.charAt(i) == '^') || (complexExpression.charAt(i) == '*') || (complexExpression.charAt(i) == '/') || (complexExpression.charAt(i) == '+') || (complexExpression.charAt(i) == '-')) {
						afterOperator = i;
						break;
					}
					else { afterOperator = -1; }
				}
				
				System.out.println("index of afterOperator is " + afterOperator);
				
				if ((beforeOperator == -1) && (afterOperator == -1)) { simpleExpress = complexExpression; }
				else if (beforeOperator == -1) { simpleExpress = complexExpression.substring(0, afterOperator); }
				else if (afterOperator == -1) { simpleExpress = complexExpression.substring(beforeOperator+1); }
				else { simpleExpress = complexExpression.substring(beforeOperator+1, afterOperator); }

				if(simpleExpress.charAt(0) == 'u') { simpleExpress = '-' + simpleExpress.substring(1); }
				
				System.out.println("simpleExpress is " + simpleExpress);
				simpleExpressVal = evaluateSimpleExpression(simpleExpress);
				
				if(simpleExpress.charAt(0) == '-') { simpleExpress = 'u' + simpleExpress.substring(1); }
				
				System.out.println("simpleExpressVal is " + simpleExpressVal);
				System.out.println("simpleExpress is " + simpleExpress);
				System.out.println("Complex ex is" + complexExpression);
				complexExpression = complexExpression.replace(simpleExpress, simpleExpressVal);
				simpleExpressVal = simpleExpressVal.replace('-', 'u');
				if(complexExpression.charAt(0) == '-') { complexExpression = 'u' + complexExpression.substring(1); }
				System.out.println("Complex express at bottom of while is " + complexExpression);
			}
		
	
		return complexExpression; 
	}

	private String evaluateSimpleExpression(String simpleExpression) throws Exception {
		// TODO Handles 1 operator remaining
		//simpleExpression = simpleExpression.replaceAll("u", "-");
		System.out.println("simpleExpression is " + simpleExpression);
		char operator = ' ';
		int i;
		for(i = 1; i < simpleExpression.length(); i++) {
			if((simpleExpression.charAt(i) == '+') 
					|| (simpleExpression.charAt(i) == '-') 
					|| (simpleExpression.charAt(i) == '*') 
					|| (simpleExpression.charAt(i) == '/') 
					|| (simpleExpression.charAt(i) == '^') 
					|| (simpleExpression.charAt(i) == 'r')) {
				operator = simpleExpression.charAt(i);
				break;
			}				
		}
		
		if((i == simpleExpression.length()) || (i == simpleExpression.length()-1)) {
			System.out.println("Expression is not an operator surrounded by operands");
			throw new IllegalArgumentException("Expression is not an operator surrounded by operands");
			//return null;
		}
		
		String leftOperand = simpleExpression.substring(0,i).trim();
		String rightOperand = simpleExpression.substring(i+1).trim();
		
		double leftNumber;
		try { leftNumber = Double.parseDouble(leftOperand);
				System.out.println("leftOperand is " + leftOperand);}
		catch(NumberFormatException nfe) { 
			System.out.println("Left operand " + leftOperand + " is not numeric.");
			throw new IllegalArgumentException("Left operand " + leftOperand + " is not numeric.");
			//return null; }
		}

		double rightNumber;
		try { rightNumber = Double.parseDouble(rightOperand); }
		catch(NumberFormatException nfe) { 
			System.out.println("Right operand is not numeric.");
			throw new IllegalArgumentException("Right operand is not numeric.");
			//return null; }
		}
		
		double result = 0;
		switch(operator) {
		case '+': 	result = leftNumber + rightNumber;
				  	break;
		case '-': 	result = leftNumber - rightNumber;
	  				break;
		case '*': 	result = leftNumber * rightNumber;
	  				break;
		case '/': 	result = leftNumber / rightNumber;
	  				break;
		case '^': 	result = Math.pow(leftNumber, rightNumber);
	  				break;
		case 'r': 	result = Math.pow(leftNumber, 1/rightNumber);
	  				break;
		}

		return Double.toString(result);
	}

	private String operandSubstitution(String expression, String x) throws Exception {
		// TODO Folds to lowercase, replaces pi, e, and x in the expression
			expression = expression.trim().toLowerCase();

		if (expression.contains("x") && x.equals("")) // No valid x given
			throw new IllegalArgumentException("No valid x value given");
		else if (!x.equals("") && !expression.contains("x"))
			throw new IllegalArgumentException("Expression does not contain x with x value specified");
		else
			expression = expression.replaceAll("x", x);

		expression = expression.replaceAll("e", String.valueOf(Math.E));
		expression = expression.replaceAll("pi", String.valueOf(Math.PI));

		System.out.println(expression);
		return expression;
	}

	public static void main(String args[]) {
		System.out.println("Jonathan Reese, Rachel Corey White, Geoffrey Balshaw");
		new ExpressionCalculator();
		//handleParenthesis("(10+5+2-3)*54+69/(19+4)");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// This method runs if there is an expression in the text area

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				double answer = calculate(inputTextField.getText(), xTextField.getText());
				outputTextArea.append(enteredExpression + " = " + answer);
				if (inputTextField.getText().toLowerCase().contains("x"))
				{
					outputTextArea.append(" for x = " + xTextField.getText() +"\n");
				}
				else {
					outputTextArea.append("\n");
				}
				outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
				inputTextField.setText("");
				errorTextField.setText("");
				xTextField.setText("");
				errorTextField.setBackground(Color.WHITE);
			} catch (Exception e1) {
				errorTextField.setText(e1.toString());
				errorTextField.setBackground(Color.PINK);
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == recallButton) {
			inputTextField.setText(enteredExpression);
			xTextField.setText(enteredX);
			errorTextField.setText("");
			errorTextField.setBackground(Color.WHITE);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		/* Ignore */ }

	@Override
	public void keyTyped(KeyEvent arg0) {
		/* Ignore */ }

}
