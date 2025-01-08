package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class FarAuto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -61, Math.toRadians(90)))


                //preload
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(9, -40), Math.toRadians(90))
                .waitSeconds(1)

                .setReversed(true)
                .splineTo(new Vector2d(15, -45), Math.toRadians(0))
                .splineTo(new Vector2d(35, -35), Math.toRadians(30))

                .turnTo(Math.toRadians(135))

                .setReversed(false)

                .turnTo(Math.toRadians(205))
                .turnTo(Math.toRadians(135))

                .turnTo(Math.toRadians(195))
                .turnTo(Math.toRadians(135))












                //first specimen
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(9, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-9, -33, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(1)






                //second specimen
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .waitSeconds(1)
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(15, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-3, -33, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(1)







                //third specimen
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .waitSeconds(1)
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(21, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(3, -33, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(1)










                //fourth specimen
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(9, -43, Math.toRadians(90)), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(47, -43), Math.toRadians(90))
                .waitSeconds(1)
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(27, -43), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(9, -33, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(1)


                //park
                .strafeToSplineHeading(new Vector2d(30, -50), Math.toRadians(125))















                .build());




        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}