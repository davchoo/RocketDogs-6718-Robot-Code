package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

public class StopDriveTrainCommand extends Command {
    public StopDriveTrainCommand() {
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.drive.stopMotor();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
