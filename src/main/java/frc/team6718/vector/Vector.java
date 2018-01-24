package frc.team6718.vector;

public interface Vector<T extends Vector<T>> {

    T set(T v);

    double magnitude();
    double magnitude2();

    T normalize();

    T add(T v);
    T sub(T v);
    T mul(double v);
    T mul(T v);
    T copy();
}
