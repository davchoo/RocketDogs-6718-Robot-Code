package frc.team6718.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public Joystick drive, arm;
    public OI() {
        drive = new Joystick(0);
        arm = new Joystick(1);
    }
}
