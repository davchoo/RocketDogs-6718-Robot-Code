package frc.team6718.robot.commands.auto;

import frc.team6718.robot.commands.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;
import frc.team6718.robot.commands.TurnCommand;

public class LMAutoCommandGroup extends CommandGroup {
    public LMAutoCommandGroup() {
        addSequential(new MoveDistanceCommand(10));
        addSequential(new MoveDistanceCommand(12));
        addSequential(new TurnCommand(58.14));
        addSequential(new MoveDistanceCommand(136.43));
        addSequential(new TurnCommand(-31.86));
        addSequential(new MoveDistanceCommand(96));
        addSequential(new TurnCommand(-90));
        addSequential(new MoveDistanceCommand(5.75));
    }
}
