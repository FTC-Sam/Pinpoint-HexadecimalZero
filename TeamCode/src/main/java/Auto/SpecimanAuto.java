package Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PinpointDrive;

import mechanisms.HoriSlides;
import mechanisms.Intake;
import mechanisms.VertiSlides;

@Autonomous
public class SpecimanAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Action trajectory;
    private Intake intake;
    private HoriSlides horiSlides;
    private VertiSlides vertiSlides;

    private void Initialize() {
        initialPose = new Pose2d(-9, -61, Math.toRadians(270));
        drive = new PinpointDrive(hardwareMap, initialPose);
        intake = new Intake(hardwareMap, this.telemetry);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry, true);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
    }

    private void BuildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.REST))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(750))
                .strafeToLinearHeading(new Vector2d(-8, -49.5), Math.toRadians(270))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.59))
                .waitSeconds(.5)
                .afterTime(0, vertiSlides.runVertiSlidesAuto(600))
                .waitSeconds(.5)
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.5))
                .waitSeconds(0.5)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0.5)
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.35))

                .strafeTo(new Vector2d(10, -50))
                .setReversed(true)
                .strafeToSplineHeading(new Vector2d(26, -5), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(28, -5, Math.toRadians(270)), Math.toRadians(270))

                .splineToLinearHeading(new Pose2d(28.5, -45, Math.toRadians(270)), Math.toRadians(90), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-30, 30))

                .strafeToConstantHeading(new Vector2d(32,-5), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-30, 30))
                .splineToLinearHeading(new Pose2d(39, -5, Math.toRadians(270)), Math.toRadians(270), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-30, 30))

                .splineToLinearHeading(new Pose2d(39.5, -45, Math.toRadians(270)), Math.toRadians(90), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-30, 30))

                .strafeToConstantHeading(new Vector2d(32.5,-5), new TranslationalVelConstraint(20), new ProfileAccelConstraint(-20, 20))
                .splineToLinearHeading(new Pose2d(44, -5, Math.toRadians(270)), Math.toRadians(270), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-30, 30))
                .strafeToConstantHeading(new Vector2d(45.5, -45), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-30, 30))
                ;

        trajectory = trajectoryHolder.build();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        BuildTrajectories();







        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        trajectory,
                        vertiSlides.updateVertiSlidesAuto()
                )
        );
    }
}
