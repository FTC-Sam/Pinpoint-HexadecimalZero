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
                //Score First Specimen
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


                 //pushing samples
                .strafeToLinearHeading(new Vector2d(37, -40), Math.toRadians(270))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.ARMPUSHING))

                .strafeToLinearHeading(new Vector2d(44, -10), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(52, -55), Math.toRadians(270))

                .strafeToLinearHeading(new Vector2d(52, -10), Math.toRadians(270))
                .afterTime(3, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.7))
                .strafeToLinearHeading(new Vector2d(58, -47), Math.toRadians(270))


                //intaking off wall(First Time)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKE))
                .afterTime(.0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .strafeToLinearHeading(new Vector2d(58, -49), Math.toRadians(270))
                .afterTime(0.65, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(.75, vertiSlides.runVertiSlidesAuto(100))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.SequentialDeposit))
                .waitSeconds(1)


                //deposit specimen(First Time)
                .strafeToLinearHeading(new Vector2d(9, -38), Math.toRadians(270))//deposit Postition
               // .splineToLinearHeading(new Pose2d(3, -36, Math.toRadians(265)), Math.toRadians(90))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(1, bot.runAutoAction(Bot.AutoActionModes.SequentialScore))
                .afterTime(1, vertiSlides.runVertiSlidesAuto(-10))
                .afterTime(1.5, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(1.5, bot.runAutoAction(Bot.AutoActionModes.SequentialDeposit))
                .afterTime(1.5, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.7))
                .afterTime(2, bot.runAutoAction(Bot.AutoActionModes.ARMPUSHING))
                .waitSeconds(2)



                //intaking off wall(Second Time)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKE))
                .afterTime(.0, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .strafeToLinearHeading(new Vector2d(58, -49), Math.toRadians(270))
                .afterTime(0.65, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.SequentialDeposit))
                .waitSeconds(1)


                //deposit specimen(Second Time)
                .strafeToLinearHeading(new Vector2d(9, -38), Math.toRadians(270))//deposit Postition
                .afterTime(0, vertiSlides.runVertiSlidesAuto(70))
                // .splineToLinearHeading(new Pose2d(3, -36, Math.toRadians(265)), Math.toRadians(90))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(1, bot.runAutoAction(Bot.AutoActionModes.SequentialScore))
                .afterTime(1, vertiSlides.runVertiSlidesAuto(-10))
                .afterTime(2, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(2, bot.runAutoAction(Bot.AutoActionModes.SequentialDeposit))
                .afterTime(2, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.7))
                .waitSeconds(1.5)

                 //intaking off wall(Third Time)
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SPECIMENINTAKE))
                .afterTime(.75, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .strafeToLinearHeading(new Vector2d(60, -55), Math.toRadians(270))
                .afterTime(0.5, bot.runAutoAction(Bot.AutoActionModes.CLOSECLAW))
                .afterTime(0.8, bot.runAutoAction(Bot.AutoActionModes.SequentialDeposit))
                .waitSeconds(1)


                //deposit specimen(Third Time)
                .strafeToLinearHeading(new Vector2d(9, -38), Math.toRadians(270))//deposit Postition
                .afterTime(0, vertiSlides.runVertiSlidesAuto(70))
                // .splineToLinearHeading(new Pose2d(3, -36, Math.toRadians(265)), Math.toRadians(90))
                .afterTime(0, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.25))
                .afterTime(1, bot.runAutoAction(Bot.AutoActionModes.SequentialScore))
                .afterTime(1, vertiSlides.runVertiSlidesAuto(-10))
                .afterTime(2, bot.runAutoAction(Bot.AutoActionModes.OPENCLAW))
                .afterTime(2, bot.runAutoAction(Bot.AutoActionModes.SequentialDeposit))
                .afterTime(2, bot.runAutoAction(Bot.AutoActionModes.SETSLIDE, 0.7))
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
