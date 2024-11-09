package org.firstinspires.ftc.teamcode.common.system;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.util.MathUtils;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;

/**
 * Main Robot - Arm Pitch
 * - 30 RPM,
 * - Range = 0 (-5 degrees) to
 *      -  450 (0 degrees)
 *      - 5700 (90 degrees)
 * - BEST P = 0.0015, I = 0, D = 0, F = 0.00001
 *
 * Robot will start with arm lifted to 95 degrees.
 *  It can stretch to 110 degrees depending on the position of the hanging hook.
 *  The bottom most position is now 0 degrees.
 *  We have to account for 5 degress of chain slack.
 *
 *  Arm Extension (117 RPM)
 * - Range = 0 (-5 degrees) to
 *      - 14000 (Full stretch)
 * - BEST P = 0.001, I = 0, D = 0, F = 0
 */
// @Config
public class ArmSubsystem extends SubsystemBase
{
    private final MotorEx armPitch;
    private final MotorEx armExtension;

    // 0    = For picking
    // 20   = For clearing the submersible wall while retracting
    // 80   = For high basket drop
    // 90   = For bot to fit
    // 105  = Latch onto first bar while climbing
    // 120  = Clear the back wall
    private static final int[]  PITCH_STOPS         = {0, 20, 80, 90, 105, 120};
    private static final int    PITCH_DEFAULT       = 90;
    private static final double PITCH_GEARING       = 25.9 / 107;
    private static final double PITCH_TICKS_DEGREE  = (Motor.GoBILDA.RPM_30.getCPR() / PITCH_GEARING) / 360.0;
    private static final double PITCH_POWER         = 0.75;

    // 13500 is total range assuming the slides are fully rolled back at 90 degrees.
    // Slides extend about 450 units as the pitch goes to zero.
    // Numbers below assume starting fully rolled back.
    private static final int    EXTENSION_VMIN      = 0;
    private static final int    EXTENSION_HMIN      = 450;
    private static final int    EXTENSION_TILTBACK  = 1600;
    private static final int    EXTENSION_HMAX      = 5000;
    private static final int    EXTENSION_VMAX      = 13400;
    private static final double EXTENSION_POWERREST = 0.30;
    private static final double EXTENSION_POWER     = 1.00;

    // Pitch gear used the pitch diameter of a 10-tooth and 42-tooth sprocket
    // GoBilda 30 RPM
    private static final double pitchP      = 0.0015;
    private static final double extensionP  = 0.005;

    public int pitchTargetDegree = PITCH_DEFAULT;
    public int extensionTarget = 0;
    private boolean extensionAuto = false;

    // -------------------------------------------------------------------------------------------

    public ArmSubsystem(final HardwareMap hMap)
    {
        armPitch = new MotorEx(hMap, "motorArmPitch", Motor.GoBILDA.RPM_30);
        armPitch.stopAndResetEncoder();
        armPitch.setRunMode(Motor.RunMode.PositionControl);
        armPitch.setInverted(true);
        armPitch.setPositionCoefficient(pitchP);
        armPitch.setPositionTolerance(PITCH_TICKS_DEGREE * 2);
        armPitch.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        armExtension = new MotorEx(hMap, "motorArmExtension", Motor.GoBILDA.RPM_117);
        armExtension.stopAndResetEncoder();
        armExtension.setRunMode(Motor.RunMode.PositionControl);
        armExtension.setPositionCoefficient(extensionP);
        armExtension.setPositionTolerance(50);
        armExtension.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    // -------------------------------------------------------------------------------------------

    @Override
    public void periodic()
    {
        armPitch.set(armPitch.atTargetPosition() ? 0 : PITCH_POWER);

        if (extensionAuto) {
            armExtension.set(armExtension.atTargetPosition() ? EXTENSION_POWERREST : EXTENSION_POWER);
        }
    }

    public void pitchStepUp() {
        int pos = Arrays.binarySearch(PITCH_STOPS, this.pitchTargetDegree);

        if (pos < PITCH_STOPS.length - 1) {
            this.pitchTargetDegree = PITCH_STOPS[pos + 1];
        }

        setTargetDegree(this.pitchTargetDegree);
    }

    public void pitchStepDown() {
        int pos = Arrays.binarySearch(PITCH_STOPS, this.pitchTargetDegree);

        if (pos >= 1) {
            this.pitchTargetDegree = PITCH_STOPS[pos - 1];
        }

        setTargetDegree(this.pitchTargetDegree);
    }

    public void setTargetDegree(int degree)
    {
        degree = MathUtils.clamp(degree, PITCH_STOPS[0], PITCH_STOPS[PITCH_STOPS.length - 1]);

        // To go beyond 90 deg, you must be extended at least certain amount
        if (extensionTarget < EXTENSION_TILTBACK && degree > PITCH_DEFAULT) {
            degree = PITCH_DEFAULT;
        }
        // To go less than 45 deg, you must not be too long
        if (degree < 45 && extensionTarget > EXTENSION_HMAX) {
            setExtension(EXTENSION_HMAX);
        }

        this.pitchTargetDegree = degree;
        armPitch.setTargetPosition((int) ((PITCH_DEFAULT - degree) * PITCH_TICKS_DEGREE));
    }

    public void extensionPower(double power, boolean checkBounds)
    {
        final int min = (pitchTargetDegree < PITCH_DEFAULT) ? EXTENSION_HMIN : EXTENSION_VMIN;
        final int max = (pitchTargetDegree < 45) ? EXTENSION_HMAX : EXTENSION_VMAX;

        if (power > 0) {
            extensionAuto = false;
            extensionTarget = checkBounds ? max : max * 2;
            armExtension.setTargetPosition(extensionTarget);
        } else if (power < 0) {
            extensionAuto = false;
            extensionTarget = checkBounds ?
                    ((pitchTargetDegree > PITCH_DEFAULT) ? EXTENSION_TILTBACK : min) :
                    -max;
            armExtension.setTargetPosition(extensionTarget);
        } else if (power == 0) {
            extensionTarget = armExtension.getCurrentPosition();
        }

        if (!extensionAuto) {
            armExtension.set(Math.abs(power));
        }
    }

    public boolean pitchAtTarget() {
        return armPitch.atTargetPosition();
    }

    public boolean extensionAtTarget() {
        return armExtension.atTargetPosition();
    }

    public int getCurrentExtension() {
        return armExtension.getCurrentPosition();
    }

    public void setExtension(int length)
    {
        extensionAuto = true;
        armExtension.setTargetPosition(length);
    }
}