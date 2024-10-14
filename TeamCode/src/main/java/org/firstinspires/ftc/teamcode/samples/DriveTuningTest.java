package org.firstinspires.ftc.teamcode.samples;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.common.command.PositionCommand;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware2;

@Config
@TeleOp(name = "DriveTuningTest")
public class DriveTuningTest extends CommandOpMode
{
    private RobotHardware2 robot;
    private GamepadEx gamepadEx;

    @Override
    public void initialize()
    {
        robot = new RobotHardware2(hardwareMap);
        gamepadEx = new GamepadEx(gamepad1);

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new PositionCommand(new Pose2d(0, 0, new Rotation2d(Math.PI / 2))));

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
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

        Pose2d pos = robot.odo.getPose();
        telemetry.addData("Position", "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(), pos.getY(), pos.getHeading());
        // telemetry.addData("arm target position", robot.armActuator.getTargetPosition());
        telemetry.update();
    }
}
