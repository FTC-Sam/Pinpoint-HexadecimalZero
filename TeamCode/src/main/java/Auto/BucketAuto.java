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

import bot.Bot;
import bot.Intake;
import bot.HoriSlides;
import bot.VertiSlides;


@Config
@Autonomous(name = "BucketAuto")
public class BucketAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Intake intake;
    private HoriSlides horiSlides;
    private VertiSlides vertiSlides;
    private Action trajectory;
    private Bot bot;



    private void initialize() {
        initialPose = new Pose2d(-35, -61, Math.toRadians(180));
        drive = new PinpointDrive(hardwareMap, initialPose);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
        bot = new Bot(hardwareMap);
    }

    private void buildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)

                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.APPROACH))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.STOPINTAKE))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .afterTime(0.2, vertiSlides.runVertiSlidesAuto(1000))
                .strafeToLinearHeading(new Vector2d(-35, -50), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(-54, -51), Math.toRadians(225))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))


                .strafeToLinearHeading(new Vector2d(-50, -46), Math.toRadians(270))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SAMPLEINTAKEPOS))
                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .waitSeconds(1)
                .afterTime(0.4, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .strafeToLinearHeading(new Vector2d(-50, -29), Math.toRadians(270))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.REST))
                .strafeToLinearHeading(new Vector2d(-54, -54), Math.toRadians(225))
                .afterTime(0.2, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(2)


                .strafeToLinearHeading(new Vector2d(-60, -45), Math.toRadians(271))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SAMPLEINTAKEPOS))
                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .waitSeconds(1)
                .afterTime(0.4, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .strafeToLinearHeading(new Vector2d(-60, -29), Math.toRadians(271))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.REST))
                .strafeToLinearHeading(new Vector2d(-54, -54), Math.toRadians(225))
                .afterTime(0.2, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.65))
                .afterTime(0.9, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .waitSeconds(2);





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
