package frc.team6718.robot;


public class RobotMap {
    //Drive Train spark motor controllers (PWM pins)
    public static final int DRIVE_TRAIN_LEFT_A = 0;
    public static final int DRIVE_TRAIN_LEFT_B = 1;
    public static final int DRIVE_TRAIN_RIGHT_A = 2;
    public static final int DRIVE_TRAIN_RIGHT_B = 3;

    //Drive Train encoders (DIO pins)
    public static final int DRIVE_TRAIN_ENCODER_LEFT_A = 0;
    public static final int DRIVE_TRAIN_ENCODER_LEFT_B = 1;
    public static final int DRIVE_TRAIN_ENCODER_RIGHT_A = 2;
    public static final int DRIVE_TRAIN_ENCODER_RIGHT_B = 3;

    //Upper Arm motor (PWM pins)
    public static final int ARM_UPPER_MOTOR = 4;
    public static final int ARM_UPPER_MOTOR_2 = 5;

    //Lower Arm motor (Talon device ID)
    public static final int ARM_LOWER_MOTOR = 10;

    //Gripper motor (PWM pins)
    public static final int GRIPPER_MOTOR = 6;
}
