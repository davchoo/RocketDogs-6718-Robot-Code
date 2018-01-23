package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

public class OIDriverCommand extends Command {
    public OIDriverCommand() {
        super("OI Driver Command");
        requires(Robot.driveTrain);
    }

    @Override
    protected void execute() {
        double speed = Robot.oi.joystick.getY();
        double xRot = Robot.oi.joystick.getX();
        Robot.driveTrain.drive.arcadeDrive(speed, xRot);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
