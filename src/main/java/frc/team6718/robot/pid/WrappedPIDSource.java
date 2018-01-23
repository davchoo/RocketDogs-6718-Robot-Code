package frc.team6718.robot.pid;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * MappedPIDSource
 * Maps the input range of a pid source to an output range
 */
public class WrappedPIDSource implements PIDSource {
    private PIDSource source;
    private double max;

    public WrappedPIDSource(PIDSource source, double max) {
        this.source = source;
        this.max = max;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        source.setPIDSourceType(pidSource);
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return source.getPIDSourceType();
    }

    @Override
    public double pidGet() {
        return source.pidGet() % max;
    }
}
