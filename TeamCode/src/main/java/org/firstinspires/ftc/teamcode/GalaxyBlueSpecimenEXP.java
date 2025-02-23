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

    public class halfSpinSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            bot.doHalfTwist();
            return false;
        }
    }


    private GalaxyBot bot;
    private MecanumDrive drive;

    public static double startPosX = -12;
    public static double startPosY = 64;
    public static double goToFirstX = -33;
    public static double goToFirstY =43.5;
    public static double goTo2ndX = -42;
    public static double goTo2ndY = 44;
    public static double goTo3rdX = -46.5;
    public static double goTo3rdY = 27.5;
    public static double goput3rdX = -48;
    public static double goput3rdY = 46;
    public static double goToPickupX = -48;
    public static double goToPickupY = 64;

    public static double tan = Math.PI / 4;
    public static double tan1 = -Math.PI / 4;
    public static double tan2 = 45;
    public static double tan3 = 0;
    public static double back = 48;

    public static double startHeading = -Math.PI / 2;

    public static double preloadScorePosX = -8;
    public static double preloadScorePosY = 34;
    public static double preloadScoreHeading = -Math.PI/2;

    public static double scoreFirstX = preloadScorePosX + 4;
    public static double scoreFirstY = preloadScorePosY + 1;

    public static double scoreSecondX = preloadScorePosX + 4 + 4;
    public static double scoreSecondY = preloadScorePosY - 0.25;

    public static double scoreThirdX = preloadScorePosX + 4 + 4 + 4;
    public static double scoreFourthX = preloadScorePosX + 4 + 4 + 4 + 4;
    public static double scoreThirdY = preloadScorePosY - 0.25;
    public static double scoreFourthY = preloadScorePosY - 0.25;
    public static double preloadSpeed = 27.5;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(startPosX, startPosY, startHeading);
        bot = new GalaxyBot(hardwareMap);
        bot.doClawClose();
        bot.intakeClawOpen();
        bot.doInitAutoPos();
        bot.clawSpinSpecimen();
        bot.intakeDetract();
        bot.doUnTwist();

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
                .setTangent(Math.PI / 4 * 3)
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
                .setTangent(Math.PI / 2)
                .splineToLinearHeading(new Pose2d(goToFirstX, back, tan1), tan)
                .build();

        Pose2d goTo2 = new Pose2d(goToFirstX, 44, tan1);
        Action goTo2nd = drive.actionBuilder(goTo2)
                .setTangent(-Math.PI / 2)
                .splineToLinearHeading(new Pose2d(goTo2ndX,goTo2ndY, tan), tan1)
                .build();

        Pose2d goToput2 = new Pose2d(goToFirstX, back, tan);
        Action goToput2nd= drive.actionBuilder(goToput2)
                .turnTo(tan1)
                .build();

        VelConstraint thirdConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(60),
                new AngularVelConstraint(Math.PI / 2)
        ));
        Pose2d goTo3 = new Pose2d(goToFirstX, back, tan1);
        Action goTo3rd = drive.actionBuilder(goTo3)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(goTo3rdX,goTo3rdY, tan3), tan1)
                .build();

        Pose2d goToput3 = new Pose2d(goTo3rdX, goTo3rdY, tan3);
        Action goToput3rd = drive.actionBuilder(goToput3)
                .setTangent(0)
                .splineToLinearHeading( new Pose2d(goput3rdX,goput3rdY, -Math.PI/2),tan3)
                .build();

        VelConstraint pickupConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(50),
                new AngularVelConstraint(Math.PI * 1.5)
        ));

        Pose2d goTopick = new Pose2d(goput3rdX, goput3rdY, -Math.PI/2);
        Action goTopickup = drive.actionBuilder(goTopick)
                .lineToY(goput3rdY - 3)
                .setTangent(-Math.PI / 2)
                .splineToLinearHeading(new Pose2d(goToPickupX,goToPickupY,Math.PI/2),-Math.PI/2,
                        pickupConstraint, new ProfileAccelConstraint(-70, 50))
                .build();
        VelConstraint scoreConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(50),
                new AngularVelConstraint(Math.PI * 1.25)
        ));
        Pose2d score1st = new Pose2d(goToPickupX, goToPickupY, Math.PI/2);
        Action scoreFirst = drive.actionBuilder(score1st)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(scoreFirstX,scoreFirstY,preloadScoreHeading),Math.PI/2,
                        scoreConstraint, new ProfileAccelConstraint(-50, 50))
                .build();


        Pose2d goTopick2 = new Pose2d(scoreFirstX, scoreFirstY, -Math.PI/2);
        Action goTopickup2 = drive.actionBuilder(goTopick2)
                .setTangent(Math.atan2(goToPickupY - scoreFirstY, goToPickupX - scoreFirstX))
                .lineToYLinearHeading(goToPickupY, Math.PI / 2)
                .build();

        Pose2d score2nd = new Pose2d(goToPickupX, goToPickupY, Math.PI/2);
        Action scoreSecond = drive.actionBuilder(score2nd)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(scoreSecondX,scoreSecondY,preloadScoreHeading),Math.PI/2,
                        scoreConstraint, new ProfileAccelConstraint(-50, 50))
                .build();

        Pose2d goTopick3 = new Pose2d(scoreSecondX, scoreSecondY, -Math.PI/2);
        Action goTopickup3 = drive.actionBuilder(goTopick3)
                .setTangent(Math.atan2(goToPickupY - scoreSecondY, goToPickupX - scoreSecondX))
                .lineToYLinearHeading(goToPickupY, Math.PI / 2)
                .build();

        Pose2d score3rd = new Pose2d(goToPickupX, goToPickupY, Math.PI/2);
        Action scoreThird = drive.actionBuilder(score3rd)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(scoreThirdX,scoreThirdY,preloadScoreHeading),Math.PI/2,
                        scoreConstraint, new ProfileAccelConstraint(-50, 50))
                .build();

        Pose2d goTopick4 = new Pose2d(scoreThirdX, scoreThirdY, -Math.PI/2);
        Action goTopickup4 = drive.actionBuilder(goTopick4)
                .setTangent(Math.atan2(goToPickupY - scoreThirdY, goToPickupX - scoreThirdX))
                .lineToYLinearHeading(goToPickupY, Math.PI / 2)
                .build();
        Pose2d score4th = new Pose2d(goToPickupX, goToPickupY, Math.PI/2);
        Action scoreFourth = drive.actionBuilder(score4th)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(scoreFourthX,scoreFourthY,preloadScoreHeading),Math.PI/2,
                        scoreConstraint, new ProfileAccelConstraint(-50, 50))
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
                        new ParallelAction(
                            goToFirst,
                            new SlideUpSpecimenPickup()
                        ),
                        new ParallelAction(
                            new IntakeExtend(),
                            new SpinDown(),
                            new IntakeClawOpen()
                        ),
                        new halfSpinSpecimen(),
                        new SleepAction(0.75),
                        new IntakeClawClose(),
                        new SleepAction(0.25)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        goToput1,
                        new IntakeClawOpen()
//                        new SleepAction(0.13)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                            goTo2nd,
                       new halfSpinSpecimen(),
                        new IntakeClawClose(),
                        new SleepAction(0.15)

                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        goToput2nd,
                        new IntakeClawOpen()
//                        new SleepAction(0.15)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                            goTo3rd,
                            new Twist()
                        ),
                        new IntakeClawClose(),
                        new SleepAction(0.15)
                )
        );

        Actions.runBlocking(
                new SequentialAction(
                        goToput3rd,
                        new IntakeClawOpen()
//                        new SleepAction(0.05)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                            new Untwist(),
                            new SpinUp(),
                            new IntakeDetract()
                        ),
                        new SleepAction(0.05)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        goTopickup,
                        new ClawClose(),
                        new SleepAction(0.05)
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                            scoreFirst,
                            new SlideUpSpecimen()
                        ),
                        new SlideDownSpecimen(),
                        new ClawOpen(),
                        new ParallelAction(
                            goTopickup2,
                            new SlideUpSpecimenPickup()
                        ),
                        new ClawClose(),
                        new SleepAction(0.05),
                        new ParallelAction(
                            scoreSecond,
                            new SlideUpSpecimen()
                        ),
                        new SlideDownSpecimen(),
                        new ClawOpen(),
                        new ParallelAction(
                                goTopickup3,
                                new SlideUpSpecimenPickup()
                        ),
                        new ClawClose(),
                        new SleepAction(0.05),
                        new ParallelAction(
                            scoreThird,
                            new SlideUpSpecimen()
                        ),
                        new SlideDownSpecimen(),
                        new ClawOpen(),
                        new ParallelAction(
                                goTopickup4,
                                new SlideUpSpecimenPickup()
                        ),
                        new ClawClose(),
                        new SleepAction(0.05),
                        new ParallelAction(
                                scoreFourth,
                                new SlideUpSpecimen()
                        ),
                        new SlideDownSpecimen(),
                        new ClawOpen(),
                        new SleepAction(0.5)

                )
        );




    }
}


