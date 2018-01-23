package frc.team6718.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
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

    public static final double LOWER_ARM_LENGTH = 0; //TODO Find arm lengths
    public static final double UPPER_ARM_LENGTH = 0;
    public static final double LOWER_ARM_BASE_FRAME = 0;

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
        upperArm.setName("Upper Arm", "Arm");
        lowerArm.setName("Lower Arm", "Arm");
        upperArmPot.setName("Upper Arm Pot", "Arm");
        lowerArmUSensor.setName("Lower Arm USensor", "Arm");
        /*
        upperArmPID.setName("Upper Arm PID", "Arm");
        lowerArmPID.setName("Lower arm PID", "Arm");

        LiveWindow.add(upperArmPID);
        LiveWindow.add(lowerArmPID);
        */
        disable();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public double getUpperArmAngle() {
        return upperArmPot.get();
    }

    private static final double BARM_BDART = 0; //TODO Find lengths of arm
    private static final double BARM_TDART = 0;
    private static final double TDART_RODEND = 0;
    private static final double OFFSET_ANGLE = 0;
    /**
        Law of cosines :O who said people don't need math
    */
    public double getLowerArmAngle() {
        double lengthDart = lowerArmUSensor.getDistance();
        return LawOfCosines.getAngleA(lengthDart + TDART_RODEND, BARM_BDART, BARM_TDART) + OFFSET_ANGLE;
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
//        lowerArmPID.disable();
//        upperArmPID.disable();
    }

    public void enable() {
        lowerArmPID.enable();
        upperArmPID.enable();
    }
}
