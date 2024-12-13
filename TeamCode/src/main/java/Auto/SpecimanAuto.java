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
        initialPose = new Pose2d(9, -60.85, Math.toRadians(90));
        drive = new PinpointDrive(hardwareMap, initialPose);
        intake = new Intake(hardwareMap, this.telemetry, true);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry, true);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
    }

    private void BuildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)

                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))
                .afterTime(0.2, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0.2, horiSlides.runHoriSlidesAuto(0.35))
                .afterTime(0.5, vertiSlides.runVertiSlidesAuto(570))
                .waitSeconds(.5)

                .strafeToLinearHeading(new Vector2d(9, -43.95), Math.toRadians(90))
                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSITDEPOSIT))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(200))
                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.35))
                .strafeToLinearHeading(new Vector2d(9, -43), Math.toRadians(90))


                .afterTime(0.1, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.REST))




                .strafeToLinearHeading(new Vector2d(9, -46), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(27, -38), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(44, -10, Math.toRadians(90)),Math.toRadians(50))


                .strafeToLinearHeading(new Vector2d(48, -45), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(56, -4), Math.toRadians(90))



                .strafeToLinearHeading(new Vector2d(61, -45), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(52, -42.8), Math.toRadians(90))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.INTAKELOL))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(140))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.7))
                .waitSeconds(.5)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.RESTLOL))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.35))
                .afterTime(0.3, vertiSlides.runVertiSlidesAuto(570))
                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.FLIPWRISTDEPOSIT))
                .strafeToLinearHeading(new Vector2d(2, -37.7), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40))

                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSITDEPOSIT))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(200))
                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.35))
                .strafeToLinearHeading(new Vector2d(6, -43), Math.toRadians(90))


                .afterTime(0.1, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.REST))





                .strafeToLinearHeading(new Vector2d(46, -41.8), Math.toRadians(90))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.INTAKELOL))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(155))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.7))
                .waitSeconds(.5)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.RESTLOL))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.35))
                .afterTime(0.3, vertiSlides.runVertiSlidesAuto(570))
                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.FLIPWRISTDEPOSIT))
                .strafeToLinearHeading(new Vector2d(0, -37.3), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40))

                .waitSeconds(0)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSITDEPOSIT))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(200))
                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.35))
                .strafeToLinearHeading(new Vector2d(6, -42.7), Math.toRadians(90))


                .afterTime(0.1, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.REST))














                .strafeToLinearHeading(new Vector2d(46, -41.1), Math.toRadians(90))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.INTAKELOL))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(160))
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.7))
                .waitSeconds(.5)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.RESTLOL))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.35))
                .afterTime(0.3, vertiSlides.runVertiSlidesAuto(570))
                .afterTime(0.3, intake.runBoxAuto(Intake.AutoActionModes.FLIPWRISTDEPOSIT))
                .strafeToLinearHeading(new Vector2d(-9, -36.3), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40))

                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSITDEPOSIT))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(200))
                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(0.35))
                .strafeToLinearHeading(new Vector2d(0, -42.7), Math.toRadians(90))


                .afterTime(0.1, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.REST))

                .strafeToLinearHeading(new Vector2d(47.5, -55.2), Math.toRadians(90));






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
