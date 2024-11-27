package TeleOp;

import android.graphics.drawable.Drawable;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import mechanisms.Drivetrain;
import mechanisms.HoriSlides;
import mechanisms.VertiSlides;


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






    private void initialize() {
        bigHingeRight = (ServoImplEx) hardwareMap.servo.get("bigHingeRight");
        bigHingeLeft = (ServoImplEx) hardwareMap.servo.get("bigHingeLeft");
        bigHingeLeft.setDirection(ServoImplEx.Direction.REVERSE);

        smallHingeRight = (ServoImplEx) hardwareMap.servo.get("smallHingeRight");
        smallHingeLeft = (ServoImplEx) hardwareMap.servo.get("smallHingeLeft");
        smallHingeLeft.setDirection(ServoImplEx.Direction.REVERSE);

        wrist = (ServoImplEx) hardwareMap.servo.get("wrist");
        claw = (ServoImplEx) hardwareMap.servo.get("claw");


        horiSlides = new HoriSlides(hardwareMap, telemetry);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        vertiSlides = new VertiSlides(hardwareMap, telemetry);
        drivetrain = new Drivetrain(hardwareMap, telemetry, gamepad1);
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
                smallHingeRight.setPosition(servoIn);
                smallHingeLeft.setPosition(servoIn);
                bigHingeRight.setPosition(servoOut);
                bigHingeLeft.setPosition(servoOut);
            }
            if (gamepad1.dpad_down) {
                horiSlides.in();
            }

           /* if(gamepad1.a){
                wrist.setPosition(middleWristPos);
            }*/

            if(gamepad1.left_trigger > 0.7 && wristPos> leftWristMax){
                wristPos -= 0.01;
                wrist.setPosition(wristPos);
            }

            if(gamepad1.right_trigger > 0.7 && wristPos < rightWristMax){
                wristPos += 0.01;
                wrist.setPosition(wristPos);
            }

            if (gamepad1.a) {
                claw.setPosition(servoDown);
            }





            if (gamepad1.dpad_right) vertiSlides.setTargetPos(1000);
            else if (gamepad1.dpad_left) vertiSlides.setTargetPos(0);

            vertiSlides.update();
            telemetry.update();
            drivetrain.drive();

        }

    }

}