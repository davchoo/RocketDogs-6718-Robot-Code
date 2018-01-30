package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;
import frc.team6718.robot.subsystems.DriveTrainSubsystem;

/**
 * Allows the Operator to move the robot with a joystick
 */
public class OIDriverCommand extends Command {
    public OIDriverCommand() {
        super("OI Driver");
        requires(Robot.driveTrain);
    }

    @Override
    protected void execute() { //TODO check if we should square xRot and if its sensitive enough
        double speed = Math.pow(Robot.oi.joystick.getY(), 2) * DriveTrainSubsystem.MAX_SPEED;
        double xRot = Robot.oi.joystick.getX();
        Robot.driveTrain.setTargetSpeeds(speed, speed);
        Robot.driveTrain.rotateTargetHeading(xRot);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
