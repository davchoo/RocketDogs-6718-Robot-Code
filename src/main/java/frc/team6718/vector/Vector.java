package frc.team6718.vector;

public interface Vector<T extends Vector<T>> {

    T set(T v);

    float magnitude();
    float magnitude2();

    T normalize();

    T add(T v);
    T sub(T v);
    T mul(float v);
    T mul(T v);
}
