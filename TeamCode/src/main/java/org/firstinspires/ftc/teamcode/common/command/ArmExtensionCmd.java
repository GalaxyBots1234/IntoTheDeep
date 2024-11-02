package org.firstinspires.ftc.teamcode.common.command;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.common.system.ArmSubsystem;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware;

public class ArmExtensionCmd extends CommandBase
{
    private ArmSubsystem arm;
    private int target;

    // -------------------------------------------------------------------------------------------

    public ArmExtensionCmd(int target) {
        this.target = target;
        this.arm = RobotHardware.instance.arm;
    }

    @Override
    public void initialize() {
        arm.setExtension(target);
    }

    @Override
    public boolean isFinished() {
        return arm.extensionAtTarget();
    }

    @Override
    public void end(boolean interrupted) {
        arm.setExtension(arm.getCurrentExtension());
    }
}
