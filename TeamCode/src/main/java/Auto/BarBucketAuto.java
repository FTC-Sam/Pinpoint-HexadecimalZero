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

import bot.HoriSlides;
import bot.Intake;
import bot.VertiSlides;


@Config
@Autonomous(name = "BarBucketAuto")
public class BarBucketAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Intake intake;
    private HoriSlides horiSlides;
    private VertiSlides vertiSlides;
    private Action trajectory;
    public static int breach = 1000;


    private void initialize() {
        initialPose = new Pose2d(-9, -60, Math.toRadians(270));
        drive = new PinpointDrive(hardwareMap, initialPose);
        intake = new Intake(hardwareMap, this.telemetry);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry, true);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
    }

    private void buildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)
                /*.afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.REST))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(breach))
                .waitSeconds(1)
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.7))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-8, -49.5), Math.toRadians(270))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(800))
                .waitSeconds(1)
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.5)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0.5)
                .afterTime(0, vertiSlides.runVertiSlidesAuto(130))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.35))

                .strafeToLinearHeading(new Vector2d(-47.5, -44.2), Math.toRadians(268))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.7))
                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.INTAKE))
                .waitSeconds(1)
                .afterTime(0, vertiSlides.runVertiSlidesAuto(-50))
                .afterTime(0.4, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)

                .afterTime(0, horiSlides.runHoriSlidesAuto(0.35))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(1200))
                .afterTime(0.8, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))
                .strafeToLinearHeading(new Vector2d(-51.7, -51.7), Math.toRadians(225))
                .waitSeconds(1.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0.3)

                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.REST))
                .afterTime(0.5, vertiSlides.runVertiSlidesAuto(130))

                .strafeToLinearHeading(new Vector2d(-56.3, -45.2), Math.toRadians(266))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.7))
                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.INTAKE))
                .waitSeconds(1)
                .afterTime(0, vertiSlides.runVertiSlidesAuto(-200))
                .afterTime(0.4, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)

                .afterTime(0, horiSlides.runHoriSlidesAuto(0.35))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(1200))
                .afterTime(0.8, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))
                .strafeToLinearHeading(new Vector2d(-54, -50), Math.toRadians(225))
                .waitSeconds(1.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0.3)

                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.REST))
                .afterTime(0.5, vertiSlides.runVertiSlidesAuto(-200))

                .strafeToLinearHeading(new Vector2d(-56, -42.5), Math.toRadians(295))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.7))
                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.INTAKELOL))
                .waitSeconds(1)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)

                .afterTime(0, horiSlides.runHoriSlidesAuto(0.35))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(1200))
                .afterTime(0.8, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))
                .strafeToLinearHeading(new Vector2d(-54.2, -51.2), Math.toRadians(225))
                .waitSeconds(1.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0.2)

                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.REST))
                .afterTime(0.5, vertiSlides.runVertiSlidesAuto(-30));

                /*.afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.ARMUP))
                .afterTime(0, horiSlides.runHoriSlidesAuto(1))
                .afterTime(0.2, intake.runBoxAuto(Intake.AutoActionModes.REST))
                .afterTime(0.8, vertiSlides.runVertiSlidesAuto(4700))
                .strafeToLinearHeading(new Vector2d(-55, -52.4), Math.toRadians(220))
                .waitSeconds(3.5)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))
                .afterTime(1, intake.runBoxAuto(Intake.AutoActionModes.OUTTAKE))
                .waitSeconds(2)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))

                .strafeToLinearHeading(new Vector2d(-50, -54), Math.toRadians(290))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.INTAKE))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.3))
                .afterTime(0.8, intake.runBoxAuto(Intake.AutoActionModes.ARMDOWN))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-55, -44), Math.toRadians(280))
                .waitSeconds(1)

                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.ARMUP))
                .afterTime(0, horiSlides.runHoriSlidesAuto(1))
                .afterTime(0.2, intake.runBoxAuto(Intake.AutoActionModes.REST))
                .afterTime(0.8, vertiSlides.runVertiSlidesAuto(4700))
                .strafeToLinearHeading(new Vector2d(-54.6, -53), Math.toRadians(210))
                .waitSeconds(3.5)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))
                .afterTime(1, intake.runBoxAuto(Intake.AutoActionModes.OUTTAKE))
                .waitSeconds(2)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(1, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))*/





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
