package mechanisms.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp(name = "ServoTest")
public class ServoTest extends OpMode {
    private Servo tester;
    @Override
    public void init() {
        tester = (ServoImplEx) hardwareMap.servo.get("x");
        tester.setPosition(0);

    }

    @Override
    public void loop() {

    }
}
