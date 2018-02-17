package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

//TODO control the arm
public class OIDriverCommand extends Command {
    public OIDriverCommand() {
        super("OI Driver Command");
        requires(Robot.driveTrain);
        requires(Robot.arm);
    }

    @Override
    protected void execute() {
        double speed = Robot.oi.joystick.getY();
        double xRot = Robot.oi.joystick.getX();
        Robot.driveTrain.drive.arcadeDrive(speed, xRot);
        Robot.arm.set(Robot.oi.joystick2.getX(), Robot.oi.joystick2.getY(), Robot.oi.joystick2.getTwist());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
