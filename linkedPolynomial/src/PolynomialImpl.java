//import Scanner class to get user input i.e polynomials in string form
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PolynomialImpl implements Polynomial {

    // fields
    private String polynomial;
    public TermNode head;

    // this constructor constructs a string polynomial using user input
    public PolynomialImpl(String inputPolynomial) {
        this.polynomial = inputPolynomial;

        // split string input into individual string polynomial terms
        String[] polynomial = inputPolynomial.split(" ");
        for (int i = 0; i < polynomial.length; i++) {
            System.out.println("term #" + (i + 1) + " of '" + inputPolynomial + "' is '" + polynomial[i] + "'");

            // get the index of variable x
            int index = polynomial[i].indexOf('x');

            // if string term of this polynomial has no x-variable
            if (index == -1) {

                // this means we are the empty polynomial
                if (inputPolynomial.equals("")) {
                    int coeff = 0;
                    int exp = 0;
                    System.out.println("with coefficient " + coeff);
                    System.out.println("and exponent " + exp);
                    System.out.println();
                    this.addTerm(0,0);
                }
                else {
                    // this means we are the constant term
                    int coeff = Integer.parseInt(polynomial[i]);
                    int exp = 0;
                    System.out.println("with coefficient " + coeff);
                    System.out.println("and exponent " + exp);
                    System.out.println();
                    this.addTerm(coeff,exp);
                }
            }
            else {
                // string term of this polynomial has an x-variable
                String strCoeff = polynomial[i].substring(0, index);
                // extract integer coefficient from this string term
                int coeff = Integer.parseInt(strCoeff);
                String strExp = polynomial[i].substring(index + 2);
                // extract integer exponent from this string term
                int exp = Integer.parseInt(strExp);
                System.out.println("with coefficient " + coeff);
                System.out.println("and exponent " + exp);
                System.out.println();
                // pass integer coefficient && exponent to build this polynomial from scratch
                this.addTerm(coeff,exp);
            }
        }
    }


    // this constructor constructs an empty linked list of polynomial i.e the zero polynomial
    public PolynomialImpl() {
        this.head = null;
    }

    @Override
    // method to add a new term to this polynomial in sorted order from largest to smallest exponent
    public void addTerm(int coefficient, int exponent) {
        if(exponent < 0) {
            throw new IllegalArgumentException("The exponent is negative :( ");
        }
        if(head == null || exponent < head.exponent) {
            // we have an empty linked list
            // so just point head to the new term
            head = new TermNode(coefficient, exponent, head);
            return;
        }

        TermNode cur = head;
        TermNode prev = null;

        while(cur != null && exponent > cur.exponent) {
            prev = cur;
            cur = cur.nextTerm;
        }
        if( cur == null || exponent != cur.exponent ) {
            assert prev != null;
            prev.nextTerm = new TermNode(coefficient, exponent, cur);
        }
        else
        {
            cur.coefficient += coefficient;
            double tolerance = 0.00000001;
            if (Math.abs(cur.coefficient) < tolerance) {
                if(prev != null) {
                    prev.nextTerm = cur.nextTerm;
                }
                else {
                    head = head.nextTerm;
                }
            }
        }
    }

    @Override
    // method to remove any and all terms in this polynomial with that exponent/power
    public void removeTerm(int exponent) {
        if (exponent == head.exponent) {
            // if we want to remove the head
            // simply point head to the second node
            System.out.println("removing node " + head.toString());
            head = head.nextTerm;
        }

        TermNode prevPtr = null;
        TermNode traversePtr = head;

        while (traversePtr.exponent != exponent) {
            prevPtr = traversePtr;
            traversePtr = traversePtr.nextTerm;
        }
        System.out.println("removing node " + traversePtr.toString());
        // when we reach here, traversePtr is pointing to the node (term with exponent) that we want to remove
        // so we simply point the next pointer of prevPtr to traversePtr's next node
        assert prevPtr != null;
        prevPtr.nextTerm = traversePtr.nextTerm;
    }

    @Override
    // method to return the degree (highest exponent) of this polynomial
    public int getDegree() {
        // create an ArrayList Object result i.e a collection
        List<Integer> result = new ArrayList<Integer>();
        for (TermNode term = head; term != null; term = term.nextTerm) {
            // add our exponents while traversing through
            result.add(term.exponent);
        }
        return Collections.max(result);
    }

    @Override
    // method to return the coefficient for the term with that exponent/power passed in
    public int getCoefficient(int exponent) {
        int result = 0;
        for (TermNode term = head; term != null; term = term.nextTerm) {
            if (term.exponent == exponent) {
                result = term.coefficient;
            }
        }
        return result;
    }

    @Override
    // method to evaluate the value of variable x at the point x = value
    public double evaluate(double value) {
        double result = 0;
        for(TermNode term = head; term != null; term = term.nextTerm) {
            result += term.coefficient * Math.pow(value, term.exponent);
        }
        return result;
    }

    @Override
    // method to return the polynomial obtained by adding another polynomial to this, w/o mutating either polynomial
    public Polynomial add(Polynomial other) {
        PolynomialImpl otherImpl = (PolynomialImpl) other; // convert to impl because Polynomial Interface has no fields
        Polynomial result = clone();
        for(TermNode term = otherImpl.head; term!= null; term = term.nextTerm) {
            result.addTerm(term.coefficient, term.exponent);
        }
        return result;
    }

    // method to clone polynomials in case of performing operations, so as to avoid mutating original polynomial
    public Polynomial clone() {
        Polynomial result = new PolynomialImpl();
        for(TermNode term = head; term != null; term = term.nextTerm) {
            result.addTerm(term.coefficient, term.exponent);
        }
        return result;
    }

    // method to return this polynomial in string form i.e format this polynomial
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (TermNode term = head; term != null; term = term.nextTerm) {
            if (term.coefficient < 0) {
                result.insert(0, "-" + term.toString());
            }
            else {
                result.insert(0,"+" + term.toString());
            }
        }
        if (result.charAt(0) == '+') {
            // return a substring i.e get rid of + if the first term is positive
            return result.substring(1);
        }
        else {
            return result.substring(1);
        }
    }

    public static void main(String[] args) {

        // User Interaction --> run in main()
        // plug in a polynomial for an interactive test!

        Scanner keyboard = new Scanner(System.in); // creates a Scanner object
        System.out.println("enter polynomial...");
        String inputPolynomial = keyboard.nextLine(); // reads user (string) input
        System.out.println("'" + inputPolynomial + "' is a good choice!"); // outputs user input
        System.out.println();

        PolynomialImpl first = new PolynomialImpl(inputPolynomial); // this takes in user polynomial
        PolynomialImpl sec = new PolynomialImpl("-2x^3 +6x^2 +5"); // given for you

        System.out.println("first polynomial looks like this...");
        System.out.println(first.toString());
        System.out.println();


        System.out.println("sec polynomial looks like this...");
        System.out.println(sec.toString());
        System.out.println();


        System.out.println("finding coeficient at this 3rd exponent of sec polynomial...");
        System.out.println(sec.getCoefficient(3));
        System.out.println();


        System.out.println("finding degree of sec polynomial...");
        System.out.println(sec.getDegree());
        System.out.println();


        System.out.println("value of sec at x = 10...");
        System.out.println(first.evaluate(10));
        System.out.println();


        System.out.println("summation of first && sec polynomials...");
        System.out.println(first.add(sec));
        System.out.println();


        System.out.println("removing sec-polynomial term at this 3rd exponent...");
        sec.removeTerm(3);
        System.out.println(sec.toString());
        System.out.println();

        System.out.println("adding this (-4 coefficient, 4 exponent) term into sec polynomial...");
        sec.addTerm(-4, 4);
        System.out.println(sec.toString());
    }
}


