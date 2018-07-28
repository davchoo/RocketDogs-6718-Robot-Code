package frc.team6718.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;

/**
 * Left auto command
 * Place the robot so that the right side(facing the scale) is
 * 32.7273 in. from the portal marking on the floor
 */
public class LAutoCommand extends CommandGroup{
    public LAutoCommand() {
        super("L Auto");
        addSequential(new MoveDistanceCommand(5.5 * 12));

    }
}
