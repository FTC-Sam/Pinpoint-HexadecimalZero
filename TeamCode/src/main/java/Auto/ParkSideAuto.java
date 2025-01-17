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
import bot.VertiSlides;



@Config
@Autonomous(name = "ParkSideAuto")
public class ParkSideAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Action trajectory;
    private Bot bot;
    private VertiSlides vertiSlides;


    private void initialize() {
        initialPose = new Pose2d(9, -61, Math.toRadians(270));
        drive = new PinpointDrive(hardwareMap, initialPose);
        bot = new Bot(hardwareMap);
        vertiSlides = new VertiSlides(hardwareMap, telemetry);
    }

    private void buildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)
                .waitSeconds(0.2)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.REST))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.STOPINTAKE))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(0.1, bot.runAutoAction(Bot.AutoActionModes.SPECIALDEPOSITPOS))
                .strafeToLinearHeading(new Vector2d(0, -36), Math.toRadians(270))
                .afterTime(0.5, bot.runAutoAction(Bot.AutoActionModes.SPECIALDEPOSIT))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.SPECIALDEPOSITPOS))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.6))
                .waitSeconds(1.5)
                .afterTime(0.5, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))

                .strafeToLinearHeading(new Vector2d(27, -38), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(40, 0, Math.toRadians(270)),Math.toRadians(320))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.REST))

                .splineToLinearHeading(new Pose2d(45, -55, Math.toRadians(270)), Math.toRadians(270))

                .strafeToLinearHeading(new Vector2d(46, -20), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(51, 0, Math.toRadians(270)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(58, -10, Math.toRadians(270)), Math.toRadians(270))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.DEPOSITPOS))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .strafeToLinearHeading(new Vector2d(58, -61), Math.toRadians(270))








                .afterTime(0.5, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.SPECIALDEPOSITPOS))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(9, -45), Math.toRadians(270))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .splineToLinearHeading(new Pose2d(3, -36, Math.toRadians(265)), Math.toRadians(90))
                .afterTime(0.5, bot.runAutoAction(Bot.AutoActionModes.SPECIALDEPOSIT))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.SPECIALDEPOSITPOS))
                .waitSeconds(1.5);














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
