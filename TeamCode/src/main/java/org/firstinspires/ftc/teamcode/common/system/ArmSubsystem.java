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
public class ArmSubsystem extends SubsystemBase
{
    private static final int    PITCH_DEFAULT       = 90;
    // private static final int    PITCH_MAX           = 100;
    // private static final int    PITCH_MIN           = 0;
    private static final double PITCH_GEARING       = 25.9 / 107;
    private static final double PITCH_TICKS_DEGREE  = (Motor.GoBILDA.RPM_30.getCPR() / PITCH_GEARING) / 360.0;
    private static final int[]  PITCH_STOPS         = {0, 15, 80, 90, 105};

    // 400 units is the right about of negative move it can take at starting position.

    private static final int    EXTENSION_MIN       = 0;
    private static final int    EXTENSION_MAX       = 13200;

    private MotorEx armPitch;
    private MotorEx armExtension;

    // Pitch gear used the pitch diameter of a 10-tooth and 42-tooth sprocket
    // GoBilda 30 RPM
    private final double pitchP = 0.0015, pitchI = 0.0, pitchD = 0.0;
    private double pitchF = 0;
    public int pitchTargetDegree = PITCH_DEFAULT;
    public int extensionTarget = 0;

    // P = 2 produces a strong vibration. At p = 0.1, the arm is not able to fully expand or close.
    public static double extensionP = .004, extensionI = 0.0, extensionD = 0.0;

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
        // armExtension.setRunMode(Motor.RunMode.VelocityControl);
        // armExtension.setVeloCoefficients(extensionP, extensionI, extensionD);
        armExtension.setRunMode(Motor.RunMode.PositionControl);
        armExtension.setPositionCoefficient(extensionP);
        armExtension.setPositionTolerance(50);
        armExtension.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    // -------------------------------------------------------------------------------------------

    @Override
    public void periodic()
    {
        armPitch.set(armPitch.atTargetPosition() ? 0 : 0.75);
        armExtension.set(armExtension.atTargetPosition() ? 0 : 1);
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

        // To go byeond 90 deg, you must be extended at least 4000.
        if (extensionTarget < 2000 && degree > 90) {
            degree = 90;
        }

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

    /**
     * Ideally get rid of this method. The joystick should control power, and we should
     * set the target to the extremes. User will be able to control power, system will control
     * bounds.
     *
     * @param length
     * @param checkBounds
     */
    public void changeExtension(int length, boolean checkBounds)
    {
        if (length == 0 && armExtension.atTargetPosition()) {
            return;
        } else if (length == 0) {
            extensionTarget = armExtension.getCurrentPosition();
            armExtension.setTargetPosition(extensionTarget);
            return;
        }

        int cp = extensionTarget + length;

        if (extensionI == 0 && checkBounds) {
            cp = MathUtils.clamp(cp,
                    (pitchTargetDegree > 90) ? 4000: EXTENSION_MIN,
                    EXTENSION_MAX);
        }

        extensionTarget = cp;

        // Need this only when tuning
        // armExtension.setPositionCoefficient(extensionP);
        armExtension.setTargetPosition(extensionTarget);
    }

    public int getCurrentExtension() {
        return armExtension.getCurrentPosition();
    }
}