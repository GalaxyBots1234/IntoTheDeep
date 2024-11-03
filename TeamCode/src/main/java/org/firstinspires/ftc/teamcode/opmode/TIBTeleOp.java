package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.common.command.ArmExtensionCmd;
import org.firstinspires.ftc.teamcode.common.command.ArmPitchDownCmd;
import org.firstinspires.ftc.teamcode.common.command.ArmPitchUpCmd;
import org.firstinspires.ftc.teamcode.common.command.Level2ClimbCmd;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware;
import org.firstinspires.ftc.teamcode.common.system.MathUtils;

@Config
@TeleOp(name = "ITD TIBTeleOp")
public class TIBTeleOp extends CommandOpMode
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
                .whenPressed(new Level2ClimbCmd());

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new SequentialCommandGroup(
                        // Assume we are already at Level 2
                        new ArmExtensionCmd(9000),
                        // 105. Delay until the arm has cleared enough of sliders to go up.
                        new ArmPitchUpCmd(),
                        // This has to be done slower, so to not cause swinging
                        // TBD: Revise this number down to 10500
                        new ArmExtensionCmd(11500),
                        new WaitCommand(1000),
                        new ArmExtensionCmd(8500),
                        new ArmPitchUpCmd(),    // 120
                        new ArmExtensionCmd(6500),
                        new ArmPitchDownCmd(),  // 105
                        new ArmExtensionCmd(1700),
                        new ArmPitchDownCmd(),  // 90
                        new ArmExtensionCmd(2700)
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
        // telemetry.addData("Ori", "Pitch: %.2f, Height: %.2f", robot.rev.getPitch(), robot.rev.getHeight());
        telemetry.update();
    }
}
