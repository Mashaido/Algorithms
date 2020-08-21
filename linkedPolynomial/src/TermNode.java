public class TermNode {

    // fields for class TermNode
    protected int coefficient;
    protected int exponent;
    protected TermNode nextTerm;

    // constructor for TermNode
    public TermNode(int coefficient, int exponent, TermNode nextTerm) {
        this.coefficient = coefficient;
        this.exponent = exponent;
        this.nextTerm = nextTerm;
    }

    public String toString() {
        if (exponent == 0) {
            return Integer.toString(coefficient);
        }
        else if (exponent == 1){
            return Integer.toString(coefficient) + "x";
        }
        else {
            return Integer.toString(coefficient) + "x^" + exponent;
        }
    }
}


