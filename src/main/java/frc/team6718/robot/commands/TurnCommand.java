package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

/**
 * TurnCommand
 * Turns the robot to face the right direction
 */
public class TurnCommand extends Command {
    private final double heading;

    /**
     * Turns the robot
     * @param heading The amount to turn in degrees (ccw neg , cw pos)
     */
    public TurnCommand(double heading) {
        super("Turn");
        requires(Robot.driveTrain);
        this.heading = heading;
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.enable();
        Robot.driveTrain.rotateTargetHeading(heading);
    }

    @Override
    protected void end() {
        Robot.driveTrain.disable();
    }

    @Override
    protected boolean isFinished() {
        return Robot.driveTrain.isHeadingOnTarget();
    }

    public double getHeading() {
        return heading;
    }
}
