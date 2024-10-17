package org.firstinspires.ftc.teamcode.common.system;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
/*import org.firstinspires.ftc.teamcode.common.drive.localizer.AprilTagConstants;
import org.firstinspires.ftc.teamcode.common.drive.localizer.FusedLocalizer;
import org.firstinspires.ftc.teamcode.common.drive.Pose;
import org.firstinspires.ftc.teamcode.common.subsystem.DroneSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.HangSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;*/
/*import org.firstinspires.ftc.teamcode.common.vision.PreloadDetectionPipeline;*/
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import javax.annotation.concurrent.GuardedBy;

@Config
public class RobotHardware {

    //drivetrain
    public DcMotorEx dtFrontLeftMotor;
    public DcMotorEx dtFrontRightMotor;
    public DcMotorEx dtBackLeftMotor;
    public DcMotorEx dtBackRightMotor;

    // extension
    // public WEncoder armPitchEncoder;
    // public AnalogInput armPitchEnc;

    public AnalogInput leftDistSensor;
    public AnalogInput rightDistSensor;

    public DcMotorEx extensionMotor;
    // public DcMotorEx armMotor;

    public CRServoImplEx leftHang;
    public CRServoImplEx rightHang;

    private HardwareMap hardwareMap;

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;

    private ElapsedTime voltageTimer = new ElapsedTime();
    private double voltage = 12.0;

    private static RobotHardware instance = null;
    private boolean enabled;

    public List<LynxModule> modules;
    public LynxModule CONTROL_HUB;

    private final Object imuLock = new Object();
    @GuardedBy("imuLock")
    public BNO055IMU imu;
    private Thread imuThread;
    private double imuAngle = 0;
    public double imuOffset = 0;
    private double startOffset = 0;
    // public FusedLocalizer localizer;

    public static RobotHardware getInstance() {
        if (instance == null) {
            instance = new RobotHardware();
        }
        instance.enabled = true;
        return instance;
    }

    /**
     * Created at the start of every OpMode.
     *
     * @param hardwareMap The HardwareMap of the robot, storing all hardware devices
     */
    public void init(final HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        // DRIVETRAIN
        this.dtBackLeftMotor = hardwareMap.get(DcMotorEx.class, "leftBackMotor");
        dtBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dtBackLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.dtFrontLeftMotor = hardwareMap.get(DcMotorEx.class, "leftFrontMotor");
        dtFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dtFrontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.dtBackRightMotor = hardwareMap.get(DcMotorEx.class, "rightBackMotor");
        dtBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.dtFrontRightMotor = hardwareMap.get(DcMotorEx.class, "rightFrontMotor");
        dtFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // UWUXTENSION
        extensionMotor = hardwareMap.get(DcMotorEx.class, "motorArmExtension");
        /*armMotor = hardwareMap.get(DcMotorEx.class, "motorArmPitch");
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
        // armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        /* armMotor = new Motor(hardwareMap, "motorArmPitch", Motor.GoBILDA.RPM_30);
        armMotor.stopAndResetEncoder();
        armMotor.setRunMode(Motor.RunMode.PositionControl);
        //armMotor.setPositionCoefficient(10);
        armMotor.setVeloCoefficients(0.01, 0.0025, 0.005);
        armMotor.setPositionTolerance(20);
        armMotor.set(0);
        //armMotor.setVeloCoefficients(1.75, 0, 0.05);*/

        /*this.leftDistSensor = hardwareMap.get(AnalogInput.class, "leftDist");
        this.rightDistSensor = hardwareMap.get(AnalogInput.class, "rightDist");*/

        /**
         * Main Robot - Arm Pitch
         * - 30 RPM,
         * - Range = 0 (-5 degrees) to
         *      -  450 (0 degrees)
         *      - 5700 (90 degrees)
         * - BEST P = 0.0015, I = 0, D = 0, F = 0.00001
         * /
        this.armPitchEncoder = new WEncoder(new MotorEx(hardwareMap, "motorArmPitch").encoder);
        this.armActuator = new WActuatorGroup(armMotor, armPitchEncoder)
                .setPIDController(new PIDController(0.0015, 0, 0.0))
                // .setFeedforward(WActuatorGroup.FeedforwardMode.CONSTANT, 0.00001)
                .setErrorTolerance(50);

        /*armLiftServo = new WServo(hardwareMap.get(Servo.class, "lift"));

        // INTAKE
        intakeClawLeftServo = new WServo(hardwareMap.get(Servo.class, "servo1"));
        intakeClawRightServo = new WServo(hardwareMap.get(Servo.class, "servo2"));
        intakeClawRightServo.setDirection(Servo.Direction.REVERSE);

        this.intakePivotLeftServo = new WServo(hardwareMap.get(Servo.class, "servo3"));
        this.intakePivotRightServo = new WServo(hardwareMap.get(Servo.class, "servo4"));
        intakePivotRightServo.setOffset(-0.03);
        intakePivotLeftServo.setOffset(-0.045);
        intakePivotRightServo.setDirection(Servo.Direction.REVERSE);

        this.intakePivotActuator = new WActuatorGroup(intakePivotLeftServo, intakePivotRightServo);
        intakePivotActuator.setOffset(-0.06);
//        intakePivotActuator

        this.podLeft = new WEncoder(new MotorEx(hardwareMap, "dtBackLeftMotor").encoder);
        this.podFront = new WEncoder(new MotorEx(hardwareMap, "dtBackRightMotor").encoder);
        this.podRight = new WEncoder(new MotorEx(hardwareMap, "dtFrontRightMotor").encoder);
*/
        /*InverseKinematics.calculateTarget(3, 0);

        modules = hardwareMap.getAll(LynxModule.class);

        for (LynxModule m : modules) {
            m.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            if (m.isParent() && LynxConstants.isEmbeddedSerialNumber(m.getSerialNumber())) CONTROL_HUB = m;
        }*/

        /*intake = new IntakeSubsystem();
        if (Globals.IS_AUTO) {
            localizer = new FusedLocalizer();

            startCamera();

            synchronized (imuLock) {
                imu = hardwareMap.get(BNO055IMU.class, "imu");
                BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
                parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
                imu.initialize(parameters);
            }

            imuOffset = AngleUnit.normalizeRadians(imu.getAngularOrientation().firstAngle);
        }*/

        voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
    }

    public void read() {
    }

    public void write() {
    }

    public void periodic() {
//        if (voltageTimer.seconds() > 5) {
//            voltageTimer.reset();
//            voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
//        }
    }

    public void startIMUThread(LinearOpMode opMode) {
        imuThread = new Thread(() -> {
            while (!opMode.isStopRequested()) {
                synchronized (imuLock) {
                    imuAngle = AngleUnit.normalizeRadians(imu.getAngularOrientation().firstAngle);
                }
            }
        });
        imuThread.start();
    }

    public void readIMU() {
        imuAngle = AngleUnit.normalizeRadians(imu.getAngularOrientation().firstAngle);
    }

    public double getAngle() {
        return AngleUnit.normalizeRadians(imuAngle - imuOffset + startOffset);
    }

    public void reset() {
        imuOffset = imuAngle;
    }

    public void setIMUStartOffset(double off) {
        startOffset = off;
    }

    public void clearBulkCache() {
        CONTROL_HUB.clearBulkCache();
    }

    public double getVoltage() {
        return voltage;
    }

    /*public Pose getAprilTagPosition() {
        if (aprilTag != null && localizer != null) {
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();

            List<Pose> backdropPositions = new ArrayList<>();
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    switch (detection.id) {
                        case 1:
                        case 4:
                            backdropPositions.add(new Pose(detection.ftcPose).add(new Pose(6, 0, 0)));
                            break;
                        case 2:
                        case 5:
                            backdropPositions.add(new Pose(detection.ftcPose));
                            break;
                        case 3:
                        case 6:
                            backdropPositions.add(new Pose(detection.ftcPose).subt(new Pose(6, 0, 0)));
                            break;
                        default:
                            break;
                    }
                }
            }

            Pose backdropPosition = backdropPositions.stream().reduce(Pose::add).orElse(new Pose());
            backdropPosition = backdropPosition.scale(1.0 / backdropPositions.size());


            Pose globalTagPosition = localizer.getPose().x > 0 ?
                    AprilTagConstants.convertBlueBackdropPoseToGlobal(backdropPosition) :
                    AprilTagConstants.convertRedBackdropPoseToGlobal(backdropPosition);

            if (Double.isNaN(globalTagPosition.x) || Double.isNaN(globalTagPosition.y) || Double.isNaN(globalTagPosition.heading)) return null;

            return globalTagPosition;
        } else {
            return null;
        }
    }*/

    public List<AprilTagDetection> getAprilTagDetections() {
        /*if (aprilTag != null && localizer != null) return aprilTag.getDetections();*/
        return null;
    }

    public void startCamera() {
        aprilTag = new AprilTagProcessor.Builder()
                // calibrated using 3DF Zephyr 7.021
                .setLensIntrinsics(549.651, 549.651, 317.108, 236.644)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessors(aprilTag)
                .enableLiveView(false)
                .build();
    }

    public VisionPortal.CameraState getCameraState() {
        if (visionPortal != null) return visionPortal.getCameraState();
        return null;
    }

    public void closeCamera() {
        if (visionPortal != null) visionPortal.close();
    }

    public void kill() {
        instance = null;
    }

    public void setProcessorEnabled(VisionProcessor processor, boolean enabled) {
        this.visionPortal.setProcessorEnabled(processor, enabled);
    }
}
