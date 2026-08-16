package org.firstinspires.ftc.teamcode.Swerve;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

@TeleOp(name = "Swerve TeleOp", group = "Swerve")
public class SwerveTeleop extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private final hackingHoundsHardware robot = new hackingHoundsHardware();
    private SwerveDriveCoordinator swerveDrive;
    private FtcDashboard dashboard;

    /**
     * This method will be called once, when the INIT button is pressed.
     */
    @Override
    public void init() {
        robot.init(hardwareMap);

        SwerveDrivetrain drivetrain = new SwerveDrivetrain(robot);
        swerveDrive = new SwerveDriveCoordinator(
                drivetrain.leftFrontPod,
                drivetrain.leftBackPod,
                drivetrain.rightFrontPod,
                drivetrain.rightBackPod
        );
        dashboard = FtcDashboard.getInstance();

        telemetry.addData("Status", "Initialized");
    }

    /**
     * This method will be called repeatedly during the period between when
     * the INIT button is pressed and when the START button is pressed (or the
     * OpMode is stopped).
     */
    @Override
    public void init_loop() {
    }

    /**
     * This method will be called once, when the START button is pressed.
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /**
     * This method will be called repeatedly during the period between when
     * the START button is pressed and when the OpMode is stopped.
     */
    @Override
    public void loop() {

        double angle = Math.toDegrees(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x));
        double magnitude = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double twist = gamepad1.right_stick_x;

        //this part makes it field centric, remove to mkae robot centric
        angle -= robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

        swerveDrive.setSwerveDrive(angle, magnitude, twist);

        if (gamepad1.options){
            robot.imu.resetYaw();
        }

        double leftFrontAngle = EncoderUtil.getEncoderAngle(robot.leftFrontEncoder);
        double leftBackAngle = EncoderUtil.getEncoderAngle(robot.leftBackEncoder);
        double rightFrontAngle = EncoderUtil.getEncoderAngle(robot.rightFrontEncoder);
        double rightBackAngle = EncoderUtil.getEncoderAngle(robot.rightBackEncoder);

        double leftFrontPower = robot.leftFront.getPower();
        double leftBackPower = robot.leftBack.getPower();
        double rightFrontPower = robot.rightFront.getPower();
        double rightBackPower = robot.rightBack.getPower();

        telemetry.addData("Status", "Run Time: " + runtime.toString());

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("angle", angle);
        packet.put("magnitude", magnitude);
        packet.put("twist", twist);
        packet.put("yaw", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        packet.put("Left_Front_Angle", leftFrontAngle);
        packet.put("Left_Back_Angle", leftBackAngle);
        packet.put("Right_Front_Angle", rightFrontAngle);
        packet.put("Right_Back_Angle", rightBackAngle);
        packet.put("Left_Front_Power", leftFrontPower);
        packet.put("Left_Back_Power", leftBackPower);
        packet.put("Right_Front_Power", rightFrontPower);
        packet.put("Right_Back_Power", rightBackPower);
        dashboard.sendTelemetryPacket(packet);

    }

    /**
     * This method will be called once, when this OpMode is stopped.
     * <p>
     * Your ability to control hardware from this method will be limited.
     */
    @Override
    public void stop() {

    }
}