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
import org.opencv.features2d.BRISK;

import TeleOp.BreachTeleOP;
import mechanisms.HoriSlides;
import mechanisms.Intake;
import mechanisms.VertiSlides;

@Autonomous
public class SpecimenAutoButBetter extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Action trajectory;
    private BreachTeleOP mechanisms;

    private void Initialize() {
        initialPose = new Pose2d(9, -61, Math.toRadians(90));
        drive = new PinpointDrive(hardwareMap, initialPose);
        mechanisms = new BreachTeleOP();
    }

    private void BuildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)

                //preload
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.REST))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.STOPINTAKE))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SPECIALDEPOSITPOS))
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(9, -40), Math.toRadians(270))
                .afterTime(0.2, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SPECIALDEPOSIT))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .waitSeconds(1)
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.REST))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))






                //deposit samples
                .strafeToLinearHeading(new Vector2d(48, -43), Math.toRadians(270))

                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.RUNINTAKE))
                .afterTime(0.3, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SAMPLEINTAKEPOS))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.STOPINTAKE))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.6))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.HUMANDROP))
                .afterTime(0.9, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))

                .waitSeconds(1)
                .afterTime(0.1, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))
                .strafeToLinearHeading(new Vector2d(58, -43), Math.toRadians(270))

                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.RUNINTAKE))
                .afterTime(0.3, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SAMPLEINTAKEPOS))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.STOPINTAKE))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.6))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.HUMANDROP))
                .afterTime(0.9, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))

                .waitSeconds(1)
                .afterTime(0.1, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))
                .turnTo(Math.toRadians(235))

                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.RUNINTAKE))
                .afterTime(0.3, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SAMPLEINTAKEPOS))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.STOPINTAKE))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.6))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.HUMANDROP))
                .afterTime(0.9, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))

                .waitSeconds(1)





                //first specimen
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.DEPOSITPOS))
                .strafeToLinearHeading(new Vector2d(9, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-9, -33, Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.DEPOSITLOW))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .waitSeconds(1)







                //second specimen
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.DEPOSITPOS))
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(15, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-3, -33, Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.DEPOSITLOW))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .waitSeconds(1)







                //third specimen
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.DEPOSITPOS))
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(21, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(3, -33, Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.DEPOSITLOW))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .waitSeconds(1)










                //fourth specimen
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.DEPOSITPOS))
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(27, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(9, -33, Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.DEPOSITLOW))
                .afterTime(0.5, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.OPENCLAW))
                .waitSeconds(1)


                //park
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.CLOSECLAW))
                .afterTime(0, mechanisms.runAutoAction(BreachTeleOP.AutoActionModes.SAMPLEINTAKEPOS))
                .strafeToSplineHeading(new Vector2d(30, -50), Math.toRadians(125));






        trajectory = trajectoryHolder.build();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        BuildTrajectories();







        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        trajectory
                )
        );
    }
}
