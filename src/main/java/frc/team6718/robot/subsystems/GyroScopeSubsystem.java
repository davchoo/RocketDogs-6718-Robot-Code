package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GyroScopeSubsystem extends Subsystem {
    private ADXRS450_Gyro gyroscope;

    public GyroScopeSubsystem() {
        super("Gyroscope");
        gyroscope = new ADXRS450_Gyro();
        gyroscope.setSubsystem("Gyroscope");
    }

    @Override
    protected void initDefaultCommand() {

    }

    public double getHeading() {
        double heading = gyroscope.getAngle() % 360;
        if (heading < 0) {
            heading = 360 + heading;
        }
        return heading;
    }
}
