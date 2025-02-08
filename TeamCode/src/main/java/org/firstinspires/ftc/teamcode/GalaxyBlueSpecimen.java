package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.teamcode.GalaxyBot.clawOpenPos;
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
@Autonomous(name = "GalaxyBlueSpecimen", group = "Autonomous")
public class GalaxyBlueSpecimen extends LinearOpMode {

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

    public static double startPosX = -12;
    public static double startPosY = 63.6;
    public static double backUpafterscoreX = -31;
    public static double backUpafterscoreY = 40;
    public static double loopSplineX = -42;
    public static double loopSplineY = 14;
    public static double goForwardX = -50;
    public static double goForwardY = 64.8;
    public static double scoreFirstX = -6;
    public static double scoreFirstY = 34;
    public static double goBacksecondX = -45;
    public static double goBacksecondY = 63.6;
    public static double scoreSecondX = -4;
    public static double scoreSecondY = 33.8;
    public static double goBack1 = 44;
    public static double goBack2 = 66;
    public static double parkX = -45;
    public static double parkY = 60;



    public static double tan = 2.52;
    public static double startHeading = -Math.PI / 2;

    public static double preloadScorePosX = -8;
    public static double preloadScorePosY = 33.8;
    public static double preloadScoreHeading = -Math.PI / 2;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(startPosX, startPosY, startHeading);
        bot = new GalaxyBot(hardwareMap);
        bot.doClawClose();
        bot.doInitAutoPos();
        bot.clawSpinSpecimen();
        bot.intakeDetract();

        drive = new MecanumDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();
// Score preload speciemen
        Action preloadScore = drive.actionBuilder(startPose)
                .splineToConstantHeading(
                        new Vector2d(
                                preloadScorePosX,
                                preloadScorePosY
                        ),
                        preloadScoreHeading)
                .build();
        Pose2d preloadPose = new Pose2d(preloadScorePosX, preloadScorePosY, preloadScoreHeading);
        Action forward1 =  drive.actionBuilder(preloadPose)
                .lineToY(goBack1)
                .splineToConstantHeading(
                      new Vector2d(
                                backUpafterscoreX,
                                backUpafterscoreY
                        ),
                        preloadScoreHeading)

                .splineToLinearHeading(
                        new Pose2d(
                                loopSplineX,
                                loopSplineY,
                                Math.PI/2
                        ),
                        Math.PI)
                .build();

        Pose2d afterloop =new Pose2d(loopSplineX, loopSplineY, Math.PI/2);
        Action forward2 =  drive.actionBuilder(afterloop)
                .splineToConstantHeading(
                        new Vector2d(
                                goForwardX,
                                goForwardY
                        ),
                        Math.PI/2
                )
                .build();

        Pose2d afterloop1 =new Pose2d(goForwardX, goForwardY, Math.PI/2);
        Action forward3 = drive.actionBuilder(afterloop1)
                .lineToY(goBack2)
                .splineToLinearHeading(
                        new Pose2d(
                                scoreFirstX,
                                scoreFirstY
                                ,
                                -Math.PI/2
                        ),
                        Math.toRadians(0.54))
                .build();

        Pose2d afterloop2 =new Pose2d(scoreFirstX, scoreFirstY, -Math.PI/2);
        Action forward4 = drive.actionBuilder(afterloop2)
                .splineToLinearHeading(
                        new Pose2d(
                                goBacksecondX,
                                goBacksecondY
                                ,
                                Math.PI/2
                        ),
                        Math.toRadians(tan))
                .build();

        Pose2d afterloop3 =new Pose2d(goBacksecondX, goBacksecondY, Math.PI/2);
        Action forward5 = drive.actionBuilder(afterloop3)
                .lineToY(goBack2)
                .splineToLinearHeading(
                        new Pose2d(
                                scoreSecondX,
                                scoreSecondY
                                ,
                                -Math.PI/2
                        ),
                        Math.toRadians(0.54))
                .build();

        Pose2d afterloop4 =new Pose2d(scoreSecondX, scoreSecondY, -Math.PI/2);
        Action forward6 = drive.actionBuilder(afterloop4)
                .splineToLinearHeading(
                        new Pose2d(
                                goBacksecondX,
                                goBacksecondY
                                ,
                                Math.PI/2
                        ),
                        Math.toRadians(tan))
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
                )
        );
        Actions.runBlocking(
            new SequentialAction(
                new SlideDownSpecimen(),
                new ClawOpen(),
                forward1
                                )
        );
        Actions.runBlocking(
                new ParallelAction(
                        forward2,
                        new SlideUpSpecimenPickup()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new ClawClose(),
                        new SleepAction(0.5)));

                Actions.runBlocking(
                        new ParallelAction(
                                forward3,
                                new SlideUpSpecimen()
                        )

                );
                        Actions.runBlocking(
                                new SequentialAction(
                                        new SleepAction(0.125),
                                        new SlideDownSpecimen(),
                                        new ClawOpen(),
                                        forward4,
                                        new SlideUpSpecimenPickup(),
                                        new ClawClose(),
                                        new SleepAction(0.25)
                                )
                        );
        Actions.runBlocking(
                new ParallelAction(
                        forward5,
                        new SlideUpSpecimen()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new SlideDownSpecimen(),
                        new ClawOpen(),
                        new SleepAction(1),
                        forward6
                )
        );
    }
}


