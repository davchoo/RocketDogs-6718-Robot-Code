package frc.team6718.robot.commands;

import java.util.ArrayList;

public class CommandGroup {
    public ArrayList<Command> commands = new ArrayList<>();

    protected void addSequential(Command c) {
        commands.add(c);
    }
}
