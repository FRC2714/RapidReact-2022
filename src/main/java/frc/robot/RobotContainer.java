// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.auto.Normalauto;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain();
  private static Joystick driverStick = new Joystick(0);
  private static Joystick operatorStick = new Joystick(1);

  private JoystickButton driverAButton = new JoystickButton(driverStick, 1);
  private JoystickButton driverBButton = new JoystickButton(driverStick, 2);
  private JoystickButton driverLeftShoulder = new JoystickButton(driverStick, 5);

  private JoystickButton operatorAButton = new JoystickButton(operatorStick, 1);
  private JoystickButton operatorBButton = new JoystickButton(operatorStick, 2);
  private JoystickButton operatorLeftShoulder = new JoystickButton(operatorStick, 5);
  private JoystickButton operatorRightShoulder = new JoystickButton(operatorStick, 6);
  private JoystickButton operatorYButton = new JoystickButton(operatorStick, 4);
  private JoystickButton operatorXButton = new JoystickButton(operatorStick, 3);
  private JoystickButton operatorUnrestrictedShooting = new JoystickButton(operatorStick, 8);
  private JoystickButton operatorUnjamButton = new JoystickButton(operatorStick, 7);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    initDefaultCommands();
  }

  public void initDefaultCommands() {
    drivetrain.initDefaultCommands(driverStick);
}

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    operatorAButton.whileHeld(new AutoIntake(shooter,intake, conveyor, AutoIntake.IntakeType.INTAKE));
    operatorYButton.whileHeld(new AutoIntake(shooter,intake, conveyor, AutoIntake.IntakeType.EXTAKE));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getNormalauto() {
    return new Normalauto(drivetrain);
}

public Command getNothingAuto(){
  return new InstantCommand(() -> drivetrain.tankDriveVolts(0,0));
}
}
