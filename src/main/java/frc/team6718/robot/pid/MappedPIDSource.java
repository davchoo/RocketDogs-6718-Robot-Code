package frc.team6718.robot.pid;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * MappedPIDSource
 * Maps the input range of a pid source to an output range
 */
public class MappedPIDSource implements PIDSource {
    private PIDSource source;
    private double inputMin, inputRange, outputMin, outputRange;

    public MappedPIDSource(PIDSource source, double inputMin, double inputMax, double outputMin, double outputMax) {
        this.source = source;
        this.inputMin = inputMin;
        this.inputRange = inputMax - inputMin;
        this.outputMin = outputMin;
        this.outputRange = outputMax - outputMin;
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
        double normalized = (source.pidGet() - inputMin) / inputRange % 1d;
        return normalized * outputRange + outputMin;
    }
}
