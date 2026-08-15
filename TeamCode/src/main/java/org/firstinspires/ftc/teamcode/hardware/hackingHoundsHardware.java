package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.Rev9AxisImu;
import com.qualcomm.hardware.rev.Rev9AxisImuOrientationOnRobot;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
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
    public DcMotorEx  leftFront;
    public DcMotorEx rightFront;
    public DcMotorEx  leftBack;
    public DcMotorEx  rightBack;
    public CRServo leftFrontSteer, leftBackSteer, rightFrontSteer, rightBackSteer;
    public AnalogInput leftFrontEncoder, leftBackEncoder, rightFrontEncoder, rightBackEncoder;
    public IMU imu;
    public GoBildaPinpointDriver pinpoint;

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

        leftFront = robotMap.get(DcMotorEx.class,"leftFront");
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontSteer = robotMap.get(CRServo.class, "leftFrontSteer");
        leftFrontEncoder = robotMap.get(AnalogInput.class, "leftFrontEncoder");

        leftBack = robotMap.get(DcMotorEx.class,"leftBack");
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackSteer = robotMap.get(CRServo.class, "leftBackSteer");
        leftBackEncoder = robotMap.get(AnalogInput.class, "leftBackEncoder");

        rightFront = robotMap.get(DcMotorEx.class,"rightFront");
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontSteer = robotMap.get(CRServo.class, "rightFrontSteer");
        rightFrontEncoder = robotMap.get(AnalogInput.class, "rightFrontEncoder");

        rightBack = robotMap.get(DcMotorEx.class,"rightBack");
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackSteer = robotMap.get(CRServo.class, "rightBackSteer");
        rightBackEncoder = robotMap.get(AnalogInput.class, "rightBackEncoder");


        pinpoint = robotMap.get(GoBildaPinpointDriver.class, "pinpoint");




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

