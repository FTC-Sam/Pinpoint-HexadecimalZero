package mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class HoriSlides {

    private ServoImplEx servoLeft;
    private ServoImplEx servoRight;
    private Telemetry telemetry;

    public static double out = 0.7;
    public static double in = 0.35;

    //public boolean in = true;

    public HoriSlides(HardwareMap hardwareMap, Telemetry telemetry) {
        servoLeft = (ServoImplEx) hardwareMap.servo.get("servoLeft");
        servoRight = (ServoImplEx) hardwareMap.servo.get("servoRight");
        servoRight.setDirection(Servo.Direction.REVERSE);
        this.telemetry = telemetry;
    }
    public HoriSlides(HardwareMap hardwareMap, Telemetry telemetry, boolean t) {
        servoLeft = (ServoImplEx) hardwareMap.servo.get("servoLeft");
        servoRight = (ServoImplEx) hardwareMap.servo.get("servoRight");
        servoRight.setDirection(Servo.Direction.REVERSE);
        this.telemetry = telemetry;
    }


    public void out() {
        servoLeft.setPosition(out);
        servoRight.setPosition(out);
        //in = false;
    }
    public void in() {
        servoLeft.setPosition(in);
        servoRight.setPosition(in);
        //in = true;
    }

    public boolean isReset() {
        return servoLeft.getPosition() < 0.4;
    }

    public double getPosition() {
        return servoLeft.getPosition();
    }

    public void setPosition(double a) {
        servoLeft.setPosition(a);
        servoRight.setPosition(a);
    }





    public class HoriSlidesAutoAction implements Action {
        private final double position;
        public HoriSlidesAutoAction(double position) {
            this.position = position;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            servoLeft.setPosition(position);
            servoRight.setPosition(position);
            return false;
        }
    }
    public Action runHoriSlidesAuto(double position) {
        return new HoriSlidesAutoAction(position);
    }
}
