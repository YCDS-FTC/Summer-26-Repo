package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.Rev9AxisImu;
import com.qualcomm.hardware.rev.Rev9AxisImuOrientationOnRobot;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;


import java.util.List;


// Generic robot class
public class hackingHoundsHardware extends hardware {
    public HardwareMap robotMap;
    public DcMotorEx leftFront;
    public DcMotorEx rightFront;
    public DcMotorEx leftBack;
    public DcMotorEx rightBack;
    public CRServo leftFrontSteer, leftBackSteer, rightFrontSteer, rightBackSteer;
    public AnalogInput leftFrontEncoder, leftBackEncoder, rightFrontEncoder, rightBackEncoder;
    public IMU imu;
    public GoBildaPinpointDriver pinpoint;


    /* Constructor */
    public hackingHoundsHardware(){}

    // Override to set actual robot configuration
    public void init(HardwareMap hwMap) {
        robotMap = hwMap;

//        pinpoint = robotMap.get(GoBildaPinpointDriver.class, "pinpoint");

        leftFront = robotMap.get(DcMotorEx.class, "leftFront");
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontSteer = robotMap.get(CRServo.class, "leftFrontSteer");
        leftFrontEncoder = robotMap.get(AnalogInput.class, "leftFrontEncoder");

        leftBack = robotMap.get(DcMotorEx.class, "leftBack");
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackSteer = robotMap.get(CRServo.class, "leftBackSteer");
        leftBackEncoder = robotMap.get(AnalogInput.class, "leftBackEncoder");

        rightFront = robotMap.get(DcMotorEx.class, "rightFront");
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontSteer = robotMap.get(CRServo.class, "rightFrontSteer");
        rightFrontEncoder = robotMap.get(AnalogInput.class, "rightFrontEncoder");

        rightBack = robotMap.get(DcMotorEx.class, "rightBack");
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackSteer = robotMap.get(CRServo.class, "rightBackSteer");
        rightBackEncoder = robotMap.get(AnalogInput.class, "rightBackEncoder");


        pinpoint = robotMap.get(GoBildaPinpointDriver.class, "pinpoint");


        imu = robotMap.get(Rev9AxisImu.class, "imu");
        Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection usb = Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection.BACKWARD;
        Rev9AxisImuOrientationOnRobot.LogoFacingDirection logo = Rev9AxisImuOrientationOnRobot.LogoFacingDirection.UP;
        Rev9AxisImuOrientationOnRobot orientationOnRobot = new Rev9AxisImuOrientationOnRobot(logo, usb);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        List<LynxModule> allHubs = robotMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

    }




}

