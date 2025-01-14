package mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
@Config
public class SlidesTest extends LinearOpMode {
    public VertiSlides vertiSlides;

    public static int a = 4600;
    public static int b = 2000;
    public static int c = 900;
    public static int d = 50;

    public void Initiate(){
        vertiSlides = vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
    }

    public void presetVertiSlides() { //gamepad2 up, left, right, down, x

            if (gamepad1.dpad_up) {
                telemetry.addLine("yep");
                telemetry.update();
                vertiSlides.setTargetPos(a);

            }
            if (gamepad1.dpad_left) {
                vertiSlides.setTargetPos(b);

            }
            if (gamepad1.dpad_right) {
                vertiSlides.setTargetPos(c);

            }
            if (gamepad1.dpad_down) {
                vertiSlides.setTargetPos(d);

            }
        }


    @Override
    public void runOpMode() throws InterruptedException {
        Initiate();
        while (opModeInInit()) {
        }
        while (opModeIsActive()) {
            presetVertiSlides();
            vertiSlides.update();
            telemetry.addData("error ", vertiSlides.getError());
            telemetry.update();
        }
    }
}
