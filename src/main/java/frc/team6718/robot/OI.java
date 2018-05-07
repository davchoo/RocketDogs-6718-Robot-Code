package frc.team6718.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class OI {
    public Joystick drive;
    public XboxController arm;

    public static final int DISABLE_ROTATION = 5;
    public static final int DISABLE_MOVEMENT = 6;

    public OI() {
        drive = new Joystick(0);
        arm = new XboxController(1);
    }
}
