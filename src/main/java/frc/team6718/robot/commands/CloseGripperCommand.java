package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team6718.robot.Robot;

public class CloseGripperCommand extends TimedCommand {

    public CloseGripperCommand() {
        super("Close Gripper Command", 2);
        requires(Robot.arm);
    }

    @Override
    protected void initialize() {
        Robot.arm.closeGripper();
    }
}
