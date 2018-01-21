package frc.team6718.robot.pid;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * CombinedPIDOutput
 * Subtracts or adds a pid controller value from another one
 */
public class CombinedPIDOutput implements PIDOutput {
    private PIDController controller;
    private PIDOutput output;
    private boolean sub = false;

    public CombinedPIDOutput(PIDController controller, PIDOutput output, boolean sub) {
        this.controller = controller;
        this.output = output;
        this.sub = sub;
    }

    @Override
    public void pidWrite(double output) {
        double other = this.controller.get();
        this.output.pidWrite(output + (sub ? -other : other));
    }
}
