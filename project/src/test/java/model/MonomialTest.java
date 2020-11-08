package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MonomialTest {
    private Monomial t0,t1,t2,t3,t4,t5,t6,t7;
    private MonomialOnDouble t8,t9,t10;
    private final double coefficientT10 = 1.73d;

    @Before
    public void setUp() {
        t0 = new Monomial(8,2);
        t1 = new Monomial(-4,0);
        t2 = new Monomial(3,1);
        t3 = new Monomial(-2,2);
        t4 = new Monomial(7,3);

        t5 = new Monomial(2,0);
        t6 = new Monomial(3,2);
        t7 = new Monomial(1,2);

        t8 = new MonomialOnDouble(Math.PI, 3);
        t9 = new MonomialOnDouble(Math.E, 2);
        t10 = new MonomialOnDouble(1.73,0);

    }

    @Test
    public void add() {
        assertEquals(new Monomial(-2,0), t1.add(t5));
        assertEquals(new Monomial(1,2), t3.add(t6));
        assertEquals(new Monomial(4,2), t6.add(t7));

        assertEquals(t1,t1.add(null));

        assertEquals(new MonomialOnDouble(Math.PI + 7,3), t4.add(t8));
        assertEquals(new MonomialOnDouble(Math.E - 2, 2), t3.add(t9));
        assertEquals(new MonomialOnDouble(coefficientT10 - 4, 0), t1.add(t10));

        assertNull(t1.add(t1.negate()));

        assertEquals(new Monomial(-3,0), t1.add(new MonomialOnDouble(1d,0)));

    }

    @Test
    public void mul() {
        assertNull(t1.mul(null));

        assertEquals(new Monomial(-12,1), t1.mul(t2));
        assertEquals(new Monomial(-14,5), t3.mul(t4));
        assertEquals(new Monomial(3,4), t6.mul(t7));

        assertEquals(new MonomialOnDouble(Math.PI * 7, 6), t4.mul(t8));
        assertEquals(new MonomialOnDouble(Math.E * -2, 4), t3.mul(t9));
        assertEquals(new MonomialOnDouble(coefficientT10 * -4, 0), t1.mul(t10));

        assertEquals(new Monomial(-4,0), t1.mul(new MonomialOnDouble(1d,0)));
    }

    @Test
    public void div() {
        assertEquals(new MonomialOnDouble(3/2d,1), t2.div(t5));
        assertEquals(new Monomial(-4,0), t0.div(t3));
        assertEquals(new MonomialOnDouble(3/Math.E, 0), t6.div(t9));

        try {
            t1.div(t8);
            fail();
        } catch(Exception ignored){}

        try {
            t2.div(null);
            fail();
        } catch (Exception ignored) {}
    }

    @Test
    public void der() {
        assertEquals(new Monomial(16,1), t0.der());
        assertEquals(new Monomial(3,0), t2.der());
        assertEquals(new Monomial(2,1), t7.der());

        assertNull(t1.der());
    }

    @Test
    public void integrate() {
        assertEquals(new Monomial(-4,1), t1.integrate());
        assertEquals(new MonomialOnDouble(8/3d, 3), t0.integrate());
        assertEquals(new MonomialOnDouble(3/2d, 2), t2.integrate());
    }

    @After
    public void cleanUp() {
        t0 = null;
        t1 = null;
        t2 = null;
        t3 = null;
        t4 = null;
        t5 = null;
        t6 = null;
        t7 = null;
        t8 = null;
        t9 = null;
        t10 = null;
    }
}