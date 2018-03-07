package frc.team6718.robot.commands.auto.simple;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;
import frc.team6718.robot.commands.TurnCommand;

public class SimpleLAutoCommand extends CommandGroup{
    public SimpleLAutoCommand() {
        super("Simple L Auto");
        addSequential(new MoveDistanceCommand(5.5 * 12));
        addSequential(new TurnCommand(-15));
        addSequential(new MoveDistanceCommand(5.5 * 12));
    }
}
