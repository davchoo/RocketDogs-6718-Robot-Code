package frc.team6718.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team6718.robot.commands.OIDriverCommand;
import frc.team6718.robot.subsystems.ArmSubsystem;
import frc.team6718.robot.subsystems.DriveTrainSubsystem;
import frc.team6718.robot.subsystems.GripperSubsystem;
import frc.team6718.robot.subsystems.GyroScopeSubsystem;

public class Robot extends TimedRobot {
    public static OI oi;

    public static DriveTrainSubsystem driveTrain;
    public static ArmSubsystem arm;
    public static GripperSubsystem gripper;
    public static GyroScopeSubsystem gyroscope;
    public static UsbCamera camera;

    private Command autonomousCommand;

    @Override
    public void robotInit() {
        gyroscope = new GyroScopeSubsystem();
        driveTrain = new DriveTrainSubsystem();
        arm = new ArmSubsystem();
        gripper = new GripperSubsystem();
        oi = new OI();
        //Create and start capturing video from the camera
        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 320, 240, 15);
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
        driveTrain.drive.stopMotor();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard.
     *
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        //autonomousCommand = chooser.getSelected();

        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        autonomousCommand = null; //TODO add auto command
        if (autonomousCommand != null) {
            Scheduler.getInstance().add(autonomousCommand);
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            if (!autonomousCommand.isCompleted()) {
                System.out.println("Autonomous didn't finish! Cancelling...");
            }
            autonomousCommand.cancel();
        }
        Scheduler.getInstance().add(new OIDriverCommand());
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {

    }
}