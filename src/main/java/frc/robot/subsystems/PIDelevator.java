// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.RobotContainer;

public class PIDelevator extends PIDSubsystem {
  /** Creates a new PIDelevator. */

  double setpointValue;
  public int setpointID;
  public boolean run;
  public String controllMode;

  public PIDelevator() {
    super(
        // The PIDController used by the subsystem
        new PIDController(1, 1, 1));
  }

  /**
   * @param controllMode Takes 2 values, either "manual" or "setpoint". Manual mode controlls elevator off of joystick, Setpoint goes to preset positions.
   * @param setpointID The setpoint that should be used 0-2
   * @return Returns the setpoint value for the elevator PID.
   */

  public double userSetpoint() {

  if(controllMode == "manual") {
    RobotContainer.elevatorLift.elevatorControl();
    run = false;
  }else if(controllMode == "setpoint") {
      switch (setpointID) {
        case 0:
        setpointValue = 120;
        break;
        case 1:
        setpointValue = 123000;
        break;
        case 2:
        setpointValue = 254000;
        break;
      }
      run = true;
  }

    return setpointValue;
  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return 0;
  }
}
