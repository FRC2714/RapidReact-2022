package frc.robot.commands.Intake;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Tower;

public class AutoIntake {
    private Intake intake;
    private Tower tower;
    private Serializer serializer;

    public AutoIntake(Intake intake, Tower tower, Serializer serializer){
        this.intake = intake;
        this.tower = tower;
        this.serializer = serializer; 
    }

    public void execute(){
        intake.intakeBalls();
        serializer.serializeBalls();
        tower.breakBeamTower();
    }
    
    public void end(){
        intake.disable();
        serializer.disable();
        tower.disable();
    }

}
