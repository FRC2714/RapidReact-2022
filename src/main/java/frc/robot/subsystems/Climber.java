package frc.robot.subsystems;

import com.revrobotics.*;

import frc.robot.Constants.ClimberConstants;


public class Climber {
    private CANSparkMax lClimberMotor;
    private CANSparkMax rClimberMotor;
    
    private SparkMaxPIDController climberPID;
    
    private RelativeEncoder climberEncoder;

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

    public void climberUp(){
        lClimberMotor.set(0.1);
    }

    public void climberDown(){
        lClimberMotor.set(-0.1);
    }

    public void disable() {
        lClimberMotor.set(0);
    }




    public double getPosition() {
        return climberEncoder.getPosition();
    }

    public void setClimberPower(double power) {
        lClimberMotor.set(power);
    }
}
