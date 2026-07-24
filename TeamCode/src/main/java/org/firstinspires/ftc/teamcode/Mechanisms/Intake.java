package org.firstinspires.ftc.teamcode.Mechanisms;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Intake {

    private final hackingHoundsHardware robot;


    public Intake(hackingHoundsHardware robot) {
        this.robot = robot;
    }


    public Command setPower(double power) {
        return Command.build()
                .setExecute(() -> {
                    robot.intake.setPower(power);
                });


    }

}
