package model;

import java.util.Collections;
import java.util.Objects;

public class Polynomial implements Cloneable {

    private MonomialList terms;

    public Polynomial(MonomialList terms) {
        this.terms = terms;
        simp(); //making sure the monomial list provided is sorted and simplified
    }

    public Polynomial() {
        this(new MonomialList());
    }

    public void insert(Monomial... terms) {
        Collections.addAll(this.terms, terms);
        simp(); //making sure the monomial list provided is sorted and simplified
    }

    private void setList(MonomialList list){
        terms = list;
    }

    public MonomialList getTerms() {
        return terms;
    }

    //the simplify method will do the job when the resulting polynomial is created
    public Polynomial add(Polynomial y) {
        MonomialList newTerms = (MonomialList)terms.clone();
        newTerms.addAll(y.terms);
        return new Polynomial(newTerms);
    }
    //the subtract method doesnt use the same approach as above since it is used by division algorithm and needs
    //special treatment with MonomialOnDouble terms using negate method
    public Polynomial sub(Polynomial y) {
        MonomialList newTerms = (MonomialList)terms.clone();
        for(Monomial monomial : y.terms) {
            newTerms.add(monomial.negate());
        }
        return new Polynomial(newTerms);
    }

    public Polynomial mul(Polynomial y) {
        MonomialList newTerms = new MonomialList();
        for(Monomial monomial : terms) {
            for(Monomial monomial1 : y.terms) {
                Monomial product = monomial.mul(monomial1);
                newTerms.add(product);
            }
        }
        return new Polynomial(newTerms);
    }
    //the division method will return the quotient and the remainder of operation
    //the algorithm is discussed in documentation
    public Polynomial[] div(Polynomial y) {
        String nullArgExceptionMessage = "The divisor must be different than null";
        String powerArgExceptionMessage =
                "The power of the divisor must be smaller or equal than the power of the dividend";
        if(y == null) throw new IllegalArgumentException(nullArgExceptionMessage);
        Monomial leadingTerm = terms.get(0);
        Monomial yLeadingTerm = y.terms.get(0);
        if(yLeadingTerm.getExponent() > leadingTerm.getExponent())
            throw new IllegalArgumentException(powerArgExceptionMessage);
        Polynomial q = new Polynomial(new MonomialList());
        Polynomial r = new Polynomial(terms);
        while(!r.terms.isEmpty() && r.terms.get(0).getExponent() >= yLeadingTerm.getExponent()) {
            MonomialList t1 = new MonomialList();
            t1.add(r.terms.get(0).div(yLeadingTerm));
            Polynomial t = new Polynomial(t1);
            q = q.add(t);
            r = r.sub(t.mul(y));
        }
        Polynomial[] result = new Polynomial[2];
        result[0] = q;
        result[1] = r;
        return result;
    }

    public Polynomial der() {
        MonomialList newTerms = new MonomialList();
        for(Monomial monomial : terms) {
            newTerms.add(monomial.der());
        }
        return new Polynomial(newTerms);
    }

    public Polynomial integrate() {
        MonomialList newTerms = new MonomialList();
        for(Monomial monomial : terms) {
            newTerms.add(monomial.integrate());
        }
        return new Polynomial(newTerms);
    }

    public void reset() {
        terms.clear();
    }

    //the method used for preparing the polynomial for upcoming operations
    private void simp() {
        terms.simplify();
        terms.sort(new MonomialByPowerComparator());
    }

    @Override
    public String toString() {
        StringBuilder polynomial = new StringBuilder();

        for(Monomial monomial : terms) {
            if(monomial == terms.get(0)) {
                if(monomial.toString().contains("+")) polynomial.append(monomial.toString().substring(1));
                else polynomial.append(monomial.toString());
            }
            else polynomial.append(monomial.toString());
        }
        return polynomial.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Polynomial)) return false;
        Polynomial polynomial = (Polynomial) o;
        return terms.equals(polynomial.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terms);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Polynomial cloned = (Polynomial) super.clone();
        cloned.setList((MonomialList)cloned.getTerms().clone());
        return cloned;
    }
}