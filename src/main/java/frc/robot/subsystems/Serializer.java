package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Serializer extends SubsystemBase{
    private CANSparkMax lSerializer;
    private CANSparkMax rSerializer;

    public Serializer(){
        lSerializer = new CANSparkMax(IntakeConstants.kLSerializerPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rSerializer = new CANSparkMax(IntakeConstants.kRSerializerPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        lSerializer.setSmartCurrentLimit(35);

        rSerializer.setInverted(false);
    }

    private void setSerializerPower(double power) {
        lSerializer.set(power);
        rSerializer.set(power);
    }

    public void serializeBalls() {
        setSerializerPower(-IntakeConstants.kSerializerPower);
    }

    public void unCerealBalls() {
        setSerializerPower(IntakeConstants.kSerializerPower);
    }
    public void disable() {
        setSerializerPower(0);
    }
}
