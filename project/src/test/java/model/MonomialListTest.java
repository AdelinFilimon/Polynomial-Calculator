package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MonomialListTest {
    private MonomialList monomialList;
    private Monomial t1,t2,t3,t4,t5,t6,t7;

    @Before
    public void setUp() {
        monomialList = new MonomialList();
        t1 = new Monomial(-4,0);
        t2 = new Monomial(3,1);
        t3 = new Monomial(-2,2);
        t4 = new Monomial(7,3);

        monomialList.add(t1);
        monomialList.add(t2);
        monomialList.add(t3);
        monomialList.add(t4);

        t5 = new Monomial(2,0);
        t6 = new Monomial(3,2);
        t7 = new Monomial(1,2);
    }

    @Test
    public void getMonomialByPower() {
        assertEquals(t1, monomialList.getMonomialByPower(0));
        assertEquals(t2, monomialList.getMonomialByPower(1));
        assertEquals(t3, monomialList.getMonomialByPower(2));
        assertEquals(t4, monomialList.getMonomialByPower(3));
    }

    @Test
    public void setMonomialByPower() {
        monomialList.setMonomialByPower(0,t6);
        monomialList.simplify();
        assertEquals(t7, monomialList.getMonomialByPower(2));
    }

    @Test
    public void removeMonomialByPower() {
        assertTrue(monomialList.contains(t2));
        monomialList.removeMonomialByPower(1);
        assertFalse(monomialList.contains(t2));
    }

    @Test
    public void simplify() {
        monomialList.add(t5);
        monomialList.add(t6);
        monomialList.add(t7);
        monomialList.simplify();
        assertEquals(monomialList.getMonomialByPower(0).getCoefficient(), -2);
        assertEquals(monomialList.getMonomialByPower(2).getCoefficient(), 2);
    }

    @After
    public void cleanUp() {
        t1 = null;
        t2 = null;
        t3 = null;
        t4 = null;
        t5 = null;
        t6 = null;
        t7 = null;
        monomialList.clear();
        monomialList = null;
    }
}