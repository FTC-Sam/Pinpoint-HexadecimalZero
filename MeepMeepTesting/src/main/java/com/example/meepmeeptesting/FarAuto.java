package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class FarAuto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(33, -61, Math.toRadians(90)))
                .lineToY(-38)
                .strafeToLinearHeading(new Vector2d(-50, -38), Math.toRadians(180))

                .strafeToLinearHeading(new Vector2d(-56, -56), Math.toRadians(225))
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(35, -40), Math.toRadians(225))
                .waitSeconds(3)

                .strafeToLinearHeading(new Vector2d(-56, -56), Math.toRadians(225))
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(45, -40), Math.toRadians(225))
                .waitSeconds(3)

                .strafeToLinearHeading(new Vector2d(-56, -56), Math.toRadians(225))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-50, -50), Math.toRadians(225))







                .build());



                ;

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}