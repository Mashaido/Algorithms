import static org.junit.jupiter.api.Assertions.*;

class PolynomialImplTest {

    private PolynomialImpl empty;
    private PolynomialImpl first;
    private PolynomialImpl sec;
    private PolynomialImpl third;


    @org.junit.jupiter.api.BeforeEach // allocating memory (mallock) //after--> freeing up memory
    void setUp() { //like a class create my privates // create diff a bunch diff sentences types

        empty = new PolynomialImpl(""); // the empty Polynomial
        first = new PolynomialImpl(""); // another empty Polynomial --> build it up to −10x^4 +30x^3 −20x^2
        sec = new PolynomialImpl("15x^2 -45x^1 +30"); // a string Polynomial
        third = new PolynomialImpl("2x^5 -6x^4 +4x^3"); // another string Polynomial
    }

    @org.junit.jupiter.api.Test
    void addTerm() {
        first.addTerm(30, 3); // starting on an empty Polynomial
        first.addTerm(-20, 2); // adding the term with a lower coefficient than previous
        first.addTerm(-10, 4); // adding the term with a higher coefficient than previous
        first.addTerm(50, 5); // bonus term that gets removed next
    }
    @org.junit.jupiter.api.Test
    void addtoString() {
        assertEquals("50x^5 -10x^4 +30x^3 -20x^2", first.toString());
    }

    @org.junit.jupiter.api.Test
    void removeTerm() {
        first.removeTerm(5); // removing the highest-coefficient term
    }

    @org.junit.jupiter.api.Test
    void getDegree() {
        assertEquals(4, first.getDegree());
        assertEquals(2, sec.getDegree());
        assertEquals(5, third.getDegree());
    }

    @org.junit.jupiter.api.Test
    void getCoefficient() {
        assertEquals(30, sec.getCoefficient(0));
        assertEquals(4, third.getCoefficient(3));
        assertEquals(-45, sec.getCoefficient(1));
    }

    @org.junit.jupiter.api.Test
    void evaluate() {
        assertEquals(1, first.evaluate(0));
        assertEquals(-10, first.evaluate(1080));
        assertEquals(0, first.evaluate(0));
    }

    @org.junit.jupiter.api.Test
    void add() {
        System.out.println(first.add(third).toString());
        assertEquals("2x^5 -16x^4 +34x^3 -20x^2", first.add(third).toString());
        assertEquals(5, sec.add(empty));

    }
}