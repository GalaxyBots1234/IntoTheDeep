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
import com.arcrobotics.ftclib.kotlin.extensions.geometry.Pose2dExtKt;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.Math;
import java.util.Arrays;

@Config
@Autonomous(name = "GalaxyBlueSpecimenEXP", group = "Autonomous")
public class GalaxyBlueSpecimenEXP extends LinearOpMode {

    public class SlideUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.slideUp();

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
            return false;
        }
    }

    public class IntakeDetract implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.intakeDetract();
            return false;
        }
    }

    public class Twist implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doTwist();
            return false;
        }
    }

    public class Untwist implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doUnTwist();
            return false;
        }
    }

    public class ClawOpen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doClawOpen();
            return false;
        }
    }

    public class ClawClose implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doClawClose();
            return false;
        }
    }

    public class IntakeClawOpen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.intakeClawOpen();
            return false;
        }
    }

    public class IntakeClawClose implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.intakeClawClose();
            return false;
        }
    }

    public class SwingUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doSwingUp();
            return false;
        }
    }

    public class SwingDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doSwingDown();
            return false;
        }
    }
    public class SwingSample implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.leftSwing.setPosition(bot.swingSamplePos);
            bot.rightSwing.setPosition(bot.swingSamplePos);
            return false;
        }
    }

    public class SpinUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.spinUp();
            return false;
        }
    }

    public class SpinDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.spinDown();
            return false;
        }
    }

    public class SpinSample implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            bot.clawSpinSample();
            return false;
        }
    }

    public class SpinSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.clawSpinSpecimen();
            return false;
        }
    }

    private GalaxyBot bot;
    private MecanumDrive drive;

    public static double startPosX = -12;
    public static double startPosY = 63.6;
    public static double goToFirstX = -31.7;
    public static double goToFirstY = 33.5;
    public static double goTo2ndX = -43.5;
    public static double goTo2ndY = 38;
    public static double goTo3rdX = -65;
    public static double goTo3rdY = 48;
    public static double tan = 120;
    public static double tan1 = -45;
    public static double tan2 = 45;
    public static double tan3 = 45;

    public static double startHeading = -Math.PI / 2;

    public static double preloadScorePosX = -8;
    public static double preloadScorePosY = 28.7;
    public static double preloadScoreHeading = -Math.PI/2;
    public static double preloadSpeed = 33.5;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(startPosX, startPosY, startHeading);
        bot = new GalaxyBot(hardwareMap);
        bot.doClawClose();
        bot.intakeClawOpen();
        bot.doInitAutoPos();
        bot.clawSpinSpecimen();
        bot.intakeDetract();

        drive = new MecanumDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();
// Score preload speciemen
        VelConstraint preloadConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(preloadSpeed),
                new AngularVelConstraint(Math.PI / 2)
        ));
        Action preloadScore = drive.actionBuilder(startPose)
                .splineToConstantHeading(
                        new Vector2d(
                                preloadScorePosX,
                                preloadScorePosY
                        ),
                        preloadScoreHeading,
                        preloadConstraint)
                .build();
//        Pose2d goback = new Pose2d(preloadScorePosX, preloadScorePosY, preloadScoreHeading);
//        Action goBack = drive.actionBuilder(goback)
//                .lineToY(42)
//                .build();

        Pose2d afterScore = new Pose2d(preloadScorePosX, preloadScorePosY, preloadScoreHeading);
        Action goToFirst = drive.actionBuilder(afterScore)
                .setTangent(Math.PI / 2)
                .splineToLinearHeading(
                        new Pose2d(
                                goToFirstX,
                                goToFirstY,
                                tan
                        ),
                        preloadScoreHeading)
                .build();
        Pose2d afterPick1 = new Pose2d(goToFirstX, goToFirstY, tan);
        Action goToput1 = drive.actionBuilder(afterPick1)
                .splineToLinearHeading(new Pose2d(goToFirstX,44,tan1),tan)
                .build();

        Pose2d goTo2 = new Pose2d(goToFirstX, 44, tan1);
        Action goTo2nd = drive.actionBuilder(goTo2)
                .splineToLinearHeading(new Pose2d(goTo2ndX,goTo2ndY,tan2),tan1)
                .build();

        Pose2d goToput2 = new Pose2d(goToFirstX, 44, tan2);
        Action goToput2nd= drive.actionBuilder(goToput2)
                .turnTo(tan1)
                .build();

//        Pose2d goTo3 = new Pose2d(goToFirstX, 44, tan1);
//        Action goTo3rd = drive.actionBuilder(goTo3)
//                .turnTo(tan3)
//                .splineToConstantHeading(new Vector2d(goTo3rdX,goTo3rdY),tan3 )
//                .build();

        Pose2d goTopick = new Pose2d(goToFirstX, 44, tan1);
        Action goTopickup = drive.actionBuilder(goTopick)
                .turnTo(tan3)
                .splineToConstantHeading(new Vector2d(goTo3rdX,goTo3rdY),tan3 )
                .build();









        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        Actions.runBlocking(
                        new ParallelAction(
                                new SwingDown(),
                                new SlideUpSpecimen(),
                                preloadScore
                        )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new SlideDownSpecimen(),
                        new ClawOpen()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        goToFirst,
                        new IntakeExtend(),
                        new SpinDown(),
                        new IntakeClawOpen(),
                        new SleepAction(0.3),
                        new Twist(),
                        new SleepAction(0.5),
                        new IntakeClawClose(),
                        new SleepAction(0.5)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        goToput1,
                        new IntakeClawOpen(),
                        new SleepAction(0.3)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                            goTo2nd,
                            new Untwist(),
                        new SleepAction(0.3),
                        new IntakeClawClose(),
                        new SleepAction(0.5)

                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        goToput2nd,
                        new IntakeClawOpen(),
                        new SleepAction(1)
                )
        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        goTo3rd,
//                        new IntakeClawClose(),
//                        new SleepAction(1)
//                )
//        );
    }
}


