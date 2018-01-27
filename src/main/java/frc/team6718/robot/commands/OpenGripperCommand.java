package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team6718.robot.Robot;

//TODO determine how to keep the gripper open
public class OpenGripperCommand extends TimedCommand {
    public OpenGripperCommand() {
        super("Open Gripper Command", 5); //TODO determine time the gripper takes to open
        requires(Robot.arm);
    }

    @Override
    protected void initialize() {
        Robot.arm.openGripper();
    }

    @Override
    protected void end() {

    }
}
