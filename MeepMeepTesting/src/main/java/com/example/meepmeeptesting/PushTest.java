package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class PushTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -61, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(9, -40), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(9, -44), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(27, -38), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(42, -5, Math.toRadians(90)),Math.toRadians(50))


                .strafeToLinearHeading(new Vector2d(46, -55), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(49, -5), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(55, -55), Math.toRadians(90))


                .strafeToLinearHeading(new Vector2d(60, -5), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(62, -55), Math.toRadians(90))



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}