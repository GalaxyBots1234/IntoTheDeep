package org.firstinspires.ftc.teamcode.common.system;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

// @Config
public class DriveSubsystem extends SubsystemBase
{
    private static final double MAX_POWER   = 0.5;
    private static final double motorP      = 0.5;

    private MotorEx fL, fR, bL, bR;
    private MecanumDrive drive;

    // -------------------------------------------------------------------------------------------

    public DriveSubsystem(final HardwareMap hMap)
    {
        fL = new MotorEx(hMap, "leftFrontMotor", Motor.GoBILDA.RPM_312);
        fR = new MotorEx(hMap, "rightFrontMotor", Motor.GoBILDA.RPM_312);
        bL = new MotorEx(hMap, "leftBackMotor", Motor.GoBILDA.RPM_312);
        bR = new MotorEx(hMap, "rightBackMotor", Motor.GoBILDA.RPM_312);

        fL.setInverted(true);
        bL.setInverted(true);

        for (MotorEx m: new MotorEx[] { fL, fR, bL, bR })
        {
            m.setRunMode(Motor.RunMode.VelocityControl);
            m.setVeloCoefficients(motorP, 0.0, 0.0);

            // This is too jerky for TeleOp. Test for Auto.
            // m.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        }

        // create our drive object
        drive = new MecanumDrive(false, fL, fR, bL, bR);
        drive.setMaxSpeed(MAX_POWER);
    }

    // -------------------------------------------------------------------------------------------

    @Override
    public void periodic() {

    }

    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed)
    {
        // Use this for tuning
        // for (MotorEx m: new MotorEx[] { fL, fR, bL, bR }) {
        //    m.setVeloCoefficients(motorP, 0.0, 0.0);
        // }

        drive.driveRobotCentric(strafeSpeed * fL.ACHIEVABLE_MAX_TICKS_PER_SECOND,
                forwardSpeed * fL.ACHIEVABLE_MAX_TICKS_PER_SECOND,
                turnSpeed * fL.ACHIEVABLE_MAX_TICKS_PER_SECOND);
    }
}
