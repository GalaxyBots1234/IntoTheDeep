package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
@Disabled
public class FtclibVeloPidf extends LinearOpMode
{
    public static double p = 0, i = 0.0, d = 0, f = 0;
    public static int target = 0;

    @Override
    public void runOpMode()
    {
        // Motor m = new Motor(hardwareMap, "perp", Motor.GoBILDA.RPM_1150);
        MotorEx m = new MotorEx(hardwareMap, "motorArmExtension", Motor.GoBILDA.RPM_117);
        m.stopAndResetEncoder();
        m.setRunMode(Motor.RunMode.VelocityControl);
        m.setVeloCoefficients(p, i, d);
        //m.setPositionTolerance(20);
        //m.set(0.1);

        telemetry = new MultipleTelemetry( telemetry, FtcDashboard.getInstance().getTelemetry());
        // leftFrontMotor (CH0), motorArmPitch (EH0), perp (EH1)
        // m = hardwareMap.get(DcMotorEx.class, "leftFrontMotor");
        // m = hardwareMap.get(DcMotorEx.class, "motorArmPitch");
        // m = hardwareMap.get(DcMotorEx.class, "perp");

        waitForStart();

        while (opModeIsActive())
        {
            int armPos = m.getCurrentPosition();

            m.setVeloCoefficients(p, i, d);
            m.setVelocity(target);
            //m.setTargetPosition(target);

            telemetry.addData("pos", armPos);
            telemetry.addData("velo", m.getVelocity());
            telemetry.addData("target", target);
            telemetry.update();
        }
    }
}
