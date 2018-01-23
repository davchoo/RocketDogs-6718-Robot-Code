package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

public class SetUpperArmAngleCommand extends Command {

    private double angle;

    public SetUpperArmAngleCommand(double angle) {
        super("Set Upper Arm Angle");
        requires(Robot.arm);
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    @Override
    protected void initialize() {
        Robot.arm.setUpperArmAngle(angle);
    }

    @Override
    protected boolean isFinished() {
        return Robot.arm.isUpperArmOnTarget();
    }
}
