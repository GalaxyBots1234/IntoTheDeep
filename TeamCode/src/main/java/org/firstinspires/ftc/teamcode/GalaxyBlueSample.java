package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.teamcode.GalaxyBot.slideDownFully;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.Math;
import java.util.Arrays;
import java.util.Vector;

@Config
@Autonomous(name = "GalaxyBlueSample", group = "Autonomous")
public class GalaxyBlueSample extends LinearOpMode{
    public class SlideUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.slideUp();
            packet.put("left tick pos: ", bot.leftSlide.getCurrentPosition());
            packet.put("right tick pos: ", bot.rightSlide.getCurrentPosition());
            packet.put("slides up:", bot.slideUp);
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy()) {
                return true;
            } else {
                bot.leftSlide.setPower(0.0);
                bot.rightSlide.setPower(0.0);
                return false;
            }
        }
    }

    public class SlideDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.slideDown();
            packet.put("left tick pos: ", bot.leftSlide.getCurrentPosition());
            packet.put("right tick pos: ", bot.rightSlide.getCurrentPosition());
            packet.put("slides down", "");
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy()) {
                return true;
            } else {
                bot.leftSlide.setPower(0.0);
                bot.rightSlide.setPower(0.0);
                return false;
            }
        }
    }

    public class SlideUpSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.slideUpSpecimen();
            packet.put("left tick pos: ", bot.leftSlide.getCurrentPosition());
            packet.put("right tick pos: ", bot.rightSlide.getCurrentPosition());
            packet.put("slides up:", bot.slideUp);
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy()) {
                return true;
            } else {
                bot.leftSlide.setPower(0.0);
                bot.rightSlide.setPower(0.0);
                return false;
            }
        }
    }

    public class SlideUpSpecimenPickup implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.slideUpSpecimenPickup();
            packet.put("left tick pos: ", bot.leftSlide.getCurrentPosition());
            packet.put("right tick pos: ", bot.rightSlide.getCurrentPosition());
            packet.put("slides up:", bot.slideUp);
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy()) {
                return true;
            } else {
                bot.leftSlide.setPower(0.0);
                bot.rightSlide.setPower(0.0);
                return false;
            }
        }
    }

    public class SlideDownSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.slideDownSpecimen();
            packet.put("left tick pos: ", bot.leftSlide.getCurrentPosition());
            packet.put("right tick pos: ", bot.rightSlide.getCurrentPosition());
            packet.put("slides down", "");
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy()) {
                return true;
            } else {
                bot.leftSlide.setPower(0.0);
                bot.rightSlide.setPower(0.0);
                return false;
            }
        }
    }

    public class slideDownFully implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.slideDown();
            packet.put("left tick pos: ", bot.leftSlide.getCurrentPosition());
            packet.put("right tick pos: ", bot.rightSlide.getCurrentPosition());
            packet.put("slides down", "");
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy()) {
                return true;
            } else {
                bot.leftSlide.setPower(0.0);
                bot.rightSlide.setPower(0.0);
                return false;
            }
        }
    }

    public class IntakeExtend implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.intakeExtend();
            packet.put("intake extended", "");
            return false;
        }
    }

    public class IntakeDetract implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.intakeDetract();
            packet.put("intake detracted", "");
            return false;
        }
    }

    public class Twist implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doTwist();
            packet.put("twisted", "");
            return false;
        }
    }

    public class Untwist implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doUnTwist();
            packet.put("untwisted", "");
            return false;
        }
    }

    public class ClawOpen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doClawOpen();
            packet.put("claw open", "");
            return false;
        }
    }

    public class ClawClose implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doClawClose();
            packet.put("claw closed", "");
            return false;
        }
    }

    public class IntakeClawOpen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.intakeClawOpen();
            packet.put("intake claw open", "");
            return false;
        }
    }

    public class IntakeClawClose implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.intakeClawClose();
            packet.put("intake claw closed", "");
            return false;
        }
    }

    public class SwingUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doSwingUp();
            packet.put("swing up", "");
            return false;
        }
    }

    public class SwingDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doSwingDown();
            packet.put("swing down", "");
            return false;
        }
    }
    public class SwingSample implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.leftSwing.setPosition(bot.swingSamplePos);
            bot.rightSwing.setPosition(bot.swingSamplePos);
            packet.put("swing down", "");
            return false;
        }
    }

    public class SpinUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.spinUp();
            packet.put("spinned up", "");
            return false;
        }
    }

    public class SpinDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.spinDown();
            packet.put("spinned down", "");
            return false;
        }
    }

    public class SpinSample implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            bot.clawSpinSample();
            packet.put("claw spinned for sample", "");
            return false;
        }
    }

    public class SpinSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.clawSpinSpecimen();
            packet.put("claw spinned for specimen", "");
            return false;
        }
    }
    private GalaxyBot bot;
    private MecanumDrive drive;

    public static double startPosX= 36;
    public static double startPosY= 63.6;
    public static double preloadScorePosX= 58;
    public static double preloadScorePosY= 58;
    public static double firstSampleX= 50;
    public static double firstSampleY= 52.5;
    public static double returnFirstX= 58;
    public static double returnFirstY= 58;
    public static double secondSampleX = 60;
    public static double secondSampleY= 52.5;
    public static double returnSecondSampleX = 58;
    public static double returnSecondSampleY= 58;
    public static double thirdSampleX= 49.25;
    public static double thirdSampleY= 28;
    public static double returnthirdSampleX= 60.25;
    public static double returnthirdSampleY= 53.25;
    public static double returnthirdSampleX2= 58;
    public static double returnthirdSampleY2= 58;
    public static double parkX= 30;
    public static double parkY= 12;
    public static double tan= 1.483;

    public static double moveBack= 60;
    public static double moveBack2= 50;



    @Override
    public void runOpMode() throws InterruptedException {
        bot = new GalaxyBot(hardwareMap);

        bot.doClawClose();
        bot.intakeDetract();
        bot.clawSpinSample();
        bot.doSwingDown();
        bot.doUnTwist();
        bot.intakeClawOpen();

        Pose2d startPose = new Pose2d(startPosX, startPosY, Math.PI/2);
        drive = new MecanumDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();
        Action preloadScore = drive.actionBuilder(startPose)
                .setTangent(-Math.PI / 2)
                .splineToLinearHeading(
                        new Pose2d(preloadScorePosX, preloadScorePosY, Math.PI / 4),
                        Math.PI / 2
                )
                .build();

        Pose2d lineUpOne = new Pose2d(preloadScorePosX, preloadScorePosY, Math.PI/4);
        Action dolineUpOne = drive.actionBuilder(lineUpOne)
                .setTangent(-Math.PI / 4)
                .splineToSplineHeading(new Pose2d(firstSampleX, firstSampleY, Math.PI / 2), Math.PI / 4)
                .build();

        Pose2d lineUpOne2 = new Pose2d(firstSampleX, firstSampleY, Math.PI/4);
        Action dolineUpOne2 = drive.actionBuilder(lineUpOne2)
                .setTangent(Math.PI / 2)
                .splineToLinearHeading(
                        new Pose2d(returnFirstX, returnFirstY, Math.PI / 4),
                        Math.PI / 2
                )
                .build();
        Pose2d lineUpOne3 = new Pose2d(returnFirstX, returnFirstY, Math.PI/4);
        Action dolineUpOne3 = drive.actionBuilder(lineUpOne3)
                .lineToY(moveBack)
                .setTangent(-Math.PI / 4)
                .splineToLinearHeading(new Pose2d(secondSampleX, secondSampleY, Math.PI / 2), Math.PI / 4)
                .build();

        Pose2d lineUpOne4 = new Pose2d(secondSampleX, secondSampleY, Math.PI / 2);
        Action dolineUpOne4 = drive.actionBuilder(lineUpOne4)
                .setTangent(Math.PI / 2)
                .splineToLinearHeading(new Pose2d(returnSecondSampleX, returnSecondSampleY, Math.PI / 4), Math.PI / 2)
                .build();

        Pose2d lineUpOne5 = new Pose2d(returnSecondSampleX, returnSecondSampleY, Math.PI / 4);
        Action dolineUpOne5 = drive.actionBuilder(lineUpOne5)
                .lineToY(moveBack2)
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(thirdSampleX, thirdSampleY, Math.PI), Math.PI / 4)
                .build();

        Pose2d lineUpOne6 = new Pose2d(thirdSampleX, thirdSampleY, Math.PI);
        Action dolineUpOne6 = drive.actionBuilder(lineUpOne6)
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(returnthirdSampleX, returnthirdSampleY, Math.PI / 4), 0)
                .splineToConstantHeading(
                        new Vector2d(returnthirdSampleX2, returnthirdSampleY2),
                        Math.PI / 4
                )
                .build();
        Pose2d backUpPose = new Pose2d(returnthirdSampleX2, returnthirdSampleY2, Math.PI /4 );
        Action backUp = drive.actionBuilder(backUpPose)
                .splineToLinearHeading(new Pose2d(parkX, parkY, Math.PI/2), tan)
                .build();


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        // move to basket to score first sample
        Actions.runBlocking(
                new ParallelAction(

                        new SlideUp(),
                        new SwingUp(),
                        preloadScore
                )
        );
        //
        // sequence to score block
        Actions.runBlocking(
                new SequentialAction(
                        new ClawOpen(),
                        new SleepAction(0.5)
                )
        );
        // sequence to go to and pick up first sample
        Actions.runBlocking(
                new SequentialAction(
                        dolineUpOne,
                        new ParallelAction
                                (
                                        new slideDownFully(),
                        new IntakeExtend(),
                        new SpinDown(),
                        new IntakeClawOpen()
                                ),

                        new SwingDown(),
                        new SleepAction(0.5),
                        new IntakeClawClose(),
                        new SleepAction(0.5)
                )
        );
        // sequence to do handoff of first sample
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                new SpinUp(),
                                new IntakeDetract()
                        ),
                        new SleepAction(1),
                        new IntakeClawOpen(),
                        new ClawClose(),
                        new SleepAction(0.25)
                )
        );

        Actions.runBlocking(
                new ParallelAction(
                        dolineUpOne2,
                        new SlideUp()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new SwingUp(),
                        new SleepAction(0.75),
                        new ClawOpen(),
                        new SleepAction(0.25)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        dolineUpOne3,
                        new ParallelAction
                                (
                                        new slideDownFully(),
                                        new IntakeExtend(),
                                        new SpinDown(),
                                        new IntakeClawOpen()

                                ),

                        new SwingDown(),
                        new SleepAction(0.5),
                        new IntakeClawClose(),
                        new SleepAction(0.5)
                )
        );
        // sequence to do handoff of first sample
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                new SpinUp(),
                                new IntakeDetract()
                        ),
                        new SleepAction(1),
                        new IntakeClawOpen(),
                        new ClawClose(),
                        new SleepAction(0.25)
                )
        );
        Actions.runBlocking(
                new ParallelAction(
                        dolineUpOne4,
                        new SlideUp()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new SwingUp(),
                        new SleepAction(0.75),
                        new ClawOpen(),
                        new SleepAction(0.5)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        dolineUpOne5,
                        new ParallelAction
                                (
                                        new slideDownFully(),
                                        new IntakeExtend(),
                                        new SpinDown(),
                                        new IntakeClawOpen()
                                ),

                        new SwingDown(),
                        new Twist(),
                        new SleepAction(0.5),
                        new IntakeClawClose(),
                        new SleepAction(0.5)
                )
        );
        // sequence to do handoff of first sample
        Actions.runBlocking(
                new SequentialAction(
                        new Untwist(),
                        new ParallelAction(
                                new SpinUp(),
                                new IntakeDetract()
                        ),
                        new SleepAction(1),
                        new IntakeClawOpen(),
                        new ClawClose(),
                        new SleepAction(0.25)
                )
        );
        Actions.runBlocking(
                new ParallelAction(
                        dolineUpOne6,
                        new SlideUp()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new SwingUp(),
                        new SleepAction(0.75),
                        new ClawOpen(),
                        new SleepAction(0.25)
                )
        );
        Actions.runBlocking(
                new ParallelAction(
                        backUp,
                        new slideDownFully(),
                        new SwingDown()
                )
        );
    }
}
