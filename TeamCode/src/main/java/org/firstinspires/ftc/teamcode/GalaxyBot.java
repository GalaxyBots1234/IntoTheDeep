package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class GalaxyBot {

    private DcMotor leftBack;
    private DcMotor leftFront;

    private DcMotor rightBack;
    private DcMotor rightFront;

    private DcMotor leftSlide;
    private DcMotor rightSlide;

    CRServo leftIntake;
    CRServo rightIntake;

    private Servo leftSwing;
    private Servo rightSwing;

//  private DcMotor rightSlide;
//   private DcMotor  leftSlide;

    private CRServo geckoIntake;

    public int swingStage = 0;
    public boolean clawOpen = false;

    public Servo leftSpin;
    public Servo rightSpin;

    private CRServo clawSpinner;
    private Servo claw;

    private HardwareMap hwMap;
    private ElapsedTime runtime = new ElapsedTime();

    public GalaxyBot(HardwareMap hwMap) {
        this.hwMap = hwMap;
        map();
    }

    private void map() {
        leftFront = hwMap.get(DcMotor.class, "left_front");
        rightFront = hwMap.get(DcMotor.class, "right_front");
        leftBack = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");

        leftIntake = hwMap.get(CRServo.class, "left_intake");
        rightIntake = hwMap.get(CRServo.class, "right_intake");

        leftSwing = hwMap.get(Servo.class, "left_swing");
        rightSwing = hwMap.get(Servo.class, "right_swing");


        rightSlide = hwMap.get(DcMotor.class,"right_slide");
        leftSlide = hwMap.get(DcMotor.class,"left_slide");

        geckoIntake = hwMap.get(CRServo.class, "gecko_intake");

        leftSpin = hwMap.get(Servo.class,"left_spin");
        rightSpin = hwMap.get(Servo.class, "right_spin");
        clawSpinner = hwMap.get(CRServo.class, "claw_spinner");

        claw = hwMap.get(Servo.class, "claw");

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        leftSpin.setDirection(Servo.Direction.REVERSE);

        leftIntake.setDirection(CRServo.Direction.REVERSE);
        leftSwing.setDirection(Servo.Direction.REVERSE);

       leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    public void drive(double speed) {
        leftBack.setPower(speed);
        leftFront.setPower(speed);
        rightBack.setPower(speed);
        rightFront.setPower(speed);
    }

    public void drive(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        leftBack.setPower(backLeftPower);
        leftFront.setPower(frontLeftPower);
        rightBack.setPower(backRightPower);
        rightFront.setPower(frontRightPower);
    }

    public void slideUp (float magnitude)
    {
        leftSlide.setPower(magnitude);
        rightSlide.setPower(magnitude);
    }
    public void slideDown(float magnitude)
    {
        leftSlide.setPower(-magnitude);
        rightSlide.setPower(-magnitude);
    }
    public void slideStop()
    {
        leftSlide.setPower(0.0);
        rightSlide.setPower(0.0);
    }

    public void intakeDetract()
    {
        leftIntake.setPower(0.8);
        rightIntake.setPower(0.8);
    }

    public void intakeStop()
    {
        leftIntake.setPower(0.0);
        rightIntake.setPower(0.0);
    }

    public void intakeExtend()
    {
        leftIntake.setPower(-0.8);
        rightIntake.setPower(-0.8);
    }

    public void clawOpen()
    {
        clawOpen = true;
        claw.setPosition(0.0);
    }

    public void clawClose()
    {
        clawOpen = false;
        claw.setPosition(0.4);
    }

    public void swingUp()
    {
        swingStage++;
    }

    public void swingDown()
    {
        swingStage--;
    }

    public void swingUpdate()
    {
        if (swingStage == -1)
        {
            leftSwing.setPosition(0.39);
            rightSwing.setPosition(0.39);
        }
        else if (swingStage == 0)
        {
            leftSwing.setPosition(0.54);
            rightSwing.setPosition(0.54);
        }
        else if (swingStage == 1)
        {
            leftSwing.setPosition(0.9);
            rightSwing.setPosition(0.9);
        }
    }

    public void geckoStart(double power)
    {
        geckoIntake.setPower(power);
    }

    public void geckoStop()
    {
        geckoIntake.setPower(0.0);
    }

    public void spinUp()
    {
        leftSpin.setPosition(0.9);
        rightSpin.setPosition(0.9);
    }

    public void spinDown()
    {
        leftSpin.setPosition(0.0);
        rightSpin.setPosition(0.0);
    }

    public void clawSpinUp()
    {
        clawSpinner.setPower(1.0);
    }

    public void clawSpinDown()
    {
        clawSpinner.setPower(-1.0);
    }

    public void clawSpinStop()
    {
        clawSpinner.setPower(0.0);
    }

    public double getElapsedTime() {
        return runtime.time(TimeUnit.MILLISECONDS);
    }

}