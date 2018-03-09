
package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.RobotMap;

/**
 * Gripper Subsystem
 * Gripper is opened and closed through a single motor and a LEAD screw
 * A limit switch is used to detect when its fully opened
 */
public class GripperSubsystem extends Subsystem {
    private Spark gripper;
    private DigitalInput limitSwitch;
    private double speed;

    public GripperSubsystem() {
        super("Gripper");

        gripper = new Spark(RobotMap.GRIPPER_MOTOR);
        limitSwitch = new DigitalInput(RobotMap.GRIPPER_LIMIT_SWITCH);

        gripper.setInverted(true);

        gripper.setName("Gripper", "Motor");
        limitSwitch.setName("Gripper", "Limit switch");
    }

    public void set(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isFullyOpenned() {
        return limitSwitch.get();
    }

    @Override
    public void periodic() {
        double aSpeed = speed;
        if (speed > 0 && limitSwitch.get()) { //Gripper openning and touches limit switch
            aSpeed = 0;
        }
        gripper.set(aSpeed);
    }



    @Override
    protected void initDefaultCommand() {

    }
}
