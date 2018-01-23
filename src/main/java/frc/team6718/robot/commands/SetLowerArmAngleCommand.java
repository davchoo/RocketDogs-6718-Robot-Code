package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

public class SetLowerArmAngleCommand extends Command {

    private double angle;

    public SetLowerArmAngleCommand(double angle) {
        super("Set Lower Arm Angle");
        requires(Robot.arm);
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    @Override
    protected void initialize() {
        Robot.arm.setLowerArmAngle(angle);
    }

    @Override
    protected boolean isFinished() {
        return Robot.arm.isLowerArmOnTarget();
    }
}
