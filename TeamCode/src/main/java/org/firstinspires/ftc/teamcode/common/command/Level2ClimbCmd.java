package org.firstinspires.ftc.teamcode.common.command;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.common.system.RobotHardware;

public class Level2ClimbCmd extends SequentialCommandGroup
{
    public Level2ClimbCmd()
    {
        super(
                // Go past the bar
                new ArmExtensionCmd(6500),
                // 105
                new ArmPitchUpCmd(),
                // Pull it right below the stationary hooks
                new ArmExtensionCmd(1700),
                // 90
                new ArmPitchDownCmd(),
                // Moving hooks past the bar, and allow robot to rest on the stationary hooks
                new ArmExtensionCmd(2700));
    }

    @Override
    public void initialize()
    {
        if (RobotHardware.instance.rev.getHeight() > 3) {
            this.cancel();
            return;
        }

        super.initialize();
    }

}
