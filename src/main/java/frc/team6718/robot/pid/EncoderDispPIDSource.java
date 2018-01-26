package frc.team6718.robot.pid;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class EncoderDispPIDSource implements PIDSource {
    private Encoder encoder;

    public EncoderDispPIDSource(Encoder encoder) {
        this.encoder = encoder;
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
        return encoder.getDistance();
    }
}
