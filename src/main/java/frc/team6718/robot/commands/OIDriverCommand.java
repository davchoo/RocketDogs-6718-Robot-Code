package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

public class OIDriverCommand extends Command {
    public OIDriverCommand() {
        super("OI Driver");
        requires(Robot.driveTrain);
    }

    @Override
    protected void execute() { //TODO joystick speed and xRotation might be too slow
        double speed = Robot.oi.joystick.getY();
        double xRot = Robot.oi.joystick.getX();
        Robot.driveTrain.setTargetSpeeds(speed, speed);
        Robot.driveTrain.rotateTargetHeading(xRot);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
