package frc.team6718.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MB1003 Ultrasonic sensor
 * Connect pin 3 to AIO
 * All units are in mm
 */
public class MB1003UltrasonicSensor implements Sendable {
    private AnalogInput input;

    public MB1003UltrasonicSensor(int port) {
        input = new AnalogInput(port);
        LiveWindow.add(this);
    }

    public double getDistance() {
        return input.getVoltage() / 4.885 * 5000;
    }

    @Override
    public String getName() {
        return "Ultrasonic sensor";
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setName(String subsystem, String name) {

    }

    @Override
    public String getSubsystem() {
        return "Dart";
    }

    @Override
    public void setSubsystem(String subsystem) {

    }

    private void ignore(double a) {

    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Distance", this::getDistance, this::ignore);
        builder.addDoubleProperty("Voltage", input::getVoltage, this::ignore);
    }

}
