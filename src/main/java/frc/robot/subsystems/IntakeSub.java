// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class IntakeSub extends SubsystemBase {
  /** Creates a new IntakeSub. */
  private WPI_TalonSRX leftRollerMotor;
  private WPI_TalonSRX rightRollerMotor;

  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  private Solenoid handlerPositionSolenoid;
  private Solenoid rocketPlacementSolenoid;

  //This is a boolean that is used for the handler position command, so it will only run once at a time.
  public boolean handlerPositionSetting;

  //I'm not entirely sure what this is used for, but it is needed for the defense/play handler methods.
  private int i;
  public IntakeSub() {
    leftRollerMotor = new WPI_TalonSRX(5);
        
        
        
    rightRollerMotor = new WPI_TalonSRX(4);
    
    
    

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    handlerPositionSolenoid = new Solenoid(1);

    handlerPositionSetting = false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public double joystickValue(int stickNumber) {
    return RobotContainer.driverJoystick.getRawAxis(stickNumber);
}

public void runIntake() {
    if (joystickValue(3) > 0 && joystickValue(2) > 0) {
        leftRollerMotor.set(0);
        rightRollerMotor.set(0);
    } else if (joystickValue(3) == 0 && joystickValue(2) == 0) {
        leftRollerMotor.set(0);
        rightRollerMotor.set(0);
    } else if (joystickValue(3) > 0 && joystickValue(2) == 0) {
        leftRollerMotor.set(1);
        rightRollerMotor.set(-1);
    }else if (joystickValue(3) == 0 && joystickValue(2) > 0) {
        leftRollerMotor.set(-1);
        rightRollerMotor.set(1);
    }
}

// These two commands set the rocket placement solenoid. True means it is receiving voltage, false means it is not.
// The solenoids can be changed physically to decide what state "true" means, it can be set to mean either forwards or backwards.

public void resetLimiting() {
    leftRollerMotor.configPeakOutputForward(1,0);
    leftRollerMotor.configPeakOutputReverse(-1,0);
    rightRollerMotor.configPeakOutputForward(1,0);
    rightRollerMotor.configPeakOutputReverse(-1,0);
}

// These two commands set the handler position solenoid. 
public void handlerPositionForward() {
    handlerPositionSolenoid.set(true);
    handlerPositionSetting = true;
}
public void handlerPositionBackward() {
    handlerPositionSolenoid.set(false);
    handlerPositionSetting = false;
}

// This is a command that makes the defense/play handler only move once when the button is pressed, rather than repeatedly.
public void handlerPositioning() {
    if (!handlerPositionSetting) {
        handlerPositionForward();
    }
    else {
        handlerPositionBackward();
    }
}

// This makes sure that our boolean is set to the right setting, and that our bot starts with the handler in play position.
public void startingPosition() {
    handlerPositionSetting = false;
    handlerPositionBackward();
    
}

// Not sure entirely how this works, but it makes sure that our defense/play handler only runs once at a time.
public void setHandlerBoolean() {
    if(i == 0 && RobotContainer.handlerPositionValue()) {
        handlerPositionSetting = !handlerPositionSetting;
        i++;
    } else if (!RobotContainer.handlerPositionValue()){
        i = 0;
    }

}

//Stops the roller motors
  public void stopRollers() {
      leftRollerMotor.set(0);
      rightRollerMotor.set(0);
  }

    public boolean ifJoysticksDepressed() {
        if (RobotContainer.driverJoystick.getRawAxis(3) != 0 || RobotContainer.driverJoystick.getRawAxis(2) != 0) {
            return true;
        } else {
            return false;
        }
    }

}
