// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  //Subsystems
  public final static DriveTrainSub driveTrainSub = new DriveTrainSub();
  public final static ShiftingSubsystem shiftingSubsystem = new ShiftingSubsystem();
  public final static IntakeSub intakeSub = new IntakeSub();
  public final static ClawSub clawSub = new ClawSub();
  public final static HandlerSolSub handlerSolSub = new HandlerSolSub();
  public final static ElevatorLift elevatorLift = new ElevatorLift();

  //Commands
  public final static ExtendoCommand extendoCommand = new ExtendoCommand(intakeSub);
  public final static RollersLower rollerLower = new RollersLower(clawSub);
  public final static RollersRaise rollerRaise = new RollersRaise(clawSub);
  public final static IntakeCommand intakeCommand = new IntakeCommand(intakeSub);
  public final static ShiftToHigh shiftToHigh = new ShiftToHigh(shiftingSubsystem);
  public final static ShiftToLow shiftToLow = new ShiftToLow(shiftingSubsystem);
  public final static ShiftingCommand shiftingCommand = new ShiftingCommand(shiftingSubsystem);
  public final static DriveDifferentially driveDifferentially = new DriveDifferentially(driveTrainSub);
  public final static PlaceCommand placeCommand = new PlaceCommand(handlerSolSub);
  public final static SuckIn suckIn = new SuckIn(intakeSub);
  public final static SafelyDrop safelyDrop = new SafelyDrop(clawSub, intakeSub);
    
  //PIDs
  //Subsystem
  public final static PIDelevator pidelevator = new PIDelevator();
  //Command
  public final static PIDcontroller pidcontroller = new PIDcontroller(pidelevator);
  public final static ChangeElevatorSetpointDown changeElevatorSetpointDown = new ChangeElevatorSetpointDown(pidelevator);
  public final static ChangeElevatorSetpointUp changeElevatorSetpointUp = new ChangeElevatorSetpointUp(pidelevator);
  public final static ToggleElevatorControllMode toggleElevatorControllMode = new ToggleElevatorControllMode(pidelevator);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
 
    public final static Joystick driverJoystick = new Joystick(0);
    public final static JoystickButton shiftButton = new JoystickButton(driverJoystick, Constants.driverButtonStart);
    public final static JoystickButton changeSetpointUp = new JoystickButton(driverJoystick, Constants.driverButtonRB);
    public final static JoystickButton changeSetpointDown = new JoystickButton(driverJoystick, Constants.driverButtonLB);
    public final static JoystickButton toggleControllMode = new JoystickButton(driverJoystick, Constants.driverButtonBack);
    public final static JoystickButton handlerButton = new JoystickButton(driverJoystick, Constants.driverButtonX);
    public final static JoystickButton lowerClawBut = new JoystickButton(driverJoystick, Constants.driverButtonA);
    public final static JoystickButton raiseClawBut = new JoystickButton(driverJoystick, Constants.driverButtonB);
    public final static JoystickButton placerButton = new JoystickButton(driverJoystick, Constants.driverButtonRightJoyClick);
    public final static JoystickButton extendButton = new JoystickButton(driverJoystick, Constants.driverButtonX);
    public final static JoystickButton bustDown = new JoystickButton(driverJoystick, Constants.driverButtonY);


  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    driveTrainSub.setDefaultCommand(driveDifferentially);
    intakeSub.setDefaultCommand(intakeCommand);
    pidelevator.setDefaultCommand(pidcontroller);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    shiftButton.whenPressed(shiftingCommand);
    raiseClawBut.whenPressed(rollerRaise);
    lowerClawBut.whenPressed(rollerLower);
    placerButton.whenPressed(placeCommand);
    extendButton.whenPressed(extendoCommand);
    bustDown.whenPressed(safelyDrop);
    changeSetpointUp.whenPressed(changeElevatorSetpointUp);
    changeSetpointDown.whenPressed(changeElevatorSetpointDown);
    toggleControllMode.whenPressed(toggleElevatorControllMode);
    
  }

  //eat my shorts Brodiey

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }

  public static Boolean shiftingValue() {
    return shiftButton.get();
  }

  public static Boolean handlerPositionValue(){
    return handlerButton.get();
  }

  public static boolean placerValue(){
    return placerButton.get();
  }
}
