package frc.team6718.robot.pid;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;


public class AvgDistancePIDSource implements PIDSource {
    private Encoder a, b;

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
        a.reset();
        b.reset();
    }

    @Override
    public double pidGet() {
        return (a.getDistance() + b.getDistance()) / 2d;
    }
}
