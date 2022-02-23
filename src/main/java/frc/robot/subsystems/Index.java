package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.*;
import frc.robot.utils.ToggledBreakBeam;

public class Index {
    public static Object setIndexState;
    public CANSparkMax innerTower;
    public CANSparkMax outerTower;
    
    private ToggledBreakBeam indexBeam;

    public boolean ballStored = false; 


    public enum IndexState {
        SHOOTING,
        INTAKING,
        EXTAKING,
        DEFAULT, 
        LOADING
    }

    public IndexState indexState = IndexState.DEFAULT;

    public Index(Boolean shooterAtVelocity) {
        innerTower = new CANSparkMax(IndexConstants.kiTowerPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        outerTower = new CANSparkMax(IndexConstants.koTowerPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        innerTower.setSmartCurrentLimit(30);
        outerTower.setSmartCurrentLimit(30);

        innerTower.setInverted(true);
        outerTower.setInverted(false);

        innerTower.setIdleMode(CANSparkMax.IdleMode.kBrake);
        outerTower.setIdleMode(CANSparkMax.IdleMode.kBrake);

        ballStored = false;

        indexBeam = new ToggledBreakBeam(new DigitalInput(4));
    }

    public Index() {
    }

    public static void setIndexState(IndexState indexState) {
    }


    public void updateIndexMotion(){
        double innerTowerPower = 0;
        double outerTowerPower = 0;
        
        if(indexState.equals(IndexState.SHOOTING)){
            innerTowerPower = 1;
            outerTowerPower = 1;
        }

        if(indexState.equals(IndexState.EXTAKING)){
            innerTowerPower = -1;
            outerTowerPower = -1;
        }
        if(indexState.equals(IndexState.LOADING)){
            innerTowerPower = 1;
            outerTowerPower = 1;
            if(ballStored)
            {
                
            }
        }

        if(indexState.equals(IndexState.DEFAULT)){
            innerTowerPower = outerTowerPower = 0;
        }
      
        innerTower.set(innerTowerPower); 
        outerTower.set(outerTowerPower);
    }

    public IndexState getIndexState() {
        return indexState;
    }

    public void disable() {
        indexState = IndexState.DEFAULT;
    }

    public void isStored(){
        indexBeam.update();
        if(indexBeam.getToggled()) ballStored = true; 
    }

}
