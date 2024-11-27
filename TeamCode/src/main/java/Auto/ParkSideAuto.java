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

import mechanisms.Intake;
import mechanisms.HoriSlides;
import mechanisms.VertiSlides;


@Config
@Autonomous(name = "ParkSideAuto")
public class ParkSideAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Action trajectory;
    private Intake intake;
    private HoriSlides horiSlides;
    private VertiSlides vertiSlides;


    private void initialize() {
        initialPose = new Pose2d(11.3, -61, Math.toRadians(180));
        drive = new PinpointDrive(hardwareMap, initialPose);
        intake = new Intake(hardwareMap, this.telemetry);
        horiSlides = new HoriSlides(hardwareMap, this.telemetry, true);
        vertiSlides = new VertiSlides(hardwareMap, this.telemetry);
    }

    private void buildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)
                .afterTime(0, horiSlides.runHoriSlidesAuto(0.8))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.ARMUP))
                .strafeToLinearHeading(new Vector2d(35.3, -56), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(-55.7, -53.4), Math.toRadians(225))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(4700))
                .waitSeconds(4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))
                .afterTime(1, intake.runBoxAuto(Intake.AutoActionModes.OUTTAKE))

                .waitSeconds(2)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.ARMUP))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(1, intake.runBoxAuto(Intake.AutoActionModes.DEPOSIT))

                .strafeToLinearHeading(new Vector2d(45, -57), Math.toRadians(180))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.REST));


        trajectory = trajectoryHolder.build();
    }


    @Override
    public void runOpMode() {
        initialize();
        buildTrajectories();







        waitForStart();


        Actions.runBlocking(
            new ParallelAction(
                 trajectory, vertiSlides.updateVertiSlidesAuto()
            )
        );
    }
}
