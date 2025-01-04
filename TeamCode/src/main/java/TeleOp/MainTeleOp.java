package TeleOp;

import static mechanisms.HoriSlides.in;

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

import mechanisms.Crane;
import mechanisms.Drivetrain;
import mechanisms.HoriSlides;
import mechanisms.VertiSlides;


@TeleOp(name = "MainTeleOp")
@Config
public class MainTeleOp extends LinearOpMode {



    //DO NOT MODIFY THIS CLASS WHATSOEVER UNLESS AUTHORIZED-> MAKE A COPY
    private Drivetrain drivetrain;
    private Crane crane;

    private ServoImplEx servoLeft;
    private ServoImplEx servoRight;



    private void initialize() {

        crane = new Crane(hardwareMap,telemetry, gamepad1, gamepad2);
        drivetrain = new Drivetrain(hardwareMap, telemetry, gamepad1, crane);


        servoLeft = (ServoImplEx) hardwareMap.servo.get("servoLeft");
        servoRight = (ServoImplEx) hardwareMap.servo.get("servoRight");
        servoRight.setDirection(Servo.Direction.REVERSE);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
    }




    @Override
    public void runOpMode() {

        initialize();
        while (opModeInInit()) {
            crane.horiSlides.in();

        }
        crane.horiSlides.in();
        crane.intake.restPosition();

        boolean started = false;

        servoLeft.setPosition(in);
        servoRight.setPosition(in);

        while (opModeIsActive()) {
            if (!started) {
                crane.horiSlides.in();
                started = true;
            }
            crane.executeTeleOp();
            drivetrain.drive();
        }

    }

}