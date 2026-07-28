#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import com.pedropathing.ivy.Command;
import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

#parse("File Header.java")
public class ${NAME} {
    private final hackingHoundsHardware robot;

    public ${NAME}(hackingHoundsHardware robot) {
        this.robot = robot;
    }

    public Command exampleCommand(double value) {
        return Command.build()
                .setExecute(() -> {
                });
    }
}