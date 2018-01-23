package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

/**
 * MoveDistanceCommand
 * Makes the robot move a certain amount of inches at a speed
 */
public class MoveDistanceCommand extends Command{
    private double distance;

    /**
     * Move the robot forward
     * @param distance in inches
     */
    public MoveDistanceCommand(double distance) {
        super("Move Distance");
        requires(Robot.driveTrain);
        this.distance = distance;
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.enable();
        Robot.driveTrain.resetDistance();
        Robot.driveTrain.setTargetDistance(distance);
    }

    @Override
    protected void end() {
        Robot.driveTrain.setTargetSpeeds(0, 0); //Stop motors
        Robot.driveTrain.disable();
    }

    @Override
    protected boolean isFinished() {
        return Robot.driveTrain.isDistanceOnTarget();
    }


}
