package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

public class StopDriveTrainCommand extends Command {
    public StopDriveTrainCommand() {
        requires(Robot.driveTrainSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.driveTrainSubsystem.stopAll();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
