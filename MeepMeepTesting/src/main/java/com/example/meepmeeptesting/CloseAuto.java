package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class CloseAuto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 70, Math.toRadians(180), Math.toRadians(180), 12.5)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, -61, Math.toRadians(180)))
                .strafeToLinearHeading(new Vector2d(-35, -50), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(-54, -54), Math.toRadians(225))

                .strafeToLinearHeading(new Vector2d(-48, -45), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(-54, -54), Math.toRadians(225))

                .strafeToLinearHeading(new Vector2d(-56, -45), Math.toRadians(275))
                .strafeToLinearHeading(new Vector2d(-54, -54), Math.toRadians(225))

                .strafeToLinearHeading(new Vector2d(-56, -45), Math.toRadians(295))
                .strafeToLinearHeading(new Vector2d(-54, -54), Math.toRadians(225))












                .build());



                ;

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}