package org.firstinspires.ftc.teamcode.common.system;

import com.arcrobotics.ftclib.command.OdometrySubsystem;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotHardware
{
    public static RobotHardware instance;

    public  DriveSubsystem      drive;
    public  OdometrySubsystem   odo;
    private PinpointOdometry    gbOdo;
    public  ArmSubsystem        arm;
    public  ClawSubsystem       claw;

    // -------------------------------------------------------------------------------------------

    public RobotHardware(final HardwareMap hMap)
    {
        instance = this;

        drive   = new DriveSubsystem(hMap);
        arm     = new ArmSubsystem(hMap);
        claw    = new ClawSubsystem(hMap);

        gbOdo = new PinpointOdometry(hMap,
                new Pose2d(0, 0, new Rotation2d(0)));
        odo = new OdometrySubsystem(gbOdo);
    }

    // -------------------------------------------------------------------------------------------

    public void periodic() {
    }
}
