#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

@TeleOp(name = "${NAME}", group = "Example")
#parse("File Header.java")
public class ${NAME} extends OpMode {

private final hackingHoundsHardware robot = new hackingHoundsHardware();
    
    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        
    }
    
}
