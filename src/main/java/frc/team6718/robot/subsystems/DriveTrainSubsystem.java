package frc.team6718.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.Robot;
import frc.team6718.robot.RobotMap;
import frc.team6718.robot.commands.StopMovingCommand;
import frc.team6718.robot.pid.NullPIDOutput;
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
    public static final double MAX_TOLERANCE = 0.5; //0.5 degrees or 0.5 sensor units(rounds to 1)

    //A of each side is master
    private TalonSRX leftA, leftB;
    private TalonSRX rightA, rightB;
    private PIDController rotation;
    //Units 1 = 1/4096 of a rotation

    private boolean disabled = false;

    //TODO check if this many PIDControllers is slowing down the RoboRIO
    public DriveTrainSubsystem() {
        super("Drive Train");

        leftA = new TalonSRX(RobotMap.DRIVE_TRAIN_LEFT_A);
        leftB = new TalonSRX(RobotMap.DRIVE_TRAIN_LEFT_B);

        rightA = new TalonSRX(RobotMap.DRIVE_TRAIN_RIGHT_A);
        rightB = new TalonSRX(RobotMap.DRIVE_TRAIN_RIGHT_B);

        //TODO determine if we want to continue moving after stopping the motors
        leftA.setNeutralMode(NeutralMode.Brake);
        leftB.setNeutralMode(NeutralMode.Brake);

        rightA.setNeutralMode(NeutralMode.Brake);
        rightB.setNeutralMode(NeutralMode.Brake);

        leftB.follow(leftA);
        rightB.follow(rightA);

        leftA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        //TODO check if we need to reverse the encoders
        /*
        leftA.setSensorPhase(false);
        rightA.setSensorPhase(false);
        */
        //TODO calibrate PID
        //Velocity
        leftA.selectProfileSlot(0,0);
        leftA.config_kP(0, 0,0);
        leftA.config_kI(0, 0,0);
        leftA.config_kD(0, 0,0);
        leftA.config_kF(0, 0,0);

        rightA.selectProfileSlot(0,0);
        rightA.config_kP(0, 0,0);
        rightA.config_kI(0, 0,0);
        rightA.config_kD(0, 0,0);
        rightA.config_kF(0, 0,0);
        //TODO find out if the slotIdx is the profile slot
        //Distance (Motion Magic)
        leftA.selectProfileSlot(1,0);
        leftA.config_kP(1, 0,0);
        leftA.config_kI(1, 0,0);
        leftA.config_kD(1, 0,0);
        leftA.config_kF(1, 0,0);

        rightA.selectProfileSlot(1,0);
        rightA.config_kP(1, 0,0);
        rightA.config_kI(1, 0,0);
        rightA.config_kD(1, 0,0);
        rightA.config_kF(1, 0,0);
        //TODO set velocity and accel for motion magic
        rotation = new PIDController(0, 0, 0, 0, Robot.gyroscope.getPIDSource(), new NullPIDOutput(), 0.02);
        rotation.setInputRange(0, 360);

        rotation.setAbsoluteTolerance(MAX_TOLERANCE);

        rotation.setContinuous();

        //Start disabled for safety
        disable();
    }

    /**
     * Sets the target speeds of both motors
     * Equivalent to tankDrive
     * @param leftSpeed The target speed for the two left motors (sensor units/s)
     * @param rightSpeed The target speed for the two right motors (sensor units/s)
     */
    public void setTargetSpeeds(double leftSpeed, double rightSpeed) {
        leftA.selectProfileSlot(0, 0);
        rightA.selectProfileSlot(0, 0);
        leftA.set(ControlMode.Velocity, leftSpeed);
        rightA.set(ControlMode.Velocity, rightSpeed);
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
     * @param d The amount of sensor units
     */
    public void setTargetDistance(double d) {
        leftA.selectProfileSlot(1, 0);
        rightA.selectProfileSlot(1, 0);
        leftA.set(ControlMode.MotionMagic, d);
        rightA.set(ControlMode.MotionMagic, d);
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
        return leftA.getClosedLoopError(0) < MAX_TOLERANCE && rightA.getClosedLoopError(0) < MAX_TOLERANCE;
    }

    /**
     * Reset both encoders to 0
     */
    public void resetDistance() {
        leftA.setSelectedSensorPosition(0, 0, 0);
        rightA.setSelectedSensorPosition(0, 0, 0);
    }

    /**
     * Enables the heading PID.
     */
    public void enable() {
        disabled = false;
        rotation.enable();
    }

    /**
     * Disable all motor controllers and rotation pid
     */
    public void disable() {
        disabled = true;
        leftA.selectProfileSlot(0, 0);
        rightA.selectProfileSlot(0, 0);
        leftA.set(ControlMode.Disabled, 0);
        rightA.set(ControlMode.Disabled, 0);
        rotation.disable();
    }

    @Override
    public void periodic() {
        if (disabled) {
            return;
        }
        //TODO figure out how to integrate gyroscope into drive train
        /*
        double rotationPID = rotation.get();
        double leftPID = left.get() - rotationPID;
        double rightPID = right.get() + rotationPID;
        double max = Math.max(Math.max(Math.abs(leftPID), Math.abs(rightPID)), 1);
        //Normalize values to be in the range -1 to 1
        leftPID /= max;
        rightPID /= max;
        leftMotorGroup.pidWrite(leftPID);
        rightMotorGroup.pidWrite(rightPID);*/
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

    /**
     * Converts sensor units to inches
     * @param units Sensor units
     * @return distance in inches
     */
    public double convertUnitsToDistance(double units) {
        return units / 4096d * 6d * Math.PI;
    }

    /**
     * Converts inches to sensor units
     * @param distance in inches
     * @return sensor units
     */
    public double convertDistanceToUnits(double distance) {
        return distance / 6d / Math.PI * 4096d;
    }
}
