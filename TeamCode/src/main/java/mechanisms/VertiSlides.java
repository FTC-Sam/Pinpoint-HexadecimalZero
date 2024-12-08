package mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class VertiSlides {
    private DcMotorEx slideTop;
    private DcMotorEx slideMid;
    private DcMotorEx slideBot;
    private DcMotorEx climb;

    private int targetPos;

    private int climbTargetPos;

    private double kpClimbUp;

    private double kpClimbDown;
    private ElapsedTime timer = new ElapsedTime();
    private double integralSum = 0;
    private double lastError = 0;
    private Telemetry telemetry;

    public static double kpu = 0.005;
    public static double kpd = 0.0000002;

    public static double ki = 0.0000000000;
    public static double kd = 0.000000;

    public VertiSlides(HardwareMap hardwareMap, Telemetry telemetry) {
        slideTop = (DcMotorEx) hardwareMap.dcMotor.get("slideTop");
        slideMid = (DcMotorEx) hardwareMap.dcMotor.get("slideMid");
        slideBot = (DcMotorEx) hardwareMap.dcMotor.get("slideBot");
        climb = (DcMotorEx) hardwareMap.dcMotor.get("climb");


        slideTop.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slideMid.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slideBot.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        climb.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        slideTop.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMid.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideBot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        climb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideTop.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        slideMid.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        slideBot.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        climb.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //climb.setDirection(DcMotorSimple.Direction.REVERSE);



        slideMid.setDirection(DcMotorSimple.Direction.REVERSE); // leave this be


        this.telemetry = telemetry;
        targetPos = 0;
    }

    public void climbUpdate(){
        int tolerance = 0;
        if ((getClimbCurrentPos() - climbTargetPos) < -tolerance) {
            climb.setPower(returnClimbPower());
        }
        else if ((getClimbCurrentPos() - climbTargetPos) > tolerance) {
            climb.setPower(returnClimbPower());
        }
        else {
            climb.setPower(0);
        }
    }

    public void update() {
        int tolerance = 0;

        /*if ((getCurrentPos() - targetPos) < -200) {
            slideTop.setPower(1);
            slideMid.setPower(1);
            slideBot.setPower(1);
        }*/
        if ((getCurrentPos() - targetPos) < -tolerance) {
            slideTop.setPower(returnPowerUp());
            slideMid.setPower(returnPowerUp());
            slideBot.setPower(returnPowerUp());
        }
        else if ((getCurrentPos() - targetPos) > tolerance) {
            slideTop.setPower(returnPowerDown());
            slideMid.setPower(returnPowerDown());
            slideBot.setPower(returnPowerDown());
        }
        else {
            slideTop.setPower(0);
            slideMid.setPower(0);
            slideBot.setPower(0);

        }
    }

    public void climbManualUp(){
        telemetry.addData("climb encoder", climb.getCurrentPosition());
        telemetry.update();
        climb.setPower(1);
    }

    public void climbManualDown(){
        climb.setPower(-1);
    }

    public void setClimbPos(){
        climb.setTargetPosition(50);
    }


    public void reset() {
        slideTop.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMid.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideBot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideTop.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        slideMid.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        slideBot.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void manualUp() {
        slideTop.setPower(1);
        slideMid.setPower(1);
        slideBot.setPower(1);

    } //when running manual, the update method needs to be halted

    public void manualDown() {
        slideTop.setPower(-0.3);
        slideMid.setPower(-0.3);
        slideBot.setPower(-0.3);

    }
    public void powerZero() {
        slideTop.setPower(0);
        slideMid.setPower(0);
        slideBot.setPower(0);

    }

    public void manualClimbUp() {
        climb.setPower(1);
    }
    public void manualClimbDown() {
        climb.setPower(-1);
    }



    public void setTargetPos(int targetPos) {
        this.targetPos = targetPos;
    }

    public void setClimbTargetPos(int targetPos) {
        this.climbTargetPos = targetPos;
    }

    public int getCurrentPos() {
        return (Math.abs(slideTop.getCurrentPosition()));
    }

    public int getClimbCurrentPos() {
        return (Math.abs(climb.getCurrentPosition()));
    }

    public int getTargetPos() {
        return targetPos;
    }

    private double returnClimbPower(){
        double currentPos = getClimbCurrentPos();
        double error = climbTargetPos - currentPos;
        lastError = error;
        timer.reset();
        telemetry.addData("Slide Position", getClimbCurrentPos());
        telemetry.addData("climb target", climbTargetPos);
        telemetry.addData("error: ", error);
        telemetry.update();
        return ((error * kpu));
    }

    private double returnPowerUp(){
        double currentPos = getCurrentPos();
        double error = targetPos - currentPos;
        integralSum += error * timer.seconds();
        double derivative = (error - lastError)/ timer.seconds();
        lastError = error;
        timer.reset();
        telemetry.addData("Slide Position", getCurrentPos());

        telemetry.addData("error: ", error);
        telemetry.update();
        return ((error * kpu) + (derivative * kd) + (integralSum * ki));
    }

    private double returnPowerDown(){
        double currentPos = getCurrentPos();
        double error = targetPos - currentPos;
        integralSum += error * timer.seconds();
        double derivative = (error - lastError)/ timer.seconds();
        lastError = error;
        timer.reset();
        telemetry.addData("Slide Position", getCurrentPos());
        telemetry.addData("error: ", error);
        telemetry.update();
        return ((error * kpd) + (derivative * kd) + (integralSum * ki));
    }

    private void reset_encoder(){
        slideMid.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMid.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        targetPos = 0;
        slideMid.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }







    public class VertiSlidesAutoAction implements Action {
        private final int position;
        public VertiSlidesAutoAction(int position) {
            this.position = position;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            targetPos = position;
            return false;
        }
    }
    public Action runVertiSlidesAuto(int position) {
        return new VertiSlidesAutoAction(position);
    }


    public class VertiSlidesAutoUpdate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            update();
            return true;
        }
    }
    public Action updateVertiSlidesAuto() {
        return new VertiSlidesAutoUpdate();
    }

}