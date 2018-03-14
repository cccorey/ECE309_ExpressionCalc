
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
		recallButton.addActionListener(this);

		calcFrame.setSize(500, 500);
		calcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calcFrame.setVisible(true);
	}

	@Override
	public double calculate(String expression, String x) throws Exception {
		enteredExpression = expression;
		String smallExpression;
		expression = operandSubstitution(expression, x);
		//while () { LOOP HERE FOR EACH PART
			
			if (expression.contains("(")) {
				smallExpression = handleParenthesis(expression);
			}
			else
			{
				smallExpression = expression;
			}
			smallExpression = evaluateComplexExpression(expression);
		//}
			return Double.parseDouble(expression); //TEMP RETURN VALUE
		
	}

	private String handleParenthesis(String expression) throws Exception {
		// TODO Find the innermost parenthesis set and returns its section of the
		// expression
		return null;
	}

	private String evaluateComplexExpression(String complexExpression) throws Exception {
		// TODO Handle complex expressions, calling evaluateSimpleExpression when 2 are
		// left

		return "1";
	}

	private String evaluateSimpleExpression(String simpleExpression) throws Exception {
		// TODO Handles 1 operator remaining

		return null;
	}

	private String operandSubstitution(String expression, String x) throws Exception {
		// TODO Folds to lowercase, replaces pi, e, and x in the expression
			expression = expression.trim().toLowerCase();

		if (expression.contains("x") && x.equals("")) // No valid x given
			throw new IllegalArgumentException("No valid x value given");
		else
			expression = expression.replaceAll("x", x);

		expression = expression.replaceAll("e", String.valueOf(Math.E));
		expression = expression.replaceAll("pi", String.valueOf(Math.PI));

		System.out.println(expression);
		return expression;
	}

	public static void main(String args[]) {
		System.out.println("Jonathan Reese, Rachel Corey White, Geoffery Balshaw");
		new ExpressionCalculator();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// This method runs if there is an expression in the text area

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				double answer = calculate(inputTextField.getText(), xTextField.getText());
				outputTextArea.append(enteredExpression + " = " + answer + "\n");
				inputTextField.setText("");
				errorTextField.setText("");
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