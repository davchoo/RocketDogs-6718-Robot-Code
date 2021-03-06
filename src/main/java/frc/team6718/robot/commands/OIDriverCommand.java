package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.OI;
import frc.team6718.robot.Robot;
import frc.team6718.robot.subsystems.ArmSubsystem;

public class OIDriverCommand extends Command {
    public OIDriverCommand() {
        super("OI Driver");
        requires(Robot.driveTrain);
        requires(Robot.arm);
        requires(Robot.gripper);
    }

    @Override
    protected void execute() {
        double speed = -Robot.oi.drive.getY();
        double xRot = -Robot.oi.drive.getX();

        if (Robot.oi.drive.getRawButton(OI.DISABLE_MOVEMENT)) {
            speed = 0;
        }

        if (Robot.oi.drive.getRawButton(OI.DISABLE_ROTATION)) {
            xRot = 0;
        }

        if (Robot.oi.drive.getTrigger()) {
            speed *= 0.5;
        }

        Robot.driveTrain.drive.arcadeDrive(speed, xRot);

        //Arm

        boolean leftTrigger = Robot.oi.arm.getTriggerAxis(GenericHID.Hand.kLeft) > 0.5;
        boolean rightTrigger = Robot.oi.arm.getTriggerAxis(GenericHID.Hand.kRight) > 0.5;

        double holdingSpeed = rightTrigger ? ArmSubsystem.HOLDING_SPEED : leftTrigger ? 0.21 : 0;

        double lowerMovement = Robot.oi.arm.getY(GenericHID.Hand.kLeft) * -0.75;
        double upperMovement = Robot.oi.arm.getY(GenericHID.Hand.kRight) * -0.75;

        lowerMovement = Math.copySign(Math.pow(lowerMovement, 2), lowerMovement);
        upperMovement = Math.copySign(Math.pow(upperMovement, 2), upperMovement);

        upperMovement += holdingSpeed;

        Robot.arm.setLowerArmAngle(lowerMovement);
        Robot.arm.setUpperArmAngle(upperMovement);

        //Gripper
        double gripperSpeed = 0;
        if (Robot.oi.arm.getBumper(GenericHID.Hand.kLeft)) {
            gripperSpeed = 1;
        }
        if (Robot.oi.arm.getBumper(GenericHID.Hand.kRight)) {
            gripperSpeed = -1;
        }
        Robot.gripper.set(gripperSpeed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
