package frc.team6718.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;
import frc.team6718.robot.commands.TurnCommand;

public class LRAutoCommandGroup extends CommandGroup {
    public LRAutoCommandGroup() {
        addSequential(new MoveDistanceCommand(12));
        addSequential(new TurnCommand(15));
        addSequential(new MoveDistanceCommand(111.81));
        addSequential(new TurnCommand(-75));
        addSequential(new MoveDistanceCommand(144));
        addSequential(new TurnCommand(-90));
        addSequential(new MoveDistanceCommand(168));
        addSequential(new TurnCommand(-90));
        addSequential(new MoveDistanceCommand(234));
        addSequential(new TurnCommand(-90));
        addSequential(new MoveDistanceCommand(84));
        addSequential(new TurnCommand(-90));
        addSequential(new MoveDistanceCommand(5.75));
    }
}
