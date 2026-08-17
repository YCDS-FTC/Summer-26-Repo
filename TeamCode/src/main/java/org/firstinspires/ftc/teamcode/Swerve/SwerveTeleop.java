package org.firstinspires.ftc.teamcode.Swerve;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

import gay.zharel.fateweaver.flight.FlightLogChannel;
import gay.zharel.fateweaver.flight.FlightRecorder;
import gay.zharel.fateweaver.schemas.LongSchema;
import gay.zharel.fateweaver.schemas.DoubleSchema;

@TeleOp(name = "Swerve TeleOp", group = "Swerve")
public class SwerveTeleop extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private final hackingHoundsHardware robot = new hackingHoundsHardware();
    private SwerveDrivetrain drivetrain;
    private SwerveDriveCoordinator swerveDrive;
    private FtcDashboard dashboard;

    FlightLogChannel<Long> timestamps;

    FlightLogChannel<Double> angleLog;
    FlightLogChannel<Double> magnitudeLog;
    FlightLogChannel<Double> twistLog;
    FlightLogChannel<Double> yawLog;

    FlightLogChannel<Double> leftFrontAngleLog;
    FlightLogChannel<Double> leftBackAngleLog;
    FlightLogChannel<Double> rightFrontAngleLog;
    FlightLogChannel<Double> rightBackAngleLog;

    FlightLogChannel<Double> leftFrontPowerLog;
    FlightLogChannel<Double> leftBackPowerLog;
    FlightLogChannel<Double> rightFrontPowerLog;
    FlightLogChannel<Double> rightBackPowerLog;

    /**
     * This method will be called once, when the INIT button is pressed.
     */
    @Override
    public void init() {
        robot.init(hardwareMap);

        drivetrain = new SwerveDrivetrain(robot);
        swerveDrive = new SwerveDriveCoordinator(
                drivetrain.leftFrontPod,
                drivetrain.leftBackPod,
                drivetrain.rightFrontPod,
                drivetrain.rightBackPod
        );

        dashboard = FtcDashboard.getInstance();

        timestamps = FlightRecorder.createChannel("TIMESTAMP", LongSchema.INSTANCE);

        angleLog = FlightRecorder.createChannel("Drive/Angle", DoubleSchema.INSTANCE);
        magnitudeLog = FlightRecorder.createChannel("Drive/Magnitude", DoubleSchema.INSTANCE);
        twistLog = FlightRecorder.createChannel("Drive/Twist", DoubleSchema.INSTANCE);
        yawLog = FlightRecorder.createChannel("Drive/Yaw", DoubleSchema.INSTANCE);

        leftFrontAngleLog = FlightRecorder.createChannel("Pods/LeftFront/Angle", DoubleSchema.INSTANCE);
        leftBackAngleLog = FlightRecorder.createChannel("Pods/LeftBack/Angle", DoubleSchema.INSTANCE);
        rightFrontAngleLog = FlightRecorder.createChannel("Pods/RightFront/Angle", DoubleSchema.INSTANCE);
        rightBackAngleLog = FlightRecorder.createChannel("Pods/RightBack/Angle", DoubleSchema.INSTANCE);

        leftFrontPowerLog = FlightRecorder.createChannel("Pods/LeftFront/Power", DoubleSchema.INSTANCE);
        leftBackPowerLog = FlightRecorder.createChannel("Pods/LeftBack/Power", DoubleSchema.INSTANCE);
        rightFrontPowerLog = FlightRecorder.createChannel("Pods/RightFront/Power", DoubleSchema.INSTANCE);
        rightBackPowerLog = FlightRecorder.createChannel("Pods/RightBack/Power", DoubleSchema.INSTANCE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
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

        double yaw = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

        //this part makes it field centric, remove to make robot centric
        angle -= yaw;

        swerveDrive.setSwerveDrive(angle, magnitude, twist);

        if (gamepad1.options){
            robot.imu.resetYaw();
        }

        double leftFrontAngle = EncoderUtil.normalize360(drivetrain.leftFrontPod.getAngle());
        double leftBackAngle = EncoderUtil.normalize360(drivetrain.leftBackPod.getAngle());
        double rightFrontAngle = EncoderUtil.normalize360(drivetrain.rightFrontPod.getAngle());
        double rightBackAngle = EncoderUtil.normalize360(drivetrain.rightBackPod.getAngle());

        double leftFrontPower = robot.leftFront.getPower();
        double leftBackPower = robot.leftBack.getPower();
        double rightFrontPower = robot.rightFront.getPower();
        double rightBackPower = robot.rightBack.getPower();

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("angle", angle);
        packet.put("magnitude", magnitude);
        packet.put("twist", twist);
        packet.put("yaw", yaw);
        packet.put("Left_Front_Angle", leftFrontAngle);
        packet.put("Left_Back_Angle", leftBackAngle);
        packet.put("Right_Front_Angle", rightFrontAngle);
        packet.put("Right_Back_Angle", rightBackAngle);
        packet.put("Left_Front_Power", leftFrontPower);
        packet.put("Left_Back_Power", leftBackPower);
        packet.put("Right_Front_Power", rightFrontPower);
        packet.put("Right_Back_Power", rightBackPower);
        dashboard.sendTelemetryPacket(packet);

        timestamps.put(System.nanoTime());
        angleLog.put(angle);
        magnitudeLog.put(magnitude);
        twistLog.put(twist);
        yawLog.put(yaw);

        leftFrontAngleLog.put(leftFrontAngle);
        leftBackAngleLog.put(leftBackAngle);
        rightFrontAngleLog.put(rightFrontAngle);
        rightBackAngleLog.put(rightBackAngle);

        leftFrontPowerLog.put(leftFrontPower);
        leftBackPowerLog.put(leftBackPower);
        rightFrontPowerLog.put(rightFrontPower);
        rightBackPowerLog.put(rightBackPower);
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