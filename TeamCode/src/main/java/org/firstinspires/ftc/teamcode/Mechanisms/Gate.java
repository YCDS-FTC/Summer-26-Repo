package org.firstinspires.ftc.teamcode.Mechanisms;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static org.firstinspires.ftc.teamcode.util.RobotConstants.gateClose;
import static org.firstinspires.ftc.teamcode.util.RobotConstants.gateDurationMs;
import static org.firstinspires.ftc.teamcode.util.RobotConstants.gateOpen;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Gate {

    private final hackingHoundsHardware robot;

    public Gate(hackingHoundsHardware robot) {
        this.robot = robot;
    }

    /**
     * Opens the gate, waits gateDurationMs, then closes it.
     */
    public Command cycleGate() {
        return sequential(
                openGate(),
                waitMs(gateDurationMs),
                closeGate()
        );
    }

    /**
     * Opens the gate
     */
    public Command openGate() {
        return instant(() -> robot.gate.setPosition(gateOpen));
    }

    /**
     * Closes the gate
     */
    public Command closeGate() {
        return instant(() -> robot.gate.setPosition(gateClose));
    }
}