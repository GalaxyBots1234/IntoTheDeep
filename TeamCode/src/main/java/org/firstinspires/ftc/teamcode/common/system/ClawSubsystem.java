package org.firstinspires.ftc.teamcode.common.system;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

// @Config
public class ClawSubsystem extends SubsystemBase
{
    private static final double CLAW_OPEN   = 0.25;
    private static final double CLAW_CLOSED = 0.50;

    private ServoEx servo;
    private boolean clawOpen = false;

    // -------------------------------------------------------------------------------------------

    public ClawSubsystem(final HardwareMap hMap)
    {
        servo = new SimpleServo(hMap, "servoClaw", 0, 300, AngleUnit.DEGREES);
        close();
    }

    // -------------------------------------------------------------------------------------------

    public void open()
    {
        servo.setPosition(CLAW_OPEN);
        clawOpen = true;
    }

    public void close()
    {
        servo.setPosition(CLAW_CLOSED);
        clawOpen = false;
    }

    public void toggle() {
        if (clawOpen) close(); else open();
    }
}