package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem extends SubsystemBase
{
    private MotorEx fL, fR, bL, bR;
    public  MecanumDrive drive;

    // -------------------------------------------------------------------------------------------

    public DriveSubsystem(final HardwareMap hMap)
    {
        fL = new MotorEx(hMap, "leftFrontMotor", Motor.GoBILDA.RPM_312);
        fR = new MotorEx(hMap, "rightFrontMotor", Motor.GoBILDA.RPM_312);
        bL = new MotorEx(hMap, "leftBackMotor", Motor.GoBILDA.RPM_312);
        bR = new MotorEx(hMap, "rightBackMotor", Motor.GoBILDA.RPM_312);

        // create our drive object
        drive = new MecanumDrive(fL, fR, bL, bR);
    }

    // -------------------------------------------------------------------------------------------

    @Override
    public void periodic() {

    }
}
