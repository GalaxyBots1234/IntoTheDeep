package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name="GalaxyBot-TeleOp", group="Opmode")
@Config
public class GalaxyBotTeleOp extends OpMode
{
    private GalaxyBot robot;
    String pushDirection = "";
    String slideDirection = "";
    String spinnerDirection = "";
    String swingDirection = "";
    String clawSpinnerDirection = "";
    String clawState = "";
    String intakeClawState = "";
    String rotaterDir = "";

    private Gamepad currentGamepad1 = new Gamepad();
    private Gamepad previousGamepad1 = new Gamepad();

    private Gamepad currentGamepad2 = new Gamepad();
    private Gamepad previousGamepad2 = new Gamepad();


    private IMU imu;

    private boolean fieldCentric = false;

    private void mecanumDrive(double botHeading)
    {
        float LIMIT_SPEED = 1.0f;
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

        double frontLeftPower = (y + x + rx) / denominator * robot.driveDir;

        double backLeftPower = (y - x + rx) / denominator * robot.driveDir;

        double frontRightPower = (y - x - rx) / denominator * robot.driveDir;

        double backRightPower = (y + x - rx) / denominator * robot.driveDir;

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

    private void copyGamepad() throws RobotCoreException
    {
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);

        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }

    @Override
    public void init()
    {
        robot = new GalaxyBot(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters myIMUparameters;
        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(myIMUparameters);
    }

    private void pushyPush()
    {
        if (currentGamepad2.cross && !previousGamepad2.cross)
        {
            if (robot.intakeExtend) {
                robot.intakeDetract();
                robot.doUnTwist();
                robot.spinUp();
                pushDirection = "extended";
            }
            else {
                robot.intakeExtend();

                robot.intakeClawOpen();
                robot.spinDown();
                pushDirection = "detracted";
            }
        }
    }
    private void pushyPushPrecise()
    {
        if (currentGamepad2.left_bumper && previousGamepad2.left_bumper)
        {
            robot.intakePreciseDetract(0.02f);
        }
        if (currentGamepad2.right_bumper && previousGamepad2.right_bumper)
        {
            robot.intakePreciseExtend(0.02f);
        }
    }
//
//    private void swingySwing()
//    {
//        if(currentGamepad2.square && !previousGamepad2.square)
//        {
//            robot.swing();
//            if (robot.swingUp)
//                swingDirection = "Swinged up";
//            else
//                swingDirection = "Swinged down";
//        }
//    }

    private void slimSlidy()
    {
        if (currentGamepad2.dpad_up && !previousGamepad2.dpad_up) {
            if (robot.slideUp) {
                robot.slideDown();

                robot.doSwingDown();
                robot.doClawOpen();
                slideDirection = "Slides going up";
            }
            else {
                robot.slideUp();

                robot.doSwingUp();
                slideDirection = "Slides are down";
            }
        }
//
//        if (currentGamepad2.dpad_up && previousGamepad2.dpad_up)
//        {
//            robot.su();
//        }
//        else if (currentGamepad2.dpad_down && previousGamepad2.dpad_down)
//        {
//            robot.sd();
//        }
//        else
//        {
//            robot.ss();
//        }
    }

    private void clawyClaw() throws InterruptedException {
        if (currentGamepad2.dpad_down && !previousGamepad2.dpad_down)
        {
            if (robot.clawOpen) {
                robot.doClawClose();

                sleep(50);
                robot.intakeClawOpen();
                clawState = "Claw is closed";

                if (robot.slideUp)
                    gamepad2.rumble(250);
            }
            else {
                robot.doClawOpen();
                clawState = "Claw is open";
            }
        }
    }

    private void intakeClawyClaw()
    {
        if (currentGamepad2.triangle && !previousGamepad2.triangle)
        {
            if (robot.intakeClawOpen) {
//                if (robot.intakeExtend)
//                {
//                    if (!robot.redDetected)
//                    {
//                        robot.intakeClawClose();
//                    }
//                    else
//                    {
//                        gamepad2.rumble(1000);
//                    }
//                }
//                else
//                {
//                    robot.intakeClawClose();
//                }
                robot.intakeClawClose();
                intakeClawState = "Claw is closed";
            }
            else {
                robot.intakeClawOpen();
                intakeClawState = "Claw is open";
            }
        }
    }
//
    private void spinnySpin()
    {
        if (currentGamepad2.circle && !previousGamepad2.circle) {
            robot.spin();
            if (robot.spinUp)
                spinnerDirection = "Spinned up";
            else
                spinnerDirection = "Spinned down";
        }
    }

    public void twistyTwist() {
        if(currentGamepad2.touchpad && !previousGamepad2.touchpad) {
            if (robot.twisted) {
                robot.doUnTwist();
            }
            else {
                robot.doTwist();
            }
        }
    }

    public void start()
    {
        robot.clawSpinSample();
        robot.doClawOpen();
        robot.intakeClawOpen();
        robot.doSwingDown();
        robot.spinUp();
        robot.intakeDetract();
        robot.doUnTwist();
    }

@Override
public void loop()
{
    try {
        copyGamepad();
    } catch (RobotCoreException e) {
        e.printStackTrace();
    }
    if (currentGamepad1.touchpad && !currentGamepad1.touchpad)
        robot.driveDir *= -1;
    double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    robot.updateSense();
    mecanumDrive(botHeading);
//
       pushyPush();
       pushyPushPrecise();
       spinnySpin();
       twistyTwist();
//
//
      // swingySwing();
      slimSlidy();
//
    try {
        clawyClaw();
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    intakeClawyClaw();
        // clawSpinnySpin();

    telemetry.addData("push extended: ", robot.intakeExtend);
    telemetry.addData("intake spun up: ", robot.spinUp);
    telemetry.addData("intake claw open: ", robot.intakeClawOpen);
    telemetry.addData("swing is up: ", robot.swingUp);
    telemetry.addData("outtake claw open: ", robot.clawOpen);
    telemetry.addData("slides up: ", robot.slideUp);
    telemetry.addData("tick left: ", robot.leftSlide.getCurrentPosition());
    telemetry.addData("tick right: ", robot.rightSlide.getCurrentPosition());
    telemetry.addData("rotated: ", robot.twisted);


    telemetry.addData("detected red: ", robot.redDetected);
    telemetry.addData("detected blue: ", robot.blueDetected);
    telemetry.addData("detected yellow: ", robot.yellowDetected);
    telemetry.update();
}
}