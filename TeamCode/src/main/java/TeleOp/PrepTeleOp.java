package TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import bot.Crane;
import bot.Drivetrain;
import bot.HoriSlides;
import bot.VertiSlides;


@TeleOp(name = "PrepTeleOp")
@Config
public class PrepTeleOp extends LinearOpMode {



    //DO NOT MODIFY THIS CLASS WHATSOEVER UNLESS AUTHORIZED-> MAKE A COPY
    private Drivetrain drivetrain;
    private CRServoImplEx spin;
    private ServoImplEx hinge;

    private ServoImplEx servoLeft;
    private ServoImplEx servoRight;
    private VertiSlides vertiSlides;

    private HoriSlides horiSlides;
    private DcMotorEx climb;

    public static double servoIn = 0;
    public static double servoOut = 0.6;

    public static double servoUp = 0.5;
    public static double servoDown = 0.2;
    public ServoImplEx bigHingeLeft;
    public ServoImplEx bigHingeRight;
    public ServoImplEx smallHingeLeft;
    public ServoImplEx smallHingeRight;


    public static double wristPos = 0;

    public static double middleWristPos = 0.05;
    public static double leftWristMax = 0;

    public static double rightWristMax = 0.1;
    public ServoImplEx wrist;
    public ServoImplEx claw;
    public Crane crane;

    public DcMotorEx slideTop;
    public DcMotorEx slideMid;
    public DcMotorEx slideBot;






    private void initialize() {
        /*bigHingeRight = (ServoImplEx) hardwareMap.servo.get("bigHingeRight");
        bigHingeLeft = (ServoImplEx) hardwareMap.servo.get("bigHingeLeft");
        bigHingeLeft.setDirection(ServoImplEx.Direction.REVERSE);

        smallHingeRight = (ServoImplEx) hardwareMap.servo.get("smallHingeRight");
        smallHingeLeft = (ServoImplEx) hardwareMap.servo.get("smallHingeLeft");
        smallHingeLeft.setDirection(ServoImplEx.Direction.REVERSE);

        wrist = (ServoImplEx) hardwareMap.servo.get("wrist");
        claw = (ServoImplEx) hardwareMap.servo.get("claw");

        crane = new Crane(hardwareMap, telemetry, gamepad1, gamepad2);*/

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());


        drivetrain = new Drivetrain(hardwareMap, telemetry, gamepad1, crane);
        slideTop = (DcMotorEx) hardwareMap.dcMotor.get("slideTop");
        slideMid = (DcMotorEx) hardwareMap.dcMotor.get("slideMid");
        slideBot = (DcMotorEx) hardwareMap.dcMotor.get("slideBot");
        climb = (DcMotorEx) hardwareMap.dcMotor.get("climb");


        slideTop.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        slideMid.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        slideBot.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        climb.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        slideTop.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMid.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideBot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        climb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideTop.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        slideMid.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        slideBot.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        climb.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //climb.setDirection(DcMotorSimple.Direction.REVERSE);



        slideTop.setDirection(DcMotorSimple.Direction.REVERSE);
        slideBot.setDirection(DcMotorSimple.Direction.REVERSE);// leave this be, nuh uh
    }




    @Override
    public void runOpMode() {

        initialize();
        while (opModeInInit()) {
        }
        while (opModeIsActive()) {
            /*if (gamepad1.a) spin.setPower(-1);
            else if (gamepad1.b) spin.setPower(1);
            else spin.setPower(0);

            if (gamepad1.x) hinge.setPosition(servoUp);

            if (gamepad1.y) hinge.setPosition(servoDown); //0.559 intake
            //0.431 rest
            */
            if (gamepad1.dpad_up) {
                slideTop.setPower(1);
                slideMid.setPower(1);
                slideBot.setPower(1);
                telemetry.addData("SlideTop", slideTop.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("SlideMid", slideMid.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("SlideBot", slideBot.getCurrent(CurrentUnit.AMPS));
                telemetry.update();

            }

            else {
                slideTop.setPower(0);
                slideMid.setPower(0);
                slideBot.setPower(0);
            }
            /*
            if (gamepad1.dpad_down) {
                horiSlides.in();
            }

           /* if(gamepad1.a){
                wrist.setPosition(middleWristPos);
            }*/

            /*if(gamepad1.left_trigger > 0.7 && wristPos> leftWristMax){
                wristPos -= 0.01;
                wrist.setPosition(wristPos);
            }
            /*
            if(gamepad1.right_trigger > 0.7 && wristPos < rightWristMax){
                wristPos += 0.01;
                wrist.setPosition(wristPos);
            }

            if (gamepad1.a) {
                claw.setPosition(servoDown);
            }*





            if (gamepad1.dpad_right) vertiSlides.setTargetPos(1000);
            else if (gamepad1.dpad_left) vertiSlides.setTargetPos(0);

            vertiSlides.update();
            telemetry.update();
            drivetrain.drive();*/


        }

    }

}