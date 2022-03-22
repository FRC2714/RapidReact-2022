package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.*;
import frc.robot.utils.ToggledBreakBeam;

public class Tower {
    public CANSparkMax innerTower;
    public CANSparkMax outerTower;
    
    private ToggledBreakBeam indexBeam;

    public boolean ballStored = false; 




    public Tower() {
        innerTower = new CANSparkMax(IndexConstants.kiTowerPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        outerTower = new CANSparkMax(IndexConstants.koTowerPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        innerTower.setSmartCurrentLimit(30);
        outerTower.setSmartCurrentLimit(30);

        innerTower.setInverted(false);
        outerTower.setInverted(true);

        innerTower.setIdleMode(CANSparkMax.IdleMode.kBrake);
        outerTower.setIdleMode(CANSparkMax.IdleMode.kBrake);

        ballStored = false;

        indexBeam = new ToggledBreakBeam(new DigitalInput(4));
    }

    public void setBothTowerPower(double input){
        innerTower.set(input);
        outerTower.set(input);
    }

    public void breakBeamTower(){
        if(isStored()){
            setBothTowerPower(.25);
        }else{
            setBothTowerPower(0);
        }
    }

    public void disable() {
        setBothTowerPower(0);
    }

    public boolean isStored(){
        indexBeam.update();
        if(indexBeam.getState()){
            return true;
        }
        else{
            return false;
        }
    }

}
