package frc.team6718.robot.commands.auto;


import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;

public class MAutoCommand extends CommandGroup{

    public MAutoCommand() {
        super("M Auto");
        addSequential(new MoveDistanceCommand(95));
    }
}
