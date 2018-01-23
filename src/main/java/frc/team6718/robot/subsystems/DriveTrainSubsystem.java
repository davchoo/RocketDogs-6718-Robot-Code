package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.Robot;
import frc.team6718.robot.RobotMap;
import frc.team6718.robot.commands.StopMovingCommand;
import frc.team6718.robot.pid.AvgDistancePIDSource;
import frc.team6718.robot.pid.EncoderDispPIDSource;
import frc.team6718.robot.pid.NullPIDOutput;
import frc.team6718.robot.pid.PIDControllerPIDOutput;
import jaci.pathfinder.Trajectory;

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
    private PIDController left, right, rotation, leftDistance, rightDistance;
    private AvgDistancePIDSource avgDistanceSource;
    //Units are inches and inches/second
    public Encoder leftEncoder, rightEncoder;

    //TODO check if this many PIDControllers is slowing down the RoboRIO
    public DriveTrainSubsystem() {
        super("Drive Train");

        leftA = new Spark(RobotMap.DRIVE_TRAIN_LEFT_A);
        leftB = new Spark(RobotMap.DRIVE_TRAIN_LEFT_B);

        rightA = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_A);
        rightB = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_B);

        leftMotorGroup = new SpeedControllerGroup(leftA, leftB);
        rightMotorGroup = new SpeedControllerGroup(rightA, rightB);

        //TODO check if one of the encoders need to be reversed
        leftEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_LEFT_A,RobotMap.DRIVE_TRAIN_ENCODER_LEFT_B, false);
        rightEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_A, RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_B, false);

        //We have 6in diameter wheels
        leftEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);
        rightEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);

        leftEncoder.setPIDSourceType(PIDSourceType.kRate);
        rightEncoder.setPIDSourceType(PIDSourceType.kRate);

        avgDistanceSource = new AvgDistancePIDSource(leftEncoder, rightEncoder);

        //TODO calibrate PID
        left = new PIDController(0, 0, 0, 0, leftEncoder, new NullPIDOutput());
        right = new PIDController(0, 0, 0, 0, rightEncoder, new NullPIDOutput());
        rotation = new PIDController(0, 0, 0, 0, Robot.gyroscope.getPIDSource(), new NullPIDOutput());
        leftDistance = new PIDController(0, 0, 0, 0, new EncoderDispPIDSource(leftEncoder), new PIDControllerPIDOutput(left));
        rightDistance = new PIDController(0, 0, 0, 0, new EncoderDispPIDSource(rightEncoder), new PIDControllerPIDOutput(right));

        rotation.setInputRange(0, 360);
        leftDistance.setOutputRange(-MAX_SPEED, MAX_SPEED);
        rightDistance.setOutputRange(-MAX_SPEED, MAX_SPEED);

        rotation.setAbsoluteTolerance(1);
        leftDistance.setAbsoluteTolerance(1);
        rightDistance.setAbsoluteTolerance(1);

        left.setContinuous();
        right.setContinuous();
        rotation.setContinuous();

        //Start disabled for safety
        left.disable();
        right.disable();
        rotation.disable();
        leftDistance.disable();
        rightDistance.disable();
    }

    /**
     * Sets the target speeds of both motors
     * Equivalent to tankDrive
     * @param leftSpeed The target speed for the two left motors (in/s)
     * @param rightSpeed The target speed for the two right motors (in/s)
     */
    public void setTargetSpeeds(double leftSpeed, double rightSpeed) {
        disableDistanceControl();
        left.setSetpoint(leftSpeed);
        right.setSetpoint(rightSpeed);
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
        avgDistanceSource.zero();
        enableDistanceControl();
        leftDistance.setSetpoint(d);
        rightDistance.setSetpoint(d);
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
        return leftDistance.onTarget() && rightDistance.onTarget();
    }

    /**
     * Get the average distance travel between both sides
     * @return
     */
    public double getDistanceCovered() {
        return avgDistanceSource.pidGet();
    }

    /**
     * Reset both encoders to 0
     */
    public void resetDistance() {
        avgDistanceSource.zero();
    }

    /**
     * Enable the left and right velocity PIDs and heading PID.
     * Also disables left and right distance PIDs
     */
    public void enable() {
        left.enable();
        right.enable();
        rotation.enable();
        disableDistanceControl();
    }

    /**
     * Disable all PID controllers and reset the setpoints to 0
     */
    public void disable() {
        left.setSetpoint(0);
        right.setSetpoint(0);
        left.reset();
        right.reset();
        rotation.disable();
        disableDistanceControl();
    }

    /**
     * Enable the left and right distance PID controllers
     */
    public void enableDistanceControl() {
        leftDistance.enable();
        rightDistance.enable();
    }

    /**
     * Disable the left and right distance PID controllers
     */
    public void disableDistanceControl() {
        leftDistance.setSetpoint(0);
        rightDistance.setSetpoint(0);
        resetDistance();
        leftDistance.reset();
        rightDistance.reset();
    }

    @Override
    public void periodic() {
        double rotationPID = rotation.get();
        double leftPID = left.get() - rotationPID;
        double rightPID = right.get() + rotationPID;
        double max = Math.max(Math.max(Math.abs(leftPID), Math.abs(rightPID)), 1);
        //Normalize values to be in the range -1 to 1
        leftPID /= max;
        rightPID /= max;
        leftMotorGroup.pidWrite(leftPID);
        rightMotorGroup.pidWrite(rightPID);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StopMovingCommand());
    }

    /**
     * Get the config for Pathfinder
     * @return A Trajectory Config
     */
    public Trajectory.Config getConfig() {
        return new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TimedRobot.DEFAULT_PERIOD, MAX_SPEED, MAX_ACCEL, MAX_JERK);
    }
}
