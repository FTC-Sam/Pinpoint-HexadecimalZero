package TeleOp;

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

    public static double servoIn = 0.8;
    public static double servoOut = -0.7;

    public static double servoUp = 0.5;
    public static double servoDown = 0.2;





    private void initialize() {

        spin = (CRServoImplEx) hardwareMap.crservo.get("spin");
        hinge = (ServoImplEx) hardwareMap.servo.get("hinge");
        servoLeft = (ServoImplEx) hardwareMap.servo.get("servoLeft");
        servoRight = (ServoImplEx) hardwareMap.servo.get("servoRight");
        servoRight.setDirection(Servo.Direction.REVERSE);


        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        vertiSlides = new VertiSlides(hardwareMap, telemetry);
    }




    @Override
    public void runOpMode() {

        initialize();
        while (opModeInInit()) {
        }
        while (opModeIsActive()) {
            if (gamepad1.a) spin.setPower(-1);
            else if (gamepad1.b) spin.setPower(1);
            else spin.setPower(0);

            if (gamepad1.x) hinge.setPosition(servoUp);

            if (gamepad1.y) hinge.setPosition(servoDown); //0.559 intake
            //0.431 rest

            if (gamepad1.dpad_up) {
                servoLeft.setPosition(servoOut);
                servoRight.setPosition(servoOut);
            }
            if (gamepad1.dpad_down) {
                servoLeft.setPosition(servoIn);
                servoRight.setPosition(servoIn);
            }

            /*if (gamepad1.right_bumper) {
                vertiSlides.manualUp();
            }
            else if (gamepad1.left_bumper) {
                vertiSlides.manualDown();
            }
            else {
                vertiSlides.powerZero();
            }*/


            if (gamepad1.dpad_right) vertiSlides.setTargetPos(3000);
            else if (gamepad1.dpad_left) vertiSlides.setTargetPos(0);

            vertiSlides.update();
            telemetry.update();

        }

    }

}