package controller;

import model.Monomial;
import model.Polynomial;
import view.CalculatorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorController {
    private Polynomial calculatorModel;
    private Polynomial previousInput;

    private CalculatorView calculatorView;
    private Operation operation;

    public CalculatorController(Polynomial calculatorModel, CalculatorView calculatorView) {
        this.calculatorModel = calculatorModel;
        this.calculatorView = calculatorView;
        calculatorView.setCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculatorView.addAddListener(new OperationListener("+", Operation.ADD));
        calculatorView.addSubListener(new OperationListener("-", Operation.SUB));
        calculatorView.addMulListener(new OperationListener("\u00D7",  Operation.MUL));
        calculatorView.addDivListener(new OperationListener("\u00F7", Operation.DIV));
        calculatorView.addIntegrateListener(new OperationListener("\u222b", Operation.INTEGRATE));
        calculatorView.addDerListener(new OperationListener("\u2202", Operation.DERIVATIVE));
        calculatorView.addEqualListener(new ResultListener());
        calculatorView.addResetListener(new ResetListener());
    }

    public static void main(String[] args) {
        Polynomial model = new Polynomial();
        CalculatorView view = new CalculatorView(model);
        new CalculatorController(model, view);
    }

    private void reset() {
        calculatorModel.reset();
        calculatorView.reset();
        operation = Operation.NOTHING;
    }

    //method used for generating the polynomial from input string using regex
    private void getPolynomial() throws IllegalArgumentException {
        String input = calculatorView.getInput();
        if (input.equals("") || input.equals("+") || input.equals("-") || input.equals("0")) throw new IllegalArgumentException("Invalid input");
        Pattern pattern = Pattern.compile("(((?<!\\^)((^[+-]?\\d{0,3}|[+-]\\d{1,3})(\\.\\d+)?)X?)|([+-]X))(\\^([+-]?\\d))?\\b");
        Matcher matcher = pattern.matcher(input);
        calculatorModel.reset();
        while (matcher.find()) {
            double coefficient;
            int exponent;
            if (matcher.group(0).equals(""))  throw new IllegalArgumentException("Invalid input");
            if (matcher.group(3) == null || matcher.group(3).equals("+") || matcher.group(3).equals("")) coefficient = 1;
            else if (matcher.group(3).equals("-")) coefficient = -1;
            else coefficient = Double.parseDouble(matcher.group(3));
            if (matcher.group(8) == null || matcher.group(8).equals("")) {
                if (matcher.group(0).contains("X")) exponent = 1; else exponent = 0;
            } else exponent = Integer.parseInt(matcher.group(8));
            if (coefficient == Math.rint(coefficient)) calculatorModel.insert(new Monomial((int) coefficient, exponent));
            else throw new IllegalArgumentException("Invalid input: double coefficient");
        }
    }

    //an enum providing the current state of calculator
    private enum Operation {
        ADD, SUB, DIV, MUL, INTEGRATE, DERIVATIVE, NOTHING
    }

    //the operation listener is used by all operations since is just preparing the actual operation, after pressing
    //equal button
    private class OperationListener implements ActionListener {

        private String opString;
        private Operation op;
        OperationListener(String opString, Operation op)
        {
            this.opString = opString;
            this.op = op;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                getPolynomial();
                calculatorView.setOperation(opString);
                calculatorView.resetPrevious();
                if(op != Operation.DERIVATIVE && op != Operation.INTEGRATE){
                    try {
                        previousInput = (Polynomial) calculatorModel.clone();
                    } catch (Exception exception) {
                        calculatorView.showError(exception.getMessage());
                        return;
                    }
                    calculatorView.setPreviousInput();
                    calculatorView.resetOutput();
                }
                operation = op;
            }
            catch (Exception exception) {
                calculatorView.showError(exception.getMessage());
            }
        }
    }

    //method used for getting the result of actual operation
    private ArrayList<Polynomial> getResult(Polynomial previousInput) throws IllegalArgumentException {
        ArrayList<Polynomial> result = new ArrayList<>();
        switch (operation) {
            case ADD:
                result.add(calculatorModel.add(previousInput));
                break;
            case SUB:
                result.add(previousInput.sub(calculatorModel));
                break;
            case MUL:
                result.add(calculatorModel.mul(previousInput));
                break;
            case DIV:
                Polynomial[] p = previousInput.div(calculatorModel);
                result.add(p[0]);
                result.add(p[1]);
                break;
            case INTEGRATE:
                result.add(calculatorModel.integrate());
                break;
            case DERIVATIVE:
                result.add(calculatorModel.der());
                break;
            default:
                return null;
        }
        return result;
    }

    //since the model is parsed by reference, when the model is changed it is needed to update also the view
    private void changeModel(Polynomial newModel) {
        calculatorModel = newModel;
        calculatorView.changeModel(newModel);
    }

    //method used for printing the result of operation
    private void doOperation() throws IllegalArgumentException {
        ArrayList<Polynomial> result;
        getPolynomial();
        result = getResult(previousInput);
        if(result == null) throw new IllegalArgumentException("Please select an operation");
        else {
            changeModel(result.get(0));
            if(operation == Operation.DERIVATIVE || operation == Operation.INTEGRATE){
                calculatorView.setPreviousUnaryOperation();
            } else calculatorView.setPreviousOperation();
            calculatorView.setOperation("=");
            if(result.size() == 1 || result.get(1).equals(new Polynomial())) calculatorView.setOutput();
            else calculatorView.setOutputDiv(result);
            operation = Operation.NOTHING;
        }
    }

    //the result listener applied on equal button will effectuate the operation
    private class ResultListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(operation != Operation.NOTHING) {
                try {
                    doOperation();
                } catch(Exception exception) {
                    calculatorView.showError(exception.getMessage());
                }
            }
        }
    }

    //the reset listener applied on reset button will reset the model and view
    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
        }
    }
}
