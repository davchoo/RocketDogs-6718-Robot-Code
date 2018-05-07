package frc.team6718.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team6718.robot.RobotMap;

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

    public static final double HOLDING_SPEED = 0.3;

    public ArmSubsystem() {
        super("Arm");
        upperArmA = new Spark(RobotMap.ARM_UPPER_MOTOR);
        upperArmB = new Spark(RobotMap.ARM_UPPER_MOTOR_2);
        upperArm = new SpeedControllerGroup(upperArmA, upperArmB);

        lowerArm = new WPI_TalonSRX(RobotMap.ARM_LOWER_MOTOR);

        //Set stuff in Shuffleboard
        upperArm.setName("Arm", "Upper Arm");
        lowerArm.setName("Arm", "Lower Arm");

        SmartDashboard.putData(upperArm);
        SmartDashboard.putData(lowerArm);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void setLowerArmAngle(double angle) {
        lowerArm.set(angle);
    }

    public void setUpperArmAngle(double angle) {
        upperArm.set(angle);
    }
}
