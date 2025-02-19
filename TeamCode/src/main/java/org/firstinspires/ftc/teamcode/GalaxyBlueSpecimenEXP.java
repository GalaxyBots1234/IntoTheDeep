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
@Autonomous(name = "GalaxyBlueSpecimenEXP", group = "Autonomous")
public class GalaxyBlueSpecimenEXP extends LinearOpMode {

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

    public static double startPosX = -12;
    public static double startPosY = 63.6;
    public static double goToFirstX = -29;
    public static double goToFirstY = 39;
    public static double turnAngleFirst = 0.658;
    public static double turnAngleFirstback = -0.5712;
    public static double goToputX = -32;
    public static double goToputY = 43;
    public static double turnBackX = -37;
    public static double turnBackY = 39;
    public static double goBacksecondX = -32;
    public static double goBacksecondY = 42;
    public static double scoreSecondX = -4;
    public static double scoreSecondY = 36.55;
    public static double goBack1 = 44;
    public static double goBack2 = 66;
    public static double parkX = -45;
    public static double parkY = 60;



    public static double tan = 2.52;
    public static double startHeading = -Math.PI / 2;

    public static double preloadScorePosX = -8;
    public static double preloadScorePosY = 34.8;
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
                                goToFirstX,
                                goToFirstY
                        ),
                        preloadScoreHeading)
                .turnTo(turnAngleFirst)
                .build();

        Pose2d turnyTurns =new Pose2d(goToFirstX,goToFirstY,turnAngleFirst);
        Action forward2 =  drive.actionBuilder(turnyTurns)
//                .splineToConstantHeading(
//                        new Vector2d(
//                                goToputX,
//                                goToputY
//                        ),
//                        Math.toRadians(turnAngleFirstback)
//                )
                .turnTo(0.8)
                .build();

        Pose2d turnyTurns1 =new Pose2d(goToputX, goToputY, turnAngleFirstback);
        Action forward3 = drive.actionBuilder(turnyTurns1)
                .splineToConstantHeading(
                        new Vector2d(
                                turnBackX,
                                turnBackY
                        ),
                        Math.toRadians(turnAngleFirst))
                .build();
        Pose2d turnyTurns2 =new Pose2d(turnBackX, turnBackY, turnAngleFirst);
        Action forward4 = drive.actionBuilder(turnyTurns2)
                .splineToLinearHeading(
                        new Pose2d(
                                goBacksecondX,
                                goBacksecondY
                                ,
                                Math.toRadians(turnAngleFirstback)
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
                new SequentialAction(
                        new IntakeClawOpen(),
                        new SlideDownSpecimen(),
                        new IntakeExtend(),
                        new SpinDown(),
                        new SleepAction(1),
                        new Twist(),
                        new IntakeClawClose(),
                        new SleepAction(1)
                )
        );

        Actions.runBlocking(
                new SequentialAction(
                        forward2,
                        new IntakeClawOpen()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new ClawClose(),
                        new SleepAction(0.5)));

        Actions.runBlocking(
                (
                        forward3
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new IntakeExtend(),
                        new SpinDown(),
                        new SleepAction(1),
                        new IntakeClawClose(),
                        new SleepAction(1),
                        new slideDownFully()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        forward4,
                        new IntakeClawOpen()
                )
        );
        Actions.runBlocking(
                new SequentialAction(
                        new ClawClose(),
                        new SleepAction(0.5)));


//        Actions.runBlocking(
//                new SequentialAction(
//                        new SleepAction(0.125),
//                        new SlideDownSpecimen(),
//                        new ClawOpen(),
//                        forward4,
//                        new SlideUpSpecimenPickup(),
//                        new ClawClose(),
//                        new SleepAction(0.25)
//                )
//        );
//        Actions.runBlocking(
//                new ParallelAction(
//                        forward5,
//                        new SlideUpSpecimen()
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new SlideDownSpecimen(),
//                        new ClawOpen(),
//                        new SleepAction(1),
//                        forward6
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new SpinSample(),
//                        new ClawClose(),
//                        new SwingSample(),
//                        new SleepAction(0.5),
//                        new SwingDown(),
//                        new slideDownFully()
//
//                )
//        );
    }
}


