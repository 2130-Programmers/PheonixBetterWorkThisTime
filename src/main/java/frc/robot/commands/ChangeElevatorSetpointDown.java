// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.PIDelevator;

public class ChangeElevatorSetpointDown extends CommandBase {
  /** Creates a new ChangeElevatorSetpointDown. */
  public PIDelevator PIDelevator;
  public ChangeElevatorSetpointDown(PIDelevator PIDelevator) {
    this.PIDelevator = PIDelevator;
    addRequirements(this.PIDelevator);
  }

  boolean done;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {done = false;}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(RobotContainer.pidelevator.setpointID > 0) {
    RobotContainer.pidelevator.setpointID--;
    }
    done = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
