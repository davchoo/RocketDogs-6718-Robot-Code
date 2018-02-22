package frc.team6718.robot.commands.auto;

import frc.team6718.robot.commands.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;
import frc.team6718.robot.commands.TurnCommand;

public class RRAutoCommandGroup extends CommandGroup {
    public RRAutoCommandGroup() {
        addSequential(new MoveDistanceCommand(12));
        addSequential(new TurnCommand(15));
        addSequential(new MoveDistanceCommand(111.81));
        addSequential(new TurnCommand(-75));
        addSequential(new MoveDistanceCommand(60));
        addSequential(new TurnCommand(-90));
        addSequential(new MoveDistanceCommand(5.75));
    }
}
