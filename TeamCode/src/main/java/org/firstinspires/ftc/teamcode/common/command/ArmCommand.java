package org.firstinspires.ftc.teamcode.common.command;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.system.RobotHardware;
import org.firstinspires.ftc.teamcode.common.system.Arm2Subsystem;

public class ArmCommand extends InstantCommand
{
    public ArmCommand(int target) {
        super(() -> RobotHardware.getInstance().armActuator.setTargetPosition(target));
    }

    public ArmCommand(Arm2Subsystem aa, int target) {
        super(() -> aa.setTargetDegree(target));
    }
}
