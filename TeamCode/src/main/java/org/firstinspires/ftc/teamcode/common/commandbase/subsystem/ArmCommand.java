package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;

public class ArmCommand extends InstantCommand
{
    public ArmCommand(int target) {
        super(
                //() -> RobotHardware.getInstance().armActuator.setTargetPosition(target)
                () -> {
                    RobotHardware.getInstance().armMotor.setTargetPosition(target);
                    RobotHardware.getInstance().armMotor.set(0.1);
                }
        );
    }
}
