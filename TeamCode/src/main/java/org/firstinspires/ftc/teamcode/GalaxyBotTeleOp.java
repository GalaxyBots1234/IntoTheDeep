package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name="GalaxyBot-TeleOp", group="Opmode")
public class GalaxyBotTeleOp extends OpMode {
    private GalaxyBot robot;
    String direction = "";

    private Gamepad currentGamepad1 = new Gamepad();
    private Gamepad previousGamepad1 = new Gamepad();

    private Gamepad currentGamepad2 = new Gamepad();
    private Gamepad previousGamepad2 = new Gamepad();

    private IMU imu;

    private boolean fieldCentric = false;

    private void mecanumDrive(double botHeading) {
        float LIMIT_SPEED = 0.8f;
        float y = -gamepad1.left_stick_y;
        float x = gamepad1.left_stick_x * 1.1f;
        float rx = gamepad1.right_stick_x;
        if(gamepad1.dpad_left) {

            x = -1;
        }
        if(gamepad1.dpad_right) {
            x = 1;
        }
        if(gamepad1.dpad_up) {
            y = 1;
        }
        if(gamepad1.dpad_down) {
            y = -1;
        }

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        double frontLeftPower = (y + x + rx) / denominator;

        double backLeftPower = (y - x + rx) / denominator;

        double frontRightPower = (y - x - rx) / denominator;

        double backRightPower = (y + x - rx) / denominator;

        robot.drive(frontRightPower * LIMIT_SPEED, frontLeftPower * LIMIT_SPEED,
                backRightPower * LIMIT_SPEED, backLeftPower * LIMIT_SPEED);
        telemetry.addData("leftFrontPower", frontLeftPower);
        telemetry.addData("leftBackPower", backLeftPower);
        telemetry.addData("rightFrontPower", frontRightPower);
        telemetry.addData("rightBackPower", backRightPower);
        telemetry.addData("y", y);
        telemetry.addData("x", x);
        telemetry.addData("rx", rx);
        telemetry.addData("heading", botHeading);
    }

    private void copyGamepad() throws RobotCoreException {
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);

        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }

    @Override
    public void init() {
        robot = new GalaxyBot(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters myIMUparameters;
        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(myIMUparameters);
    }
    private void pushyPush()
    {
        if(gamepad2.dpad_right)
        {
            robot.intakePushForward(0.6);
            direction = "Moving forward";
        }
        else if (gamepad2.dpad_left)
        {
            robot.intakePushBack(0.6);
            direction = "Moving back";
        }
        else
        {
           robot.intakePushBack(0.00);
           direction = "Not moving";
        }
    }


    public void start()
    {
    }
    @Override
    public void loop()
    {
        try {
            copyGamepad();
        } catch (RobotCoreException e) {
            e.printStackTrace();
        }
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        mecanumDrive(botHeading);
        pushyPush();
        telemetry.addData("direction: ",  direction);
        telemetry.update();
    }
}