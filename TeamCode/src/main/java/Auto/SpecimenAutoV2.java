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

import bot.Bot;

@Autonomous
public class SpecimenAutoV2 extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Action trajectory;
    private Bot bot;

    private void Initialize() {
        initialPose = new Pose2d(9, -60.85, Math.toRadians(90));
        drive = new PinpointDrive(hardwareMap, initialPose);
        bot = new Bot(hardwareMap);
    }

    private void BuildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)

              //  .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.REST))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.APPROACH))

                .strafeToLinearHeading(new Vector2d(-5, -33.1), Math.toRadians(90))
                .waitSeconds(0.1)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.DEPOSITHIGH))
                .waitSeconds(0.2)
                .afterTime(0.2, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(.1)
                .strafeToLinearHeading(new Vector2d(9, -43), Math.toRadians(90))


                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.REST))




                .strafeToLinearHeading(new Vector2d(9, -46), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(27, -38), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(44, -10, Math.toRadians(90)),Math.toRadians(50))


                .strafeToLinearHeading(new Vector2d(48, -45), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(56, -4), Math.toRadians(90))



                .strafeToLinearHeading(new Vector2d(61, -43), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(51, -39.4), Math.toRadians(90))  //GRAB POS
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .waitSeconds(.7)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .waitSeconds(.1)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.APPROACH))
                .afterTime(0.3, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .strafeToLinearHeading(new Vector2d(-1, -48), Math.toRadians(90)) // DESPOSIT POS
                .strafeToLinearHeading(new Vector2d(-1, -30.5), Math.toRadians(90))

                .waitSeconds(0.1)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.DEPOSITHIGH))
                .waitSeconds(.2)
                .afterTime(0.2, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(.1)
                .strafeToLinearHeading(new Vector2d(6, -43), Math.toRadians(90))


                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.REST))




                //second cycle
                .strafeToLinearHeading(new Vector2d(46, -40), Math.toRadians(95))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .waitSeconds(.7)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .waitSeconds(.1)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.APPROACH))
                .afterTime(0.3, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .strafeToLinearHeading(new Vector2d(2, -48), Math.toRadians(90)) // DESPOSIT POS
                .strafeToLinearHeading(new Vector2d(2, -31), Math.toRadians(90))
                .waitSeconds(0)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.DEPOSITHIGH))
                .waitSeconds(0.2)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.3, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .strafeToLinearHeading(new Vector2d(10, -41), Math.toRadians(90))


                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.REST))













                //Third cycle
                .strafeToLinearHeading(new Vector2d(46, -38.1), Math.toRadians(97)) //supposed to be y=-41
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKEPOS))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .waitSeconds(.7)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .waitSeconds(.1)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.APPROACH))
                .afterTime(0.3, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .strafeToLinearHeading(new Vector2d(5, -41.65), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(5, -28), Math.toRadians(90))
                .waitSeconds(0.1)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.DEPOSITHIGH))
                .waitSeconds(0.5)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(0.3, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))

                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.REST));







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
