package Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PinpointDrive;

import mechanisms.Box;
import mechanisms.HoriSlides;
import mechanisms.VertiSlides;


@Config
@Autonomous(name = "BucketAuto")
public class BucketAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Box box;
    private HoriSlides horiSlides;
    private VertiSlides vertiSlides;
    private Action trajectory;


    private void initialize() {
        initialPose = new Pose2d(-33, -61, Math.toRadians(180));
        drive = new PinpointDrive(hardwareMap, initialPose);
        box = new Box(hardwareMap, this.telemetry,true);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry, true);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
    }

    private void buildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)
                .afterTime(0, horiSlides.runHoriSlidesAuto(1))
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(4700))
                .strafeToLinearHeading(new Vector2d(-33, -56), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(-55, -52.7), Math.toRadians(225))
                .waitSeconds(1.5)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.DEPOSIT))
                .afterTime(1, box.runBoxAuto(Box.AutoActionModes.OUTTAKE))

                .waitSeconds(2)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))

                .strafeToLinearHeading(new Vector2d(-44, -53), Math.toRadians(275))
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.INTAKE))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.3))
                .afterTime(0.8, box.runBoxAuto(Box.AutoActionModes.ARMDOWN))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-47, -46), Math.toRadians(280))
                .waitSeconds(1)

                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, horiSlides.runHoriSlidesAuto(1))
                .afterTime(0.2, box.runBoxAuto(Box.AutoActionModes.REST))
                .afterTime(0.8, vertiSlides.runVertiSlidesAuto(4700))
                .strafeToLinearHeading(new Vector2d(-55, -52.4), Math.toRadians(220))
                .waitSeconds(3.5)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.DEPOSIT))
                .afterTime(1, box.runBoxAuto(Box.AutoActionModes.OUTTAKE))
                .waitSeconds(2)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))

                .strafeToLinearHeading(new Vector2d(-50, -54), Math.toRadians(290))
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.INTAKE))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.3))
                .afterTime(0.8, box.runBoxAuto(Box.AutoActionModes.ARMDOWN))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-55, -44), Math.toRadians(280))
                .waitSeconds(1)

                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, horiSlides.runHoriSlidesAuto(1))
                .afterTime(0.2, box.runBoxAuto(Box.AutoActionModes.REST))
                .afterTime(0.8, vertiSlides.runVertiSlidesAuto(4700))
                .strafeToLinearHeading(new Vector2d(-54.6, -53), Math.toRadians(210))
                .waitSeconds(3.5)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.DEPOSIT))
                .afterTime(1, box.runBoxAuto(Box.AutoActionModes.OUTTAKE))
                .waitSeconds(2)
                .afterTime(0, box.runBoxAuto(Box.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(1, box.runBoxAuto(Box.AutoActionModes.DEPOSIT))





                .strafeToLinearHeading(new Vector2d(-45, -45), Math.toRadians(270));



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
