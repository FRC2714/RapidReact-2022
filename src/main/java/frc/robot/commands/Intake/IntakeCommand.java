package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Index.IndexState;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase{

    private Intake intake; 
    private Index index;
    private IntakeType intakeType;
    
    public IntakeCommand(Index index, Intake intake, IntakeType intakeType){
        this.intakeType = intakeType;
        this.index = index;
        this.intake = intake;
    }


    @Override 

    public void initialize (){
        switch(intakeType){
            case EXTAKE:
            intake.extakeBalls(); 
            index.setIndexState(IndexState.EXTAKING);
            break;
            
            case INTAKE:
            intake.intakeBalls();            
            index.setIndexState(IndexState.INTAKING);
            break;
        }

    }

    


    public enum IntakeType{
        EXTAKE,
        INTAKE

    }

}