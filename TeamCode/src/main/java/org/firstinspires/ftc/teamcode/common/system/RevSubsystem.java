package org.firstinspires.ftc.teamcode.common.system;

import static com.qualcomm.hardware.rev.RevHubOrientationOnRobot.xyzOrientation;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class RevSubsystem extends SubsystemBase
{
    private IMU imu;
    private Rev2mDistanceSensor height;

    // -------------------------------------------------------------------------------------------

    public RevSubsystem(final HardwareMap hMap)
    {
        imu = hMap.get(IMU.class, "imu");
        height = (Rev2mDistanceSensor)hMap.get(DistanceSensor.class, "distance");

        // https://ftc-docs.firstinspires.org/en/latest/programming_resources/imu/imu.html
        Orientation hubRotation = xyzOrientation(90, 0, 90);
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(hubRotation);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();
    }

    // -------------------------------------------------------------------------------------------

    public double getHeight() {
        return height.getDistance(DistanceUnit.INCH);
    }

    public double getPitch() {
        return imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);
    }
}