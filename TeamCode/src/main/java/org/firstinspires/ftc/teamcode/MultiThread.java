package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

//Start of multithreading
public class MultiThread implements Runnable{
    private Thread t;
    private String threadName;
    private int height;
    private final int TOLERANCE = 50;
    private RobotDrive robot;
    public boolean running = false;

    public MultiThread(String name,RobotDrive r)
    {
        threadName = name;
        robot = r;
    }
    public void run()
    {
        if(height==robot.levels[0])
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
        else if(height == robot.levels[1])
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
        else if(height == robot.levels[2])
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);

        running = true;
        while(robot.liftMotor.getCurrentPosition() < height || robot.liftMotor.getCurrentPosition() > height + TOLERANCE)
        {
            if (robot.liftMotor.getCurrentPosition() > height + TOLERANCE)
                robot.liftMotor.setPower(-1); //down
            else if (robot.liftMotor.getCurrentPosition() < height)
                robot.liftMotor.setPower(1); //up
        }
        robot.liftMotor.setPower(0);
        robot.turnOnLights();
        running = false;
    }

    public void start()
    {
            t = new Thread (this, threadName);
            t.start ();
    }

    public void terminate() {
        if (t != null)
            t.interrupt();
    }

    public void changeLevel(int h) { height = h; }
}
