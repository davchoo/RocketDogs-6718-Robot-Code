package frc.team6718.robot;

import edu.wpi.first.wpilibj.Joystick;

//TODO 2 or 3 joysticks?
public class OI {
    public Joystick joystick, joystick2;
    public OI() {
        joystick = new Joystick(0);
        joystick2 = new Joystick(1);
    }
}
