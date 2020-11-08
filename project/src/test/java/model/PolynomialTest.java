package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PolynomialTest {
    private Polynomial p1,p2,result,q,r;

    @Before
    public void setUp() {
        Monomial t1,t2,t3,t4,t5,t6,t7;
        t1 = new Monomial(-4,0);
        t2 = new Monomial(3,1);
        t3 = new Monomial(-2,2);
        t4 = new Monomial(7,3);
        t5 = new Monomial(2,0);
        t6 = new Monomial(3,2);
        t7 = new Monomial(1,2);
        p1 = new Polynomial();
        p2 = new Polynomial();
        result = new Polynomial();
        q = new Polynomial();
        r = new Polynomial();

        p1.insert(t1,t2,t3);
        p2.insert(t4,t5,t6,t7);
    }

    @Test
    public void add() {
        Monomial r1 = new Monomial(7,3);
        Monomial r2 = new Monomial(2,2);
        Monomial r3 = new Monomial(3,1);
        Monomial r4 = new Monomial(-2,0);
        result.insert(r1,r2,r3,r4);
        assertEquals(result, p1.add(p2));
    }

    @Test
    public void sub() {
        Monomial r1 = new Monomial(-7,3);
        Monomial r2 = new Monomial(-6,2);
        Monomial r3 = new Monomial(3,1);
        Monomial r4 = new Monomial(-6,0);
        result.insert(r1,r2,r3,r4);
        assertEquals(result, p1.sub(p2));
    }

    @Test
    public void mul() {
        Monomial r1 = new Monomial(-14,5);
        Monomial r2 = new Monomial(13,4);
        Monomial r3 = new Monomial(-16,3);
        Monomial r4 = new Monomial(-20,2);
        Monomial r5 = new Monomial(6,1);
        Monomial r6 = new Monomial(-8,0);
        result.insert(r1,r2,r3,r4,r5,r6);
        assertEquals(result, p1.mul(p2));
    }

    @Test
    public void div() {

        Monomial q1 = new MonomialOnDouble(-3.5,1);
        Monomial q2 = new MonomialOnDouble(-7.25,0);
        Monomial r1 = new MonomialOnDouble(7.75,1);
        Monomial r2 = new MonomialOnDouble(-27,0);
        q.insert(q1,q2);
        r.insert(r1,r2);

        assertEquals(q.toString(), p2.div(p1)[0].toString());
        assertEquals(r.toString(),p2.div(p1)[1].toString());
        assertEquals(p2, p1.mul(q).add(r));
    }

    @Test
    public void der() {
        Polynomial result = new Polynomial();
        Monomial r1 = new Monomial(-4,1);
        Monomial r2 = new Monomial(3,0);
        result.insert(r1,r2);
        assertEquals(result, p1.der());
    }

    @Test
    public void integrate() {
       Polynomial result = new Polynomial();
       Monomial r1 = new MonomialOnDouble(-0.6667, 3);
       Monomial r2 = new MonomialOnDouble(1.5,2);
       Monomial r3 = new MonomialOnDouble(-4,1);
       result.insert(r1,r2,r3);
       assertEquals(result.toString(), p1.integrate().toString());
    }

    @After
    public void cleanUp() {
        p1 = null;
        p2 = null;
        result = null;
        q = null;
        r = null;
    }

}