package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.common.command.ArmExtensionCmd;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware;
import org.firstinspires.ftc.teamcode.common.system.MathUtils;

@Config
@TeleOp(name = "Duo2")
public class Duo2 extends CommandOpMode
{
    private RobotHardware robot;
    private GamepadEx gp1;

    public static boolean checkBounds = true;

    private double lastRunTime = 0.0;

    // -------------------------------------------------------------------------------------------

    @Override
    public void initialize()
    {
        robot = new RobotHardware(hardwareMap);
        gp1 = new GamepadEx(gamepad1);

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() -> robot.arm.pitchStepUp()));

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new InstantCommand(() -> robot.arm.pitchStepDown()));

        gp1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(() -> robot.claw.toggle()));

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new ArmExtensionCmd(2000));
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new ArmExtensionCmd(3000));

        gp1.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new SequentialCommandGroup(
                        new ArmExtensionCmd(2000),
                        new ArmExtensionCmd(3000)
                ));

    }

    @Override
    public void run()
    {
        double runTime = System.nanoTime();

        super.run();

        // telemetry.addData("hz ", 1000000000 / (loop - loopTime));

        // Joystick Y is negative when pushed forward.
        robot.arm.extensionPower(-gp1.getRightY(), checkBounds);

        robot.drive.driveRobotCentric(gp1.getLeftX(), gp1.getLeftY(),
                // gp1.getRightX(),
                MathUtils.joystickScalar(-gp1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) +
                        gp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER), 0.01)
        );

        lastRunTime = runTime;

        telemetry.addData("GP1", "LX: %.2f, LY: %.2f, RY: %.2f", gp1.getLeftX(), gp1.getLeftY(), gp1.getRightY());
        telemetry.addData("Arm", "Pitch: %d, Extension: %d", robot.arm.pitchTargetDegree, robot.arm.getCurrentExtension());
        telemetry.update();
    }
}
