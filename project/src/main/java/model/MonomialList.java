package model;

import java.util.ArrayList;
//an array list of monomials with some features regarding getting and setting monomials by their power
public class MonomialList extends ArrayList<Monomial> {
    //the method will return the monomial with specified power, if dont exist return null
    public Monomial getMonomialByPower(int power) {
        for(Monomial monomial : this) {
            if(monomial.getExponent() == power) return monomial;
        }
        return null;
    }

    //the method will change the monomial with specified power with a new monomial
    public void setMonomialByPower(int power, Monomial monomial) {
        int index = this.indexOf(getMonomialByPower(power));
        this.set(index, monomial);
    }

    public void removeMonomialByPower(int power) {
        this.remove(getMonomialByPower(power));
    }

    //method used for reducing the number of elements by combining the monomials with the same power
    //useful in operations like add and subtract
    public void simplify() {

        boolean existDuplicates = false;
        MonomialList temp = new MonomialList();
        for(Monomial monomial : this) {
            int power = monomial.getExponent();
            if(temp.getMonomialByPower(power) != null) {
                existDuplicates = true;
                if(monomial.add(temp.getMonomialByPower(power)) != null)
                    temp.setMonomialByPower(power, monomial.add(temp.getMonomialByPower(power)));
                else temp.removeMonomialByPower(power);
            } else temp.add(monomial);
        }

        if(existDuplicates) {
            this.clear();
            this.addAll(temp);
        }
    }

    @Override
    public boolean add(Monomial monomial) {
        if(monomial != null) return super.add(monomial);
        return false;
    }
}
