/*
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
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PinpointDrive;

import mechanisms.Caroline;
import mechanisms.HoriSlides;
import mechanisms.Intake;
import mechanisms.VertiSlides;

@Autonomous
public class SpecimanAuto extends LinearOpMode {

    private PinpointDrive drive;
    private Pose2d initialPose;
    private Action trajectory;
    private Caroline caroline;
    private void Initialize() {
        initialPose = new Pose2d(9, -60.85, Math.toRadians(90));
        drive = new PinpointDrive(hardwareMap, initialPose);
        caroline=new Caroline(hardwareMap);
        caroline.OpeningMove();
    }

    private void BuildTrajectories() {
        TrajectoryActionBuilder trajectoryHolder = drive.actionBuilder(initialPose)




                .strafeToLinearHeading(new Vector2d(9, -43.45), Math.toRadians(90))
                .afterTime(50, caroline.specimenDepositAuto());
                //.waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSITDEPOSIT))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(300))
                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(HoriSlides.in))
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
                .strafeToLinearHeading(new Vector2d(49.5, -43.7), Math.toRadians(90))  //GRAB POS
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.INTAKELOL))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(789))
                .afterTime(0, horiSlides.runHoriSlidesAuto(HoriSlides.out))
                .waitSeconds(.6)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(.2)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.RESTLOL))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(HoriSlides.in))
                .afterTime(0.3, vertiSlides.runVertiSlidesAuto(1769))
                .afterTime(1.2, intake.runBoxAuto(Intake.AutoActionModes.FLIPWRISTDEPOSIT))
                .strafeToLinearHeading(new Vector2d(2, -44), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40)) // DESPOSIT POS
                .waitSeconds(.01)
                .strafeToLinearHeading(new Vector2d(2, -39.2), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40))

                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSITDEPOSIT))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(300))
                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(HoriSlides.in))
                .strafeToLinearHeading(new Vector2d(6, -43), Math.toRadians(90))


                .afterTime(0.1, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.REST))




                //second cycle
                .strafeToLinearHeading(new Vector2d(46, -42.5), Math.toRadians(93))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.INTAKELOL))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(798))
                .afterTime(0, horiSlides.runHoriSlidesAuto(HoriSlides.out))
                .waitSeconds(.6)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(.2)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.RESTLOL))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(HoriSlides.in))
                .afterTime(0.3, vertiSlides.runVertiSlidesAuto(1789))
                .afterTime(0.8, intake.runBoxAuto(Intake.AutoActionModes.FLIPWRISTDEPOSIT))
                .strafeToLinearHeading(new Vector2d(-3, -37.5), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40))

                .waitSeconds(0)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSITDEPOSIT))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(300))
                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(HoriSlides.in))
                .strafeToLinearHeading(new Vector2d(6, -42.7), Math.toRadians(90))


                .afterTime(0.1, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .afterTime(0.1, intake.runBoxAuto(Intake.AutoActionModes.REST))













                //Third cycle
                .strafeToLinearHeading(new Vector2d(46, -42.5), Math.toRadians(93)) //supposed to be y=-41
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.INTAKELOL))
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(799))
                .afterTime(0, horiSlides.runHoriSlidesAuto(HoriSlides.out))
                .waitSeconds(.6)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                .waitSeconds(.2)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.RESTLOL))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(HoriSlides.in))
                .afterTime(0.3, vertiSlides.runVertiSlidesAuto(1799))
                .afterTime(0.8, intake.runBoxAuto(Intake.AutoActionModes.FLIPWRISTDEPOSIT))
                .strafeToLinearHeading(new Vector2d(-7, -37.65), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40))

                .waitSeconds(0.4)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.DEPOSITDEPOSIT))
                .afterTime(0, vertiSlides.runVertiSlidesAuto(300))
                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.3)
                .afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.OPENCLAW))
                .waitSeconds(0)
                .afterTime(0.2, vertiSlides.runVertiSlidesAuto(0))
                .afterTime(0.3, horiSlides.runHoriSlidesAuto(HoriSlides.in))
                .strafeToLinearHeading(new Vector2d(47.5, -55.2), Math.toRadians(200),  new TranslationalVelConstraint(150), new ProfileAccelConstraint(-150, 150))*/
               // .afterTime(0, horiSlides.runHoriSlidesAuto(0.5));


                //.afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.CLOSECLAW))
                //.afterTime(0, intake.runBoxAuto(Intake.AutoActionModes.REST));






