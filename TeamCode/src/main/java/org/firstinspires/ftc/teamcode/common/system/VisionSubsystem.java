package org.firstinspires.ftc.teamcode.common.system;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

// @Config
public class VisionSubsystem extends SubsystemBase
{
    private Limelight3A limelight;

    // -------------------------------------------------------------------------------------------

    public VisionSubsystem(final HardwareMap hMap)
    {
        limelight = hMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(3);
        limelight.start();
    }

    // -------------------------------------------------------------------------------------------

    @Override
    public void periodic() {
    }
}
