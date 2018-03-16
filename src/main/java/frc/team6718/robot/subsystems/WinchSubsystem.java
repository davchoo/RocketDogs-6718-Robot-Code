package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.RobotMap;

public class WinchSubsystem extends Subsystem {
    public static final double TIME_TO_CLIMB = 15;
    private Spark winch;

    public WinchSubsystem() {
        super("Winch");
        winch = new Spark(RobotMap.WINCH_MOTOR);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void set(double speed) {
        if (speed < 0) {
            return; //Prevent from moving backwards
        }
        winch.set(speed);
    }
}
