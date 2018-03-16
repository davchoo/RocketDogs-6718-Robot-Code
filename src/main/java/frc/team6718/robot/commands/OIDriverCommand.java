package frc.team6718.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team6718.robot.OI;
import frc.team6718.robot.Robot;

/**
 * Allows the Operator to move the robot with a joystick
 */
public class OIDriverCommand extends Command {
    public OIDriverCommand() {
        super("OI Driver");
        requires(Robot.driveTrain);
        requires(Robot.arm);
        requires(Robot.gripper);
    }

    @Override
    protected void execute() { //TODO check if we should square xRot and if its sensitive enough
        double speed = Math.pow(Robot.oi.drive.getY(), 2);
        double xRot = Robot.oi.drive.getX();

        if (Robot.oi.drive.getRawButton(OI.DISABLE_MOVEMENT)) {
            speed = 0;
        }

        if (Robot.oi.drive.getRawButton(OI.DISABLE_ROTATION)) {
            xRot = 0;
        }

        Robot.driveTrain.setTargetSpeeds(speed, speed);
        Robot.driveTrain.rotateTargetHeading(xRot);

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
