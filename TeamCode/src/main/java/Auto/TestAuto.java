package Auto;

import android.app.Notification;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointDrive;

import mechanisms.Box;
import mechanisms.HoriSlides;
import mechanisms.VertiSlides;


@Config
@Autonomous(name = "TestAuto")
public class TestAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Box box;
    private HoriSlides horiSlides;
    private VertiSlides vertiSlides;
    private Action trajectory;


    private void initialize() {
        initialPose = new Pose2d(-33, -61, Math.toRadians(90));
        drive = new PinpointDrive(hardwareMap, initialPose);
        box = new Box(hardwareMap, this.telemetry);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
    }

    private void buildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)
                .lineToY(-57)
                .afterTime(0, vertiSlides.runVertiSlidesAuto(4000))
                .afterTime(1, box.runBoxAuto(Box.AutoActionModes.DEPOSIT))
                .strafeToLinearHeading(new Vector2d(-45, -48), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(-56, -56), Math.toRadians(225))
                .waitSeconds(1)
                .afterTime(0.5, box.runBoxAuto(Box.AutoActionModes.OUTTAKE))

                .strafeToLinearHeading(new Vector2d(-48, -40), Math.toRadians(270))
                .afterTime(1, box.runBoxAuto(Box.AutoActionModes.REST))
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0.5, horiSlides.runHoriSlidesAuto(-0.7))
                .afterTime(0.5, box.runBoxAuto(Box.AutoActionModes.ARMDOWN))

                .waitSeconds(3.5)
                .afterTime(0.5, box.runBoxAuto(Box.AutoActionModes.INTAKE))

                .strafeToLinearHeading(new Vector2d(-56, -56), Math.toRadians(225))
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(-57, -40), Math.toRadians(270))
                .waitSeconds(3.5)

                .strafeToLinearHeading(new Vector2d(-56, -56), Math.toRadians(225))
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(-45, -45), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(-57, -37), Math.toRadians(310))
                .waitSeconds(3.5)

                .strafeToLinearHeading(new Vector2d(-56, -56), Math.toRadians(225))
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(40, -56), Math.toRadians(180));


        trajectory = trajectoryHolder.build();
    }


    @Override
    public void runOpMode() {
        initialize();
        buildTrajectories();







        waitForStart();

        Actions.runBlocking(
            new SequentialAction(
                 trajectory
            )
        );
    }
}
