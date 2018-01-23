package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team6718.robot.Robot;

/**
 * StopMovingCommand
 * Disables the PIDControllers in the driveTrain
 */
public class StopMovingCommand extends InstantCommand {
    public StopMovingCommand() {
        super("Stop Moving");
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.disable();
    }

}
