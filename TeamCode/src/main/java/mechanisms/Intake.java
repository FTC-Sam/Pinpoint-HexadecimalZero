package mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Intake {
    private Telemetry telemetry;
    public ServoImplEx bigHingeLeft;
    public ServoImplEx bigHingeRight;
    public ServoImplEx smallHingeLeft;
    public ServoImplEx smallHingeRight;

    public ServoImplEx wrist;
    public ServoImplEx claw;
    public CRServoImplEx spinRight;
    public CRServoImplEx spinLeft;
    public enum AutoActionModes {
        OPENCLAW,
        CLOSECLAW,
        INTAKE,
        REST,
        DEPOSIT,
        INTAKELOL,
        DEPOSITDEPOSIT,
        RESTLOL,
        FLIPWRISTDEPOSIT
    }

    private final double downPosition = 0.19;
    private final double restPosition = 0.6;
    private final double depositPosition = 0.95;
    public static double big_arm_intake = 0.08;

    public static double small_arm_rest = 1;

    public static double small_arm_intake = 0.55;

    public static double big_arm_rest = .2;

    public static double sigma = .61;

    public boolean ranAlready = false;

    public ElapsedTime smallHingeTimer;

    private double time = 0;



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

        spinRight = (CRServoImplEx) hardwareMap.crservo.get("spinRight");
        spinLeft = (CRServoImplEx) hardwareMap.crservo.get("spinLeft");
        spinLeft.setDirection(CRServoImplEx.Direction.REVERSE);




    }

    public Intake(HardwareMap hardwareMap, Telemetry telemetry, boolean t) {
        this.telemetry = telemetry;

        smallHingeRight = (ServoImplEx) hardwareMap.servo.get("smallHingeRight");
        smallHingeLeft = (ServoImplEx) hardwareMap.servo.get("smallHingeLeft");
        smallHingeLeft.setDirection(ServoImplEx.Direction.REVERSE);

        bigHingeRight = (ServoImplEx) hardwareMap.servo.get("bigHingeRight");
        bigHingeLeft = (ServoImplEx) hardwareMap.servo.get("bigHingeLeft");
        bigHingeLeft.setDirection(ServoImplEx.Direction.REVERSE);

        wrist = (ServoImplEx) hardwareMap.servo.get("wrist");

        claw = (ServoImplEx) hardwareMap.servo.get("claw");
        spinRight = (CRServoImplEx) hardwareMap.crservo.get("spinRight");
        spinLeft = (CRServoImplEx) hardwareMap.crservo.get("spinLeft");
        spinLeft.setDirection(CRServoImplEx.Direction.REVERSE);


        setBigHinge(0.1);
        setSmallHinge(0.85);
        horiWrist();

        closeClaw();

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
        setSmallHinge(small_arm_rest);
        if(!ranAlready){
            smallHingeTimer = new ElapsedTime();

            smallHingeTimer.reset();

            //smallHingeTimer.startTime();
            ranAlready = true;
        }

        if(ranAlready){
            time = smallHingeTimer.time();
        }

        horiWrist();
        if(time > .8) {
            ranAlready = false;
            setBigHinge(big_arm_rest);
            time = 0;
        }

    }



    public void intakePosition(boolean a) {
        setSmallHinge(small_arm_intake);
        setBigHinge(big_arm_intake);
        if (a) {
            horiWrist();
        } else {
            vertiWrist();
        }
    }

    public void samplePosition() {
        setBigHinge(0.6);
        setSmallHinge(0.6);
        horiWrist();
    }
    public void specimenPosition() {
        setSmallHinge(0.7);
        setBigHinge(0.6);
        horiWrist();
    }
    public void openClaw() {
        claw.setPosition(sigma);
    }

    public void closeClaw() {
        claw.setPosition(0.5);
    }

    public void horiWrist() {
        wrist.setPosition(0.05);
    }
    public void vertiWrist() {
        wrist.setPosition(0.5);
    }

    public void horiWristOtherWay() {
        wrist.setPosition(0.95);
    }

    public void spinIn() {
        spinLeft.setPower(1);
        spinRight.setPower(1);
    }

    public void spinOut() {
        spinLeft.setPower(-1);
        spinRight.setPower(-1);
    }

    public void spinStop() {
        spinLeft.setPower(0);
        spinRight.setPower(0);
    }










    public class BoxAutoAction implements Action {
        private final AutoActionModes action;
        public BoxAutoAction(AutoActionModes action) {
            this.action = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            switch (action) {
                case OPENCLAW:
                    openClaw();
                    break;
                case CLOSECLAW:
                    closeClaw();
                    break;
                case INTAKE:
                    setSmallHinge(0.4);
                    setBigHinge(-0.8);
                    break;
                case REST:
                    setSmallHinge(.5);
                    setBigHinge(0.1);
                    horiWrist();
                    break;
                case DEPOSIT:
                    setBigHinge(0.6);
                    setSmallHinge(0.65);
                    horiWrist();
                    break;
                case DEPOSITDEPOSIT:
                    setSmallHinge(0.6);
                    setBigHinge(1);
                    break;
                case INTAKELOL:
                    setSmallHinge(0.5);
                    setBigHinge(0.1);
                    break;
                case RESTLOL:
                    setBigHinge(0.1);
                    setSmallHinge(0.85);
                    horiWrist();
                    break;
                case FLIPWRISTDEPOSIT:
                    setBigHinge(0.6);
                    setSmallHinge(0.65);
                    wrist.setPosition(0.75);
                    break;
            }
            return false;
        }
    }
    public Action runBoxAuto(AutoActionModes action) {
        return new BoxAutoAction(action);
    }
}