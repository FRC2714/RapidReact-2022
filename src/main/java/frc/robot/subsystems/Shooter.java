// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.ShooterConstants;
import frc.robot.utils.InterpolatingTreeMap;



public class Shooter extends SubsystemBase {
  public CANSparkMax sM1;
  public CANSparkMax sM2;

  private RelativeEncoder sENC;
  private SparkMaxPIDController sPID;

  private InterpolatingTreeMap sVeloc = new InterpolatingTreeMap();

  private Limelight limelight;

  private double dRPM = 2e3;
  private double tRPM = 0.0;

  private double iRPM = 0;

  public Shooter(Limelight limelight) {
    this.limelight = limelight;
    iRPM = 0;
    
    sM1 = new CANSparkMax(ShooterConstants.kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    sM2 = new CANSparkMax(ShooterConstants.kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

    sM1.setSmartCurrentLimit(60);
    sM2.setSmartCurrentLimit(60);

    sM1.setIdleMode(CANSparkMax.IdleMode.kCoast);
    sM2.setIdleMode(CANSparkMax.IdleMode.kCoast);

    sENC = sM1.getEncoder();

    sM2.follow(sM1, true);

    sPID = sM1.getPIDController();
    sPID.setFF(ShooterConstants.kSparkMaxFeedforward);
    sPID.setP(ShooterConstants.kSparkMaxP);

    populateVelocityMap();

    SmartDashboard.putNumber("Target RPM", tRPM);
    SmartDashboard.putNumber("Current RPM", 0);
  }

  private void populateVelocityMap() {

    //TODO Find velocities

  }

  public void setRPM(double rRPM){
    sPID.setReference(rRPM, ControlType.kVelocity);
    setTargetRpm(rRPM);
  }

  public void setShooterPower(double power){
    sM1.set(power);
  }

  public void setTargetRpm(double tRPM) {
    this.tRPM = tRPM;
}

public double getVelocity() { // in rpm
  return sENC.getVelocity();
}

public void incrementRPM(){
  iRPM += 50;
}

public void decrementRPM(){
  iRPM -= 50;
}

public void setRpmIncrement(double iRPM){
  this.iRPM = iRPM;
}

public double getTargetRpm() {
  return limelight.targetVisible() ? sVeloc.getInterpolated(Units.metersToFeet(limelight.getDistanceToGoal())) + iRPM: dRPM;
}
public void setDynamicRpm() {
  sPID.setReference(getTargetRpm(), ControlType.kVelocity);
  setTargetRpm(getTargetRpm());
}

public boolean atSetpoint() {
  return Math.abs(tRPM- getVelocity()) < ShooterConstants.kVelocityTolerance;
}

  @Override
  public void periodic() {
  }

  
  public void disable() {
    sM1.set(0);
  }

  
  public void setSetpoint(double rpm) {
    dRPM = rpm;
  }
}
