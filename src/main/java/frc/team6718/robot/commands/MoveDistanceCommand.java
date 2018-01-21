package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

/**
 * MoveDistanceCommand
 * Makes the robot move a certain amount of inches at a speed
 * TODO may overshoot the distance
 */
public class MoveDistanceCommand extends Command{
    private double distance, speed;

    public MoveDistanceCommand(double distance, double speed) {
        super("Move Distance");
        requires(Robot.driveTrain);
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.leftEncoder.reset();
        Robot.driveTrain.rightEncoder.reset();
        Robot.driveTrain.setTargetSpeeds(speed, speed);
    }

    @Override
    protected void end() {
        Robot.driveTrain.setTargetSpeeds(0, 0);
    }

    @Override
    protected boolean isFinished() {
        double avg = (Robot.driveTrain.leftEncoder.getDistance() + Robot.driveTrain.rightEncoder.getDistance()) / 2d;
        return avg >= distance;
    }


}
