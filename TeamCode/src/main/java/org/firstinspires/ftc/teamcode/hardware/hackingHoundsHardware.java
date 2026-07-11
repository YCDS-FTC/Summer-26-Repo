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
import com.seattlesolvers.solverslib.util.InterpLUT;

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

    InterpLUT getShootPower = new InterpLUT();
    public static double shooterPower = 0;
    InterpLUT getHoodAngle = new InterpLUT();
    public static double hoodAngle = 0;

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


        getHoodAngle.add(-100, 0.23);
        getHoodAngle.add(0,0.23);
        getHoodAngle.add(30, 0.22);
        getHoodAngle.add(40, 0.18);
        getHoodAngle.add(45, 0.17);
        getHoodAngle.add(50, 0.16);
        getHoodAngle.add(55, 0.16);
        getHoodAngle.add(60, 0.14);
        getHoodAngle.add(65, 0.14);
        getHoodAngle.add(70, 0.14);
        getHoodAngle.add(75, 0.13);
        getHoodAngle.add(80, 0.11);
        getHoodAngle.add(85, 0.11);
        getHoodAngle.add(90, 0.11);
        getHoodAngle.add(100, 0.08);
        getHoodAngle.add(110, 0.07);
        getHoodAngle.add(120, 0.09);
        getHoodAngle.add(123, 0.086);
        getHoodAngle.add(125, 0.095);
        getHoodAngle.add(130, 0.1);
        getHoodAngle.add(140, .1);
        getHoodAngle.add(150, .08);
        getHoodAngle.add(190, .15);

        getShootPower.add(-200, 1000);
        getShootPower.add(1,1000);
        getShootPower.add(27, 1000);
        getShootPower.add(30, 960);
        getShootPower.add(40, 1000);
        getShootPower.add(45, 1020);
        getShootPower.add(50, 1080);
        getShootPower.add(55, 1100);
        getShootPower.add(60, 1140);
        getShootPower.add(65, 1140);
        getShootPower.add(70, 1160);
        getShootPower.add(75, 1220);
        getShootPower.add(80, 1260);
        getShootPower.add(85, 1280);
        getShootPower.add(90, 1320);
        getShootPower.add(100, 1350);
        getShootPower.add(105, 1460);

        getShootPower.add(110, 1470);
        getShootPower.add(120, 1520);
        getShootPower.add(125, 1590);
        getShootPower.add(130,1600);
        getShootPower.add(135,1640);
        getShootPower.add(140,1680);
        getShootPower.add(150, 1720);
        getShootPower.add(250, 1700);

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


    public double getHoodAngle (double distanceToGoal) {
        getHoodAngle.createLUT();

        if (distanceToGoal > 200) {
            hoodAngle = 0;
        } else if (distanceToGoal < -70) {
            hoodAngle = 0;
        } else{
            hoodAngle = getHoodAngle.get(distanceToGoal);

        }

        return hoodAngle;

    }

    public double getshooterPower(double distanceToGoal) {
        getShootPower.createLUT();


        shooterPower = getShootPower.get(distanceToGoal);


        return shooterPower;
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

