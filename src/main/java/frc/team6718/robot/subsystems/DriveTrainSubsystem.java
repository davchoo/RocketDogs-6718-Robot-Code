package frc.team6718.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.Robot;
import frc.team6718.robot.RobotMap;
import frc.team6718.robot.commands.StopMovingCommand;
import jaci.pathfinder.Trajectory;

import static frc.team6718.robot.subsystems.DriveTrainConstants.*;

/**
 * Drive Train Subsystem
 * TLDR: Talons are complicated
 */
public class DriveTrainSubsystem extends Subsystem {
    //A of each side is master
    private TalonSRX leftA, leftB;
    private TalonSRX rightA, rightB;
    //Units 1 = 1/4096 of a rotation

    public DriveTrainSubsystem() {
        super("Drive Train");

        leftA = new TalonSRX(RobotMap.DRIVE_TRAIN_LEFT_A);
        leftB = new TalonSRX(RobotMap.DRIVE_TRAIN_LEFT_B);

        rightA = new TalonSRX(RobotMap.DRIVE_TRAIN_RIGHT_A);
        rightB = new TalonSRX(RobotMap.DRIVE_TRAIN_RIGHT_B);

        //Start disabled for safety
        disable();

        //Configure Neutral Mode
        leftA.setNeutralMode(NeutralMode.Brake);
        leftB.setNeutralMode(NeutralMode.Brake);

        rightA.setNeutralMode(NeutralMode.Brake);
        rightB.setNeutralMode(NeutralMode.Brake);

        //LeftB and RightB have to follow because they are mechanically linked
        leftB.follow(leftA);
        rightB.follow(rightA);

        //Configure the sensors
        leftA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_PRIMARY, TIMEOUT);

        rightA.configRemoteFeedbackFilter(RobotMap.DRIVE_TRAIN_LEFT_A, RemoteSensorSource.TalonSRX_SelectedSensor, 0, TIMEOUT);
        rightA.configRemoteFeedbackFilter(RobotMap.PIGEON_IMU, RemoteSensorSource.Pigeon_Yaw, 1, TIMEOUT);

        rightA.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, TIMEOUT);
        rightA.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, TIMEOUT);

        rightA.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, PID_PRIMARY, TIMEOUT); //Sum of the distances from both encoders
        rightA.configSelectedFeedbackCoefficient(0.5, PID_PRIMARY, TIMEOUT); //We want the mean distance traveled

        rightA.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, PID_HEADING, TIMEOUT); //Pigeon yaw
        rightA.configSelectedFeedbackCoefficient(TURN_UNITS_PER_ROTATION / PIGEON_UNITS_PER_ROTATION, PID_HEADING, TIMEOUT);

        //Configure Status Frame Periods
        rightA.setStatusFramePeriod(StatusFrameEnhanced.Status_12_Feedback1, 20, TIMEOUT);
        rightA.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 20, TIMEOUT);
        rightA.setStatusFramePeriod(StatusFrameEnhanced.Status_14_Turn_PIDF1, 20, TIMEOUT);
        Robot.gyroscope.pigeonIMU.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR , 5, TIMEOUT);

        //Configure neutral deadband
        leftA.configNeutralDeadband(DEADBAND, TIMEOUT);
        leftB.configNeutralDeadband(DEADBAND, TIMEOUT); //May be unnecessary

        rightA.configNeutralDeadband(DEADBAND, TIMEOUT);
        rightB.configNeutralDeadband(DEADBAND, TIMEOUT);

        //Configure peak output
        leftA.configPeakOutputForward(1.0, TIMEOUT);
        leftA.configPeakOutputReverse(-1.0, TIMEOUT);
        leftB.configPeakOutputForward(1.0, TIMEOUT);
        leftB.configPeakOutputReverse(-1.0, TIMEOUT);

        rightA.configPeakOutputForward(1.0, TIMEOUT);
        rightA.configPeakOutputReverse(-1.0, TIMEOUT);
        rightB.configPeakOutputForward(1.0, TIMEOUT);
        rightB.configPeakOutputReverse(-1.0, TIMEOUT);

        //TODO check if we need to reverse the encoders/motors
        leftA.setInverted(false);
        leftA.setSensorPhase(false);

        rightA.setInverted(false);
        rightA.setSensorPhase(false);

        //TODO calibrate PID
        //Heading
        rightA.config_kP(SLOT_HEADING, 0, TIMEOUT);
        rightA.config_kI(SLOT_HEADING, 0, TIMEOUT);
        rightA.config_kD(SLOT_HEADING, 0, TIMEOUT);
        rightA.config_kF(SLOT_HEADING, 0, TIMEOUT);
        rightA.config_IntegralZone(SLOT_HEADING, 0, TIMEOUT);
        rightA.configClosedLoopPeakOutput(SLOT_HEADING, 0, TIMEOUT);
        rightA.configAllowableClosedloopError(SLOT_HEADING, HEADING_TOLERANCE, TIMEOUT);

        //Distance
        rightA.config_kP(SLOT_DISTANCE, 0, TIMEOUT);
        rightA.config_kI(SLOT_DISTANCE, 0, TIMEOUT);
        rightA.config_kD(SLOT_DISTANCE, 0, TIMEOUT);
        rightA.config_kF(SLOT_DISTANCE, 0, TIMEOUT);
        rightA.config_IntegralZone(SLOT_DISTANCE, 0, TIMEOUT);
        rightA.configClosedLoopPeakOutput(SLOT_DISTANCE, 0, TIMEOUT);
        rightA.configAllowableClosedloopError(SLOT_DISTANCE, DISTANCE_TOLERANCE, TIMEOUT);

        //Velocity
        rightA.config_kP(SLOT_VELOCITY, 0, TIMEOUT);
        rightA.config_kI(SLOT_VELOCITY, 0, TIMEOUT);
        rightA.config_kD(SLOT_VELOCITY, 0, TIMEOUT);
        rightA.config_kF(SLOT_VELOCITY, 1.0 / convertI2EU(MAX_SPEED) * 0.1, TIMEOUT);
        rightA.config_IntegralZone(SLOT_VELOCITY, 0, TIMEOUT);
        rightA.configClosedLoopPeakOutput(SLOT_VELOCITY, 0, TIMEOUT);
        rightA.configAllowableClosedloopError(SLOT_VELOCITY, VELOCITY_TOLERANCE, TIMEOUT);

        //Configure Motion Magic
        rightA.configMotionAcceleration((int)(convertI2EU(MAX_ACCEL) * 0.1), TIMEOUT);
        rightA.configMotionCruiseVelocity((int)(convertI2EU(MAX_SPEED) * 0.1), TIMEOUT);

        /*
          false -> rightA = PID[0] + PID[1] leftA = PID[0] - PID[1]
          true -> rightA = PID[0] - PID[1] leftA = PID[0] + PID[1]
         */
        rightA.configAuxPIDPolarity(false, TIMEOUT);
    }

    /**
     * Sets the target speed
     * @param speed The target speed to drive at (in/s)
     */
    public void setTargetSpeed(double speed) {
        //Select the correct profile
        rightA.selectProfileSlot(SLOT_VELOCITY, PID_PRIMARY);
        rightA.selectProfileSlot(SLOT_HEADING, PID_HEADING);

        double heading = rightA.getSelectedSensorPosition(1);
        rightA.set(ControlMode.Velocity, convertI2EU(speed) * 0.100, DemandType.AuxPID, heading);
        leftA.follow(rightA, FollowerType.AuxOutput1);
    }

    /**
     * Set the direction the robot to face
     * @param angle The direction in degrees
     */
    public void setTargetHeading(double angle) {
        rightA.selectProfileSlot(SLOT_HEADING, PID_HEADING);

        rightA.set(ControlMode.PercentOutput, 0, DemandType.AuxPID, angle * 10);
        leftA.follow(rightA, FollowerType.AuxOutput1);
    }

    /**
     * Drive the robot forward at the heading currently measured
     * Note this is absolute distance and you need to reset the distance sensors
     * @param distance The amount of sensor units
     */
    public void setTargetDistance(double distance) {
        //Select the correct profile
        rightA.selectProfileSlot(SLOT_DISTANCE, PID_PRIMARY);
        rightA.selectProfileSlot(SLOT_HEADING, PID_HEADING);

        double heading = rightA.getSelectedSensorPosition(1);
        rightA.set(ControlMode.Position, distance, DemandType.AuxPID, heading);
        leftA.follow(rightA, FollowerType.AuxOutput1);
    }

    /**
     * Rotate the robot
     * @param angle the angle delta in degrees
     */
    public void rotateTargetHeading(double angle) {
        setTargetHeading(rightA.getSelectedSensorPosition(1) * 0.1 + angle);
    }

    /**
     * Check if the heading is within tolerance
     * @return if heading is within tolerance
     */
    public boolean isHeadingOnTarget() {
        return rightA.getClosedLoopError(PID_HEADING) <= HEADING_TOLERANCE;
    }

    /**
     * Check if the distance is within tolerance
     * @return if the distance is within tolerance
     */
    public boolean isDistanceOnTarget() {
        return rightA.getClosedLoopError(PID_PRIMARY) < DISTANCE_TOLERANCE;
    }

    /**
     * Reset both encoders to 0
     */
    public void resetDistance() {
        leftA.getSensorCollection().setQuadraturePosition(0, TIMEOUT);
        rightA.getSensorCollection().setQuadraturePosition(0, TIMEOUT);
    }

    /**
     * Enable distance(and thus velocity) averaging
     * Use averaging when you're not adjusting heading while moving using velocity/distance targets
     */
    public void enableAvgDistance() {
        rightA.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, TIMEOUT);
        rightA.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, TIMEOUT);

        rightA.set(ControlMode.Disabled, 0); //Stop moving the motors

        rightA.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, PID_PRIMARY, TIMEOUT); //Sum of the distances from both encoders
        rightA.configSelectedFeedbackCoefficient(0.5, PID_PRIMARY, TIMEOUT); //We want the mean distance traveled
    }

    /**
     * Disable distance(and thus velocity) averaging
     * Don't use averaging when the one side has to go faster/farther or slower/closer
     */
    public void disableAvgDistance() {
        rightA.set(ControlMode.Disabled, 0); //Stop moving the motors

        rightA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_PRIMARY, TIMEOUT); //Use only its own encoder
        rightA.configSelectedFeedbackCoefficient(1, PID_PRIMARY, TIMEOUT); //We don't want to divide the distance by 2
    }

    /**
     * Disable all motor controllers and rotation pid
     */
    public void disable() {
        leftA.set(ControlMode.Disabled, 0);
        rightA.set(ControlMode.Disabled, 0);
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
     * Converts encoder sensor units to inches
     * @param units Encoder Sensor units
     * @return distance in inches
     */
    public double convertEU2I(double units) {
        return units / ENCODER_UNITS_PER_ROTATION * 6d * Math.PI;
    }

    /**
     * Converts inches to encoder sensor units
     * @param distance in inches
     * @return encoder sensor units
     */
    public double convertI2EU(double distance) {
        return (int) (distance / 6d / Math.PI * ENCODER_UNITS_PER_ROTATION);
    }
}
