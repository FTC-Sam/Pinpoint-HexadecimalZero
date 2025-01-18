package TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import bot.VertiSlides;

@TeleOp(name = "TeleV2")
@Config
public class BreachTeleOP extends LinearOpMode {

    // Declare variables for servos and CR servos
    public VertiSlides vertiSlides;


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

    // Declare mecanum drivetrain motors
    private DcMotor FL;
    private DcMotor FR;
    private DcMotor BL;
    private DcMotor BR;

    // Declare tunable variables for servo positions and speeds
    public static double servoSlidesRetract = 0.7;    //rough tuned
    public static double servoSlidesExtend = .25;       //rough tuned


    public static double largeHingeSampleIntake = .38;   //rough tuned
    public static double smallHingeSampleIntake = .65;    //rough tuned


    public static double largeHingeSpecimenIntake = 0.5;
    public static double smallHingeSpecimenIntake = 0.595;


    public static double highLargeHingeSampleDeposit = .82;
    public static double highSmallHingeSampleDeposit = .84;





    public static double lowLargeHingeSpecimenDeposit = .82;
    public static double lowSmallHingeSpecimenDeposit = .825;

    public static double largeHingeSpecimenDeposit = .8;
    public static double smallHingeSpecimenDeposit = .5;

    public static double highLargeHingeSpecimenScore = .9;
    public static double highSmallHingeSpecimenScore = .65;
    public static double lowLargeHingeSpecimenScore=.94;
    public static double lowSmallSpecimenScore=.9;


    public static double SmallHingeSampleDeposit = 0.0;
    public static double secondHingeServoPositionHigh = 1.0;





    public static double specimenClawOpen = .76;
    public static double sampleClawOpen = .72;
    public static double clawCloseSample = .57;
    public static double clawCloseSpecimen = .5;


    public static int slidesHighBucket=2000;

    public static int slidesLowBucket=900;

    public static int SlideDelayDuration=500;


    public static double intakeWheelSpeed = -1.0; // Range 0.0 to 1.0

    // Declare mode flags
    private boolean specimenMode = true;
    private boolean sampleMode = false;
    private boolean servoSlidesExtended = false;
    private boolean isIntaking = false;
    private boolean clawClosed = true;
    private boolean isArmMoving=false;
    private boolean isDepositing=false;

    private boolean justScored=false;

    private boolean isDelaying;
    private boolean verticalSlidesFullExtended=false;
    //private boolean verticalSlidesFullExtended=false;
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();

    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    //goofy ahh motion pathing;
    ElapsedTime timer = new ElapsedTime();

    ElapsedTime timerTwo = new ElapsedTime();
    private ElapsedTime time1 = new ElapsedTime();
    private boolean moveUp = false;
    double startPosition=0;
    double endPosition=0;
    long duration=0;
    boolean isTopDown = false;

    @Override
    public void runOpMode() {
        inizaltizeEverything();
        OpeningMove();
        waitForStart();

        while (opModeIsActive()) {
            updateGamepads();
            RunDriveBase();

            if(isDelaying)
            {
                delaySlides();
            }

            if(!previousGamepad2.a && currentGamepad2.a && !isTopDown) {
                isTopDown = true;
            }
            else if (!previousGamepad2.a && currentGamepad2.a && isTopDown) {
                isTopDown = false;
            }

            //slide Extension
            if (currentGamepad1.b && !previousGamepad1.b&&!isDepositing) {
                if (servoSlidesExtended) {
                    if (sampleMode) {
                        OpeningMove();
                    }
                    if (specimenMode&&clawClosed) {
                        specimenDeposit();

                    }
                    servoSlidesExtended = false;
                } else {
                    moveHorizontalSlides(servoSlidesExtend);
                    wristServo.setPosition(.05);
                    if(specimenMode)
                    {
                        specimenIntake();
                    }
                    servoSlidesExtended = true;
                }

            }
            if (currentGamepad1.dpad_up && !previousGamepad1.dpad_up) {
                if (specimenMode) {
                    specimenDeposit();
                    startDelaySlides(500);
                }
                if (sampleMode) {
                    sampleDeposit();
                }
                isDepositing=true;
            }
            if (currentGamepad1.dpad_down && !previousGamepad1.dpad_down)
            {
               // vertiSlides.setTargetPos(-40);
                if (sampleMode) {
                    OpeningMove();
                    //lower slides
                }
                if (specimenMode)
                {
                    if(justScored)
                    {
                        clawServo.setPosition(specimenClawOpen);
                        sleepNonBlocking(200);
                        moveLargeHingeWithPathing(.5,2000);
                        moveSmallHinge(.5);
                        justScored=false;
                    }
                    if(isDepositing) {
                        if (verticalSlidesFullExtended) {
                            scoreHighSpecimen();
                        } else {
                            scoreLowSpecimen();
                        }
                        isDepositing = false;
                        justScored=true;
                    }


                    else{
                        OpeningMove();

                    }
                }
            }

            if (gamepad1.a && !previousGamepad1.a) {
                if (isIntaking) {
                    if (sampleMode) {
                        sampleClawOpen = .76;
                    }
                    if (specimenMode) {
                        specimenDeposit();
                    }
                } else {
                    if (servoSlidesExtended) {
                        if (sampleMode) {
                            if (!isTopDown) {
                                sampleIntakeSide();
                            }
                            else {
                                sampleIntakeTop();
                            }
                        } else if (specimenMode) {
                            specimenIntake();
                        }
                    }
                }

            }
            if (gamepad1.right_bumper) {
                if (sampleMode) {
                    runIntake();
                }
                if (specimenMode) {
                    clawToggle();
                }
            } else if (gamepad1.left_bumper) {
                if (sampleMode) {
                    reverseIntake();
                }
            } else {
                if (sampleMode) {
                    stopIntake();
                }
            }
            // Toggle Specimen Mode on the second gamepad (e.g., D-Pad Up)
            if (currentGamepad1.start&&!previousGamepad1.start&&sampleMode) {
                specimenMode = !specimenMode;
                sampleMode = false; // Disable Sample Mode when switching to Specimen Mode
            }

            // Toggle Sample Mode on the second gamepad (e.g., D-Pad Down)
            if (currentGamepad1.share&&!previousGamepad1.share&&specimenMode) {
                sampleMode = !sampleMode;
                specimenMode = false; // Disable Specimen Mode when switching to Sample Mode
            }

            if(isArmMoving)
                servoMoveWithPathing();
            if(currentGamepad1.dpad_right&&!previousGamepad1.dpad_right)
            {
                if(servoSlidesExtended)
                {
                    startDelaySlides(500);
                    servoSlidesExtended=false;
                }
                if(specimenMode)
                {
                    moveLargeHingeWithPathing(lowLargeHingeSpecimenDeposit);
                    moveSmallHinge(lowSmallHingeSpecimenDeposit);
                }
                if(sampleMode)
                {
                    sampleDeposit();
                   // vertiSlides.setTargetPos(slidesLowBucket);
                }
                isDepositing=true;
                isIntaking = false;
            }

            if(time1.seconds()>5 && moveUp) {
                telemetry.addLine("Shits run");
                moveHorizontalSlides(servoSlidesRetract);
                moveUp = false;
            }




            // Send telemetry data for debugging
            telemetry.addData("Mode", specimenMode ? "Specimen Mode" : (sampleMode ? "Sample Mode" : "Normal"));
            telemetry.addData("Horizontal Slide Position", slideServoLeft.getPosition());
            telemetry.addData("Large Hinge Position", largeHingeServoLeft.getPosition());
            telemetry.addData("Small Hinge Position", secondHingeServoLeft.getPosition());
            telemetry.addData("Intake Wheel Speed", intakeWheelSpeed);
            telemetry.addData("Claw Position", clawServo.getPosition());
            telemetry.addData("IsIntaking", isIntaking);
            telemetry.addData("SlidesExtended", servoSlidesExtended);
            telemetry.addData("Claw Closed", clawClosed);
            telemetry.addData("Top down: ",isTopDown);

            telemetry.update();
        }
    }



    // Method to move the large hinge to a specified position
    private void moveLargeHingeWithPathing(double position) {
        startArmMovement(largeHingeServoLeft.getPosition(), position, 1200);
    }
    private void moveLargeHingeWithPathing(double position,int duration) {
        startArmMovement(largeHingeServoLeft.getPosition(), position, duration);
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

    public void RunDriveBase() {
        // Joystick values
        double y = -gamepad1.left_stick_y; // Reversed Y-axis for forward/backward
        double x = gamepad1.left_stick_x;  // X-axis for strafing
        double rx = gamepad1.right_stick_x; // Right stick for turning

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        // Calculate the mecanum motor powers
        double frontLeftPower = (y + x + 2 * rx) / denominator;
        double backLeftPower = (y - x + 2 * rx) / denominator;
        double frontRightPower = (y - x - 2 * rx) / denominator;
        double backRightPower = (y + x - 2 * rx) / denominator;

        // Cube the motor powers
        frontLeftPower = Math.pow(frontLeftPower, 3);
        frontRightPower = Math.pow(frontRightPower, 3);
        backLeftPower = Math.pow(backLeftPower, 3);
        backRightPower = Math.pow(backRightPower, 3);

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]

        // Calculate the maximum value of all the motor powers
        // The argument here is just an array separated into different lines
        double maxValue = getMax(new double[]{
                frontLeftPower,
                frontRightPower,
                backLeftPower,
                backRightPower
        });

        // Resize the motor power values
        if (maxValue > 1) {
            frontLeftPower /= maxValue;
            frontRightPower /= maxValue;
            backLeftPower /= maxValue;
            backRightPower /= maxValue;
        }
        if (gamepad1.right_trigger>.3||servoSlidesExtended||verticalSlidesFullExtended) {
            FL.setPower(frontLeftPower * .4);
            BL.setPower(backLeftPower * .4);
            FR.setPower(frontRightPower * .4);
            BR.setPower(backRightPower * .4);
        } else {
            FL.setPower(frontLeftPower);
            BL.setPower(backLeftPower);
            FR.setPower(frontRightPower);
            BR.setPower(backRightPower);
        }
    }

    private double getMax(double[] input) {
        double max = Integer.MIN_VALUE;
        for (double value : input) {
            if (Math.abs(value) > max) {
                max = Math.abs(value);
            }
        }
        return max;
    }

    private void scoreHighSpecimen()
    {
        largeHingeServoLeft.setPosition(highLargeHingeSpecimenScore);
        largeHingeServoRight.setPosition(highLargeHingeSpecimenScore);
        moveSmallHinge(highSmallHingeSpecimenScore);
        isDepositing=false;

    }


    public void inizaltizeEverything() {

        // Initialize servos
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
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

        // Initialize mecanum drivetrain motors
        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");


        FL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        FL.setDirection(DcMotorEx.Direction.REVERSE);
        BL.setDirection(DcMotorEx.Direction.REVERSE);

        FL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void OpeningMove() {

        moveLargeHinge(.5);
        moveSmallHinge(.5);
        wristServo.setPosition(.05);
        moveHorizontalSlides(servoSlidesRetract);
        isIntaking = false;




    }

    public void sampleIntakeSide() {
        moveSmallHinge(smallHingeSampleIntake);
        largeHingeServoLeft.setPosition(largeHingeSampleIntake);
        largeHingeServoRight.setPosition(largeHingeSampleIntake);
        isIntaking = true;
        vertiSlides.setTargetPos(0);
    }

    public void sampleIntakeTop() {
        moveSmallHinge(0);
        largeHingeServoLeft.setPosition(0.5);
        largeHingeServoRight.setPosition(0.5);
        isIntaking = true;
        vertiSlides.setTargetPos(0);
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
        sampleClawOpen = .7;
    }

    public void clawToggle() {
        if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper) {
            if (clawClosed) {
                clawServo.setPosition(specimenClawOpen);
                clawClosed = false;
            } else {
                clawServo.setPosition(clawCloseSpecimen);
                clawClosed = true;
            }
        }
    }

    public void specimenIntake() {
        moveSmallHinge(smallHingeSpecimenIntake);
        moveLargeHinge(largeHingeSpecimenIntake);
        clawServo.setPosition(specimenClawOpen);
        clawClosed = false;
        isIntaking = true;
    }
    public void scoreLowSpecimen()
    {
        moveSmallHinge(lowSmallSpecimenScore);
        largeHingeServoLeft.setPosition(lowLargeHingeSpecimenScore);
        largeHingeServoRight.setPosition(lowLargeHingeSpecimenScore);
        isDepositing=false;
    }
    public void closeClaw() {

    }

    public void specimenDeposit() {
        if (clawClosed) {
            if(!moveUp) {
                time1.reset();
                moveLargeHingeWithPathing(largeHingeSpecimenDeposit);
                moveSmallHinge(smallHingeSpecimenDeposit);
                moveUp = true;
            }

            servoSlidesExtended=false;
            isDepositing=true;
            isIntaking = false;

            //moveHorizontalSlides(1);
        }

    }

    public void sampleDeposit() {
        moveHorizontalSlides(servoSlidesRetract);
        moveLargeHingeWithPathing(highLargeHingeSampleDeposit);
        moveSmallHinge(SmallHingeSampleDeposit);
        isIntaking = false;
        wristServo.setPosition(.772);
      //  vertiSlides.setTargetPos(slidesHighBucket);
    }

    public void updateGamepads() {
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);

        // Copy current gamepad states
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);
    }

    private void startArmMovement(double sP, double eP, long d){
        timer.reset();
        isArmMoving=true;
        startPosition=sP;
        endPosition=eP;
        duration=d;
    }
    private void startDelaySlides(int duration)
    {
        timerTwo.reset();
        SlideDelayDuration=duration;
        delaySlides();
        isDelaying=true;
    }
    private void servoMoveWithPathing() {


        // Loop until the duration has elapsed
        if (timer.milliseconds() < duration) {
            // Calculate the fraction of time elapsed
            double fractionElapsed = timer.milliseconds() / duration;

            // Apply cubic easing for ease-out effect
            double easedFraction = cubicEaseOut(fractionElapsed);

            // Calculate the current position based on the eased fraction of time elapsed
            double currentPosition = startPosition + easedFraction * (endPosition - startPosition);

            // Set the servo to the current position
            largeHingeServoLeft.setPosition(currentPosition);
            largeHingeServoRight.setPosition(currentPosition);

            // Optionally add telemetry data to observe the process
            telemetry.addData("Current Position", currentPosition);
            telemetry.update();

            // Small delay to prevent overloading the CPU
            sleepNonBlocking(50); // Custom non-blocking sleep function
        } else {
            // Ensure servo reaches the exact end position
            isArmMoving = false;
            largeHingeServoRight.setPosition(endPosition);
            largeHingeServoLeft.setPosition(endPosition);
        }
    }
    private void delaySlides() {
        // Loop until the duration has elapsed
        if (timerTwo.milliseconds() < 500) {
            sleepNonBlocking(50); // Custom non-blocking sleep function
        } else {
            // Ensure servo reaches the exact end position
            isDelaying=false;
            moveHorizontalSlides(servoSlidesRetract);
            wristServo.setPosition(.772);
        }
    }


    // Cubic easing function for ease-out
    private double cubicEaseOut(double t) {
        return 1 - Math.pow(1 - t, 3);
    }

    // Custom non-blocking sleep function
    private void sleepNonBlocking(int milliseconds) {
        long endTime = System.currentTimeMillis() + milliseconds;
        while (System.currentTimeMillis() < endTime && opModeIsActive()) {
            idle(); // Yield control to allow other operations
        }
    }
}
















