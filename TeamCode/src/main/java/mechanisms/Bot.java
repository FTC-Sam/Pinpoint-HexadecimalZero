package mechanisms;
/*
//import static TeleOp.BreachTeleOP.SmallHingeSpecimenDeposit;
import static TeleOp.BreachTeleOP.clawCloseSample;
import static TeleOp.BreachTeleOP.clawCloseSpecimen;
import static TeleOp.BreachTeleOP.highSmallHingeSampleDeposit;
import static TeleOp.BreachTeleOP.intakeWheelSpeed;
import static TeleOp.BreachTeleOP.largeHingeSampleIntake;
import static TeleOp.BreachTeleOP.largeHingeSpecimenDeposit;
import static TeleOp.BreachTeleOP.largeHingeSpecimenIntake;
//import static TeleOp.BreachTeleOP.largeHingeSpecimenScore;
import static TeleOp.BreachTeleOP.lowLargeHingeSpecimenScore;
import static TeleOp.BreachTeleOP.lowSmallSpecimenScore;
import static TeleOp.BreachTeleOP.sampleClawOpen;
import static TeleOp.BreachTeleOP.servoSlidesRetract;
import static TeleOp.BreachTeleOP.smallHingeSampleIntake;
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
    }



    public enum AutoActionModes {
        SETSLIDE,
        DEPOSITPOS,
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
        EXTAKE
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
                case DEPOSITPOS:
                    moveLargeHinge(largeHingeSpecimenDeposit);
                    moveHorizontalSlides(servoSlidesRetract);
                    moveLargeHinge(largeHingeSpecimenDeposit);
                    moveSmallHinge(SmallHingeSpecimenDeposit);
                    wristServo.setPosition(.772);
                    break;
                case DEPOSITHIGH:
                    largeHingeServoLeft.setPosition(largeHingeSpecimenScore);
                    largeHingeServoRight.setPosition(largeHingeSpecimenScore);
                    moveSmallHinge(highSmallHingeSampleDeposit);
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
                    moveHorizontalSlides(servoSlidesRetract);
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
                    wristServo.setPosition(.05);
                    break;
                case SPECIMENINTAKEPOS:
                    moveSmallHinge(smallHingeSpecimenIntake);
                    moveLargeHinge(largeHingeSpecimenIntake);
                    wristServo.setPosition(.05);
                    break;
                case SPECIALDEPOSITPOS:
                    moveSmallHinge(0.5);
                    moveLargeHinge(0.8);
                    wristServo.setPosition(.05);
                    break;
                case SPECIALDEPOSIT:
                    moveSmallHinge(.5);
                    moveLargeHinge(.5);
                    wristServo.setPosition(.05);
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
*/

