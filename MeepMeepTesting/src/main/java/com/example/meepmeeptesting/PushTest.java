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
                .setConstraints(75, 75, Math.toRadians(180), Math.toRadians(180), 12.5)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -61, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(9, -40), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(9, -44), Math.toRadians(270))

                .strafeToLinearHeading(new Vector2d(27, -38), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(40, 0, Math.toRadians(270)),Math.toRadians(320))

                .splineToLinearHeading(new Pose2d(45, -55, Math.toRadians(270)), Math.toRadians(270))

                .strafeToLinearHeading(new Vector2d(46, -20), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(51, 0, Math.toRadians(270)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(58, -10, Math.toRadians(270)), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(58, -55), Math.toRadians(270))





                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}