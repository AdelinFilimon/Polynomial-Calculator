package model;

import java.util.Objects;

public class Monomial {
    private int coefficient;
    protected int exponent;


    public Monomial(int coefficient, int exponent) {
        if(exponent < 0) throw new IllegalArgumentException("The exponent must be greater or equal than 0");
        if(coefficient == 0) throw new IllegalArgumentException("The coefficient must be different than 0");
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    public int getCoefficient() { return coefficient; }

    public int getExponent() {
        return exponent;
    }


    public Monomial add(Monomial y) {
        if(y == null) return new Monomial(coefficient, exponent);
        if(exponent != y.exponent) throw new IllegalArgumentException("The monomials must have the same exponent");

        //checking the class of y, assign the correct coefficient to yCoefficient
        double yCoefficient = y instanceof MonomialOnDouble ?
                                ((MonomialOnDouble) y).getDoubleCoefficient() : (double) y.coefficient;
        double newCoefficient = coefficient + yCoefficient;
        if(newCoefficient == 0) return null;
        //if the result coefficient is an int the method will return a Monomial else it will return a MonomialOnDouble
        if(newCoefficient == Math.rint(newCoefficient))
            return new Monomial((int)newCoefficient, exponent);
        else return new MonomialOnDouble(newCoefficient, exponent);
    }

    public Monomial mul(Monomial y) {
        if(y == null) return null;
        //checking the class of y, assign the correct coefficient to yCoefficient
        double yCoefficient = y instanceof MonomialOnDouble ?
                            ((MonomialOnDouble) y).getDoubleCoefficient() : (double) y.coefficient;
        double newCoefficient = coefficient * yCoefficient;
        //if the result coefficient is an int the method will return a Monomial else it will return a MonomialOnDouble
        if(newCoefficient == Math.rint(newCoefficient))
            return new Monomial((int)newCoefficient, exponent + y.exponent);
        else return new MonomialOnDouble(newCoefficient, exponent + y.exponent);
    }


    public Monomial div(Monomial y) {
        if(y == null) throw new IllegalArgumentException("The divisor must be different than null");
        if(y.exponent > exponent) throw new IllegalArgumentException(
                "The power of the divisor must be smaller or equal than the power of the dividend");
        //checking the class of y, assign the correct coefficient to yCoefficient
        double yCoefficient = y instanceof MonomialOnDouble ?
                                ((MonomialOnDouble) y).getDoubleCoefficient() : (double) y.coefficient;
        double newCoefficient = coefficient / yCoefficient;
        //if the result coefficient is an int the method will return a Monomial else it will return a MonomialOnDouble
        if(newCoefficient == Math.rint(newCoefficient))
            return new Monomial((int)newCoefficient, exponent - y.exponent);
        else return new MonomialOnDouble(newCoefficient, exponent - y.exponent);
    }

    public Monomial der() {
        if(exponent == 0) return null;
        return new Monomial(coefficient * exponent, exponent - 1);
    }

    public Monomial integrate() {
        double newCoefficient = (double) coefficient / (exponent +1);
        //if the result coefficient is an int the method will return a Monomial else it will return a MonomialOnDouble
        return (newCoefficient == Math.rint(newCoefficient)) ?
                new Monomial((int)newCoefficient, exponent + 1)
                : new MonomialOnDouble(newCoefficient, exponent +1);
    }

    //method used for subtracting, since this operation is just an add with a negated operand
    public Monomial negate() {
        return new Monomial(-coefficient, exponent);
    }

    //used for displaying purposes, the resulted string looks more natural
    @Override
    public String toString() {

        switch(exponent) {
            case 0 :
                return (coefficient > 0) ? "+" + coefficient : String.valueOf(coefficient);
            case 1:
                if(coefficient > 0) return (coefficient == 1) ? "+X" : "+" + coefficient + "X";
                return (coefficient == -1) ? "-X" : coefficient + "X";
            default:
                if(coefficient > 0)
                    return (coefficient == 1) ? "+X^" + exponent : "+" + coefficient + "X^" + exponent;
                return (coefficient == -1) ? "-X^" + exponent : coefficient + "X^"+ exponent;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Monomial)) return false;
        Monomial monomial = (Monomial) o;
        return coefficient == monomial.coefficient && exponent == monomial.exponent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, exponent);
    }

}
