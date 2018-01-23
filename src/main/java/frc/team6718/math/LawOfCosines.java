package frc.team6718.math;

/*
 * Using the law of cosines, these functions return the angles of a triangle
 * based on the size of all three sides. Or the length of a side given its opposite angle
 * and the length of the other two sides.
 * Lower case variables are sides, upper case are the angle opposite the lower case side
 * i.e. "A" is the angle opposite side "a"
 *
 * The Law of Cosines:
 *  a = sqrt(Math.pow(b, 2) + Math.pow(c, 2) - 2 * b * c * Math.cos(A))
 *  b = sqrt(Math.pow(a, 2) + Math.pow(c, 2) - 2 * a * c * Math.cos(B))
 *  c = sqrt(Math.pow(a, 2) + Math.pow(b, 2) - 2 * a * b * Math.cos(C))
 */
public class LawOfCosines
{
    // get angle A in degrees given sides a, b, and c
    public static double getAngleA(double a, double b, double c) {
        return Math.toDegrees(Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2)) / (-2 * b * c)));
    }

    // get angle B in degrees given sides a, b, and c
    public static double getAngleB(double a, double b, double c) {
        return Math.toDegrees(Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2)) / (-2 * a * c)));
    }

    // get angle B in degrees given sides a, b, and c
    public static double getAngleC(double a, double b, double c) {
        return Math.toDegrees(Math.acos((Math.pow(c, 2) - Math.pow(a, 2) - Math.pow(b, 2)) / (-2 * b * a)));
    }


    // get the length of side a given angle A and sides b and c
    public static double getSideA(double A, double b, double c) {
        return Math.toDegrees(Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2) - 2 * b * c * Math.cos(Math.toDegrees(A))));
    }

    // get the length of side b given angle B and sides a and c
    public static double getSideB(double B, double a, double c) {
        return Math.toDegrees(Math.sqrt(Math.pow(a, 2) + Math.pow(c, 2) - 2 * a * c * Math.cos(Math.toDegrees(B))));
    }

    // get the length of side c given angle C and sides a and b
    public static double getSideC(double C, double a, double b) {
        return Math.toDegrees(Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) - 2 * a * b * Math.cos(Math.toDegrees(C))));
    }

}