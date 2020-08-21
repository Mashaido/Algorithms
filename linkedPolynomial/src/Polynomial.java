public interface Polynomial {

    Polynomial head = null;

    // interface method
    void addTerm(int coefficient, int exponent);

    // interface method
    void removeTerm(int exponent);

    // interface method
    int getDegree();

    // interface method
    int getCoefficient(int exponent);

    // interface method
    double evaluate(double value);

    // interface method
    Polynomial add(Polynomial other);
}


