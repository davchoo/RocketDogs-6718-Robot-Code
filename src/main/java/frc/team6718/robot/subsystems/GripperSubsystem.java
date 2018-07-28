
package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team6718.robot.RobotMap;

/**
 * Gripper Subsystem
 * Gripper is opened and closed through a single motor and a LEAD screw
 * A limit switch is used to detect when its fully opened
 */
public class GripperSubsystem extends Subsystem {
    private Spark gripper;

    public GripperSubsystem() {
        super("Gripper");

        gripper = new Spark(RobotMap.GRIPPER_MOTOR);

        gripper.setInverted(true);

        gripper.setName("Gripper", "Motor");

        SmartDashboard.putData(gripper);
    }

    public void set(double speed) {
        gripper.set(speed);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
