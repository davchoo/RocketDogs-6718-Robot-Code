package frc.team6718.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.RobotMap;

/**
 * A subsystem that handles the gyroscope heading
 */
public class GyroScopeSubsystem extends Subsystem {
    public PigeonIMU pigeonIMU;

    public static final int TIMEOUT = 20;

    public GyroScopeSubsystem() {
        super("Gyroscope");
        pigeonIMU = new PigeonIMU(RobotMap.PIGEON_IMU);
    }

    @Override
    protected void initDefaultCommand() {

    }

    /**
     * Get the heading that is 0 - 360
     * @return a heading in degrees
     */
    public double getHeading() {
        return pigeonIMU.getFusedHeading();
    }

    public void zero() {
        pigeonIMU.setYaw(0, TIMEOUT);
        pigeonIMU.setAccumZAngle(0, TIMEOUT);
    }
}
