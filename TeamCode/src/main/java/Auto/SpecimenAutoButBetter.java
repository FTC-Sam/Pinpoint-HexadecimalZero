package Auto;
/*
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
//import mechanisms.Bot;
import mechanisms.HoriSlides;
import mechanisms.Intake;
import mechanisms.VertiSlides;

@Autonomous
public class SpecimenAutoButBetter extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Action trajectory;
    private Bot mechanisms;

    private void Initialize() {
        initialPose = new Pose2d(9, -61, Math.toRadians(90));
        drive = new PinpointDrive(hardwareMap, initialPose);
        mechanisms = new Bot(hardwareMap);
    }

    private void BuildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)

                //preload
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.REST))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.STOPINTAKE))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITPOS))
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(9, -45), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITLOW))
                .afterTime(1, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(1)
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.3, mechanisms.runAutoAction(Bot.AutoActionModes.REST))






                //deposit samples
                .setReversed(true)
                .splineTo(new Vector2d(15, -45), Math.toRadians(0))
                .splineTo(new Vector2d(35, -35), Math.toRadians(30))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.45))
                .afterTime(0.3, mechanisms.runAutoAction(Bot.AutoActionModes.RUNINTAKE))
                .afterTime(0.3, mechanisms.runAutoAction(Bot.AutoActionModes.SAMPLEINTAKEPOS))
                .afterTime(1, mechanisms.runAutoAction(Bot.AutoActionModes.STOPINTAKE))
                .waitSeconds(1)

                .turnTo(Math.toRadians(135))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.EXTAKE))
                .waitSeconds(0.4)



                .setReversed(false)

                .turnTo(Math.toRadians(205))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.35))
                .afterTime(0.3, mechanisms.runAutoAction(Bot.AutoActionModes.RUNINTAKE))
                .afterTime(0.3, mechanisms.runAutoAction(Bot.AutoActionModes.SAMPLEINTAKEPOS))
                .afterTime(1, mechanisms.runAutoAction(Bot.AutoActionModes.STOPINTAKE))
                .waitSeconds(1)


                .turnTo(Math.toRadians(135))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.EXTAKE))
                .waitSeconds(0.4)

                .turnTo(Math.toRadians(195))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.3, mechanisms.runAutoAction(Bot.AutoActionModes.RUNINTAKE))
                .afterTime(0.3, mechanisms.runAutoAction(Bot.AutoActionModes.SAMPLEINTAKEPOS))
                .afterTime(1, mechanisms.runAutoAction(Bot.AutoActionModes.STOPINTAKE))
                .waitSeconds(1)


                .turnTo(Math.toRadians(135))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.EXTAKE))
                .waitSeconds(0.4)





                //first specimen
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.STOPINTAKE))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITPOS))
                .strafeToLinearHeading(new Vector2d(9, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-9, -40, Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITLOW))
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(1)







                //second specimen
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITPOS))
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(15, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-3, -40, Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITLOW))
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(1)







                //third specimen
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITPOS))
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(21, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(3, -40, Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITLOW))
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(1)










                //fourth specimen
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .waitSeconds(1)
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITPOS))
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(27, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(9, -40, Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0.2, mechanisms.runAutoAction(Bot.AutoActionModes.DEPOSITLOW))
                .afterTime(0.5, mechanisms.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(1)


                //park
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0, mechanisms.runAutoAction(Bot.AutoActionModes.SAMPLEINTAKEPOS))
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
*/