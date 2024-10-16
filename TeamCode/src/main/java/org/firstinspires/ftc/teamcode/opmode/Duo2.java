package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware2;
import org.firstinspires.ftc.teamcode.common.system.MathUtils;

@TeleOp(name = "Duo2")
public class Duo2 extends CommandOpMode
{
    private RobotHardware2 robot;
    private GamepadEx gp1;

    @Override
    public void initialize()
    {
        robot = new RobotHardware2(hardwareMap);
        gp1 = new GamepadEx(gamepad1);
    }

    @Override
    public void run()
    {
        super.run();

        robot.drive.driveRobotCentric(gp1.getLeftX(), gp1.getLeftY(),
                // gp1.getRightX(),
                MathUtils.joystickScalar(-gp1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) +
                        gp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER), 0.01)
        );

        telemetry.addData("Joysticks", "leftX: %.2f, leftY: %.2f", gp1.getLeftX(), gp1.getLeftY());
        telemetry.update();
    }
}
