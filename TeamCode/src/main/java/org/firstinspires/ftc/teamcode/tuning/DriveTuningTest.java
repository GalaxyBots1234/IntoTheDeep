package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.common.command.PositionCommand;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware;

@Config
@TeleOp(name = "DriveTuningTest")
public class DriveTuningTest extends CommandOpMode
{
    private RobotHardware robot;
    private GamepadEx gp1;

    @Override
    public void initialize()
    {
        robot = new RobotHardware(hardwareMap);
        gp1 = new GamepadEx(gamepad1);

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new PositionCommand(new Pose2d(0, 0, new Rotation2d(Math.PI / 2))));

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new PositionCommand(new Pose2d(6, 6, new Rotation2d())));

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new PositionCommand(new Pose2d(0, 6, new Rotation2d())));

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new PositionCommand(new Pose2d(0, 0, new Rotation2d())));

        while (opModeInInit()) {
            telemetry.addLine("Robot Initialized.");
            telemetry.update();
        }
    }

    @Override
    public void run()
    {
        super.run();

        robot.arm.extensionPower(-gp1.getRightY(), false);

        Pose2d pos = robot.odo.getPose();
        telemetry.addData("Position", "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(), pos.getY(), pos.getHeading());
        telemetry.addData("Arm", "Pitch: %.2f, Extension: %d", 0.1, robot.arm.getCurrentExtension());
        telemetry.update();
    }
}
