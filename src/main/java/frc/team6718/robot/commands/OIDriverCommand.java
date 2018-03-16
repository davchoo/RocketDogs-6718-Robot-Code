package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.OI;
import frc.team6718.robot.Robot;

public class OIDriverCommand extends Command {
    public OIDriverCommand() {
        super("OI Driver");
        requires(Robot.driveTrain);
        requires(Robot.arm);
        requires(Robot.gripper);
    }

    @Override
    protected void execute() {
        double speed = Robot.oi.drive.getY();
        double xRot = Robot.oi.drive.getX();
        Robot.driveTrain.drive.arcadeDrive(speed, xRot);

        //Arm
        double lowerMovement = Robot.oi.arm.getY(GenericHID.Hand.kLeft);
        double upperMovement = Robot.oi.arm.getY(GenericHID.Hand.kRight);

        Robot.arm.setLowerArmAngle(lowerMovement);
        Robot.arm.setUpperArmAngle(upperMovement);

        //Gripper
        if (Robot.oi.arm.getBumper(GenericHID.Hand.kLeft)) {
            Robot.gripper.set(-1);
        } else if (Robot.oi.arm.getBumper(GenericHID.Hand.kRight)) {
            Robot.gripper.set(1);
        }else{
            Robot.gripper.set(0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
