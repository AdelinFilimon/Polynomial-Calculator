package model;

import java.util.Comparator;
//simple comparator used for sorting a monomial list in decrease order by its monomial`s power
public class MonomialByPowerComparator implements Comparator<Monomial> {

    @Override
    public int compare(Monomial o1, Monomial o2) {
        return o2.getExponent() - o1.getExponent();
    }
}
