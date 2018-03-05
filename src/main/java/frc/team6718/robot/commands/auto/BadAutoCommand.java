package frc.team6718.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;

public class BadAutoCommand extends CommandGroup{
    public BadAutoCommand() {
        super("Bad Auto");
        addSequential(new MoveDistanceCommand(11 * 12));
    }
}
