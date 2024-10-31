package mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Box {
    private Telemetry telemetry;
    private CRServoImplEx spin;
    private ServoImplEx hinge;
    public enum AutoActionModes {
        ARMDOWN,
        ARMUP,
        INTAKE,
        REST
    }

    private final double downPosition = 0.17;
    private final double restPosition = 0.6;

    public Box(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        spin = (CRServoImplEx) hardwareMap.crservo.get("spin");
        hinge = (ServoImplEx) hardwareMap.servo.get("hinge");
        hinge.setPosition(0.75);
    }

    public void rest() {
        spin.setPower(0);
    }
    public void restPosition() {
        if (hinge.getPosition() < restPosition) hinge.setPosition(hinge.getPosition()+0.005);
        else if (hinge.getPosition() > restPosition) hinge.setPosition(hinge.getPosition()-0.005);
        else hinge.setPosition(restPosition);
    }

    public void intake() {
        spin.setPower(-1);
    }

    public void outtake() {
        spin.setPower(1);
    }
    public void downPosition() {
        if (hinge.getPosition() > downPosition) hinge.setPosition(hinge.getPosition()-0.005);
        else hinge.setPosition(downPosition);
    }

    public void depositPosition() {
        if (hinge.getPosition() < 0.965) hinge.setPosition(hinge.getPosition()+0.005);
        else hinge.setPosition(0.965);
    }
    public void deposit() {
        spin.setPower(1);
    }










    public class BoxAutoAction implements Action {
        private AutoActionModes action;
        public BoxAutoAction(AutoActionModes action) {
            this.action = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            switch (action) {
                case ARMDOWN:
                    hinge.setPosition(downPosition);
                    break;
                case ARMUP:
                    hinge.setPosition(restPosition);
                    break;
                case INTAKE:
                    intake();
                    break;
                case REST:
                    rest();
                    break;
            }
            return true;
        }
    }
    public Action runBox(AutoActionModes action) {
        return new BoxAutoAction(action);
    }
}