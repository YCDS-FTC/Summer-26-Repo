package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

/*
     * Demonstrates an empty iterative OpMode
     */
    @TeleOp(name = "Concept: NullOp", group = "Concept")
    @Disabled
    public class SwerveTeleop extends OpMode {

        private ElapsedTime runtime = new ElapsedTime();
        private final hackingHoundsHardware robot = new hackingHoundsHardware();
        private SwerveDriveCoordinator swerveDrive;

        /**
         * This method will be called once, when the INIT button is pressed.
         */
        @Override
        public void init() {
            robot.init(hardwareMap);
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

            double angle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
            double magnitude = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double twist = gamepad1.right_stick_x;

            //this part makes it field centric, remove to mkae robot centric
            angle -= robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

            swerveDrive.setSwerveDrive(angle, magnitude, twist);

            if (gamepad1.options){
                robot.imu.resetYaw();
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
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


