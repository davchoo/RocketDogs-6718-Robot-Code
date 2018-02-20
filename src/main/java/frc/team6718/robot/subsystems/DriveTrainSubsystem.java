package frc.team6718.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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

        leftA = new Spark(RobotMap.DRIVE_TRAIN_LEFT_A);
        leftB = new Spark(RobotMap.DRIVE_TRAIN_LEFT_B);

        rightA = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_A);
        rightB = new Spark(RobotMap.DRIVE_TRAIN_RIGHT_B);

        left = new SpeedControllerGroup(leftA, leftB);
        right = new SpeedControllerGroup(rightA, rightB);

        //TODO check if one of the encoders need to be reversed
        leftEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_LEFT_A, RobotMap.DRIVE_TRAIN_ENCODER_LEFT_B, false);
        rightEncoder = new Encoder(RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_A, RobotMap.DRIVE_TRAIN_ENCODER_RIGHT_B, false);

        //We have 6in diameter wheels
        leftEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);
        rightEncoder.setDistancePerPulse(Math.PI * 6d / 1024d);

        drive = new DifferentialDrive(left, right);

        //Add to shuffleboard
        drive.setSubsystem("Drive Train");

        leftEncoder.setName("Left Encoder");
        leftEncoder.setSubsystem("Drive Train");

        rightEncoder.setName("Right Encoder");
        rightEncoder.setSubsystem("Drive Train");
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StopDriveTrainCommand());
    }
}
