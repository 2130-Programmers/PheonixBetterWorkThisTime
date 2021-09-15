// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class ElevatorLift extends SubsystemBase {

  // Defining Victors, which are actually ESCs. Sending motor speed to them on a
  // scale of -1 to 1.
  public static WPI_TalonSRX elevatorControllerMaster;
  public static WPI_TalonSRX elevatorControllerSlave;
  public DigitalInput lowProxRaw;
  public DigitalInput midProxRaw;
  public DigitalInput highProxRaw;

  private Solenoid elevatorBrakeSolenoid;

  /** Creates a new ElevatorLift. */
  public ElevatorLift() {
    // Creating motors, proxes, and solenoids. Value input is the port that the ESC
    // resides on.
    lowProxRaw = new DigitalInput(0);
    midProxRaw = new DigitalInput(1);
    highProxRaw = new DigitalInput(2);
    elevatorControllerMaster = new WPI_TalonSRX(8);
    elevatorControllerSlave = new WPI_TalonSRX(9);

    elevatorBrakeSolenoid = new Solenoid(4);
  }





  // Section Controlling elevator height
  // Joystick input is inverted raw, so up = down, and vice versa.

  //Modifier for down power of elevator.

  public double downThrottlePower() {
    if (RobotContainer.driverJoystick.getRawAxis(5) > 0) {
      return RobotContainer.driverJoystick.getRawAxis(5)*-.2;
    } else {
      return 0;
    }
  }

  //Method to control state of brake on elevator.
  public void setBrake(String state) {
    boolean setting;
    if(state == "on"){
      setting = false;
    }else{
      setting = true;
    }
    elevatorBrakeSolenoid.set(setting);
  }

  //Encoder methods
  public double elevatorEncoderValue() {
    return -elevatorControllerSlave.getSelectedSensorPosition(0);
  }

  public void resetElevatorEncoder() {
    elevatorControllerSlave.setSelectedSensorPosition(0);
  }

  // This code controlls the elevator

  public void elevatorControl() {

    double motorPower;
    //MAIN STATEMENT TO CONTROLL ELEVATOR FUNCTION
    //Loops through this elseif statement. It checks to see if the elevator meets any conditions
    //that should change the normal operation, and if not, defaults to normal operation.

    //Deadzone for controller, disables motors & locks brakes.
    if(java.lang.Math.abs(RobotContainer.driverJoystick.getRawAxis(5)) < .25) {
      motorPower = 0;
      setBrake("on");

    //Checks to see if the elevator is in bottom position & the joystick is in a downward position
    //if so, engages brake and disables motors.
    }else if (!lowProxRaw.get() && RobotContainer.driverJoystick.getRawAxis(5) > .25) {
      motorPower = 0;
      setBrake("on");
    
    //This statement checks to see if elevator is at top & joystick is in upwards position.
    //If so, disables motors and engages brake.
    } else if (!highProxRaw.get() && RobotContainer.driverJoystick.getRawAxis(5) < .25) {
      motorPower = 0;
      setBrake("on");

    //If the elevator is above 19000 on the encoder, slows down elevator.
    }else if (elevatorEncoderValue()>=19000 && RobotContainer.driverJoystick.getRawAxis(5)<-.25) {
      motorPower=RobotContainer.driverJoystick.getRawAxis(5)*-.4;
      setBrake("off");

    //If the encoder value is below 3000, slows down elevator.
    } else if (elevatorEncoderValue()<=1000 && RobotContainer.driverJoystick.getRawAxis(5)>.25) {
      motorPower = 0;
      setBrake("off");

    //If the robot is not at top or bottom, and the joystick is in a downwards position,
    //engages a modifier method.
    } else if (RobotContainer.driverJoystick.getRawAxis(5) > .25) {
      motorPower = downThrottlePower();
      setBrake("off");

    //Dead zone, if joystick is within .1 of 0 in either direction, engages brake & disables motors.
    } else {
      motorPower = RobotContainer.driverJoystick.getRawAxis(5)*-1;
      setBrake("off");
    }

    elevatorControllerMaster.set(motorPower*-1);
  }

  // Methods to send values to driverstation. Just inverting the raw input from
  // proxes and converting to bool

  public void setSlave() {
    elevatorControllerSlave.follow(elevatorControllerMaster);
  }

  public boolean lowProxDetect() {
    return !lowProxRaw.get();
  }

  public boolean highProxDetect() {
    return !highProxRaw.get();
  }


  // Unused
  public void stopElevator() {
    elevatorControllerMaster.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}