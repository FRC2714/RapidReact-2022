 package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.Index.Shot.IndexType;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase{

    private Intake intake; 
    private IntakeType intakeType;

    
    public IntakeCommand(Intake intake, IntakeType intakeType){
        this.intake = intake;
        this.intakeType = intakeType;
   
    }


    @Override 

    public void initialize (){
        switch(intakeType){
            case EXTAKE:
            intake.extakeBalls(); 
            Index.setIndexState(Index.IndexState.EXTAKING); 
            break;
            
            case INTAKE:
            intake.intakeBalls();            
            Index.setIndexState(Index.IndexState.INTAKING);
            break;
        }
    }

    


    public enum IntakeType{
        EXTAKE,
        INTAKE
    }

}