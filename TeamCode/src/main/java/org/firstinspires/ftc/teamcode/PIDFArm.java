package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Main Robot - Arm Pitch
 * - 30 RPM,
 * - Range = 0 (-5 degrees) to
 *      -  450 (0 degrees)
 *      - 5700 (90 degrees)
 * - BEST P = 0.0015, I = 0, D = 0, F = 0.00001
 *
 *  Arm Extension (117 RPM)
 * - Range = 0 (-5 degrees) to
 *      - 14000 (Full stretch)
 * - BEST P = 0.001, I = 0, D = 0, F = 0
 */

@TeleOp
@Config
public class PIDFArm extends LinearOpMode
{
    private PIDFController controller;

    public static double p = 0, i = 0.0, d = 0, f = 0;
    public static int target = 0;

    private DcMotorEx m;
    // 1150 RPM   = 145.1
    // 117 RPM  = 1425.1
    private final double ticks_in_degree = 1425.1 / 360.0;

    @Override
    public void runOpMode()
    {
        controller = new PIDFController(p, i ,d, f);
        telemetry = new MultipleTelemetry( telemetry, FtcDashboard.getInstance().getTelemetry());
        // leftFrontMotor (CH0), motorArmPitch (EH0), perp (EH1)
        // m = hardwareMap.get(DcMotorEx.class, "leftFrontMotor");
        m = hardwareMap.get(DcMotorEx.class, "motorArmPitch");
        // m = hardwareMap.get(DcMotorEx.class, "perp");
        m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // m.setMotorEnable();

        waitForStart();

        while (opModeIsActive())
        {
            int armPos = m.getCurrentPosition();
            double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

            controller.setPIDF(p, i ,d, ff);
            double power = controller.calculate(armPos, target);
            m.setPower(power);

            telemetry.addData("pos", armPos);
            telemetry.addData("power", power);
            telemetry.addData("target", target);
            telemetry.update();
        }
    }
}
