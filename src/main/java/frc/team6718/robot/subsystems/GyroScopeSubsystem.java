package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

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

    public double getHeading() {
        double heading = gyroscope.getAngle() % 360;
        if (heading < 0) {
            heading = 360 + heading;
        }
        return heading;
    }

    public GyroscopePIDSource getPIDSource() {
        return new GyroscopePIDSource(this);
    }
}
