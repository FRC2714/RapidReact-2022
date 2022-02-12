package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;


public class Climber {
    private CANSparkMax cM1;
    private CANSparkMax cM2;
    
    private SparkMaxPIDController cPID;
    
    private RelativeEncoder cENC;

    private double targetHeightInches = 0.0;

    public Climber(){
        cM1 = new CANSparkMax(ClimberConstants.kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        cM2 = new CANSparkMax(ClimberConstants.kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        cENC = cM1.getEncoder();

        cM1.setSmartCurrentLimit(30);

        cM2.follow(cM1, true);

        cPID = cM1.getPIDController();
        cPID.setP(ClimberConstants.kP);

        cM1.setIdleMode(CANSparkMax.IdleMode.kBrake);
        cM2.setIdleMode(CANSparkMax.IdleMode.kBrake);

        cENC.setPosition(0);
    }

    public void setPower(double power){
        cM1.set(power);
    }

    public void setClimberDown(){
       if(cENC.getPosition() > ClimberConstants.kMinHeightTicks){
            setPower(-0.75);
        } else setPower(0);
    }

    public void setClimberUp(){
        if(cENC.getPosition() <= ClimberConstants.kMaxHeightTicks){
            setPower(1);
        } else setPower(0);
    }

    public void setPIDTargetHeight(double targetHeightInches) {
        this.targetHeightInches = targetHeightInches;
        cPID.setReference(targetHeightInches / (2 * Math.PI * ClimberConstants.kSprocketRadius), ControlType.kPosition);
    }

    public boolean atSetpoint() {
        return Math.abs(targetHeightInches - (cENC.getPosition() * 2 * Math.PI * ClimberConstants.kSprocketRadius))
                    < ClimberConstants.kToleranceInches;
    }

    public double getClimberHeightInches(){
        return Math.abs(targetHeightInches - (cENC.getPosition() * 2 * Math.PI * ClimberConstants.kSprocketRadius));
    }

    public void setToTargetTicks(double targetTicks){
        if(Math.abs(cENC.getPosition() - targetTicks) < 200){
            cM1.set(0);
        } else if(cENC.getPosition() < targetTicks)
            cM1.set(0.6);
        else
            cM1.set(-0.6);
    }

    public void setToTargetInches(double targetHeightInches){
        this.targetHeightInches = targetHeightInches;
        if(atSetpoint())
            cM1.set(0);
        else if(getClimberHeightInches() < targetHeightInches)
            cM1.set(0.6);
        else
            cM1.set(-0.6);
    }

    public void disable() {
        cM1.set(0);
    }


    @Override
    public void periodic() {

    }

    public double getPosition() {
        return cENC.getPosition();
    }
}
