package org.firstinspires.ftc.teamcode.common.command;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.system.ArmSubsystem;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware;

public class ArmPitchDownCmd extends CommandBase
{
    private ArmSubsystem arm;

    // -------------------------------------------------------------------------------------------

    public ArmPitchDownCmd() {
        this.arm = RobotHardware.instance.arm;
    }

    @Override
    public void initialize() {
        arm.pitchStepDown();
    }

    @Override
    public boolean isFinished() {
        return arm.pitchAtTarget();
    }
}
