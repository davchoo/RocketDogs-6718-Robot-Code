package frc.team6718.vector;

public class Vector2 implements Vector<Vector2> {
    public float x, y;

    public Vector2(float x, float y) {
        set(x,y);
    }

    @Override
    public Vector2 set(Vector2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public float magnitude() {
        return (float) Math.sqrt(x * x  + y * y);
    }

    @Override
    public float magnitude2() {
        return x * x * y * y;
    }

    @Override
    public Vector2 normalize() {
        float length = magnitude();
        x /= length;
        y /= length;
        return this;
    }

    @Override
    public Vector2 add(Vector2 v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector2 add(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public Vector2 sub(Vector2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector2 sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    @Override
    public Vector2 mul(float v) {
        x *= v;
        y *= v;
        return this;
    }

    @Override
    public Vector2 mul(Vector2 v) {
        x *= v.x;
        y *= v.y;
        return this;
    }

    public Vector2 mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }
}
