package org.firstinspires.ftc.teamcode.Mechanisms;

import static org.firstinspires.ftc.teamcode.util.LookUpTables.hoodAngleLut;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

import com.pedropathing.ivy.Command;
import com.seattlesolvers.solverslib.util.InterpLUT;

public class Hood {

    private final hackingHoundsHardware robot;

    public static double hoodAngle;


    public Hood (hackingHoundsHardware robot){
        this.robot = robot;

    }

    /**
     * @param distanceToGoal input the distance from the robot to the goal and the hood wil go to the correct angle
     */
    public Command setHoodAngle(double distanceToGoal){
        return Command.build()
                .setExecute(() -> {
                    if (distanceToGoal > 200){
                        hoodAngle = 0;
                    } else if (distanceToGoal < -70) {
                        hoodAngle = 0;
                    } else{
                        hoodAngle = hoodAngleLut.get(distanceToGoal);
                    }
                    robot.hood.setPosition(hoodAngle);
                });
    }

}
