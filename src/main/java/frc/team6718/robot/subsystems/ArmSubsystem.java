package frc.team6718.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team6718.math.LawOfCosines;
import frc.team6718.robot.MB1003UltrasonicSensor;
import frc.team6718.robot.RobotMap;

import java.util.logging.Logger;

/**
 * Arm Subsystem
 * Lower arm is controlled with DART and a talon
 * Upper arm is controlled using a chain and 2 motors and sparks
 * A potentiometer is used to measure the angle of the upper arm
 * A ultrasonic sensor and math is used to measure the angle of the lower arm
 * Angles are given in degrees and have 0 going up
 */
public class ArmSubsystem extends Subsystem {
    private Spark upperArmA, upperArmB;
    private SpeedControllerGroup upperArm;
    private WPI_TalonSRX lowerArm;

    //Sensors
    private AnalogPotentiometer upperArmPot;
    private MB1003UltrasonicSensor lowerArmUSensor;

    //PID controllers
    private PIDController upperArmPID, lowerArmPID;

    private boolean disabled = true, lastState = true;
    private double lastLowerArm = 0;

    public static final double LOWER_ARM_LENGTH = 43;
    public static final double UPPER_ARM_LENGTH = 44.5;
    public static final double LOWER_ARM_BASE_FRAME = 24;

    private final Logger logger = Logger.getLogger("Arm");

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
        super("Arm");
        upperArmA = new Spark(RobotMap.ARM_UPPER_MOTOR);
        upperArmB = new Spark(RobotMap.ARM_UPPER_MOTOR_2);
        upperArm = new SpeedControllerGroup(upperArmA, upperArmB);

        lowerArm = new WPI_TalonSRX(RobotMap.ARM_LOWER_MOTOR);

        upperArmPot = new AnalogPotentiometer(RobotMap.ARM_UPPER_POT, 135, 35); //TODO find potentiometer range and offset
        lowerArmUSensor = new MB1003UltrasonicSensor(RobotMap.ARM_LOWER_USENSOR);

        //upperArmPID = new PIDController(0, 0, 0, new UpperArmPIDSource(), upperArm);
        //lowerArmPID = new PIDController(0, 0, 0, new LowerArmPIDSource(), lowerArm);

        //upperArmPID.setAbsoluteTolerance(1);
        //lowerArmPID.setAbsoluteTolerance(1);

        //Set stuff in Shuffleboard
        upperArm.setName("Arm", "Upper Arm");
        lowerArm.setName("Arm", "Lower Arm");
        upperArmPot.setName("Arm", "Upper Arm Pot");
        lowerArmUSensor.setName("Arm", "Lower Arm USensor");
        /*
        upperArmPID.setName("Arm", "Upper Arm PID");
        lowerArmPID.setName("Arm", "Lower arm PID");
        */
        SmartDashboard.putData(upperArm);
        SmartDashboard.putData(lowerArm);
        SmartDashboard.putData(upperArmPot);
        SmartDashboard.putData(lowerArmUSensor);
        //SmartDashboard.putData(upperArmPID);
        //SmartDashboard.putData(lowerArmPID);

        disable();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public double getUpperArmAngle() {
        return upperArmPot.get();
    }

    private static final double BARM_BDART = 10.75;
    private static final double BARM_TDART = 24;
    private static final double TDART_RODEND = 2 + 2.5;
    private static final double OFFSET_ANGLE = 0;
    /**
        Law of cosines :O who said people don't need math
    */
    public double getLowerArmAngle() {
        double lengthDart = lowerArmUSensor.getDistance();
        double angle = LawOfCosines.getAngleA(lengthDart + TDART_RODEND, BARM_BDART, BARM_TDART) + OFFSET_ANGLE;
        if (lengthDart > 31) {
            lastLowerArm = angle;
        }else{
            return lastLowerArm;
        }
        return angle;
    }

    public boolean isInPerimeter() {
        return isInPerimeter(new double[]{getLowerArmAngle(), getUpperArmAngle()});
    }

    public boolean isInPerimeter(double[] angles) {
        return Math.sin(Math.toDegrees(angles[0])) * LOWER_ARM_LENGTH + Math.sin(Math.toDegrees(angles[1])) * UPPER_ARM_LENGTH - LOWER_ARM_BASE_FRAME < 16;
    }

    public boolean setLowerArmAngle(double angle) {
        if (!isInPerimeter(new double[]{angle, getUpperArmTarget()})) {
            logger.severe("Lower arm tried to go outside frame perimeter");
            logger.severe(angle + " " + getUpperArmTarget());
            return false;
        }
        lowerArmPID.setSetpoint(angle);
        return true;
    }

    public boolean setUpperArmAngle(double angle) {
        if (!isInPerimeter(new double[]{getLowerArmTarget(), angle})) {
            logger.severe("Upper arm arm tried to go outside frame perimeter");
            logger.severe(getLowerArmTarget() + " " + angle);
            return false;
        }
        upperArmPID.setSetpoint(angle);
        return true;
    }

    @Override
    public void periodic() {
        if (lowerArmUSensor.getDistance() < 31 && lastState) { //Lower arm sensor too close
            lastState = false;
            //lowerArmPID.disable();
        }else if (!disabled && !lastState) {
            lastState = true;
            //lowerArmPID.enable();
        }
    }

    public boolean isLowerArmOnTarget() {
        return lowerArmPID.onTarget();
    }

    public boolean isUpperArmOnTarget() {
        return upperArmPID.onTarget();
    }

    public double getLowerArmTarget() {
        return lowerArmPID.getSetpoint();
    }

    public double getUpperArmTarget() {
        return upperArmPID.getSetpoint();
    }

    public void disable() {
        disabled = true;
//      lowerArmPID.disable();
//      upperArmPID.disable();
    }

    public void enable() {
        disabled = false;
        lowerArmPID.enable();
        upperArmPID.enable();
    }
}
