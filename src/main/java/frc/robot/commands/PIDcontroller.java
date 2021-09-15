// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.PIDelevator;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PIDcontroller extends PIDCommand {
  /** Creates a new PIDcontroller. */

  private PIDelevator pidelevator;

  public PIDcontroller(PIDelevator pidelevator) {
    super(
        // The controller that the command will use
        new PIDController(.5, 1.5, 1),
        // This should return the measurement
        () -> RobotContainer.elevatorLift.elevatorEncoderValue(),
        // This should return the setpoint (can also be a constant)
        () -> RobotContainer.pidelevator.userSetpoint(),
        // This uses the output
        output -> {
          // Use the output here
          if(RobotContainer.pidelevator.run == true) {
          RobotContainer.elevatorLift.elevatorControllerMaster.set(output*-1);
          }
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    this.pidelevator = pidelevator;
    addRequirements(this.pidelevator);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
