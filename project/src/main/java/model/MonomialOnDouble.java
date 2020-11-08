package model;

import java.text.DecimalFormat;
import java.util.Objects;

//class providing the ability to work with double coefficient monomials
//the inherited operations will have minor changes
public class MonomialOnDouble extends Monomial {
    private double coefficient;

    public MonomialOnDouble(double coefficient, int exponent) {
        super(1, exponent);
        this.coefficient = coefficient;
    }

    public double getDoubleCoefficient() {
        return coefficient;
    }

    @Override
    public Monomial add(Monomial y) {
        if(y == null) return this;
        if(exponent != y.exponent) throw new IllegalArgumentException("The monomials must have the same exponent");
        double yCoefficient = y instanceof MonomialOnDouble ?
                ((MonomialOnDouble) y).getDoubleCoefficient() : (double) y.getCoefficient();
        double newCoefficient = coefficient + yCoefficient;
        if(newCoefficient == 0) return null;
        if(newCoefficient == Math.rint(newCoefficient))
            return new Monomial((int)(newCoefficient), exponent);
        else return new MonomialOnDouble(newCoefficient, exponent);
    }

    @Override
    public Monomial mul(Monomial y) {
        if(y == null) return null;
        double yCoefficient = y instanceof MonomialOnDouble ?
                ((MonomialOnDouble) y).getDoubleCoefficient() : (double) y.getCoefficient();
        double newCoefficient = coefficient * yCoefficient;
        if(newCoefficient == Math.rint(newCoefficient))
            return new Monomial((int)newCoefficient, exponent + y.exponent);
        else return new MonomialOnDouble(newCoefficient, exponent + y.exponent);
    }

    @Override
    public Monomial div(Monomial y) {
        if(y == null) throw new IllegalArgumentException("The divisor must be different than null");
        if(y.exponent > exponent) throw new IllegalArgumentException(
                "The power of the divisor must be smaller or equal than the power of the dividend");
        double yCoefficient = y instanceof MonomialOnDouble ?
                ((MonomialOnDouble) y).getDoubleCoefficient() : (double) y.getCoefficient();
        double newCoefficient = coefficient / yCoefficient;
        if(newCoefficient == Math.rint(newCoefficient))
            return new Monomial((int)newCoefficient, exponent - y.exponent);
        else return new MonomialOnDouble(newCoefficient, exponent - y.exponent);
    }

    @Override
    public Monomial negate() {
        return new MonomialOnDouble(-coefficient, exponent);
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("##.####");
        String coefficientString = decimalFormat.format(coefficient);
        switch(exponent) {
            case 0 :
                return (coefficient > 0) ? "+" + coefficientString : coefficientString;
            case 1:
                if(coefficient > 0) return (coefficient == 1) ? "+X" : "+" + coefficientString + "X";
                return (coefficient == -1) ? "-X" : coefficientString + "X";
            default:
                if(coefficient > 0)
                    return (coefficient == 1) ? "+X^" + exponent : "+" + coefficientString + "X^" + exponent;
                return (coefficient == -1) ? "-X^" + exponent : coefficientString + "X^"+ exponent;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof MonomialOnDouble)) return false;
        MonomialOnDouble monomial = (MonomialOnDouble) o;
        return Double.compare(coefficient, monomial.coefficient) == 0 && exponent == monomial.exponent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, exponent);
    }

}
