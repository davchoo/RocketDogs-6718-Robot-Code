package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

public class SetJointAngleCommand extends Command {
    private int jointId;
    private double angle;

    public SetJointAngleCommand(int jointId, double angle) {
        this.jointId = jointId;
        this.angle = angle;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
