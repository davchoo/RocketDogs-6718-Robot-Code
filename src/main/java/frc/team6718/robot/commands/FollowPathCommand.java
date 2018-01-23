package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;
import frc.team6718.robot.subsystems.DriveTrainSubsystem;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import static jaci.pathfinder.Pathfinder.r2d;
import static jaci.pathfinder.Trajectory.Segment;

/**
 * Follows a path of waypoints
 * Use the test-pathfinder branch to visualize a path
 */
public class FollowPathCommand extends Command {
    private Trajectory left, right;
    private int leftSegment, rightSegment;

    public FollowPathCommand(Waypoint[] points) {
        super("Follow Path");
        requires(Robot.driveTrain);
        Trajectory trajectory = Pathfinder.generate(points, Robot.driveTrain.getConfig());
        TankModifier tankModifier = new TankModifier(trajectory);
        tankModifier.modify(DriveTrainSubsystem.TRACK_WIDTH);
        left = tankModifier.getLeftTrajectory();
        right = tankModifier.getRightTrajectory();
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.enable();
        leftSegment = 0;
        rightSegment = 0;
    }

    @Override
    protected void execute() { //TODO check if velocity pid is good enough probably not :C
        //TODO check if left and right segments are always the same length
        double leftSpeed = 0;
        double rightSpeed = 0;
        if (leftSegment < left.length()) {
            Segment segment = left.get(leftSegment);
            leftSpeed = segment.velocity;
            Robot.driveTrain.setTargetHeading(r2d(segment.heading));
            leftSegment++;
        }
        if (rightSegment < right.length()) {
            Segment segment = right.get(rightSegment);
            rightSpeed = segment.velocity;
            rightSegment++;
        }
        Robot.driveTrain.setTargetSpeeds(leftSpeed, rightSpeed);
    }


    @Override
    protected void end() {
        Robot.driveTrain.setTargetSpeeds(0, 0);
        Robot.driveTrain.disable();
    }

    @Override
    protected boolean isFinished() {
        return leftSegment >= left.length() && rightSegment >= right.length();
    }
}
