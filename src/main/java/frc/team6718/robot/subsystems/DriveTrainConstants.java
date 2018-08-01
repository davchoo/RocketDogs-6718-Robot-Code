package frc.team6718.robot.subsystems;

public class DriveTrainConstants {
    public static final double MAX_SPEED = 126.6; //TODO find max speed
    public static final double MAX_ACCEL = 10; //TODO find max acceleration
    public static final double MAX_JERK = 2; //TODO find max jerk
    public static final double TRACK_WIDTH = 23;

    //TODO check if these tolerances are unreasonable (too small)
    public static final int HEADING_TOLERANCE = 2; //3600 => 360 2 => 0.2 deg
    public static final int DISTANCE_TOLERANCE = 2; //2/4096 * pi * 6in ~ 0.01 in
    public static final int VELOCITY_TOLERANCE = 4; //4/4096 * pi * 6in ~ 0.02 in/s

    /**
     * From Pigeon docs (which means don't change it)
     */
    public static final double PIGEON_UNITS_PER_ROTATION = 8192d;

    /**
     * From Encoder docs (which means don't change it)
     */
    public static final double ENCODER_UNITS_PER_ROTATION = 4096d;

    /**
     * Resolution of heading targets, conveniently scaled by 10
     */
    public static final double TURN_UNITS_PER_ROTATION = 3600d;


    
    public static final double DEADBAND = 0.001;

    /**
     * These constants probably won't ever be changed
     */

    public static final int TIMEOUT = 20;

    public static final int SLOT_DISTANCE = 0;
    public static final int SLOT_VELOCITY = 1;
    public static final int SLOT_HEADING = 2;

    public static final int PID_PRIMARY = 0;
    public static final int PID_HEADING = 1;
}