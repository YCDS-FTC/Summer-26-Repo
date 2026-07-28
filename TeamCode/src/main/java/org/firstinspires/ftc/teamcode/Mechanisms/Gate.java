package org.firstinspires.ftc.teamcode.Mechanisms;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static org.firstinspires.ftc.teamcode.RobotConstants.gateClose;
import static org.firstinspires.ftc.teamcode.RobotConstants.gateDurationMs;
import static org.firstinspires.ftc.teamcode.RobotConstants.gateOpen;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Gate {

    private final hackingHoundsHardware robot;

    public Gate(hackingHoundsHardware robot) {
        this.robot = robot;
    }

    /**
     * Opens the gate and closes it after a set duration (gateDurationMs)
     */
    public Command cycleGate(){
        ElapsedTime timer = null;
        return Command.build()
                .setExecute(() -> {
                    robot.gate.setPosition(gateOpen);
                    waitMs(gateDurationMs);
                    robot.gate.setPosition(gateClose);
                });
    }

    /**
     * Opens the gate
     */
    public Command openGate(){
        return Command.build()
                .setExecute(() -> {
                   robot.gate.setPosition(gateOpen);
                });
    }

    /**
     * Closes the gate
     */
    public Command closeGate(){
        return Command.build()
                .setExecute(() -> {
                   robot.gate.setPosition(gateClose);
                });
    }

}
