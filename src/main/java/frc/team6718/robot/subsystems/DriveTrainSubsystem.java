package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team6718.robot.Robot;
import frc.team6718.robot.RobotMap;
import frc.team6718.robot.commands.StopMovingCommand;
import frc.team6718.robot.pid.NullPIDOutput;

/**
 * Drive Train Subsystem
 * Uses PIDControllers to control the speed of the 2 sides and 1
 * to make sure the robot goes straight
 */
public class DriveTrainSubsystem extends Subsystem {
    public static final double MAX_SPEED = 126.6; //TODO find max speed
    public static final double MAX_ACCEL = 10; //TODO find max acceleration
    public static final double MAX_JERK = 2; //TODO find max jerk
    public static final double TRACK_WIDTH = 23;

    private Spark leftA, leftB;
    private Spark rightA, rightB;
    private SpeedControllerGroup leftMotorGroup, rightMotorGroup;
    private PIDController left, right, rotation;
    //Units are inches and inches/second
    private Encoder leftEncoder, rightEncoder;

    private double leftSpeed, rightSpeed;


    //TODO check if this many PIDControllers is slowing down the RoboRIO
    public DriveTrainSubsystem() {
        super("Drive Train");

        leftA = new Spark(RobotMap.DRIVE_TRAIN_LEFT_A);
        leftB = new Spark(RobotMap.DRIVE_TRAIN_LEFT_B);

        rightA = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_A);
        rightB = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_B);

        leftMotorGroup = new SpeedControllerGroup(leftA, leftB);
        rightMotorGroup = new SpeedControllerGroup(rightA, rightB);

        leftMotorGroup.setInverted(true);
        rightMotorGroup.setInverted(true);

        //TODO check if one of the encoders need to be reversed
        leftEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_LEFT_A,RobotMap.DRIVE_TRAIN_ENCODER_LEFT_B, false);
        rightEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_A, RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_B, true);

        //We have 6in diameter wheels
        leftEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);
        rightEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);

        leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);

        //TODO calibrate PID
        left = new PIDController(0, 0, 0, 0, leftEncoder, new NullPIDOutput(), 0.02);
        right = new PIDController(0, 0, 0, 0, rightEncoder, new NullPIDOutput(), 0.02);
        rotation = new PIDController(0, 0, 0, 0, Robot.gyroscope.getPIDSource(), new NullPIDOutput(), 0.02);

        rotation.setInputRange(0, 360);

        rotation.setAbsoluteTolerance(1);
        left.setAbsoluteTolerance(1);
        right.setAbsoluteTolerance(1);

        rotation.setContinuous();

        //Add to shuffleboard
        leftEncoder.setName("Drive Train", "Left Encoder");
        rightEncoder.setName("Drive Train", "Right Encoder");
        left.setName("Drive Train", "Left Dist PID");
        right.setName("Drive Train", "Right Dist PID");
        rotation.setName("Drive Train", "Rotation PID");

        SmartDashboard.putData(leftEncoder);
        SmartDashboard.putData(rightEncoder);
        SmartDashboard.putData(left);
        SmartDashboard.putData(right);
        SmartDashboard.putData(rotation);

        //Start disabled for safety
        disable();
    }

    /**
     * Sets the target speeds of both motors
     * Equivalent to tankDrive
     * @param leftSpeed The target speed for the two left motors (-1 to 1)
     * @param rightSpeed The target speed for the two right motors (-1 to 1)
     */
    public void setTargetSpeeds(double leftSpeed, double rightSpeed) {
        disableDistanceControl();
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }

    /**
     * Set the direction the robot to face
     * @param angle The direction in degrees
     */
    public void setTargetHeading(double angle) {
        rotation.setSetpoint(angle);
    }

    /**
     * Drive the robot forward
     * @param d The amount of inches
     */
    public void setTargetDistance(double d) {
        resetDistance();
        enableDistanceControl();
        left.setSetpoint(d);
        left.setSetpoint(d);
    }

    /**
     * Rotate the robot
     * @param angle the angle delta in degrees
     */
    public void rotateTargetHeading(double angle) {
        double newRotation = (rotation.getSetpoint() + angle) % 360d;
        if (newRotation < 0) {
            newRotation += 360;
        }
        rotation.setSetpoint(newRotation);
    }

    /**
     * Check if the heading is within tolerances
     * @return if heading is within tolerances
     */
    public boolean isHeadingOnTarget() {
        return rotation.onTarget();
    }

    /**
     * Check if the distance is within tolerances
     * @return if both left and right distance PIDControllers are in tolerances
     */
    public boolean isDistanceOnTarget() {
        return left.onTarget() && right.onTarget();
    }

    /**
     * Get the average distance travel between both sides
     * @return
     */
    public double getDistanceCovered() {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2d;
    }

    /**
     * Reset both encoders to 0
     */
    public void resetDistance() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    /**
     * Enables heading PID and disables left and right distance PIDs
     */
    public void enable() {
        rotation.enable();
        disableDistanceControl();
    }

    /**
     * Disable all heading PID and distance PIDs
     */
    public void disable() {
        leftSpeed = 0;
        rightSpeed = 0;
        rotation.disable();
        disableDistanceControl();
    }

    /**
     * Zero the distances of both encoders
     */
    public void zero() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    /**
     * Enable the left and right distance PID controllers
     */
    public void enableDistanceControl() {
        left.enable();
        right.enable();
    }

    /**
     * Disable the left and right distance PID controllers
     */
    public void disableDistanceControl() {
        left.setSetpoint(0);
        right.setSetpoint(0);
        resetDistance();
        left.reset();
        right.reset();
    }

    @Override
    public void periodic() {
        double rotationPID = rotation.get();
        double leftPID = leftSpeed + left.get() - rotationPID;
        double rightPID = rightSpeed + right.get() + rotationPID;

        //Normalize values to be in the range -1 to 1
        double max = Math.max(Math.max(Math.abs(leftPID), Math.abs(rightPID)), 1);
        leftPID /= max;
        rightPID /= max;

        //Write
        leftMotorGroup.pidWrite(leftPID);
        rightMotorGroup.pidWrite(rightPID);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StopMovingCommand());
    }
}
