package mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class VertiSlides {
    private DcMotorEx slideLeft;
    private DcMotorEx slideRight;
    private int targetPos;
    private ElapsedTime timer = new ElapsedTime();
    private double integralSum = 0;
    private double lastError = 0;
    private Telemetry telemetry;

    public static double kpu = 0.005;
    public static double kpd = 0.0000003;

    public static double ki = 0.0000000000;
    public static double kd = 0.000000;

    public VertiSlides(HardwareMap hardwareMap, Telemetry telemetry) {
        slideLeft = (DcMotorEx) hardwareMap.dcMotor.get("slideLeft");
        slideRight = (DcMotorEx) hardwareMap.dcMotor.get("slideRight");

        slideLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        slideRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        slideRight.setDirection(DcMotorSimple.Direction.REVERSE);

        this.telemetry = telemetry;
        targetPos = 0;
    }

    public void update() {
        int tolerance = 50;
        if ((getCurrentPos() - targetPos) < -tolerance) {
            slideLeft.setPower(returnPowerUp());
            slideRight.setPower(returnPowerUp());
        }
        else if ((getCurrentPos() - targetPos) > tolerance) {
            slideLeft.setPower(returnPowerDown());
            slideRight.setPower(returnPowerDown());
        }
        else {
            slideLeft.setPower(0);
            slideRight.setPower(0);
        }
    }

    public void manualUp() {
        slideLeft.setPower(1);
        slideRight.setPower(1);
    } //when running manual, the update method needs to be halted

    public void manualDown() {
        slideLeft.setPower(-1);
        slideRight.setPower(-1);
    }

    public void powerZero() {
        slideLeft.setPower(0);
        slideRight.setPower(0);
    }

    public void setTargetPos(int targetPos) {
        this.targetPos = targetPos;
    }

    public int getCurrentPos() {
        return (Math.abs(slideRight.getCurrentPosition()));
    }
    public int getTargetPos() {
        return targetPos;
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

}