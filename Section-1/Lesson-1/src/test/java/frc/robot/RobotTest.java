package frc.robot;

import edu.wpi.first.hal.HAL;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    void testRobotClass() {
        System.setOut(new PrintStream(outputStreamCaptor));
        var robot = new Robot();
        assertTrue(HAL.initialize(500, 0));
        assertTrue(
            fetchLastLine().toLowerCase().contains("hello world"),
            "To pass this exercise, make sure to print 'hello world'" +
                " when the robot program starts!"
        );
        robot.robotPeriodic();
        assertTrue(
            fetchLastLine().toLowerCase().contains("robot periodic"),
            "To pass this exercise, make sure to print 'robot periodic'" +
                " every 0.02 seconds!"
        );
        robot.teleopPeriodic();
        assertTrue(
            fetchLastLine().toLowerCase().contains("teleop periodic"),
            "To pass this exercise, make sure to print 'teleop periodic'" +
                " every 0.02 seconds in teleop mode!"
        );
        robot.autonomousPeriodic();
        assertTrue(
            fetchLastLine().toLowerCase().contains("autonomous periodic"),
            "To pass this exercise, make sure to print 'autonomous periodic'" +
                " every 0.02 seconds in autonomous mode!"
        );
        robot.close();
        System.setOut(standardOut);
    }

    private String fetchLastLine() {
        var lines = outputStreamCaptor.toString().split(System.lineSeparator());
        return lines.length == 0 ? "" : lines[lines.length - 1];
    }
}
