package frc.team6718.robot.commands;

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
        double armJoystick = Robot.oi.arm.getY();
        boolean armTrigger = Robot.oi.arm.getTrigger(); //TODO Pressed?

        double lowerMovement = armTrigger ? 0 : armJoystick;
        double upperMovement = armTrigger ? armJoystick : 0;

        Robot.arm.setLowerArmAngle(lowerMovement);
        Robot.arm.setUpperArmAngle(upperMovement);

        //Gripper
        if (Robot.oi.arm.getRawButton(OI.CLOSE_GRIPPER)) {
            Robot.gripper.set(-1);
        } else if (Robot.oi.arm.getRawButton(OI.OPEN_GRIPPER)) {
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
