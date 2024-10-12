package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
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
public class Arm2Subsystem extends SubsystemBase
{
    private static final int    PITCH_DEFAULT       = 95;
    private static final int    PITCH_MAX           = 100;
    private static final int    PITCH_MIN           = 0;
    private static final double PITCH_GEARING       = 25.9 / 107;
    private static final double PITCH_TICKS_DEGREE  = (Motor.GoBILDA.RPM_30.getCPR() / PITCH_GEARING) / 360.0;
    private static final int[]  PITCH_STOPS         = {0, 10, 70, 90, 95};

    private static final int    EXTENSION_MIN       = 0;
    private static final int    EXTENSION_MAX       = 14000;

    private MotorEx armPitch;
    public MotorEx armExtension;

    private PIDFController pitchC;
    private PIDFController extensionC;

    // Pitch gear used the pitch diameter of a 10-tooth and 42-tooth sprocket
    // GoBilda 30 RPM
    private final double pitchP = 0.0015, pitchI = 0.0, pitchD = 0.0;
    private double pitchF = 0;
    private int pitchTargetDegree = PITCH_DEFAULT;

    private final double extensionP = 2, extensionI = 0.0, extensionD = 0.0;

    // -------------------------------------------------------------------------------------------

    public Arm2Subsystem(final HardwareMap hMap) {
        pitchC = new PIDFController(pitchP, pitchI, pitchD, pitchF);

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
    public void periodic() {
        /*int pPos = armPitch.getCurrentPosition();
        double ff = 0; // Math.cos(Math.toRadians(pitchTarget / pitchTicksDegree)) * pitchF;

        pitchC.setPIDF(pitchP, pitchI ,pitchD, ff);
        double power = pitchC.calculate(pPos, pitchTarget);
        armPitch.set(power);

        System.out.println("arm2ss: " + power);*/

        armPitch.set(armPitch.atTargetPosition() ? 0 : 0.75);

        //   System.out.println("sam was here " + robot.armActuator.getPosition() + " " + robot.armActuator.getTargetPosition());
        /*double kG = 0.18;
        double dist_cog_cor_max = 10.161;
        double arm_reference_angle = robot.armActuator.getTargetPosition();

        double offset = -(robot.armActuator.getPosition() / Math.PI) * 50;
        double extension = (liftTicks.getAsInt() + offset) / 26.0;

        double m_1 = 1.07;
        double m_2 = 0.36;
        double m_3 = 0.14;
        double m_4 = 0.44;

        double length_r = 1.3;
        double length_last = 6.0;

        double dist_cog = (m_2 * (extension + 1) + m_3 * (2 * extension - 1) + m_4 * (2 * extension + 0.5 * length_last)) / (m_1 + m_2 + m_3 + m_4);
        double dist_cog_cor = Math.sqrt(Math.pow(dist_cog, 2) + Math.pow(length_r, 2));

        double dist_ratio = dist_cog_cor / dist_cog_cor_max;

        feedforward = kG * Math.cos(arm_reference_angle) * dist_ratio;

        robot.armActuator.updateFeedforward(feedforward);
        robot.extensionActuator.setOffset(-(robot.armActuator.getPosition() / Math.PI) * 50);

        robot.armActuator.setCurrentPosition(armAngle.getAsDouble());
        robot.extensionActuator.setCurrentPosition(liftTicks.getAsInt());

        double error = robot.extensionActuator.getOverallTargetPosition() - robot.extensionActuator.getPosition();
        double feedforward = 0.1 * Math.abs(Math.cos(robot.armActuator.getPosition())) * Math.signum(error);


//        robot.extensionActuator.updateFeedforward(Math.abs(error) > 10 ? feedforward : 0);*/

        //robot.armActuator.periodic();
        //robot.extensionActuator.periodic();
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

    public void extensionPower(double power)
    {
        int cp = armExtension.getCurrentPosition();
        double ms = Motor.GoBILDA.RPM_117.getAchievableMaxTicksPerSecond();

        System.out.print(power + " " + ms + " " + cp);

        if (power < 0 && cp < 1500) {
            ms *= cp / 1500.0;
        } else if (power > 0 && cp > EXTENSION_MAX - 1500) {
            ms *= (EXTENSION_MAX - cp) / 1500.0;
        }

        System.out.println(" " + power * ms);
        armExtension.setVelocity(power * ms);
    }
}

/*{
    private final RobotHardware robot = RobotHardware.getInstance();
    public IntSupplier liftTicks;
    public DoubleSupplier armAngle;

    public double feedforward = 0.0;

    public Arm2Subsystem() {
        this.liftTicks = () -> robot.intSubscriber(Sensors.SensorType.EXTENSION_ENCODER);
        this.armAngle = () -> robot.doubleSubscriber(Sensors.SensorType.ARM_ENCODER);
    }

    @Override
    public void periodic()
    {
        System.out.println("sam was here " + robot.armActuator.getPosition() + " " + robot.armActuator.getTargetPosition());
        /*double kG = 0.18;
        double dist_cog_cor_max = 10.161;
        double arm_reference_angle = robot.armActuator.getTargetPosition();

        double offset = -(robot.armActuator.getPosition() / Math.PI) * 50;
        double extension = (liftTicks.getAsInt() + offset) / 26.0;

        double m_1 = 1.07;
        double m_2 = 0.36;
        double m_3 = 0.14;
        double m_4 = 0.44;

        double length_r = 1.3;
        double length_last = 6.0;

        double dist_cog = (m_2 * (extension + 1) + m_3 * (2 * extension - 1) + m_4 * (2 * extension + 0.5 * length_last)) / (m_1 + m_2 + m_3 + m_4);
        double dist_cog_cor = Math.sqrt(Math.pow(dist_cog, 2) + Math.pow(length_r, 2));

        double dist_ratio = dist_cog_cor / dist_cog_cor_max;

        feedforward = kG * Math.cos(arm_reference_angle) * dist_ratio;

        robot.armActuator.updateFeedforward(feedforward);
        robot.extensionActuator.setOffset(-(robot.armActuator.getPosition() / Math.PI) * 50);

        robot.armActuator.setCurrentPosition(armAngle.getAsDouble());
        robot.extensionActuator.setCurrentPosition(liftTicks.getAsInt());

        double error = robot.extensionActuator.getOverallTargetPosition() - robot.extensionActuator.getPosition();
        double feedforward = 0.1 * Math.abs(Math.cos(robot.armActuator.getPosition())) * Math.signum(error);


//        robot.extensionActuator.updateFeedforward(Math.abs(error) > 10 ? feedforward : 0);* /

        robot.armActuator.periodic();
        robot.extensionActuator.periodic();
    }

    @Override
    public void read() {
        robot.armActuator.read();
    }

    @Override
    public void write() {
        robot.armActuator.write();
        robot.extensionActuator.write();
    }

    @Override
    public void reset() {

    }
}*/
