package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.ArmCommand;
/*import org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand.ClawDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand.ClawToggleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand.DepositExtendCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand.DepositRetractionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand.HeightChangeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand.IntakeExtendCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand.IntakeRetractCommand;*/
import org.firstinspires.ftc.teamcode.common.drive.pathing.geometry.Pose;
import org.firstinspires.ftc.teamcode.common.hardware.Globals;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
/*import org.firstinspires.ftc.teamcode.common.subsystem.DroneSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.HangSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;*/
import org.firstinspires.ftc.teamcode.common.subsystem.Arm2Subsystem;
import org.firstinspires.ftc.teamcode.common.util.MathUtils;

@Config
@TeleOp(name = "Duo")
public class Duo extends CommandOpMode {

    private final RobotHardware robot = RobotHardware.getInstance();
    private GamepadEx gamepadEx;
    private GamepadEx gamepadEx2;

    private double loopTime = 0.0;
    private boolean lastJoystickUpRight = false;
    private boolean lastJoystickDownRight = false;
    //    private boolean lastJoystickitUpLeft = false;
//    private boolean lastJoystickDownLeft = false;
    private boolean extendIntake = true;

    private Arm2Subsystem a2s;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

        Globals.IS_AUTO = false;
        Globals.stopIntaking();
        Globals.stopScoring();

        gamepadEx = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        a2s = new Arm2Subsystem(hardwareMap);
        robot.init(hardwareMap);

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() -> a2s.pitchStepUp()));

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new InstantCommand(() -> a2s.pitchStepDown()));

        robot.read();
        while (opModeInInit()) {
            telemetry.addLine("Robot Initialized.");
            telemetry.update();
        }
    }

    @Override
    public void run()
    {
        super.run();

        a2s.extensionPower(-gamepadEx.getRightY());

        /*CommandScheduler.getInstance().run();
        // robot.clearBulkCache();
        robot.read();
        robot.periodic();
        robot.write();

        // G1 - Drivetrain Control
        // robot.drivetrain.set(new Pose(gamepad1.left_stick_x, -gamepad1.left_stick_y, MathUtils.joystickScalar(-gamepad1.left_trigger + gamepad1.right_trigger, 0.01)), 0);

        /*boolean currentJoystickUpRight = gamepad1.right_stick_y < -0.5 || gamepad2.right_stick_y < -0.5;
        boolean currentJoystickDownRight = gamepad1.right_stick_y > 0.5 || gamepad2.right_stick_y > 0.5;

        lastJoystickUpRight = currentJoystickUpRight;
        lastJoystickDownRight = currentJoystickDownRight;* /

        double loop = System.nanoTime();
        // telemetry.addData("hz ", 1000000000 / (loop - loopTime));
        telemetry.addData("arm power", robot.armMotor.getPower());
        telemetry.addData("arm current pos", robot.armMotor.getCurrentPosition());
        telemetry.addData("arm get postition", robot.armActuator.getPosition());
        telemetry.addData("arm target position", robot.armActuator.getTargetPosition());
        loopTime = loop;
        telemetry.update();*/
    }
}
