package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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

                .waitSeconds(.5)

                .strafeToLinearHeading(new Vector2d(9, -43.95), Math.toRadians(90))
                .waitSeconds(0.4)

                .waitSeconds(0.3)

                .waitSeconds(0)

                .strafeToLinearHeading(new Vector2d(9, -43), Math.toRadians(90))




                .strafeToLinearHeading(new Vector2d(9, -46), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(27, -38), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(44, -10, Math.toRadians(90)),Math.toRadians(50))


                .strafeToLinearHeading(new Vector2d(48, -45), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(56, -4), Math.toRadians(90))



                .strafeToLinearHeading(new Vector2d(61, -45), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(49.5, -43.7), Math.toRadians(90))  //GRAB POS

                .waitSeconds(.6)


                .strafeToLinearHeading(new Vector2d(2, -37.75), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40)) // DESPOSIT POS

                .waitSeconds(0.4)

                .waitSeconds(0.3)

                .waitSeconds(0)

                .strafeToLinearHeading(new Vector2d(6, -43), Math.toRadians(90))





                //second cycle
                .strafeToLinearHeading(new Vector2d(46, -42.5), Math.toRadians(93))

                .waitSeconds(.6)

                .waitSeconds(.2)

                .strafeToLinearHeading(new Vector2d(-3, -37.75), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40))

                .waitSeconds(0)

                //.afterTime(0.3, horiSlides.runHoriSlidesAuto(0.55))
                .waitSeconds(0.3)

                .waitSeconds(0)

                .strafeToLinearHeading(new Vector2d(6, -42.7), Math.toRadians(90))














                //Third cycle
                .strafeToLinearHeading(new Vector2d(46, -42.5), Math.toRadians(93)) //supposed to be y=-41

                .waitSeconds(.6)

                .waitSeconds(.2)

                .strafeToLinearHeading(new Vector2d(-7, -38.55), Math.toRadians(90), new TranslationalVelConstraint(40), new ProfileAccelConstraint(-20, 40))

                .waitSeconds(0.4)

                .waitSeconds(0.3)

                .waitSeconds(0)

                .strafeToLinearHeading(new Vector2d(47.5, -55.2), Math.toRadians(200),  new TranslationalVelConstraint(150), new ProfileAccelConstraint(-150, 150))

                .build());




        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}