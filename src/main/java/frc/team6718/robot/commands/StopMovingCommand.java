package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

/**
 * StopMovingCommand
 * Disables the PIDControllers in the driveTrain
 */
public class StopMovingCommand extends Command {
    public StopMovingCommand() {
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.disable();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
