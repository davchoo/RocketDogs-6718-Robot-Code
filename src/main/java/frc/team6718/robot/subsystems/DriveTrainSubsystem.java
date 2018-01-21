package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.Robot;
import frc.team6718.robot.RobotMap;
import frc.team6718.robot.commands.StopMovingCommand;
import frc.team6718.robot.pid.CombinedPIDOutput;
import frc.team6718.robot.pid.MappedPIDSource;
import frc.team6718.robot.pid.NullPIDOutput;

/**
 * Drive Train Subsystem
 * Uses PIDControllers to control the speed of the 2 sides and 1
 * to make sure the robot goes straight
 */
public class DriveTrainSubsystem extends Subsystem {
    private Spark leftA, leftB;
    private Spark rightA, rightB;
    private SpeedControllerGroup leftMotorGroup, rightMotorGroup;
    private PIDController left, right, rotation;

    //Units are inches and inches/second
    public Encoder leftEncoder, rightEncoder;

    public DriveTrainSubsystem() {
        super("Drive Train");

        //TODO check if one of the motors need to be reversed
        leftA = new Spark(RobotMap.DRIVE_TRAIN_LEFT_A);
        leftB = new Spark(RobotMap.DRIVE_TRAIN_LEFT_B);

        rightA = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_A);
        rightB = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_B);

        //TODO check if one of the sides need to be reversed
        leftMotorGroup = new SpeedControllerGroup(leftA, leftB);
        rightMotorGroup = new SpeedControllerGroup(rightA, rightB);

        //TODO check if one of the encoders need to be reversed
        leftEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_LEFT_A,RobotMap.DRIVE_TRAIN_ENCODER_LEFT_B, false);
        rightEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_A, RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_B, false);

        //We have 6in diameter wheels
        //TODO check if encoder has 1024 pulses
        leftEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);
        rightEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);

        leftEncoder.setPIDSourceType(PIDSourceType.kRate);
        rightEncoder.setPIDSourceType(PIDSourceType.kRate);

        //TODO calibrate PID
        rotation = new PIDController(0, 0, 0, 0, new MappedPIDSource(Robot.gyroscope, 0, 360, -180, 180), new NullPIDOutput());
        left = new PIDController(0, 0, 0, 0, leftEncoder, new CombinedPIDOutput(rotation, leftMotorGroup, false));
        right = new PIDController(0, 0, 0, 0, rightEncoder, new CombinedPIDOutput(rotation, rightMotorGroup, true));

        //TODO set input ranges
        rotation.setInputRange(-180, 180);

        //TODO set rotation tolerances
        rotation.setAbsoluteTolerance(1);

        left.setContinuous();
        right.setContinuous();
        rotation.setContinuous();

        //Start disabled for safety
        left.disable();
        right.disable();
        rotation.disable();
    }

    /**
     * Sets the target speeds of both motors
     * Equivalent to tankDrive
     * @param leftSpeed The target speed for the two left motors (in/s)
     * @param rightSpeed The target speed for the two right motors (in/s)
     */
    public void setTargetSpeeds(double leftSpeed, double rightSpeed) {
        left.setSetpoint(leftSpeed);
        right.setSetpoint(rightSpeed);
    }

    public void setTargetHeading(double angle) {
        rotation.setSetpoint(angle);
    }

    public boolean isHeadingOnTarget() {
        return rotation.onTarget();
    }

    public void enable() {
        left.enable();
        right.enable();
        rotation.enable();
    }

    public void disable() {
        left.disable();
        right.disable();
        rotation.disable();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StopMovingCommand());
    }
}
