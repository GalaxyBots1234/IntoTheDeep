package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.subsystem.Arm2Subsystem;

public class ArmCommand extends InstantCommand
{
    public ArmCommand(int target) {
        super(() -> RobotHardware.getInstance().armActuator.setTargetPosition(target));
    }

    public ArmCommand(Arm2Subsystem aa, int target) {
        super(() -> aa.setTargetDegree(target));
    }
}
