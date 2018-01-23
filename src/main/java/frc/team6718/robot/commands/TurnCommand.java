package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

/**
 * TurnCommand
 * Turns the robot to face the right direction
 */
public class TurnCommand extends Command {
    private double heading;

    /**
     * Turns the robot
     * @param heading In range 0 - 360 degrees
     */
    public TurnCommand(double heading) {
        super("Turn");
        this.heading = heading;
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.setTargetHeading(heading);
    }

    @Override
    protected boolean isFinished() {
        return Robot.driveTrain.isHeadingOnTarget();
    }
}
