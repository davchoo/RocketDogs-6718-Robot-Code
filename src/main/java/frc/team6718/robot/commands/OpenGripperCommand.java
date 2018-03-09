package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team6718.robot.Robot;

//TODO Wire limit switch to RIO instead
public class OpenGripperCommand extends TimedCommand {
    public OpenGripperCommand() {
        super("Open Gripper", 5); //TODO determine time the gripper takes to open
        requires(Robot.gripper);
    }

    @Override
    protected void initialize() {
        Robot.gripper.set(1);
    }

    @Override
    protected void end() {
        Robot.gripper.set(0);
    }
}
