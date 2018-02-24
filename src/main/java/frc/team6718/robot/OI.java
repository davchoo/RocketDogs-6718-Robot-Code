package frc.team6718.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public Joystick joystick;

    //Buttons
    public int disableRotation, disableMovement;

    public OI() {
        joystick = new Joystick(0);
        disableRotation = 6;
        disableMovement = 8;
    }
}
