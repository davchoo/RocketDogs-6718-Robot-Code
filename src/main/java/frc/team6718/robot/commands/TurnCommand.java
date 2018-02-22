package frc.team6718.robot.commands;

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
        this.heading = heading;
    }

    public double getHeading() {
        return heading;
    }
}
