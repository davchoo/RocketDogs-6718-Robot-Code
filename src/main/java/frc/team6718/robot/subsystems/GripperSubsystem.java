
package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.RobotMap;

public class GripperSubsystem extends Subsystem {
    private Spark gripper;

    public GripperSubsystem() {
        super("Gripper");

        gripper = new Spark(RobotMap.GRIPPER_MOTOR);
        gripper.setName("Gripper Motor", "Gripper");
    }

    public void set(double speed) {
        gripper.set(speed);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
