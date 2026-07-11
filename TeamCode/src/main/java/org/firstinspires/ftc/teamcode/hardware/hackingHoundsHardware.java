package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.Rev9AxisImu;
import com.qualcomm.hardware.rev.Rev9AxisImuOrientationOnRobot;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.List;


// Generic robot class
public class hackingHoundsHardware extends hardware {
    public HardwareMap robotMap;
    public Limelight3A limelight;
    public DcMotorEx  leftFront;
    public DcMotorEx rightFront;
    public DcMotorEx  leftBack;
    public DcMotorEx  rightBack;
    public DcMotorEx intake;
    public DcMotorEx shooterTop;
    public DcMotorEx shooterBottom;
    public DcMotorEx turret;
    public Servo kickstand1;
    public Servo kickstand2;
    public Servo gate;
    public Servo hood;
    public Servo shooterLight;
    public Servo intakeLight;
    public IMU imu;
    public GoBildaPinpointDriver pinpoint;
    public RevColorSensorV3 color1, color2;
    public DistanceSensor distance0;


    public double lastAngle;
    private double globalAngle;


    public double turretMin = -270;
    public double turretMax =105;
    public double buffer = 3;
    /* Constructor */
    public hackingHoundsHardware(){}

    // Override to set actual robot configuration
    public void init(HardwareMap hwMap) {
        robotMap = hwMap;

//        pinpoint = robotMap.get(GoBildaPinpointDriver.class, "pinpoint");

        leftFront = initMotor("leftFront", DcMotor.Direction.REVERSE);
        rightFront = initMotor("rightFront", DcMotor.Direction.FORWARD);
        leftBack = initMotor("leftBack", DcMotor.Direction.REVERSE);
        rightBack = initMotor("rightBack", DcMotor.Direction.FORWARD);
        intake = initMotor("intake", DcMotorEx.Direction.FORWARD);
        shooterTop = initMotor("shooterTop", DcMotorEx.Direction.REVERSE);
        shooterBottom = initMotor("shooterBottom", DcMotorEx.Direction.FORWARD);
        turret = robotMap.get(DcMotorEx.class, "turret");
        turret.setDirection(DcMotorSimple.Direction.FORWARD);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);




        kickstand1 = robotMap.get(Servo.class, "kickstand1");
        kickstand2 = robotMap.get(Servo.class, "kickstand2");
        gate = robotMap.get(Servo.class,"gate");
        hood = robotMap.get(Servo.class,"hood");

        limelight = robotMap.get(Limelight3A.class, "limelight");
        pinpoint = robotMap.get(GoBildaPinpointDriver.class, "pinpoint");


        distance0 = robotMap.get(DistanceSensor.class,"distance0");
        color1 = robotMap.get(RevColorSensorV3.class,"color1");
        color1.setGain(8);
        color2 = robotMap.get(RevColorSensorV3.class,"color2");
        color2.setGain(8);

        intakeLight = robotMap.get(Servo.class, "shooterLight");
        shooterLight = robotMap.get(Servo.class,"light");

//        Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection usb = Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection.LEFT;
//        Rev9AxisImuOrientationOnRobot.LogoFacingDirection logo = Rev9AxisImuOrientationOnRobot.LogoFacingDirection.DOWN;
//
        imu = robotMap.get(Rev9AxisImu.class, "imu");
        Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection usb = Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection.BACKWARD;
        Rev9AxisImuOrientationOnRobot.LogoFacingDirection logo = Rev9AxisImuOrientationOnRobot.LogoFacingDirection.UP;
        Rev9AxisImuOrientationOnRobot orientationOnRobot = new Rev9AxisImuOrientationOnRobot(logo, usb);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        //imu.resetYaw();
        lastAngle = 0;

        List<LynxModule> allHubs = robotMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

    }

    public double clamp(double x, double min, double max) {
        return Math.max(min,Math.min(max,x));
    }

    private DcMotorEx initMotor(String name, DcMotor.Direction direction) {
        DcMotorEx motor = robotMap.get(DcMotorEx.class, name);
        motor.setDirection(direction);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        return motor;
    }

    public double getAngle() {
        double angle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

        double deltaAngle = angle - lastAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngle = angle;

        return globalAngle;


    }

    public double odometryDistanceRed(double X, double Y){

        double distanceToGoal1 = Math.sqrt(Math.pow(144 - X, 2) + Math.pow(144 - Y, 2));

        return distanceToGoal1 - 25.5;
    }

    public double odometryDistanceBlue(double X, double Y){

        double distanceToGoal1 = Math.sqrt(Math.pow(0 - X, 2) + Math.pow(144 - Y, 2));

        return distanceToGoal1 - 25.5;
    }




    public double normA(double angle) {
        angle %= 360;
        return angle;
    }

    public double normTurret(double angle) {
        angle %= 360;
        if (angle < -275)angle += 360;
        if (angle > 160)angle -= 360;
        return angle;
    }

}

