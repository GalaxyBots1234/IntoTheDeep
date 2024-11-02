package org.firstinspires.ftc.teamcode.common.command;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.common.system.ArmSubsystem;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware;

public class ArmPitchCmd extends CommandBase
{
    private ArmSubsystem arm;
    private int target;

    // -------------------------------------------------------------------------------------------

    public ArmPitchCmd(int target) {
        this.target = target;
        this.arm = RobotHardware.instance.arm;
    }

    @Override
    public void initialize() {
        arm.setTargetDegree(target);
    }

    @Override
    public boolean isFinished() {
        return arm.pitchAtTarget();
    }
}
