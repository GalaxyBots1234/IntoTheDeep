package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.command.PositionCommand;
import org.firstinspires.ftc.teamcode.common.hardware.Globals;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware2;
import org.firstinspires.ftc.teamcode.common.subsystem.Arm2Subsystem;

@Config
@TeleOp
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
                .whenPressed(new PositionCommand(new Pose2d(1, 0, new Rotation2d())));

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

        /* telemetry.addData("arm target position", robot.armActuator.getTargetPosition());
        telemetry.update(); */
    }
}
