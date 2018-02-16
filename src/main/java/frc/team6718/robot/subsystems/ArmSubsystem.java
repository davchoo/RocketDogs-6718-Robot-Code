package frc.team6718.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.MB1003UltrasonicSensor;
import frc.team6718.robot.RobotMap;

public class ArmSubsystem extends Subsystem {
    private Spark upperArm, gripper;
    private WPI_TalonSRX lowerArm;

    //Sensors
    private AnalogPotentiometer upperArmPot;
    private MB1003UltrasonicSensor lowerArmUSensor;

    //PID controllers
    private PIDController upperArmPID, lowerArmPID;

    private class LowerArmPIDSource implements PIDSource {

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return getLowerArmAngle();
        }
    }

    private class UpperArmPIDSource implements PIDSource {

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return getUpperArmAngle();
        }
    }


    public ArmSubsystem() {
        upperArm = new Spark(RobotMap.ARM_UPPER_MOTOR);
        //lowerArm = new WPI_TalonSRX(RobotMap.ARM_LOWER_MOTOR);
        gripper = new Spark(RobotMap.GRIPPER_MOTOR);

        upperArmPot = new AnalogPotentiometer(0, 135, 35); //TODO find potentiometer range and offset
        lowerArmUSensor = new MB1003UltrasonicSensor();

        upperArmPID = new PIDController(0, 0, 0, new UpperArmPIDSource(), upperArm);
        lowerArmPID = new PIDController(0, 0, 0, new LowerArmPIDSource(), lowerArm);

        upperArmPID.setAbsoluteTolerance(1);
        lowerArmPID.setAbsoluteTolerance(1);

        disable();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public double getUpperArmAngle() {
        return upperArmPot.get();
    }

    private static final double A = 0; //TODO Find lengths of arm
    private static final double B = 0;
    private static final double SQUARED_AB = A * A + B * B;
    /**
        Law of cosines :O who said people don't need math
        Let C = Length of dart
        Let A = Distance between bottom DART joint and bottom arm joint
        Let B = Distance between bottom arm joint and upper DART joint
        Let c = Angle from joint to bump sign
        C^2 = A^2 + B^2 - 2(A)(B)(Cos c)
        cos^-1((C^2 - A^2 - B^2) / -2(A)(B)) = c
    */
    public double getLowerArmAngle() {
        double C = lowerArmUSensor.getDistance();
        return Math.toDegrees(Math.acos((C * C - SQUARED_AB) / (-2 * A * B)));
    }

    //TODO check if angles are inside frame perimeter
    public boolean isInPerimeter() {
        return true;
    }

    public boolean isInPerimeter(double[] angles) {
        return true;
    }

    public void setLowerArmAngle(double angle) {
        lowerArmPID.setSetpoint(angle);
    }

    public void setUpperArmAngle(double angle) {
        upperArmPID.setSetpoint(angle);
    }

    public boolean onTarget() {
        return lowerArmPID.onTarget() && upperArmPID.onTarget();
    }

    public void disable() {
        lowerArmPID.disable();
        upperArmPID.disable();
    }

    public void enable() {
        lowerArmPID.enable();
        upperArmPID.enable();
    }

    public void closeGripper() {
        gripper.set(0);
    }

    public void openGripper() {
        gripper.set(0.5); //TODO figure out how fast the gripper needs to open and to keep it open
    }
}
