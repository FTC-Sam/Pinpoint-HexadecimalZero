package mechanisms;

import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Box {
    private Telemetry telemetry;
    private CRServoImplEx spin;
    private ServoImplEx hinge;

    public Box(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        spin = (CRServoImplEx) hardwareMap.crservo.get("spin");
        hinge = (ServoImplEx) hardwareMap.servo.get("hinge");
        hinge.setPosition(0.75);
    }

    public void rest() {
        spin.setPower(-0.1);
    }
    public void restPosition() {
        if (hinge.getPosition() < 0.6) hinge.setPosition(hinge.getPosition()+0.005);
        else if (hinge.getPosition() > 0.6) hinge.setPosition(hinge.getPosition()-0.005);
        else hinge.setPosition(0.6);
    }

    public void intake() {
        spin.setPower(-1);
    }

    public void outtake() {
        spin.setPower(1);
    }
    public void downPosition() {
        if (hinge.getPosition() > 0.17) hinge.setPosition(hinge.getPosition()-0.005);
        else hinge.setPosition(0.17);
    }

    public void depositPosition() {
        if (hinge.getPosition() < 0.965) hinge.setPosition(hinge.getPosition()+0.005);
        else hinge.setPosition(0.965);
    }
    public void deposit() {
        spin.setPower(1);
    }
}