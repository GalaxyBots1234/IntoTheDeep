package org.firstinspires.ftc.teamcode;

// RR-specific imports
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;

// Non-RR imports
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name = "Galaxy_Red_Specimen", group = "Autonomous")
public class GalaxyRedSpecimen extends LinearOpMode {
    private GalaxyBot bot;
    private MecanumDrive drive;
    public static double y1 = 35;
    public static double y2 = 31.9;
    public static double x = 10;
    public static int slideUpPos = 2350;
    public static int slideDownPos = 50;
    public static double outtakeSpinPos = 0.05;
    public static int itrSlides = 1;
    public static double itrDrive = 0.01;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Pose2d startPose = new Pose2d(18, 60, Math.toRadians(-90));
        Pose2d pose2 = new Pose2d(18, 35, Math.toRadians(-90));
        bot = new GalaxyBot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        TrajectoryActionBuilder start = drive.actionBuilder(startPose)
                .lineToY(y1);

        TrajectoryActionBuilder drop = drive.actionBuilder(pose2)
                .lineToY(y2);

        Action one = start.build();
        Action two = drop.build();

        bot.claw();
        bot.claw();
        bot.clawSpinner.setPosition(outtakeSpinPos);
        Actions.runBlocking(one);
        slidesUp();
        Actions.runBlocking(two);
        placeSpecimen();
        slidesDown();
    }

    public void slidesUp() {
        while (bot.leftSlide.getCurrentPosition() != slideUpPos) {
            bot.slideUp(slideUpPos);
        }
    }

    public void slidesDown() {
        while (bot.leftSlide.getCurrentPosition() != slideDownPos) {
            bot.slideDown(slideDownPos);
            if (bot.leftSlide.getCurrentPosition() < slideUpPos - 20) {
                bot.claw();
            }
        }
    }

    public void placeSpecimen() {
        int x = 0;
        double posY = y2;
        int slidesPos = slideUpPos;
        while (posY > 31) {
            Pose2d specimen = new Pose2d(18, posY, Math.toRadians(-90));
            TrajectoryActionBuilder place = drive.actionBuilder(specimen)
                    .lineToY(posY - itrDrive);
            Action three = place.build();
            while (slidesPos != slidesPos - itrSlides) {
                bot.slideDown(slidesPos - itrSlides);
            }
            Actions.runBlocking(three);
            slidesPos--;
            posY -= 0.01;
        }
    }
}

