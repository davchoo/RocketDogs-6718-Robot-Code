package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

//TODO may overshoot the distance
public class MoveDistanceCommand extends Command{
    public double distance, speed;
    private double lastAngle;
    //TODO calibrate P value
    private double P = 0.00; //Proportional part of PID

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
        lastAngle = Robot.gyroscope.getHeading();
    }

    @Override
    protected void execute() {
        double xRot = -(Robot.gyroscope.getHeading() - lastAngle) * P;
        Robot.driveTrain.drive.arcadeDrive(speed, xRot);
    }

    @Override
    protected void end() {
        Robot.driveTrain.drive.arcadeDrive(0, 0);
    }

    @Override
    protected boolean isFinished() {
        double avg = (Robot.driveTrain.leftEncoder.getDistance() + Robot.driveTrain.rightEncoder.getDistance()) / 2d;
        return avg >= distance;
    }


}
