package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.Robot;

//TODO control the arm
public class OIDriverCommand extends Command {
    public OIDriverCommand() {
        super("OI Driver Command");
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
        double armJoystick = Robot.oi.arm.getY();
        boolean armTrigger = Robot.oi.arm.getTriggerPressed();

        double lowerMovement = armTrigger ? 0 : armJoystick;
        double upperMovement = armTrigger ? armJoystick : 0;

        Robot.arm.setLowerArmAngle(lowerMovement);
        Robot.arm.setUpperArmAngle(upperMovement);

        //Gripper
        if (Robot.oi.arm.getRawButton(4)) {
            Robot.gripper.set(1);
        } else if (Robot.oi.arm.getRawButton(5)) {
            Robot.gripper.set(-1);
        }else{
            Robot.gripper.set(0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
