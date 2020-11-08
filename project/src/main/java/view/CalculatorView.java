package view;

import com.formdev.flatlaf.FlatDarculaLaf;
import model.Polynomial;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class CalculatorView extends JFrame {

    private Polynomial calculatorModel;

    private JPanel content;
    //the inoutText acts as a dual channel, providing, based on the state of the calculator, the input channel or the output
    //of the operation performed
    private JTextField inoutText;
    private JLabel previousInput, operation;
    private JButton addButton, subButton, divButton, mulButton, integrateButton, derButton, resetButton, equalButton;
    private JButton getInfo;
    private JScrollPane previousInputPane;

    public CalculatorView(Polynomial calculatorModel) {
        FlatDarculaLaf.install(); //dark theme used for GUI
        this.calculatorModel = calculatorModel;
        init(); //the method will initialize the buttons and will config the frame
        setContent(); //the method will arrange the components and set the fonts
    }

    public void changeModel(Polynomial newModel) {
        calculatorModel = newModel;
    }

    private void init() {

        content = new JPanel(new MigLayout("fill"));
        inoutText = new JTextField();
        previousInput = new JLabel();
        operation = new JLabel("#");
        addButton = new JButton("+");
        subButton = new JButton("-");
        divButton = new JButton("\u00F7");
        mulButton = new JButton("\u00D7");
        integrateButton = new JButton("\u222b");
        derButton = new JButton("\u2202");
        resetButton = new JButton("C");
        equalButton = new JButton("=");
        getInfo = new JButton("\u03AF");
        previousInputPane = new JScrollPane(previousInput,JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setTitle("Polynomial Calculator");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(content);
        setVisible(true);
    }

    //the layout used for arranging the components is MigLayout, a powerful and simple layout
    private void setContent() {

        Font btnFont = new Font("Arial",Font.PLAIN,70);
        Font textFont = new Font("Arial", Font.PLAIN, 30);
        Font pInputFont = new Font("Arial", Font.PLAIN, 20);

        content.add(previousInputPane, "span,center, wrap, width :80%:, height :7.5%:");
        content.add(operation, "height :7.5%:, split, gapx 20");
        content.add(inoutText, "span, center, wrap, width :80%:, height :10%:");
        content.add(addButton, "span,center,width :20%:, height :20%:, split 3, gapy 20");
        content.add(subButton, "width :20%:, height :20%:");
        content.add(divButton, "width :20%:, height :20%:,wrap");
        content.add(mulButton, "span,center,width :20%:, height :20%:, split 3");
        content.add(integrateButton, "width :20%:, height :20%:");
        content.add(derButton,"width :20%:, height :20%:, wrap");
        content.add(resetButton, "width :20%:, height :20%:, split, center");
        content.add(equalButton, "width :40%, height :20%:, span 2, wrap");
        content.add(getInfo);

        previousInput.setFont(pInputFont);
        inoutText.setFont(textFont);
        operation.setFont(textFont);
        addButton.setFont(btnFont);
        subButton.setFont(btnFont);
        divButton.setFont(btnFont);
        mulButton.setFont(btnFont);
        integrateButton.setFont(btnFont);
        derButton.setFont(btnFont);
        resetButton.setFont(btnFont);
        equalButton.setFont(btnFont);

        getInfo.addActionListener((ActionEvent)->{
            String message = "The polynomial must contain one or multiple monomials.\n" +
                    "Example: 3X^3 -5X^2 +3X^4 -12.\n" +
                    "The monomial is of form: +/-aX^b, the sign can be omitted for first monomial.";
            JOptionPane.showMessageDialog(this, message, "info", JOptionPane.INFORMATION_MESSAGE);
        });

    }

    public void reset() {
        inoutText.setText("");
        previousInput.setText("");
        operation.setText("#");
    }

    public String getInput() {
        return inoutText.getText();
    }

    public void setOutput() {
        inoutText.setText(calculatorModel.toString());
    }

    //method used for displaying the result of a division operation
    public void setOutputDiv(ArrayList<Polynomial> result) {
        inoutText.setText("Q:" + result.get(0).toString() + " R:" + result.get(1).toString());
    }

    public void resetOutput() { inoutText.setText("");}

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setPreviousInput() {
        previousInput.setText(calculatorModel.toString());
    }

    //method used for displaying the operation performed, for 2 operands
    public void setPreviousOperation() {
        previousInput.setText("("+previousInput.getText() + ") " + operation.getText() + " (" + inoutText.getText()+")");
    }

    //method used for displaying the operation performed, for 1 operand
    public void setPreviousUnaryOperation() {
        previousInput.setText(operation.getText() + "("+inoutText.getText()+")");
    }

    public void resetPrevious() {
        previousInput.setText("");
    }

    public void setOperation(String operationString) {
        operation.setText(operationString);
    }

    public void addAddListener(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void addSubListener(ActionListener actionListener) {
        subButton.addActionListener(actionListener);
    }

    public void addDivListener(ActionListener actionListener) {
        divButton.addActionListener(actionListener);
    }

    public void addMulListener(ActionListener actionListener) {
        mulButton.addActionListener(actionListener);
    }

    public void addDerListener(ActionListener actionListener) {
        derButton.addActionListener(actionListener);
    }

    public void addIntegrateListener(ActionListener actionListener) {
        integrateButton.addActionListener(actionListener);
    }

    public void addResetListener(ActionListener actionListener) {
        resetButton.addActionListener(actionListener);
    }

    public void addEqualListener(ActionListener actionListener) {
        equalButton.addActionListener(actionListener);
    }

    public void setCloseOperation(int operation) {
        setDefaultCloseOperation(operation);
    }

}
