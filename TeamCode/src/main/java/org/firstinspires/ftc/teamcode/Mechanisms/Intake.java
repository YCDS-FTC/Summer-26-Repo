package org.firstinspires.ftc.teamcode.Mechanisms;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Intake {

    private final hackingHoundsHardware robot;


    public Intake(hackingHoundsHardware robot) {
        this.robot = robot;
    }

    /**
     * @param power Sets the intake power
     */
    public Command runIntake(double power) {
        return Command.build()
                .setExecute(() -> {
                    robot.intake.setPower(power);
                });


    }


    /**
     * Stops the intake
     */
    public Command stopIntake(){
        return Command.build()
                .setExecute(() -> {
                    robot.intake.setPower(0);
                });
    }

}
