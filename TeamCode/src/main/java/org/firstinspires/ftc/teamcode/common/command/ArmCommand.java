package org.firstinspires.ftc.teamcode.common.command;

import com.arcrobotics.ftclib.command.InstantCommand;
import org.firstinspires.ftc.teamcode.common.system.ArmSubsystem;

public class ArmCommand extends InstantCommand
{
    public ArmCommand(ArmSubsystem aa, int target) {
        super(() -> aa.setTargetDegree(target));
    }
}
