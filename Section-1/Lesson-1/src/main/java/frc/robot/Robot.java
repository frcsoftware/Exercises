// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * This file stores all the code necessary to run your robot.
 * To check your work, press ctrl+shift+p, type and click on "open terminal",
 * type "./gradlew test" in said terminal, and press enter.
 */
public class Robot extends TimedRobot {
  /**
   * This is called a constructor. Code within here is run once when the robot code
   * first starts.
   */
  public Robot() {
    System.out.println("hello world");
  }

  /**
   * This method is called once every 0.02 seconds while the robot code is running.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This method is called once every 0.02 seconds while the robot code is active
   * in autonomous mode.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This method is called once every 0.02 seconds while the robot code is active
   * in teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
  }
}
