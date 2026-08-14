package org.firstinspires.ftc.teamcode.Swerve;

import static org.firstinspires.ftc.teamcode.Swerve.EncoderUtil.closestAngle;

public class SwerveDriveCoordinator {
        SwerveDrivePod leftFrontWheel;
        SwerveDrivePod leftBackWheel;
        SwerveDrivePod rightFrontWheel;
        SwerveDrivePod rightBackWheel;

        public SwerveDriveCoordinator(SwerveDrivePod leftFrontWheel,
                                      SwerveDrivePod leftBackWheel,
                                      SwerveDrivePod rightFrontWheel,
                                      SwerveDrivePod rightBackWheel) {
            this.leftFrontWheel = leftFrontWheel;
            this.leftBackWheel = leftBackWheel;
            this.rightFrontWheel = rightFrontWheel;
            this.rightBackWheel = rightBackWheel;
        }

    public void translate(double direction, double power) {
        leftFrontWheel.setPod(direction, power);
        leftBackWheel.setPod(direction,power);
        rightFrontWheel.setPod(direction, power);
        rightBackWheel.setPod(direction, power);
    }

    public void inplaceTurn(double power){
        leftFrontWheel.setPod(135, power);
        leftBackWheel.setPod(45,power);
        rightFrontWheel.setPod(-45, power);
        rightBackWheel.setPod(-135, power);
    }

    public void translateTurn(double direction, double power, double turnPower)
    {
        double turnAngle = turnPower * 45.0;

        // if the left front wheel is in the front
        if (closestAngle(direction, 135.0) >= 90.0)
        {
            leftFrontWheel.setPod((direction + turnAngle), power);
        }
        // if it's in the back
        else
        {
            leftFrontWheel.setPod((direction - turnAngle), power);
        }
        // if the left back wheel is in the front
        if (closestAngle(direction, 225.0) > 90.0)
        {
            leftBackWheel.setPod((direction + turnAngle), power);
        }
        // if it's in the back
        else
        {
            leftBackWheel.setPod((direction - turnAngle), power);
        }
        // if the right front wheel is in the front
        if (closestAngle(direction, 45.0) > 90.0)
        {
            rightFrontWheel.setPod((direction + turnAngle), power);
        }
        // if it's in the back
        else
        {
            rightFrontWheel.setPod((direction - turnAngle), power);
        }
        // if the right back wheel is in the front
        if (closestAngle(direction, 315.0) >= 90.0)
        {
            rightBackWheel.setPod((direction + turnAngle), power);
        }
        // if it's in the back
        else
        {
            rightBackWheel.setPod((direction - turnAngle), power);
        }
    }
    public void setSwerveDrive(double direction, double translatePower, double turnPower)
    {
        if ((translatePower == 0.0) && (turnPower != 0.0))
        {
            inplaceTurn(turnPower);
        }
        else
        {
            translateTurn(direction, translatePower, turnPower);
        }
    }
}
