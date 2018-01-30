package frc.team6718.robot.pid;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class PIDControllerPIDOutput implements PIDOutput {
    private PIDController controller;

    public PIDControllerPIDOutput(PIDController controller) {
        this.controller = controller;
    }

    @Override
    public void pidWrite(double output) {
        controller.setSetpoint(output);
    }
}
