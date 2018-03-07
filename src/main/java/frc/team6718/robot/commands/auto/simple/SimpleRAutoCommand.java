package frc.team6718.robot.commands.auto.simple;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;
import frc.team6718.robot.commands.TurnCommand;

public class SimpleRAutoCommand extends CommandGroup{
    public SimpleRAutoCommand() {
        super("Simple R Auto");
        addSequential(new MoveDistanceCommand(5.5 * 12));
        addSequential(new TurnCommand(15));
        addSequential(new MoveDistanceCommand(5.5 * 12));
    }
}
