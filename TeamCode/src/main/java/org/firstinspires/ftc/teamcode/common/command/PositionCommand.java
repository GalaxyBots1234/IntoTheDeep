package org.firstinspires.ftc.teamcode.common.command;

import org.firstinspires.ftc.teamcode.common.system.RobotHardware;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Config
public class PositionCommand extends CommandBase
{
    private final RobotHardware robot;

    public Pose2d targetPose;

    public static double xP = 0.0001;
    public static double xD = 0.00005;

    public static double yP = 0.0001;
    public static double yD = 0.00005;

    public static double hP = 0.0001;
    public static double hD = 0.00005;

    private static PIDFController xController = new PIDFController(xP, 0.0, xD, 0);
    private static PIDFController yController = new PIDFController(yP, 0.0, yD, 0);
    private static PIDFController hController = new PIDFController(hP, 0.0, hD, 0);

    public static double ALLOWED_TRANSLATIONAL_ERROR = 0.5;
    public static double ALLOWED_HEADING_ERROR = (Math.PI / 180) * 2.5;

    private ElapsedTime timer;
    private ElapsedTime stable;

    public static double STABLE_MS  = 100;
    public static double DEAD_MS    = 3000;

    private final double    MAX_TRANSLATIONAL_SPEED = 0.5;
    private final double    MAX_ROTATIONAL_SPEED = 0.4;
    private final double    X_GAIN = 2.00;

    // -------------------------------------------------------------------------------------------

    public PositionCommand(Pose2d targetPose)
    {
        robot = RobotHardware.instance;
        this.targetPose = targetPose;

        xController.reset();
        yController.reset();
        hController.reset();
    }

    public void initialize()
    {
        timer = new ElapsedTime();
        stable = new ElapsedTime();
    }

    @Override
    public void execute()
    {
        Pose2d robotPose = robot.odo.getPose();

        double[] p = getSpeeds(robotPose);
        robot.drive.driveRobotCentric(p[0], p[1], p[2]);
    }

    @Override
    public boolean isFinished()
    {
        Pose2d robotPose = robot.odo.getPose();
        Pose2d delta = subtract(robotPose, targetPose);
        Vector2d v = new Vector2d(delta.getX(), delta.getY());

        if (v.magnitude() > ALLOWED_TRANSLATIONAL_ERROR
                || Math.abs(delta.getHeading()) > ALLOWED_HEADING_ERROR) {
            stable.reset();
        }

        System.out.println("isFinished v = " + v.magnitude() + " "
                + " " + delta);

        return timer.milliseconds() > DEAD_MS || stable.milliseconds() > STABLE_MS;
    }

    private Pose2d subtract(Pose2d a, Pose2d b)
    {
        return new Pose2d(a.getX() - b.getX(),
                a.getY() - b.getY(),
                new Rotation2d(a.getHeading() - b.getHeading()));
    }

    // strafeSpeed, forwardSpeed, turnSpeed
    private double[] getSpeeds(Pose2d robotPose)
    {
        double rh = robotPose.getHeading();
        double th = targetPose.getHeading();

        while (th - rh > Math.PI)   {   th -= 2 * Math.PI;  }
        while (th - rh < -Math.PI)  {   th += 2 * Math.PI;  }

        xController.setPIDF(xP, 0, xD, 0);
        yController.setPIDF(yP, 0, yD, 0);
        hController.setPIDF(hP, 0, hD, 0);

        double xPower = xController.calculate(robotPose.getX(), targetPose.getX());
        double yPower = yController.calculate(robotPose.getY(), targetPose.getY());
        double hPower = hController.calculate(rh, th);

        double x_rotated = xPower * Math.cos(-rh) - yPower * Math.sin(-rh);
        double y_rotated = xPower * Math.sin(-rh) + yPower * Math.cos(-rh);

        // technically i dont think this is normalized correctly
        hPower = Range.clip(hPower, -MAX_ROTATIONAL_SPEED, MAX_ROTATIONAL_SPEED);
        x_rotated = Range.clip(x_rotated, -MAX_TRANSLATIONAL_SPEED / X_GAIN, MAX_TRANSLATIONAL_SPEED / X_GAIN);
        y_rotated = Range.clip(y_rotated, -MAX_TRANSLATIONAL_SPEED, MAX_TRANSLATIONAL_SPEED);

        // System.out.println("ypwoer " + robotPose.getY() + " " + targetPose.getY() + " " + yPower);
        // System.out.printf("hPower: %.3f, %.3f, %.3f", rh, th, hPower);

        return new double[] { -y_rotated, x_rotated, -hPower };
        // return new double[] { y_rotated, -x_rotated * X_GAIN, hPower };
    }

    @Override
    public void end(boolean interrupted) {
        robot.drive.driveRobotCentric(0, 0, 0);
    }
}
