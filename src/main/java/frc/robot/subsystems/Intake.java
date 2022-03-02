package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    public CANSparkMax intakeMotor;

    public Intake() {
        intakeMotor = new CANSparkMax(15, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeMotor.setSmartCurrentLimit(40);
    }

    public void intakeBalls() {
        setIntakePower(-1);
    }
    
    public void extakeBalls() {
        setIntakePower(1);
    }

    public void disable() {
        setIntakePower(0);
    }

    public void setIntakePower(double power){
        intakeMotor.set(power);
    }
}