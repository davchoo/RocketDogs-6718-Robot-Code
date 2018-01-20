package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team6718.robot.RobotMap;
import frc.team6718.robot.commands.StopDriveTrainCommand;

public class DriveTrainSubsystem extends Subsystem {
    private Spark leftA, leftB;
    private Spark rightA, rightB;
    private SpeedControllerGroup left, right;

    public DifferentialDrive drive;

    public Encoder leftEncoder, rightEncoder;

    public DriveTrainSubsystem() {
        super("Drive Train");

        //TODO check if one of the motors need to be reversed
        leftA = new Spark(RobotMap.DRIVE_TRAIN_LEFT_A);
        leftB = new Spark(RobotMap.DRIVE_TRAIN_LEFT_B);

        rightA = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_A);
        rightB = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_B);

        //TODO check if one of the sides need to be reversed
        left = new SpeedControllerGroup(leftA, leftB);
        right = new SpeedControllerGroup(rightA, rightB);

        //TODO check if one of the encoders need to be reversed
        leftEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_LEFT_A, RobotMap.DRIVE_TRAIN_ENCODER_LEFT_B, false);
        rightEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_A, RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_B, false);

        //We have 6in diameter wheels
        //TODO check if encoder has 1024 pulses
        leftEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);
        rightEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);

        drive = new DifferentialDrive(left, right);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StopDriveTrainCommand());
    }
}
