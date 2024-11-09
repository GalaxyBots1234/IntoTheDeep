package org.firstinspires.ftc.teamcode.common.system;

import static com.qualcomm.hardware.rev.RevHubOrientationOnRobot.xyzOrientation;
import android.graphics.Color;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class RevSubsystem extends SubsystemBase
{
    private IMU                 imu;
    private Rev2mDistanceSensor height;
    private RevColorSensorV3    colorSensor;

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

        colorSensor = hMap.get(RevColorSensorV3.class, "sensor_color");
        colorSensor.setGain(12);
        // No effect
        // colorSensor.enableLed(false);
    }

    // -------------------------------------------------------------------------------------------

    public double getHeight() {
        return height.getDistance(DistanceUnit.INCH);
    }

    public double getSampleDistance() {
        return colorSensor.getDistance(DistanceUnit.INCH);
    }

    public double getPitch() {
        return imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);
    }

    public FTCSample detectSample()
    {
        double dist = colorSensor.getDistance(DistanceUnit.INCH);

        if (dist > 1.75) {
            return FTCSample.NONE;
        }

        final float[] hsv = new float[3];
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsv);

        if (hsv[0] > 200 && hsv[0] < 240) {
            return FTCSample.BLUE;
        } else if (hsv[0] > 60 && hsv[0] < 90) {
            return FTCSample.YELLOW;
        } else if (hsv[0] > 10 && hsv[0] < 40) {
            return FTCSample.RED;
        }

        return FTCSample.UNKNOWN;
    }
}