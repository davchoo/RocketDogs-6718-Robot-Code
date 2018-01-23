package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.vector.Vector2;

public class ArmSubsystem extends Subsystem {
    class Joint {
        private AnalogPotentiometer potentiometer;
        private Spark motor;
        private PIDController controller;
        public final double length;

        Joint(double Kp, double Ki, double Kd, double Kf, int potChannel, double fullRange, double offset, int motorPWMPin, double length) {
            this.potentiometer = new AnalogPotentiometer(potChannel, fullRange, offset);
            this.motor = new Spark(motorPWMPin);
            this.controller = new PIDController(Kp, Ki, Kd, Kf, potentiometer, motor);
            this.length = length;
        }

        public double getAngle() {
            return potentiometer.get();
        }

        public void setAngle(double angle) {
            controller.setSetpoint(angle);
        }
    }

    private Joint[] joints;
    private Spark gripper;

    public ArmSubsystem() {
        //TODO set channel, range and offset of arm potentiometers
        //TODO set motor pwm pin
        //TODO set arm length
        //TODO calibrate PIDS
        joints = new Joint[]{
                new Joint(0, 0, 0, 0, 0, 0, 0, 0, 0),
                new Joint(0, 0, 0, 0,0, 0, 0, 0, 0),
                new Joint(0, 0, 0, 0, 0, 0, 0, 0, 0)
        };
    }

    @Override
    protected void initDefaultCommand() {

    }

    public boolean isInPerimeter() {
        double[] angles = new double[3];
        for (int i = 0; i < 3; i++) {
            angles[i] = joints[i].getAngle();
        }
        return isInPerimeter(angles);
    }

    public boolean isInPerimeter(double[] angles) {
        Vector2[] jointPositions = new Vector2[3];
        for (int i = 0; i < 3; i++) {
            Vector2 last = new Vector2(0,0);
            if (i != 0) {
                last = jointPositions[i - 1];
            }
            jointPositions[i] = new Vector2(0, 0);
            jointPositions[i].add(last);

            if (false) { //Pos is outside perimeter
                //TODO find out how to check if the joints are in the perimeters
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the angle of one of the joints
     * @param jointId
     * @param angle
     * @return true when we we're able to set the angle
     */
    public boolean setAngle(int jointId, double angle) {
        double[] angles = new double[3];
        for (int i = 0; i < 3; i++) {
            angles[i] = joints[i].getAngle();
        }
        angles[jointId] = angle;
        if (isInPerimeter(angles)) {
            joints[jointId].setAngle(angle);
            return true;
        }else{
            System.err.println("Rule violation! One of arms are outside the perimeter!");
            return false;
        }
    }

    //TODO ADD GRIPPER
    public void closeGripper() {

    }

    public void openGripper() {

    }

    public boolean isGripperOpen() {
        return false;
    }
}
