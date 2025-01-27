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

@Config
@Autonomous(name = "GalaxyBlueSpecimen 3", group = "Autonomous")
public class GalaxyBlueSpecimen extends LinearOpMode {

    public class SlideUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.slideUp();
            packet.put("left tick pos: ", bot.leftSlide.getCurrentPosition());
            packet.put("right tick pos: ", bot.rightSlide.getCurrentPosition());
            packet.put("slides up:", bot.slideUp);
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy())
            {
                return true;
            }
            else
            {
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
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy())
            {
                return true;
            }
            else
            {
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
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy())
            {
                return true;
            }
            else
            {
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
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy())
            {
                return true;
            }
            else
            {
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
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy())
            {
                return true;
            }
            else
            {
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
            if (bot.leftSlide.isBusy() || bot.rightSlide.isBusy())
            {
                return true;
            }
            else
            {
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
            try {
                bot.clawSpinSample();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

    public static double startPosX = -12;
    public static double startPosY = 66;
    public static double x1 = -24;
    public static double y1 = 48;
    public static double startHeading = -Math.PI / 2;

    public static double preloadScorePosX = -8;
    public static double preloadScorePosY = 36.75;
    public static double preloadScoreHeading = -Math.PI / 2;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(startPosX, startPosY, startHeading);
        Pose2d preloadPose = new Pose2d(preloadScorePosX,preloadScorePosY,preloadScoreHeading);
        Pose2d putspecimenPose = new Pose2d(-40, 66.3, 0);
        bot = new GalaxyBot(hardwareMap);
        bot.doClawClose();
        //bot.doSwingDown();
        bot.doInitAutoPos();
        bot.clawSpinSpecimen();
        bot.intakeDetract();

        drive = new MecanumDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();

       Action preloadScore = drive.actionBuilder(startPose)
                .splineToConstantHeading(
                            new Vector2d(
                                    preloadScorePosX,
                                    preloadScorePosY
                                    ),
                                    preloadScoreHeading)
                .build();

        Action specimenDrop = drive.actionBuilder(putspecimenPose)
                .turnTo(-Math.PI/2)
                .build();

        Action forward = drive.actionBuilder(preloadPose)
                .splineToConstantHeading(
                        new Vector2d(
                                -31,
                                40
                        ),
                        preloadScoreHeading)
                .build();
        //
        Pose2d lineupOnePose = new Pose2d(-31, 40, -Math.PI / 2);
        Action forward2 = drive.actionBuilder(lineupOnePose)
                .splineToConstantHeading(
                        new Vector2d(
                                -38.6,
                                9
                        ),
                        preloadScoreHeading)
                .build();
        Pose2d line2 = new Pose2d(-38.6, 9, -Math.PI / 2);
        Action forward3 = drive.actionBuilder(line2)
                .splineToConstantHeading(
                        new Vector2d(
                                -48,
                                50
                        ),
                        preloadScoreHeading)
                .build();
        Pose2d line3 = new Pose2d(-48, 50, -Math.PI / 2);
        Action forward4 = drive.actionBuilder(line3)
                .splineToConstantHeading(
                        new Vector2d(
                                -49.5,
                                9
                        ),
                        preloadScoreHeading)
                .build();
        Pose2d line4 = new Pose2d(-49.5, 9, -Math.PI / 2);
        Action forward5 = drive.actionBuilder(line4)
                .splineToConstantHeading(
                        new Vector2d(
                                -62,
                                50
                        ),
                        preloadScoreHeading)
                .build();

      Action toSpecimen = drive.actionBuilder(preloadPose)
                .lineToY(60)
                .turnTo(Math.PI / 2)
                .setTangent(Math.PI)
                .splineTo(
                        new Vector2d(
                                -48,
                                23),
                        Math.PI / 2
                )
                .build();
        Action toSpecimen2 = drive.actionBuilder(putspecimenPose)
                .splineToConstantHeading(
                        new Vector2d(
                                -48,
                                65),
                        Math.PI
                )
                .build();




      //  Action lineupTwo = drive.actionBuilder(lineupOnePose)
        //        .splineToConstantHeading(new Vector2d(-60, 6), Math.PI / 2)
            //    .lineToY(68)
          //      .build();



        Action specimenBackup = drive.actionBuilder(lineupOnePose)
               .lineToY(50)
             .build();

        Action specimenPickup = drive.actionBuilder(lineupOnePose)
                .splineToConstantHeading(new Vector2d(-47, 63), Math.PI / 2)
                .build();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

       Actions.runBlocking(
                new SequentialAction(
                    new ParallelAction(
                        new SwingDown(),
                                new SlideUpSpecimen(),
                        preloadScore
                            )
                        ,
                    new SequentialAction(
                            new SlideDownSpecimen(),
                            new ClawOpen(),
                            new SleepAction(0.01),
                            new slideDownFully()
                    )
                )
        );

//        Actions.runBlocking(
//                new SequentialAction(
//                    new SlideDown()
//                )
//        );
        Actions.runBlocking(
                new SequentialAction(
                        forward,
                      forward2,
                        forward3,
                        forward4,
                        forward5
                )
       );
////
////        Actions.runBlocking(
////                new SequentialAction(
////                        new SleepAction(1),
////                   //     new SlideUpSpecimenPickup(),
////                        new ClawClose(),
////                        new SleepAction(2)
////                )
//        );
//
//
//        Actions.runBlocking(
//                    new ParallelAction(
//                            new SlideUpSpecimen(),
//                            specimenDrop,
//                            new SlideDown()
//                    )
//        );
    }
}


