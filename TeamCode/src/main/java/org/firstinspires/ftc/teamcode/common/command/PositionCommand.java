package org.firstinspires.ftc.teamcode.common.command;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.pathing.geometry.Pose;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware2;
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
    private RobotHardware2 robot;

    public Pose2d targetPose;

    public static double xP = 0.095;
    public static double xD = 0.011;

    public static double yP = 0.09;
    public static double yD = 0.011;

    public static double hP = 1.1;
    public static double hD = 0.045;

    public static PIDFController xController = new PIDFController(xP, 0.0, xD, 0);
    public static PIDFController yController = new PIDFController(yP, 0.0, yD, 0);
    public static PIDFController hController = new PIDFController(hP, 0.0, hD, 0);

    public static double ALLOWED_TRANSLATIONAL_ERROR = 1;
    public static double ALLOWED_HEADING_ERROR = 0.02;

    private ElapsedTime timer;
    private ElapsedTime stable;

    public static double STABLE_MS  = 100;
    public static double DEAD_MS    = 2500;

    private final double    MAX_TRANSLATIONAL_SPEED = 0.5;
    private final double    MAX_ROTATIONAL_SPEED = 0.4;
    private final double    X_GAIN = 2.00;

    // -------------------------------------------------------------------------------------------

    public PositionCommand(Pose2d targetPose)
    {
        this.targetPose = targetPose;

        xController.reset();
        yController.reset();
        hController.reset();
    }

    @Override
    public void execute()
    {
        if (timer == null)  timer = new ElapsedTime();
        if (stable == null) stable = new ElapsedTime();

        Pose2d robotPose = robot.odo.getPose();
        // System.out.println("TARGET POSE " + targetPose);

        double[] p = getSpeeds(robotPose);
        robot.drive.drive.driveRobotCentric(p[0], p[1], p[2]);
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

        double xPower = xController.calculate(robotPose.getX(), targetPose.getX());
        double yPower = yController.calculate(robotPose.getY(), targetPose.getY());
        double hPower = -hController.calculate(rh, th);

        double x_rotated = xPower * Math.cos(-rh) - yPower * Math.sin(-rh);
        double y_rotated = xPower * Math.sin(-rh) + yPower * Math.cos(-rh);

        // technically i dont think this is normalized correctly
        hPower = Range.clip(hPower, -MAX_ROTATIONAL_SPEED, MAX_ROTATIONAL_SPEED);
        x_rotated = Range.clip(x_rotated, -MAX_TRANSLATIONAL_SPEED / X_GAIN, MAX_TRANSLATIONAL_SPEED / X_GAIN);
        y_rotated = Range.clip(y_rotated, -MAX_TRANSLATIONAL_SPEED, MAX_TRANSLATIONAL_SPEED);

        return new double[] { x_rotated * X_GAIN, y_rotated, hPower };
    }

    @Override
    public void end(boolean interrupted)
    {
        robot.drive.drive.driveRobotCentric(0, 0, 0);
    }
}
