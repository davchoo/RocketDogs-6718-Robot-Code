package frc.team6718.robot.commands;

//TODO may overshoot the distance
public class MoveDistanceCommand extends Command{
    public double distance;
    /**
     * Move the robot forward
     * @param distance in inches
     */
    public MoveDistanceCommand(double distance) {
        this.distance = distance;
    }

}
