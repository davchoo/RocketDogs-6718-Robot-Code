package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

//TODO Wire limit switch to RIO instead
public class OpenGripperCommand extends Command {
    public OpenGripperCommand() {
        super("Open Gripper");
        requires(Robot.gripper);
    }

    @Override
    protected void initialize() {
        Robot.gripper.set(1);
    }

    @Override
    protected boolean isFinished() {
        return Robot.gripper.isFullyOpened();
    }

    @Override
    protected void end() {
        Robot.gripper.set(0);
    }
}
