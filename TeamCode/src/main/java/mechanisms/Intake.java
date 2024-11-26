package mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private Telemetry telemetry;
    public ServoImplEx bigHingeLeft;
    public ServoImplEx bigHingeRight;
    public ServoImplEx smallHingeLeft;
    public ServoImplEx smallHingeRight;

    public ServoImplEx wrist;
    public ServoImplEx claw;
    public enum AutoActionModes {
        ARMDOWN,
        ARMUP,
        INTAKE,
        REST,
        DEPOSIT,
        OUTTAKE,
        INTAKEHALF
    }

    private final double downPosition = 0.19;
    private final double restPosition = 0.6;
    private final double depositPosition = 0.95;

    public Intake(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        smallHingeRight = (ServoImplEx) hardwareMap.servo.get("smallHingeRight");
        smallHingeLeft = (ServoImplEx) hardwareMap.servo.get("smallHingeLeft");
        smallHingeLeft.setDirection(ServoImplEx.Direction.REVERSE);

        bigHingeRight = (ServoImplEx) hardwareMap.servo.get("bigHingeRight");
        bigHingeLeft = (ServoImplEx) hardwareMap.servo.get("bigHingeLeft");
        bigHingeLeft.setDirection(ServoImplEx.Direction.REVERSE);

        wrist = (ServoImplEx) hardwareMap.servo.get("wrist");

        claw = (ServoImplEx) hardwareMap.servo.get("claw");

    }

    public void setSmallHinge(double a) {
        smallHingeRight.setPosition(a);
        smallHingeLeft.setPosition(a);
    }
    public void setBigHinge(double a) {
        bigHingeRight.setPosition(a);
        bigHingeLeft.setPosition(a);
    }

    public void restPosition() {
        setSmallHinge(0.3);
        setBigHinge(0.3);
    }

    public void intakePosition(boolean a) {
        setSmallHinge(0.3);
        setBigHinge(0.3);
        if (a) {
            horiWrist();
        } else {
            vertiWrist();
        }
    }

    public void samplePosition() {
        setSmallHinge(0.3);
        setBigHinge(0.3);
        horiWrist();
    }
    public void specimenPosition() {
        setSmallHinge(0.3);
        setBigHinge(0.3);
        horiWrist();
    }
    public void openClaw() {
        claw.setPosition(0.3);
    }

    public void closeClaw() {
        claw.setPosition(0.3);
    }

    public void horiWrist() {
        wrist.setPosition(0.3);
    }
    public void vertiWrist() {
        wrist.setPosition(0.3);
    }










    public class BoxAutoAction implements Action {
        private final AutoActionModes action;
        public BoxAutoAction(AutoActionModes action) {
            this.action = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            switch (action) {
                case ARMDOWN:
                    //hinge.setPosition(0.17);
                    break;
                case ARMUP:
                    //hinge.setPosition(restPosition);
                    break;
                case INTAKE:
                    //spin.setPower(-0.8);
                    break;
                case REST:
                    //rest();
                    break;
                case DEPOSIT:
                    //hinge.setPosition(depositPosition);
                    break;
                case OUTTAKE:
                    //outtake();
                    break;
                case INTAKEHALF:
                    //spin.setPower(-0.5);
                    break;
            }
            return false;
        }
    }
    public Action runBoxAuto(AutoActionModes action) {
        return new BoxAutoAction(action);
    }
}