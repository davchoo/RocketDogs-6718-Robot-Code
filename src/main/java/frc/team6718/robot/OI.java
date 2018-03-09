package frc.team6718.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public Joystick drive, arm;

    public static final int OPEN_GRIPPER = 5;
    public static final int CLOSE_GRIPPER = 6;

    public OI() {
        drive = new Joystick(0);
        arm = new Joystick(1);
    }
}
