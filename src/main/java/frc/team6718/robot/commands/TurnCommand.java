package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

//TODO may overshoot
public class TurnCommand extends Command {
    private double heading;
    private double rotation;
    public static final double ROTATION_SPEED = 0.8;

    public TurnCommand(double heading) {
        this.heading = heading;
        requires(Robot.driveTrain);
        requires(Robot.gyroscope);
    }

    @Override
    protected void initialize() {
        double ccw = Robot.gyroscope.getHeading() - heading;
        double cw = heading - Robot.gyroscope.getHeading();
        rotation = cw > ccw ? -ROTATION_SPEED : ROTATION_SPEED;
    }

    @Override
    protected void execute() {
        Robot.driveTrain.drive.arcadeDrive(0, rotation);
    }

    @Override
    protected void end() {
        Robot.driveTrain.drive.arcadeDrive(0, 0);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.gyroscope.getHeading() - heading) < 1;
    }
}
