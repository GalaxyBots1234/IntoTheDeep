package org.firstinspires.ftc.teamcode.samples;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.common.drive.WEncoder;

@Config
@TeleOp(name = "ActuationMotorTest")
@Disabled
public class ActuationMotorTest extends OpMode {
    public WEncoder extensionPitchEncoder;
    public AnalogInput extensionPitchEnc;

    public DcMotorEx armMotor;

    private double loopTime = 0.0;

    public static double P = 1;
    public static double I = 0.0;
    public static double D = 0.045;
    public static double F = 0.07;

    public static double A = 10;
    public static double DA = 10;
    public static double V = 10;

    public static double armTargetPosition = 1;

    @Override
    public void init()
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        armMotor = hardwareMap.get(DcMotorEx.class, "motorArmPitch");
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.extensionPitchEncoder = new WEncoder(new MotorEx(hardwareMap, "motorArmPitch").encoder);

        // a, lift, went up with 0.1
        // b, arm, went down with 0.1

        telemetry.addLine("here");
        telemetry.update();
    }

    @Override
    public void loop() {
//        pitchActuator.setPID(P, I, D);
//        pitchActuator.setFeedforward(WActuatorGroup.FeedforwardMode.ANGLE_BASED, F);

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        if (gamepad1.a) {
//            liftMotor.setPower(0.1);
//        } else {
//            liftMotor.setPower(0.0);
//        }
//
//        if (gamepad1.b) {
//            armMotor.setPower(0.1);
//        } else {
//            armMotor.setPower(0.0);
//        }


//        telemetry.addData("radian reading", extensionPitchEncoder.getCurrentPosition());

//        telemetry.addData("voltage", extensionEncoder.getVoltage());
        telemetry.addData("targetPosition", armTargetPosition);
        // telemetry.addData("targetPositionLift", pitchActuator.getState().x);

//        telemetry.addData("v", state.v);
//        telemetry.addData("p", state.x);
//        telemetry.addData("a", state.a);
//        telemetry.addData("v", pitchActuator.getConstraints().velo);
//        telemetry.addData("time:", pitchActuator.timer.time());

//        telemetry.addData("liftPosition", liftTicks);
//        telemetry.addData("currentFF", pitchActuator.getCurrentFeedforward());

        double loop = System.nanoTime();
        telemetry.addData("hz ", 1000000000 / (loop - loopTime));
        loopTime = loop;
        telemetry.update();
    }
}
