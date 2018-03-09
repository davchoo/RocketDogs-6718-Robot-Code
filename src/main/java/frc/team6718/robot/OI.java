package frc.team6718.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public Joystick drive, arm;

    //Buttons
    public int disableRotation, disableMovement;

    public OI() {
        drive = new Joystick(0);
        arm = new Joystick(1);
        disableRotation = 6;
        disableMovement = 8;
    }
}
