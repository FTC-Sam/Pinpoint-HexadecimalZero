package mechanisms;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Crane { //I got rid of hardwareMap variable and wanna try it as a disposable
                     //constructor variable since it's only needed during initialization
    private Telemetry telemetry;
    public Box box;
    public HoriSlides horiSlides;
    public VertiSlides vertiSlides;
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private int down = 50;
    private int lowBucket = 2000;
    private int highBucket = 4700;
    private int lowBar = 500;
    private int highBar = 1500;
    private int climbHeight = 2000;
    private boolean isVertiManual = false; //stop pid when it's manual
    private enum CraneStates{
        EXTENSION,
        GROUND,
        CLIMB
    }
    private enum DepositState {
        SAMPLE,
        SPECIMEN
    }
    private ElapsedTime timer1 = new ElapsedTime();
    private ElapsedTime timer2 = new ElapsedTime();

    CraneStates currentState = CraneStates.GROUND;
    public DepositState currentDepositState = DepositState.SAMPLE;
    private double horiThreshold = 0;

    private boolean isArmDown = false;
    private boolean isArmButtonDown = false;


    public Crane(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {

        this.telemetry = telemetry;
        box = new Box(hardwareMap, this.telemetry, true);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry, true);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

    }

    public void executeTeleOp() {
        switch (currentState) {
            case GROUND:
                boxTake(); //intake outtake, ensures retraction of box if slides retract
                manualHoriSlides(); //slide manual
                presetHoriSlides(); //slide auto
                break;

            case EXTENSION:
                setArm(); //set arm preset according to slide auto preset, doesn't happen if slide position is set manually
                deposit();
                break;


        }
        manualVertiSlides(); //works any time
        presetVertiSlides(); //works only if horizontal slides retracted, meaning also not intake and outtake by logic check
        if (!isVertiManual) vertiSlides.update();
    }





    //all mode related

    public void presetVertiSlides() { //gamepad2 up, left, right, down, x
        if (horiSlides.isReset() && timer1.seconds() > 0.5) {
            if (gamepad2.dpad_up) {
                vertiSlides.setTargetPos(highBucket);
                currentState = CraneStates.EXTENSION;
                currentDepositState = DepositState.SAMPLE;
            }
            if (gamepad2.dpad_left) {
                vertiSlides.setTargetPos(lowBucket);
                currentState = CraneStates.EXTENSION;
                currentDepositState = DepositState.SAMPLE;
            }
            if (gamepad2.dpad_right) {
                vertiSlides.setTargetPos(highBar);
                currentState = CraneStates.EXTENSION;
                currentDepositState = DepositState.SPECIMEN;
            }
            /*if (gamepad2.x) {
                vertiSlides.setTargetPos(lowBar);
                currentState = CraneStates.EXTENSION;
                currentDepositState = DepositState.SPECIMEN;
            }*/
            if (gamepad2.dpad_down) {
                vertiSlides.setTargetPos(down);
                currentState = CraneStates.GROUND;
            }
        }
    }

    public void manualVertiSlides() { //gamepad2 right_stick_y
        if (-gamepad2.right_stick_y > 0.2) {
            vertiSlides.manualUp();
            isVertiManual = true;
        }
        else if (-gamepad2.right_stick_y < -0.2) {
            vertiSlides.manualDown();
            isVertiManual = true;
        }
        else if (isVertiManual){
            isVertiManual = false;
            vertiSlides.setTargetPos(vertiSlides.getCurrentPos());
        }
    }


    //---------------------------------------------------------------------------------------------
    //extension mode related

    public void setArm() {
        if (currentDepositState == DepositState.SAMPLE) { //can add and here to threshold arm flipping
            box.depositPosition();
        }
        if ((vertiSlides.getTargetPos() == down) || currentDepositState == DepositState.SPECIMEN) {
            box.rest();
            box.restPosition();
        }
    }

    public void deposit() { //gamepad2 a
        if (gamepad2.a) {
            if (currentDepositState == DepositState.SAMPLE) {
                box.deposit();
            }
            else {
                //claw code need edge detector
            }
        }
    }








    //---------------------------------------------------------------------------------------------
    //ground mode related
    public void manualHoriSlides() { //gamepad2 left_stick_y
        if (-gamepad2.left_stick_y > 0.2 && horiSlides.getPosition() > -0.65) {
            horiSlides.manualOut();
        }
        if (-gamepad2.left_stick_y < -0.2 && horiSlides.getPosition() < 0.75) {
            horiSlides.manualIn();
        }
    }
    public void boxTake() { //gamepad1 a, right bumper, left bumper
        if ((horiSlides.getPosition() <= horiThreshold) && timer2.seconds() > 0.3) {
            if (gamepad1.a && !isArmButtonDown && !isArmDown) {
                isArmDown = true;
                isArmButtonDown = true;
            }
            else if (gamepad1.a && !isArmButtonDown && isArmDown) {
                isArmDown = false;
                isArmButtonDown = true;
            }
            else if (!gamepad1.a) {
                isArmButtonDown = false;
            }


            if (isArmDown) {
                box.downPosition();
                if (gamepad1.right_bumper) {
                    box.intake();
                } else if (gamepad1.left_bumper) {
                    box.outtake();
                } else {
                    box.rest();
                }
            }
            else {
                box.rest();
                box.holdPosition();
            }
        }
        else {
            isArmDown = false;
            isArmButtonDown = false;
            box.rest();
            box.restPosition();
        }
    }




    public void presetHoriSlides() { //gamepad2 y, b
        if (gamepad2.b) {

            horiSlides.in();
            timer1.reset();
            timer1.startTime();

        }
        else if (gamepad2.y) {
            horiSlides.out();
            timer2.reset();
            timer2.startTime();

        }
    }
}
