package frc.team6718.vector;

public class Vector3 implements Vector<Vector3> {
    public float x, y, z;

    public Vector3(float x, float y, float z) {
        set(x, y, z);
    }

    @Override
    public Vector3 set(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    public Vector3 set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public float magnitude2() {
        return x * x + y * y + z * z;
    }

    @Override
    public Vector3 normalize() {
        float length = magnitude();
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

    public Vector3 add(float x, float y, float z) {
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

    public Vector3 sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector3 mul(float v) {
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

    public Vector3 mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
}
