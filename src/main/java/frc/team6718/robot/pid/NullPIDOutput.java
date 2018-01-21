package frc.team6718.robot.pid;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * NullPIDOuput
 * Ignores all pidWrites to it
 */
public class NullPIDOutput implements PIDOutput {
    @Override
    public void pidWrite(double output) {

    }
}
