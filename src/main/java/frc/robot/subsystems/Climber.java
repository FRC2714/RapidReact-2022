package frc.robot.subsystems;

import com.revrobotics.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ClimberConstants;


public class Climber {
    private CANSparkMax lClimberMotor;
    private CANSparkMax rClimberMotor;

    private CANSparkMax lHighMotor;
    private CANSparkMax rHighMotor;
    
    private RelativeEncoder climberEncoder;
    private RelativeEncoder highEncoder; 
    
    private int maxHeight = 0;
    private int kMinHeight = 0;

    public Climber(){
        lClimberMotor = new CANSparkMax(ClimberConstants.kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rClimberMotor = new CANSparkMax(ClimberConstants.kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        lHighMotor = new CANSparkMax(ClimberConstants.kLHighMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rHighMotor = new CANSparkMax(ClimberConstants.kRHighMotorPort,CANSparkMaxLowLevel.MotorType.kBrushless);
        climberEncoder = lClimberMotor.getEncoder();
        highEncoder = lHighMotor.getEncoder();

        lClimberMotor.setSmartCurrentLimit(30);
        lHighMotor.setSmartCurrentLimit(30);

    
        rClimberMotor.follow(lClimberMotor, true);
        rHighMotor.follow(lHighMotor, true);

        lClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        lHighMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rHighMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        climberEncoder.setPosition(0);
        highEncoder.setPosition(0);

    }

    public void climberUp(){
        lClimberMotor.set(-0.75);
    }

    public void climberDown(){
        lClimberMotor.set(0.75);
    }

    public void climbDisable() {
        lClimberMotor.set(0);
    }

    public void highUp(){
        lHighMotor.set(0.5);
    }

    public void highDown(){
        lHighMotor.set(-0.5);
    }

    public void highDisable(){
        lHighMotor.set(0);
    }

    public double getPosition() {
        return climberEncoder.getPosition();
    }

    public void setClimberPower(double power) {
        lClimberMotor.set(power);
    }

    public void disable(){
        lClimberMotor.set(0);
        lHighMotor.set(0);
    }

    public void periodic(){
        SmartDashboard.putNumber("Climber Encoder", climberEncoder.getPosition());
    }

}
