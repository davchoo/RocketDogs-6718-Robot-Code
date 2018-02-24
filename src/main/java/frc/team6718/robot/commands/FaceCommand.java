package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

/**
 * Set the heading
 */
public class FaceCommand extends Command {
    public double heading;

    public FaceCommand(double heading) {
        super("Face");
        requires(Robot.driveTrain);
        this.heading = heading;
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
