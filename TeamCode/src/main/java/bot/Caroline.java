package bot;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Caroline {
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

    public double servoSlidesRetract = 0.6;    //rough tuned
    public double servoSlidesExtend = .25;       //rough tuned


    public double largeHingeSampleIntake = .3;   //rough tuned
    public double smallHingeSampleIntake = .69;    //rough tuned


    public double largeHingeSpecimenIntake = 0.5;
    public double smallHingeSpecimenIntake = 0.595;


    public double highLargeHingeSampleDeposit = .82;
    public double highSmallHingeSampleDeposit = .84;


    public double lowLargeHingeSpecimenDeposit = .82;
    public double lowSmallHingeSpecimenDeposit = .825;

    public double largeHingeSpecimenDeposit = .8;
    public double smallHingeSpecimenDeposit = .5;

    public double highLargeHingeSpecimenScore = .9;
    public double highSmallHingeSpecimenScore = .65;
    public double lowLargeHingeSpecimenScore = .94;
    public double lowSmallSpecimenScore = .9;


    public double SmallHingeSampleDeposit = 0.0;
    public double secondHingeServoPositionHigh = 1.0;


    public double specimenClawOpen = .76;
    public double sampleClawOpen = .67;
    public double clawCloseSample = .57;
    public double clawCloseSpecimen = .5;


    public double intakeWheelSpeed = -1.0; // Range 0.0 to 1.0

    // Declare mode flags
    private boolean specimenMode = true;
    private boolean sampleMode = false;
    private boolean servoSlidesExtended = false;
    private boolean isIntaking = false;
    private boolean clawClosed = true;
    private boolean isArmMoving = false;
    private boolean isDepositing = false;
    private boolean verticalSlidesFullExtended = false;

    public Caroline(HardwareMap hardwareMap) {

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

    public Action specimenDepositAuto() {
        specimenDeposit();
        return null;
    }



    private void moveLargeHinge(double position) {
        largeHingeServoLeft.setPosition(position);
        largeHingeServoRight.setPosition(position);
    }

    // Method to move the second hinge to a specified position
    private void moveSmallHinge(double position) {
        secondHingeServoLeft.setPosition(position);
        secondHingeServoRight.setPosition(position);
    }

    private void moveHorizontalSlides(double position) {
        slideServoLeft.setPosition(position);
        slideServoRight.setPosition(position);
        if (position == servoSlidesRetract) {
            servoSlidesExtended = false;
        }
        if (position == servoSlidesExtend) {
            servoSlidesExtended = true;
        }
    }


    private void scoreHighSpecimen() {
        largeHingeServoLeft.setPosition(highLargeHingeSpecimenScore);
        largeHingeServoRight.setPosition(highLargeHingeSpecimenScore);
        moveSmallHinge(highSmallHingeSpecimenScore);
        isDepositing = false;

    }


    public void OpeningMove() {
        moveLargeHinge(.5);
        moveSmallHinge(.5);
        wristServo.setPosition(.05);
        closeClaw();
        moveHorizontalSlides(servoSlidesRetract);
        isIntaking = false;
    }

    public void sampleIntake() {
        moveSmallHinge(smallHingeSampleIntake);
        largeHingeServoLeft.setPosition(largeHingeSampleIntake);
        largeHingeServoRight.setPosition(largeHingeSampleIntake);
        isIntaking = true;
    }

    public void runIntake() {
        intakeServoLeft.setPower(-intakeWheelSpeed);
        intakeServoRight.setPower(intakeWheelSpeed);
        clawServo.setPosition(sampleClawOpen);
        clawClosed = false;
    }

    public void reverseIntake() {
        intakeServoLeft.setPower(intakeWheelSpeed);
        intakeServoRight.setPower(-intakeWheelSpeed);
        sampleClawOpen = .76;
        clawServo.setPosition(sampleClawOpen);
        clawClosed = false;
    }

    public void stopIntake() {
        intakeServoLeft.setPower(0);
        intakeServoRight.setPower(0);
        clawServo.setPosition(clawCloseSample);
        clawClosed = true;
        sampleClawOpen = .67;
    }


    public void specimenIntake() {
        moveSmallHinge(smallHingeSpecimenIntake);
        moveLargeHinge(largeHingeSpecimenIntake);
        clawServo.setPosition(specimenClawOpen);
        clawClosed = false;
        isIntaking = true;
    }

    public void scoreLowSpecimen() {
        moveSmallHinge(lowSmallSpecimenScore);
        largeHingeServoLeft.setPosition(lowLargeHingeSpecimenScore);
        largeHingeServoRight.setPosition(lowLargeHingeSpecimenScore);
        isDepositing = false;
    }

    public void closeClaw() {

    }

    public void specimenDeposit() {
        if (clawClosed) {
            moveLargeHinge(largeHingeSpecimenDeposit);
            moveSmallHinge(smallHingeSpecimenDeposit);
            moveHorizontalSlides(servoSlidesRetract);
            servoSlidesExtended = false;
            isDepositing = true;
            isIntaking = false;
            wristServo.setPosition(.772);
        }

    }

    public void sampleDeposit() {
        moveHorizontalSlides(servoSlidesRetract);
        moveLargeHinge(highLargeHingeSampleDeposit);
        moveSmallHinge(SmallHingeSampleDeposit);
        isIntaking = false;
        wristServo.setPosition(.772);
    }
}


