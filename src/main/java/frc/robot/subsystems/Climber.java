package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;

import frc.robot.Constants.ClimberConstants;


public class Climber {
    private CANSparkMax lClimberMotor;
    private CANSparkMax rClimberMotor;
    
    private SparkMaxPIDController climberPID;
    
    private RelativeEncoder climberEncoder;

    private double targetHeightInches = 0.0;

    public Climber(){
        lClimberMotor = new CANSparkMax(ClimberConstants.kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rClimberMotor = new CANSparkMax(ClimberConstants.kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        climberEncoder = lClimberMotor.getEncoder();

        lClimberMotor.setSmartCurrentLimit(30);

        rClimberMotor.follow(lClimberMotor, true);

        climberPID = lClimberMotor.getPIDController();
        climberPID.setP(ClimberConstants.kP);

        lClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        climberEncoder.setPosition(0);
    }

    public void setPower(double power){
        lClimberMotor.set(power);
    }

    public void setClimberDown(){
       if(climberEncoder.getPosition() > ClimberConstants.kMinHeightTicks){
            setPower(-0.75);
        } else setPower(0);
    }

    public void setClimberUp(){
        if(climberEncoder.getPosition() <= ClimberConstants.kMaxHeightTicks){
            setPower(1);
        } else setPower(0);
    }

    public void setPIDTargetHeight(double targetHeightInches) {
        this.targetHeightInches = targetHeightInches;
        climberPID.setReference(targetHeightInches / (2 * Math.PI * ClimberConstants.kSprocketRadius), ControlType.kPosition);
    }

    public boolean atSetpoint() {
        return Math.abs(targetHeightInches - (climberEncoder.getPosition() * 2 * Math.PI * ClimberConstants.kSprocketRadius))
                    < ClimberConstants.kToleranceInches;
    }

    public double getClimberHeightInches(){
        return Math.abs(targetHeightInches - (climberEncoder.getPosition() * 2 * Math.PI * ClimberConstants.kSprocketRadius));
    }

    public void setToTargetTicks(double targetTicks){
        if(Math.abs(climberEncoder.getPosition() - targetTicks) < 200){
            lClimberMotor.set(0);
        } else if(climberEncoder.getPosition() < targetTicks)
            lClimberMotor.set(0.6);
        else
            lClimberMotor.set(-0.6);
    }

    public void setToTargetInches(double targetHeightInches){
        this.targetHeightInches = targetHeightInches;
        if(atSetpoint())
            lClimberMotor.set(0);
        else if(getClimberHeightInches() < targetHeightInches)
            lClimberMotor.set(0.6);
        else
            lClimberMotor.set(-0.6);
    }

    public void disable() {
        lClimberMotor.set(0);
    }



    public double getPosition() {
        return climberEncoder.getPosition();
    }
}
