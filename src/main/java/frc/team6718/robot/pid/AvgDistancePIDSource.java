package frc.team6718.robot.pid;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Created by davchoo
 * Date: 1/26/2018
 * Time: 12:16 AM
 */
public class AvgDistancePIDSource implements PIDSource {
    private Encoder a, b;
    private double zero;

    public AvgDistancePIDSource(Encoder a, Encoder b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        //Ignore
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    public void zero() {
        zero = a.getDistance() + b.getDistance();
    }

    @Override
    public double pidGet() {
        return (a.getDistance() + b.getDistance() - zero) / 2d;
    }
}
