package frc.team6718.robot.subsystem;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team6718.robot.RobotMap;
import frc.team6718.robot.commands.StopDriveTrainCommand;

public class DriveTrainSubsystem extends Subsystem {
    private Spark leftA, leftB;
    private Spark rightA, rightB;

    public DriveTrainSubsystem() {
        setName("Drive Train");
        leftA = new Spark(RobotMap.driveTrainLeftA);
        leftB = new Spark(RobotMap.driveTrainLeftB);
        rightA = new Spark(RobotMap.driveTrainRightA);
        rightB = new Spark(RobotMap.driveTrainRightB);
        //TODO add encoder stuff
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StopDriveTrainCommand());
    }

    public void stopAll() {
        leftA.stopMotor();
        leftB.stopMotor();

        rightA.stopMotor();
        rightB.stopMotor();
    }

    public void setLeftSpeed(double leftSpeed) {
        leftA.set(leftSpeed);
        leftB.set(leftSpeed);
    }

    public void setRightSpeed(double rightSpeed) {
        rightA.set(rightSpeed);
        rightB.set(rightSpeed);
    }
}
