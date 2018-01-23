package frc.team6718.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * MB1003 Ultrasonic sensor
 * Connect pin 3 to AIO
 * All units are in mm
 */
public class MB1003UltrasonicSensor extends SendableBase {
    private AnalogInput input;

    public MB1003UltrasonicSensor(int port) {
        input = new AnalogInput(port);
        setName("MB1003", port);
    }

    public double getDistance() {
        return input.getVoltage() / 4.885 * 5000;
    }

    private void ignore(double a) {

    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Distance", this::getDistance, this::ignore);
        builder.addDoubleProperty("Voltage", input::getVoltage, this::ignore);
    }

}
