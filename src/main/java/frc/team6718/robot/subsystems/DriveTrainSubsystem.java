package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.Robot;
import frc.team6718.robot.RobotMap;
import frc.team6718.robot.commands.StopMovingCommand;
import frc.team6718.robot.pid.AvgDistancePIDSource;
import frc.team6718.robot.pid.EncoderDispPIDSource;
import frc.team6718.robot.pid.NullPIDOutput;
import jaci.pathfinder.Trajectory;

/**
 * Drive Train Subsystem
 * Uses PIDControllers to control the speed of the 2 sides and 1
 * to make sure the robot goes straight
 */
public class DriveTrainSubsystem extends Subsystem {
    public static final double MAX_SPEED = 0; //TODO find max speed
    public static final double MAX_ACCEL = 0; //TODO find max acceleration
    public static final double MAX_JERK = 0; //TODO find max jerk
    public static final double TRACK_WIDTH = 23;

    private Spark leftA, leftB;
    private Spark rightA, rightB;
    private SpeedControllerGroup leftMotorGroup, rightMotorGroup;
    private PIDController left, right, rotation, leftDistance, rightDistance;
    private AvgDistancePIDSource avgDistanceSource;
    //Units are inches and inches/second
    public Encoder leftEncoder, rightEncoder;

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
        leftDistance = new PIDController(0, 0, 0, 0, new EncoderDispPIDSource(leftEncoder), new NullPIDOutput());
        rightDistance = new PIDController(0, 0, 0, 0, new EncoderDispPIDSource(rightEncoder), new NullPIDOutput());

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

    public void setTargetHeading(double angle) {
        rotation.setSetpoint(angle);
    }

    public void setTargetDistance(double d) {
        avgDistanceSource.zero();
        enableDistanceControl();
        leftDistance.setSetpoint(d);
        rightDistance.setSetpoint(d);
    }

    public void rotateTargetHeading(double angle) {
        rotation.setSetpoint(rotation.getSetpoint() + angle);
    }

    public boolean isHeadingOnTarget() {
        return rotation.onTarget();
    }

    public boolean isDistanceOnTarget() {
        return leftDistance.onTarget() && rightDistance.onTarget();
    }

    public double getDistanceCovered() {
        return avgDistanceSource.pidGet();
    }

    public void resetDistance() {
        avgDistanceSource.zero();
    }

    public void enable() {
        left.enable();
        right.enable();
        rotation.enable();
        leftDistance.reset(); //Distance shouldn't be controlling at start
        rightDistance.reset();
    }

    public void disable() {
        left.disable();
        right.disable();
        rotation.disable();
        leftDistance.reset();
        rightDistance.reset();
    }

    public void enableDistanceControl() {
        leftDistance.enable();
        rightDistance.enable();
    }

    public void disableDistanceControl() {
        leftDistance.reset();
        rightDistance.reset();
    }

    @Override
    public void periodic() {
        if (leftDistance.isEnabled()) {
            left.setSetpoint(leftDistance.get());
            right.setSetpoint(rightDistance.get());
        }
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

    public Trajectory.Config getConfig() {
        return new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TimedRobot.DEFAULT_PERIOD, MAX_SPEED, MAX_ACCEL, MAX_JERK);
    }
}
