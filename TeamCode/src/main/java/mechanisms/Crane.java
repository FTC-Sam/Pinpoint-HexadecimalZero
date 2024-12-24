package mechanisms;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Crane { //I got rid of hardwareMap variable and wanna try it as a disposable
    //constructor variable since it's only needed during initialization
    private Telemetry telemetry;
    public Intake intake;
    public HoriSlides horiSlides;
    public VertiSlides vertiSlides;
    private Gamepad gamepad1;

    private Gamepad americanGirl= new Gamepad();

    private Gamepad Indra= new Gamepad();
    private Gamepad gamepad2;
    private int down = 50;

    private int lowBucket = 2000;
    private int highBucket = 4600;
    private int lowBar = 400;
    private int highBar = 900;
    private int climb_up = 200;

    private int climb_down = 50;
    private boolean isVertiManual = false; //stop pid when it's manual

    private boolean isHoriExtended=false;

    private boolean isClawClosed=true;
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
    private double horiThreshold = 0.6;

    private boolean isArmDown = false;
    private boolean isArmButtonDown = false;
    private boolean isWristButtonDown = false;
    private boolean wristMode = true;


    public Crane(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {

        this.telemetry = telemetry;
        intake = new Intake(hardwareMap, this.telemetry);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry, true);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        timer1.startTime();

    }

    public void executeTeleOp() {
        //fight me Sam
        americanGirl.copy(Indra);
        Indra.copy(gamepad1);

        switch (currentState) {
            case GROUND:
                setWristMode();
                intake(); //intake outtake, ensures retraction of box if slides retract
                presetHoriSlides(); //slide auto
                break;

            case EXTENSION:
                setArm(); //set arm preset according to slide auto preset, doesn't happen if slide position is set manually
                deposit();
                break;

            case CLIMB:
                if (gamepad1.right_bumper) {
                    vertiSlides.setClimbTargetPos(5000);
                } else if (gamepad1.left_bumper) {
                    vertiSlides.setClimbTargetPos(1560);
                }
                vertiSlides.climbUpdate();
                return;
        }
        manualVertiSlides(); //works any time
        presetVertiSlides(); //works only if horizontal slides retracted, meaning also not intake and outtake by logic check
        checkClimb();
        if (!isVertiManual) vertiSlides.update();

    }







    public void checkClimb() {
        if(gamepad1.start && gamepad1.back){

            telemetry.addLine("switching to climb");
            telemetry.update();
            currentState = CraneStates.CLIMB;
        }
    }
    //all mode related

    public void presetVertiSlides() { //gamepad2 up, left, right, down, x
        if (horiSlides.isReset() && timer1.seconds() > 0.5) {

            if (gamepad1.dpad_up) {
                telemetry.addLine("yep");
                telemetry.update();
                vertiSlides.setTargetPos(highBucket);
                currentState = CraneStates.EXTENSION;
                currentDepositState = DepositState.SAMPLE;
            }
            if (gamepad1.dpad_left) {
                vertiSlides.setTargetPos(lowBucket);
                currentState = CraneStates.EXTENSION;
                currentDepositState = DepositState.SAMPLE;
            }
            if (gamepad1.dpad_right) {
                vertiSlides.setTargetPos(highBar);
                currentState = CraneStates.EXTENSION;
                currentDepositState = DepositState.SPECIMEN;
            }
            if (gamepad1.dpad_down) {
                vertiSlides.setTargetPos(down);
                currentState = CraneStates.GROUND;
            }
        }
    }

    public void manualVertiSlides() { //gamepad2 right_stick_y
        if (-gamepad1.right_trigger > 0.2) {
            vertiSlides.manualUp();
            isVertiManual = true;
        }
        else if (-gamepad1.left_trigger < -0.2) {
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
            intake.samplePosition();

        }
        if (currentDepositState == DepositState.SPECIMEN) {
            intake.specimenPosition();
        }
        if (vertiSlides.getTargetPos() == down) {
            intake.closeClaw();
            isClawClosed=true;
            intake.restPosition();
        }
    }

    public void deposit() { //gamepad2 a
        if (gamepad1.right_bumper&&!americanGirl.right_bumper&&!isClawClosed) {
            intake.closeClaw();
            isClawClosed=!isClawClosed;
        } else if (gamepad1.right_bumper&&!americanGirl.right_bumper&&isClawClosed) {
            intake.openClaw();
            isClawClosed=!isClawClosed;
        }
    }








    //---------------------------------------------------------------------------------------------
    //ground mode related

    public void intake() { //gamepad1 a, right bumper, left bumper
        if ((horiSlides.getPosition() <= horiThreshold) && timer2.seconds() > 0.3) {
            if (gamepad1.a && !isArmButtonDown && !isArmDown) {
                isArmDown = true;
                isArmButtonDown = true;
                intake.openClaw();
                isClawClosed=!isClawClosed;
            }
            else if (gamepad1.a && !isArmButtonDown && isArmDown) {
                isArmDown = false;
                isArmButtonDown = true;
            }
            else if (!gamepad1.a) {
                isArmButtonDown = false;
            }


            if (isArmDown) {
                intake.intakePosition(wristMode);
                if (gamepad1.right_bumper) {
                    intake.openClaw();
                    intake.spinIn();
                }
                else if (gamepad1.left_bumper) {
                    intake.openClaw();
                    intake.spinOut();
                }
                else {
                    intake.closeClaw();
                    intake.spinStop();
                }
                /*if (gamepad1.right_bumper&&!americanGirl.right_bumper&&!isClawClosed) {
                    intake.closeClaw();
                    isClawClosed=!isClawClosed;
                } else if (gamepad1.right_bumper&&!americanGirl.right_bumper&&isClawClosed) {
                    intake.openClaw();
                    isClawClosed=!isClawClosed;
                }*/
            }
            else {
                intake.spinStop();
                intake.restPosition();
                intake.closeClaw();
            }
        }
        else {
            isArmDown = false;
            isArmButtonDown = false;
            intake.closeClaw();
            isClawClosed=true;
            intake.restPosition();

        }
    }

    public void setWristMode() {
        if (gamepad1.x && !isWristButtonDown && !wristMode) {
            wristMode = true;
            isWristButtonDown = true;
        }
        else if (gamepad1.x && !isWristButtonDown && wristMode) {
            wristMode = false;
            isWristButtonDown = true;
        }
        else if (!gamepad1.x) {
            isWristButtonDown = false;
        }
    }




    public void presetHoriSlides() { //gamepad2 y, b
        if (gamepad1.b&&!americanGirl.b&&isHoriExtended) {

            horiSlides.in();
            timer1.reset();
            timer1.startTime();
            telemetry.addLine("Extended: "+isHoriExtended);
            telemetry.addLine("American Girl: "+americanGirl.b);
            telemetry.update();
            isHoriExtended=false;
        }
        else if (gamepad1.b&&!americanGirl.b&&!isHoriExtended) {
            horiSlides.out();
            timer2.reset();
            timer2.startTime();
            telemetry.addLine("Extended: "+isHoriExtended);
            telemetry.addLine("American Girl: "+americanGirl.b);
            telemetry.update();
            isHoriExtended=true;
        }
    }
}
