package bot;

import static TeleOp.BreachTeleOP.lowLargeHingeSpecimenDeposit;
import static TeleOp.BreachTeleOP.lowSmallHingeSpecimenDeposit;
import static TeleOp.BreachTeleOP.clawCloseSample;
import static TeleOp.BreachTeleOP.clawCloseSpecimen;
import static TeleOp.BreachTeleOP.intakeWheelSpeed;
import static TeleOp.BreachTeleOP.largeHingeSampleIntake;
import static TeleOp.BreachTeleOP.largeHingeSpecimenIntake;
import static TeleOp.BreachTeleOP.lowLargeHingeSpecimenScore;
import static TeleOp.BreachTeleOP.lowSmallSpecimenScore;
import static TeleOp.BreachTeleOP.sampleClawOpen;
import static TeleOp.BreachTeleOP.smallHingeSampleIntake;
import static TeleOp.BreachTeleOP.smallHingeSpecimenDeposit;
import static TeleOp.BreachTeleOP.smallHingeSpecimenIntake;
import static TeleOp.BreachTeleOP.specimenClawOpen;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Bot {

    private Servo clawServo;

    private Servo wristServo;
    private Servo slideServoLeft;
    private Servo slideServoRight;
    private Servo largeHingeServoLeft;
    private Servo largeHingeServoRight;
    private Servo secondHingeServoLeft;
    private Servo secondHingeServoRight;
    private CRServo intakeServoLeft;
    private CRServo intakeServoRight;

    public Bot(HardwareMap hardwareMap) {

        wristServo = hardwareMap.get(Servo.class, "wrist");
        slideServoLeft = hardwareMap.get(Servo.class, "servoLeft");
        slideServoRight = hardwareMap.get(Servo.class, "servoRight");
        largeHingeServoLeft = hardwareMap.get(Servo.class, "bigHingeLeft");
        largeHingeServoRight = hardwareMap.get(Servo.class, "bigHingeRight");
        secondHingeServoLeft = hardwareMap.get(Servo.class, "smallHingeLeft");
        secondHingeServoRight = hardwareMap.get(Servo.class, "smallHingeRight");
        intakeServoLeft = hardwareMap.get(CRServo.class, "spinLeft");
        intakeServoRight = hardwareMap.get(CRServo.class, "spinRight");
        clawServo = hardwareMap.get(Servo.class, "claw");

        //reverse mirrored Servos
        slideServoLeft.setDirection(Servo.Direction.REVERSE);
        largeHingeServoLeft.setDirection(Servo.Direction.REVERSE);
        secondHingeServoLeft.setDirection(Servo.Direction.REVERSE);

        moveSmallHinge(lowSmallHingeSpecimenDeposit);
        largeHingeServoLeft.setPosition(lowLargeHingeSpecimenDeposit);
        largeHingeServoRight.setPosition(lowLargeHingeSpecimenDeposit);
        clawServo.setPosition(clawCloseSpecimen);
        slideServoLeft.setPosition(0.5);
        slideServoRight.setPosition(0.5);
        wristServo.setPosition(.05);



    }



    public enum AutoActionModes {
        SETSLIDE,
        SPECIMENINTAKE,
        APPROACH,
        DEPOSITHIGH,
        DEPOSITLOW,
        OPENCLAW,
        CLOSECLAW,
        RUNINTAKE,
        STOPINTAKE,
        REST,
        SAMPLEINTAKEPOS,
        SPECIMENINTAKEPOS,
        SPECIALDEPOSITPOS,
        SPECIALDEPOSIT,
        HUMANDROP,
        ARMPUSHING, SequentialDeposit, SequentialScore, EXTAKE
    }



    private void moveSmallHinge(double position) {
        secondHingeServoLeft.setPosition(position);
        secondHingeServoRight.setPosition(position);
    }

    private void moveLargeHinge(double position) {
        largeHingeServoLeft.setPosition(position);
        largeHingeServoRight.setPosition(position);
    }

    private void moveHorizontalSlides(double position) {
        slideServoLeft.setPosition(position);
        slideServoRight.setPosition(position);
    }




    public class AutoActions implements Action {
        private final AutoActionModes action;
        private double slidePos = 0;
        public AutoActions(AutoActionModes action) {
            this.action = action;

        }
        public AutoActions(AutoActionModes action, double slidePos) {
            this.action = action;
            this.slidePos = slidePos;

        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            switch (action) {
                case APPROACH:
                    moveSmallHinge(smallHingeSpecimenDeposit);
                    largeHingeServoLeft.setPosition(.8);
                    largeHingeServoRight.setPosition(.8);
                    wristServo.setPosition(.755);
                case OPENCLAW:
                    clawServo.setPosition(specimenClawOpen);
                    break;
                case CLOSECLAW:
                    clawServo.setPosition(clawCloseSpecimen);
                    break;
                case SETSLIDE:
                    slideServoLeft.setPosition(slidePos);
                    slideServoRight.setPosition(slidePos);
                    break;
                case SPECIMENINTAKE:
                    moveSmallHinge(.8);
                    largeHingeServoLeft.setPosition(0);
                    largeHingeServoRight.setPosition(0);
                    wristServo.setPosition(.05);
                    break;
                case DEPOSITHIGH:
                    moveSmallHinge(lowSmallHingeSpecimenDeposit);
                    largeHingeServoLeft.setPosition(0.85);
                    largeHingeServoRight.setPosition(0.85);
                    break;
                case DEPOSITLOW:
                    moveSmallHinge(lowSmallSpecimenScore);
                    largeHingeServoLeft.setPosition(lowLargeHingeSpecimenScore);
                    largeHingeServoRight.setPosition(lowLargeHingeSpecimenScore);
                    break;
                case REST:
                    moveLargeHinge(.5);
                    moveSmallHinge(.5);
                    wristServo.setPosition(.05);
                    moveHorizontalSlides(0.6);
                    break;
                case RUNINTAKE:
                    intakeServoLeft.setPower(-intakeWheelSpeed);
                    intakeServoRight.setPower(intakeWheelSpeed);
                    clawServo.setPosition(sampleClawOpen);
                    break;
                case STOPINTAKE:
                    intakeServoLeft.setPower(0);
                    intakeServoRight.setPower(0);
                    clawServo.setPosition(clawCloseSample);
                    break;
                case SAMPLEINTAKEPOS:
                    moveSmallHinge(smallHingeSampleIntake);
                    moveLargeHinge(largeHingeSampleIntake);
                    wristServo.setPosition(.055);
                    break;
                case SPECIMENINTAKEPOS:
                    moveSmallHinge(0.595);
                    moveLargeHinge(0.5);
                    wristServo.setPosition(.05);
                    break;
                case SPECIALDEPOSITPOS:
                    moveSmallHinge(0.5);
                    moveLargeHinge(0.8);
                    wristServo.setPosition(.05);
                    break;
                case SPECIALDEPOSIT:
                    moveSmallHinge(.2);
                    moveLargeHinge(.8);
                    wristServo.setPosition(.05);
                    break;
                case SequentialDeposit:
                    moveSmallHinge(.54);
                    moveLargeHinge(.8);
                    wristServo.setPosition(.755);

                    break;
                case SequentialScore:
                    moveSmallHinge(.0);
                    moveLargeHinge(.5);
                    wristServo.setPosition(.755);
                    break;
                case HUMANDROP:
                    moveSmallHinge(.7);
                    largeHingeServoLeft.setPosition(lowLargeHingeSpecimenScore);
                    largeHingeServoRight.setPosition(lowLargeHingeSpecimenScore);
                    wristServo.setPosition(.05);
                    break;
                case EXTAKE:
                    intakeServoLeft.setPower(intakeWheelSpeed);
                    intakeServoRight.setPower(-intakeWheelSpeed);
                    clawServo.setPosition(sampleClawOpen);
                    break;
                case ARMPUSHING:
                    moveHorizontalSlides(0.6);
                    moveSmallHinge(.75);
                    largeHingeServoLeft.setPosition(.8);
                    largeHingeServoRight.setPosition(.8);
                    wristServo.setPosition(.05);
                    break;
            }
            return false;
        }
    }
    public Action runAutoAction(AutoActionModes action) {
        return new AutoActions(action);
    }
    public Action runAutoAction(AutoActionModes action, double a) {
        return new AutoActions(action, a);
    }
}


