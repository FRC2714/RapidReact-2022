// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ShooterConstants;
import frc.robot.utils.InterpolatingTreeMap;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Shooter extends SubsystemBase {
  public CANSparkMax shooterMotor1;
  public CANSparkMax shooterMotor2;

  private RelativeEncoder shooterEncoder;
  private SparkMaxPIDController shooterPID;

  private InterpolatingTreeMap shooterVelocity = new InterpolatingTreeMap();

  private Limelight limelight;

  private double defaultRPM = 0.0;
  private double targetRPM = 0.0;
  private double incrementRPM = 0.0;

  private double closeShotRPM = 3100;
  private double midShotRPM = 3200;
  private double longShotRPM = 3800;

  public Shooter(Limelight limelight) {
    this.limelight = limelight;

    shooterMotor1 = new CANSparkMax(ShooterConstants.kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    shooterMotor2 = new CANSparkMax(ShooterConstants.kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

    shooterMotor1.setSmartCurrentLimit(60);
    shooterMotor2.setSmartCurrentLimit(60);

    shooterEncoder = shooterMotor1.getEncoder();

    shooterMotor2.follow(shooterMotor1, true);

    shooterPID = shooterMotor1.getPIDController();
    shooterPID.setFF(ShooterConstants.kSparkMaxFeedforward);
    shooterPID.setP(ShooterConstants.kSparkMaxP);
    shooterPID.setI(ShooterConstants.KSparkMaxI);
    shooterPID.setD(ShooterConstants.KSparkMaxD);

    // populateVelocityMap();
  }

  // public void populateVelocityMap() {
  //   shooterVelocity.put(6.8, 2200.0);
  //   shooterVelocity.put(11.1, 2050.0);
  //   shooterVelocity.put(14.3, 2100.0);
  //   shooterVelocity.put(22.0, 2350.0);
  //   shooterVelocity.put(26.75, 2650.0);
  //   shooterVelocity.put(36.0, 3500.0);

  // }

  public void setShooterPower(double power) {
    shooterMotor1.set(-power);
  }

  public void setTargetRpm(double targetRPM) {
    this.targetRPM = targetRPM;
    shooterPID.setReference(-targetRPM, CANSparkMax.ControlType.kVelocity);
  }

  public double getVelocity() { // in rpm
    return shooterEncoder.getVelocity();
  }

  public double getTargetRpm() {
    return limelight.targetVisible()
        ? shooterVelocity.getInterpolated(Units.metersToFeet(limelight.getDistanceToGoal())) + incrementRPM
        : defaultRPM;
  }

  public void setDynamicRpm() {
    shooterPID.setReference(getTargetRpm(), ControlType.kVelocity);
    setTargetRpm(getTargetRpm());
  }

  public boolean atSetpoint() {
    return Math.abs(-targetRPM - getVelocity()) < ShooterConstants.kVelocityTolerance;
  }

  public void closeShot() {
    setTargetRpm(closeShotRPM);
  }

  public void midShot() {
    setTargetRpm(midShotRPM);
  }

  public void longShot() {
    setTargetRpm(longShotRPM);

  }

  public void disable() {
    shooterPID.setReference(0, CANSparkMax.ControlType.kVelocity);
    shooterMotor1.set(0);
  }
  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter RPM", getVelocity());
  }
}