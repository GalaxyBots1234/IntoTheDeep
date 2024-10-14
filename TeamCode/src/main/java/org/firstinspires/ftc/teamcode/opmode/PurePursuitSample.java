package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.MecanumControllerCommand;
import com.arcrobotics.ftclib.command.OdometrySubsystem;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveKinematics;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveWheelSpeeds;
import com.arcrobotics.ftclib.trajectory.Trajectory;
import com.arcrobotics.ftclib.trajectory.TrajectoryConfig;
import com.arcrobotics.ftclib.trajectory.TrajectoryGenerator;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.common.system.PinpointOdometry;

import java.util.Locale;
import java.util.function.Consumer;

final class AutoConstants
{
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 2;
    public static final double kPYController = 0.5;
    public static final double kPThetaController = 0.5;

    // Constraint for the motion profilied robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                    kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
}

final class DriveConstants
{
    public static final double kTrackWidth = 0.42;  // meters
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = 0.35;   // meters
    // Distance between centers of front and back wheels on robot

    public static final MecanumDriveKinematics kDriveKinematics =
            new MecanumDriveKinematics(
                    new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                    new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                    new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
                    new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    /*public static final int kEncoderCPR = 1024;
    public static final double kWheelDiameterMeters = 0.15;
    public static final double kEncoderDistancePerPulse =
            // Assumes the encoders are directly mounted on the wheel shafts
            (kWheelDiameterMeters * Math.PI) / kEncoderCPR;*/

    // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
    // These characterization values MUST be determined either experimentally or theoretically
    // for *your* robot's drive.
    // The SysId tool provides a convenient method for obtaining these values for your robot.
    public static final SimpleMotorFeedforward kFeedforward =
            new SimpleMotorFeedforward(1, 0.8, 0.15);

    // Example value only - as above, this must be tuned for your drive!
    public static final double kPFrontLeftVel = 0.5;
    public static final double kPRearLeftVel = 0.5;
    public static final double kPFrontRightVel = 0.5;
    public static final double kPRearRightVel = 0.5;
}

@Autonomous
public class PurePursuitSample extends CommandOpMode
{
    private PinpointOdometry m_robotOdometry;
    private OdometrySubsystem m_odometry;
    private Command ppCommand;
    private MecanumDrive m_robotDrive;
    private Motor fL, fR, bL, bR;

    private GamepadEx gp;

    @Override
    public void initialize()
    {
        fL = new Motor(hardwareMap, "leftFrontMotor", Motor.GoBILDA.RPM_312);
        fR = new Motor(hardwareMap, "rightFrontMotor", Motor.GoBILDA.RPM_312);
        bL = new Motor(hardwareMap, "leftBackMotor", Motor.GoBILDA.RPM_312);
        bR = new Motor(hardwareMap, "rightBackMotor", Motor.GoBILDA.RPM_312);

        // create our drive object
        m_robotDrive = new MecanumDrive(fL, fR, bL, bR);

        // create our odometry object and subsystem
        m_robotOdometry = new PinpointOdometry(hardwareMap,
                new Pose2d(0, 0, new Rotation2d(0)));
        m_odometry = new OdometrySubsystem(m_robotOdometry);

        gp = new GamepadEx(gamepad1);

        // create our pure pursuit command
        /*ppCommand = new PurePursuitCommand(
                m_robotDrive, m_odometry,
                new StartWaypoint(0, 0),
                new GeneralWaypoint(20, 0, 0.4, 0.4, 3),
                new EndWaypoint(
                        40, 0, 0, 0.2,
                        0.2, 30, 0.8, 1
                )
        );*/

        ppCommand = this.getAutonomousCommand();

        // schedule the command
        // schedule(ppCommand);
    }

    @Override
    public void run()
    {
        super.run();

        m_robotDrive.driveRobotCentric(-gp.getLeftX(), -gp.getLeftY(), -gp.getRightX());
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", -gp.getLeftX(), -gp.getLeftY(), -gp.getRightX());
        telemetry.addData("joystick: ", data);
        // set(new Pose(gamepad1.left_stick_x, -gamepad1.left_stick_y, MathUtils.joystickScalar(-gamepad1.left_trigger + gamepad1.right_trigger, 0.01)), 0);

        // Pose2d pos = m_robotOdometry.getPose();
        Pose2d pos = m_odometry.getPose();
        data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(), pos.getY(), pos.getHeading());
        telemetry.addData("Position", data);

        Pose2D pos1 = m_robotOdometry.odo.getPosition();
        data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos1.getX(DistanceUnit.INCH), pos1.getY(DistanceUnit.INCH), pos1.getHeading(AngleUnit.DEGREES));
        telemetry.addData("direct: ", data);

        telemetry.addData("trajectory: ", ppCommand.isScheduled() + "  " + ppCommand.isFinished());

        telemetry.update();
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // Create config for trajectory
        TrajectoryConfig config =
                new TrajectoryConfig(
                        AutoConstants.kMaxSpeedMetersPerSecond,
                        AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(DriveConstants.kDriveKinematics);
                        //.setStartVelocity(0.1);

        // An example trajectory to follow. All units in meters.
        Trajectory et =
                TrajectoryGenerator.generateTrajectory(
                        // Start at the origin facing the +X direction
                        new Pose2d(),
                        // Pass through these two interior waypoints, making an 's' curve path
                        List.of(new Translation2d(-0.04, 0.04), new Translation2d(-0.05, 0.05)),
                        // End 1 meters straight ahead of where we started, facing forward
                        new Pose2d(-0.1, 0, new Rotation2d()),
                        config);

        MecanumControllerCommand mcc = new MecanumControllerCommand(et,
                m_odometry::getPose, DriveConstants.kDriveKinematics,
                new PIDController(AutoConstants.kPXController, 0, 0),
                new PIDController(AutoConstants.kPYController, 0, 0),
                new ProfiledPIDController(
                        AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints),
                AutoConstants.kMaxSpeedMetersPerSecond,
                new Consumer<MecanumDriveWheelSpeeds>() {
                    @Override
                    public void accept(MecanumDriveWheelSpeeds mdws) {
                        m_robotDrive.driveWithMotorPowers(mdws.frontLeftMetersPerSecond,
                                mdws.frontRightMetersPerSecond, mdws.rearLeftMetersPerSecond,
                                mdws.rearRightMetersPerSecond);

                        System.out.println(" " + mdws.frontLeftMetersPerSecond +
                                " " + mdws.frontRightMetersPerSecond +
                                " " + mdws.rearLeftMetersPerSecond +
                                " " + mdws.rearRightMetersPerSecond);

                        // m_robotDrive.driveWithMotorPowers(0.1,0.1,0.1,0.1);
                    }
                }
                );

        /*MecanumControllerCommand mcc =
                new MecanumControllerCommand(
                        et,
                        m_odometry::getPose,
                        DriveConstants.kFeedforward,
                        DriveConstants.kDriveKinematics,

                        // Position controllers
                        new PIDController(AutoConstants.kPXController, 0, 0),
                        new PIDController(AutoConstants.kPYController, 0, 0),
                        new ProfiledPIDController(
                                AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints),

                        // Needed for normalizing wheel speeds
                        AutoConstants.kMaxSpeedMetersPerSecond,

                        // Velocity PID's
                        new PIDController(DriveConstants.kPFrontLeftVel, 0, 0),
                        new PIDController(DriveConstants.kPRearLeftVel, 0, 0),
                        new PIDController(DriveConstants.kPFrontRightVel, 0, 0),
                        new PIDController(DriveConstants.kPRearRightVel, 0, 0),

                        new Supplier<MecanumDriveWheelSpeeds> () {
                            @Override
                            public MecanumDriveWheelSpeeds get() {
                                return new MecanumDriveWheelSpeeds(
                                        m_robotDrive.motors[0].);
                            }
                        },
                        new Consumer<MecanumDriveWheelSpeeds>() {
                            @Override
                            public void accept(MecanumDriveWheelSpeeds mdws) {
                                m_robotDrive.driveWithMotorPowers(mdws.frontLeftMetersPerSecond,
                                    mdws.frontRightMetersPerSecond, mdws.rearLeftMetersPerSecond,
                                    mdws.rearRightMetersPerSecond);
                            }
                        },
                        // m_robotDrive::getCurrentWheelSpeeds,
                        // m_robotDrive::setDriveMotorControllersVolts, // Consumer for the output motor voltages
                        m_robotDrive);

        //Supplier<MecanumDriveWheelSpeeds> currentWheelSpeeds,
        //Consumer<MecanumDriveMotorVoltages> outputDriveVoltages*/

        // Reset odometry to the initial pose of the trajectory, run path following
        // command, then stop at the end.
        return mcc;
        /*return new SequentialCommandGroup(
                new InstantCommand(() -> m_robotOdometry.updatePose(et.getInitialPose())),
                mcc,
                new InstantCommand(() -> m_robotDrive.stop()));*/
    }
}
