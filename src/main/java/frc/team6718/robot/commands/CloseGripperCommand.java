package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team6718.robot.Robot;

public class CloseGripperCommand extends TimedCommand {

    public CloseGripperCommand() {
        super("Close Gripper", 2); //TODO determine how long to
        requires(Robot.gripper);
    }

    @Override
    protected void initialize() {
        Robot.gripper.set(-1);
    }

    @Override
    protected void end() {
        Robot.gripper.set(0);
    }
}
