package frc.team6718.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team6718.robot.commands.OIDriverCommand;
import frc.team6718.robot.commands.auto.LLAutoCommandGroup;
import frc.team6718.robot.commands.auto.RRAutoCommandGroup;
import frc.team6718.robot.subsystems.DriveTrainSubsystem;
import frc.team6718.robot.subsystems.GyroScopeSubsystem;

import static openrio.powerup.MatchData.*;


public class Robot extends TimedRobot {
    public static OI oi;
    public static DriveTrainSubsystem driveTrain;
    public static GyroScopeSubsystem gyroscope;
    public static UsbCamera camera;

    private Command autonomousCommand;
    private boolean hasRunAutonomousCommand = false;

    private OwnedSide switchNear = OwnedSide.UNKNOWN;
    private OwnedSide scale = OwnedSide.UNKNOWN;
    private OwnedSide switchFar = OwnedSide.UNKNOWN;

    @Override
    public void robotInit() {
        gyroscope = new GyroScopeSubsystem();
        driveTrain = new DriveTrainSubsystem();
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
        driveTrain.disable();
    }

    private void checkSides() {
        if (switchNear == OwnedSide.UNKNOWN || scale == OwnedSide.UNKNOWN || switchFar == OwnedSide.UNKNOWN) {
            switchNear = getOwnedSide(GameFeature.SWITCH_NEAR);
            scale = getOwnedSide(GameFeature.SCALE);
            switchFar = getOwnedSide(GameFeature.SWITCH_FAR);
        }else{
            if (switchNear == OwnedSide.LEFT) {
                autonomousCommand = new LLAutoCommandGroup();
            }else if(switchNear == OwnedSide.RIGHT){
                autonomousCommand = new RRAutoCommandGroup();
            }
            if (autonomousCommand != null && !hasRunAutonomousCommand) {
                hasRunAutonomousCommand = true;
                Scheduler.getInstance().add(autonomousCommand);
            }
        }
    }

    @Override
    public void disabledPeriodic() {
        checkSides();
    }


    @Override
    public void autonomousInit() {
        checkSides();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        checkSides();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        hasRunAutonomousCommand = true;
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
        driveTrain.test();
    }
}

