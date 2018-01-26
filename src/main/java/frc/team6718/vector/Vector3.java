package frc.team6718.vector;

public class Vector3 implements Vector<Vector3> {
    public double x, y, z;

    public Vector3(double x, double y, double z) {
        set(x, y, z);
    }

    @Override
    public Vector3 set(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    public Vector3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public double magnitude2() {
        return x * x + y * y + z * z;
    }

    @Override
    public Vector3 normalize() {
        double length = magnitude();
        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    @Override
    public Vector3 add(Vector3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public Vector3 add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector3 sub(Vector3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    public Vector3 sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector3 mul(double v) {
        x *= v;
        y *= v;
        z *= v;
        return this;
    }

    @Override
    public Vector3 mul(Vector3 v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    public Vector3 mul(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public Vector3 copy() {
        return new Vector3(x, y, z);
    }
}
