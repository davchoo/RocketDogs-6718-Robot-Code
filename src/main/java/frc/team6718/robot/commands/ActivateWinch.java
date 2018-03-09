package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team6718.robot.Robot;
import frc.team6718.robot.subsystems.WinchSubsystem;

public class ActivateWinch extends TimedCommand{
    public ActivateWinch() {
        super("Activate Winch", WinchSubsystem.TIME_TO_CLIMB);
        requires(Robot.winch);
    }

    @Override
    protected void initialize() {
        Robot.winch.set(1);
    }

    @Override
    protected void end() {
        Robot.winch.set(0);
    }
}
