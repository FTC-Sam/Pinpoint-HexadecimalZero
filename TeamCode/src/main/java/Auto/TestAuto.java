package Auto;

import android.app.Notification;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
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
        initialPose = new Pose2d(-33, -61, Math.toRadians(180));
        drive = new PinpointDrive(hardwareMap, initialPose);
        box = new Box(hardwareMap, this.telemetry);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
    }

    private void buildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)
                .afterTime(0, vertiSlides.runVertiSlidesAuto(4700))
                .strafeToLinearHeading(new Vector2d(-33, -56), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(-55.6, -53.6), Math.toRadians(225))
                .waitSeconds(2.1)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.DEPOSIT))
                .afterTime(0.1, box.runBoxAuto(Box.AutoActionModes.OUTTAKE))
                .waitSeconds(1)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))

                .strafeToLinearHeading(new Vector2d(-44, -53), Math.toRadians(275))
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.INTAKE))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(-0.7))
                .afterTime(0.8, box.runBoxAuto(Box.AutoActionModes.ARMDOWN))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-51, -48), Math.toRadians(275))
                .waitSeconds(1)

                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.8))
                .afterTime(0.5, box.runBoxAuto(Box.AutoActionModes.REST))
                .afterTime(0.8, vertiSlides.runVertiSlidesAuto(4700))
                .strafeToLinearHeading(new Vector2d(-55.4, -53.4), Math.toRadians(225))
                .waitSeconds(3.8)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.DEPOSIT))
                .afterTime(0.1, box.runBoxAuto(Box.AutoActionModes.OUTTAKE))
                .waitSeconds(1)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))

                .strafeToLinearHeading(new Vector2d(-54, -54), Math.toRadians(290))
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.INTAKE))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(-0.7))
                .afterTime(0.8, box.runBoxAuto(Box.AutoActionModes.ARMDOWN))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-57, -49), Math.toRadians(290))
                .waitSeconds(1)

                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.8))
                .afterTime(0.5, box.runBoxAuto(Box.AutoActionModes.REST))
                .afterTime(0.8, vertiSlides.runVertiSlidesAuto(4700))
                .strafeToLinearHeading(new Vector2d(-55.6, -53.6), Math.toRadians(225))
                .waitSeconds(3.8)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.DEPOSIT))
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.OUTTAKE))
                .waitSeconds(1)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))






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
            new ParallelAction(
                 trajectory,
                 vertiSlides.updateVertiSlidesAuto()
            )
        );
    }
}
