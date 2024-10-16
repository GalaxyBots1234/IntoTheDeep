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
@Config
public class Arm2Subsystem extends SubsystemBase
{
    private static final int    PITCH_DEFAULT       = 95;
    private static final int    PITCH_MAX           = 100;
    private static final int    PITCH_MIN           = 0;
    private static final double PITCH_GEARING       = 25.9 / 107;
    private static final double PITCH_TICKS_DEGREE  = (Motor.GoBILDA.RPM_30.getCPR() / PITCH_GEARING) / 360.0;
    private static final int[]  PITCH_STOPS         = {0, 10, 70, 90, 95};

    private static final int    EXTENSION_MIN       = 0;
    private static final int    EXTENSION_MAX       = 13000;

    private MotorEx armPitch;
    private MotorEx armExtension;

    // Pitch gear used the pitch diameter of a 10-tooth and 42-tooth sprocket
    // GoBilda 30 RPM
    private final double pitchP = 0.0015, pitchI = 0.0, pitchD = 0.0;
    private double pitchF = 0;
    private int pitchTargetDegree = PITCH_DEFAULT;

    // P = 2 produces a strong vibration. At p = 0.1, the arm is not able to fully expand or close.
    public static double extensionP = 1, extensionI = 0.0, extensionD = 0.0;

    // -------------------------------------------------------------------------------------------

    public Arm2Subsystem(final HardwareMap hMap)
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
        armExtension.setRunMode(Motor.RunMode.VelocityControl);
        armExtension.setVeloCoefficients(extensionP, extensionI, extensionD);
        armExtension.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    // -------------------------------------------------------------------------------------------

    @Override
    public void periodic()
    {
        armPitch.set(armPitch.atTargetPosition() ? 0 : 0.75);
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

    public void setTargetDegree(int degree) {
        degree = MathUtils.clamp(degree, PITCH_MIN, PITCH_DEFAULT);

        this.pitchTargetDegree = degree;
        armPitch.setTargetPosition((int) ((PITCH_DEFAULT - degree) * PITCH_TICKS_DEGREE));
    }

    public void extensionPower(double power, boolean checkBounds)
    {
        int cp = armExtension.getCurrentPosition();
        double ms = Motor.GoBILDA.RPM_117.getAchievableMaxTicksPerSecond();

        if (checkBounds && power < 0 && cp < 1500) {
            ms *= cp / 1500.0;
        } else if (checkBounds && power > 0 && cp > EXTENSION_MAX - 1500) {
            ms *= (EXTENSION_MAX - cp) / 1500.0;
        }

        armExtension.setVeloCoefficients(extensionP, extensionI, extensionD);
        armExtension.setVelocity(power * ms);
    }

    public int getCurrentExtension() {
        return armExtension.getCurrentPosition();
    }

}