package frc.team6718.robot;

import edu.wpi.first.wpilibj.SerialPort;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MB1003 Ultrasonic sensor
 * Connect pin 5 of sensor to pin 10 of the MXP
 * All units are in mm
 * TODO test ultrasonic sensor
 */
public class MB1003UltrasonicSensor {
    private SerialPort port;
    private Timer timer;
    private int distance;
    private ReentrantLock distanceLock;

    private class UpdateTask extends TimerTask{
        private MB1003UltrasonicSensor sensor;
        private UpdateTask(MB1003UltrasonicSensor sensor) {
            this.sensor = sensor;
        }

        @Override
        public void run() {
            sensor.update();
        }
    }
    public MB1003UltrasonicSensor() {
        port = new SerialPort(9600, SerialPort.Port.kMXP, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne);
        port.enableTermination('\r'); //Terminate after carriage return
        timer = new Timer();
        timer.schedule(new UpdateTask(this), 0, 100);
        distanceLock = new ReentrantLock();
    }


    private void update() {
        if (port.getBytesReceived() >= 6) {
            byte[] data = port.read(6);
            while (port.getBytesReceived() >= 6) { //Empty the buffer and keep latest value
                data = port.read(6);
            }
            if (data.length == 5 && data[0] == 'R') { //Verify Data
                distanceLock.lock();
                try {
                    distance = 0; //Zero
                    for (int i = 1;i < 5;i++) { //Turn ascii text number to number
                        int num = data[i] - 48;
                        distance += num * (10 ^ (4 - i));
                    }
                }finally {
                    distanceLock.unlock();
                }
            }
        }
    }

    public int getDistance() {
        distanceLock.lock();
        try {
            return distance;
        }finally {
            distanceLock.unlock();
        }
    }

}
