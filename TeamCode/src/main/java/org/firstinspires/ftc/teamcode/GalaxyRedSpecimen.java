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
    public static double y = 33;
    public static double x = 10;


    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        Pose2d startPose = new Pose2d(18, 60, Math.toRadians(-90));
        bot = new GalaxyBot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, startPose);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        TrajectoryActionBuilder specimen = drive.actionBuilder(startPose)
                .lineToY(y);
        //.lineToX(x);

        Action actionChosen = specimen.build();
        Actions.runBlocking(new SequentialAction(actionChosen));
    }
}
