package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem that handles the gyroscope heading
 */
public class GyroScopeSubsystem extends Subsystem {

    public class GyroscopePIDSource implements PIDSource {
        private GyroScopeSubsystem gyroScopeSubsystem;

        public GyroscopePIDSource(GyroScopeSubsystem subsystem) {
            this.gyroScopeSubsystem = subsystem;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
            //Ignore
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return gyroScopeSubsystem.getHeading();
        }
    }

    private ADXRS450_Gyro gyroscope;

    public GyroScopeSubsystem() {
        super("Gyroscope");
        gyroscope = new ADXRS450_Gyro();
    }

    @Override
    protected void initDefaultCommand() {

    }

    /**
     * Get the heading that is 0 - 360
     * @return a heading in degrees
     */
    public double getHeading() {
        double heading = gyroscope.getAngle() % 360d;
        if (heading < 0) {
            heading += 360;
        }
        return heading;
    }

    /**
     * Create a GyroscopePIDSource
     * @return a GyroscopePIDSource
     */
    public GyroscopePIDSource getPIDSource() {
        return new GyroscopePIDSource(this);
    }
}
