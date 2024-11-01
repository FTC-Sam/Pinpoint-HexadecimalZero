package Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PinpointDrive;


@Config
@Autonomous(name = "ParkSideAuto")
public class ParkSideAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Action trajectory;


    private void initialize() {
        initialPose = new Pose2d(35.3, -61, Math.toRadians(180));
        drive = new PinpointDrive(hardwareMap, initialPose);
    }

    private void buildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)
                .lineToX(50);


        trajectory = trajectoryHolder.build();
    }


    @Override
    public void runOpMode() {
        initialize();
        buildTrajectories();







        waitForStart();


        Actions.runBlocking(
            new ParallelAction(
                 trajectory
            )
        );
    }
}
