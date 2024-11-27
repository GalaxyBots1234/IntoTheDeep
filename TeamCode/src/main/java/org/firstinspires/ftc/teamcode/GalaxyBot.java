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
    private CRServo leftIntake;
    private CRServo rightIntake;
    private DcMotor rightSlide;
    private DcMotor  leftSlide;
    private CRServo geckoIntake;
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
        rightSlide = hwMap.get(DcMotor.class,"right_slide" );
        leftSlide = hwMap.get(DcMotor.class,"left_slide" );
        geckoIntake = hwMap.get(CRServo.class, "gecko_intake");

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);

        leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);
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

    public void intakePushForward(double amount)
    {
        leftIntake.setPower(amount);
        rightIntake.setPower(amount);
    }
    public void intakePushBack(double amount)
    {
        leftIntake.setPower(-amount);
        rightIntake.setPower(-amount);
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
    public void geckoStart(double power)
    {
        geckoIntake.setPower(power);
    }
    public void geckoStop()
    {
        geckoIntake.setPower(0.0);
    }

    public double getElapsedTime() {
        return runtime.time(TimeUnit.MILLISECONDS);
    }

}