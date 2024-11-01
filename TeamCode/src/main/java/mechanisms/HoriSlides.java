package mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HoriSlides {

    private ServoImplEx servoLeft;
    private ServoImplEx servoRight;
    private Telemetry telemetry;

    public HoriSlides(HardwareMap hardwareMap, Telemetry telemetry) {
        servoLeft = (ServoImplEx) hardwareMap.servo.get("servoLeft");
        servoRight = (ServoImplEx) hardwareMap.servo.get("servoRight");
        servoRight.setDirection(Servo.Direction.REVERSE);
        this.telemetry = telemetry;
        in();
    }
    public HoriSlides(HardwareMap hardwareMap, Telemetry telemetry, boolean t) {
        servoLeft = (ServoImplEx) hardwareMap.servo.get("servoLeft");
        servoRight = (ServoImplEx) hardwareMap.servo.get("servoRight");
        servoRight.setDirection(Servo.Direction.REVERSE);
        this.telemetry = telemetry;
    }

    public void manualOut() {
        servoLeft.setPosition(servoLeft.getPosition()-0.01);
        servoRight.setPosition(servoRight.getPosition()-0.01);
    }

    public void manualIn() {
        servoLeft.setPosition(servoLeft.getPosition()+0.01);
        servoRight.setPosition(servoRight.getPosition()+0.01);
    }

    public void out() {
        servoLeft.setPosition(-0.7);
        servoRight.setPosition(-0.7);
    }
    public void in() {
        servoLeft.setPosition(0.8);
        servoRight.setPosition(0.8);
    }

    public boolean isReset() {
        return servoLeft.getPosition() == 0.8;
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
