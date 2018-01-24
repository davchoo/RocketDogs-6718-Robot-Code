package frc.team6718.vector;

public class Vector2 implements Vector<Vector2> {
    public double x, y;

    public Vector2(double x, double y) {
        set(x,y);
    }

    @Override
    public Vector2 set(Vector2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public Vector2 set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public double magnitude() {
        return Math.sqrt(x * x  + y * y);
    }

    @Override
    public double magnitude2() {
        return x * x * y * y;
    }

    @Override
    public Vector2 normalize() {
        double length = magnitude();
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

    public Vector2 add(double x, double y) {
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

    public Vector2 sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    @Override
    public Vector2 mul(double v) {
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

    public Vector2 mul(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    @Override
    public Vector2 copy() {
        return new Vector2(x, y);
    }
}
