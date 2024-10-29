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
        hinge.setPosition(0.45);
    }

    public void rest() {
        spin.setPower(-0.1);
        if (hinge.getPosition() < 0.65) hinge.setPosition(hinge.getPosition()+0.005);
        else if (hinge.getPosition() > 0.65) hinge.setPosition(hinge.getPosition()-0.005);
        else hinge.setPosition(0.65);
    }

    public void intake() {
        if (hinge.getPosition() > 0.25) hinge.setPosition(hinge.getPosition()-0.005);
        else hinge.setPosition(0.25);
        spin.setPower(-1);
    }

    public void outtake() {
        if (hinge.getPosition() > 0.25) hinge.setPosition(hinge.getPosition()-0.005);
        else hinge.setPosition(0.25);
        spin.setPower(1);
    }

    public void depositPosition() {
        if (hinge.getPosition() < 0.99) hinge.setPosition(hinge.getPosition()+0.005);
        else hinge.setPosition(0.99);
    }
    public void deposit() {
        spin.setPower(1);
    }

    public boolean isDepositPosition() {
        return hinge.getPosition() == 0.99;
    }
}