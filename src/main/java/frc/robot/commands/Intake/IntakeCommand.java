 package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Tower;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;

public class IntakeCommand extends CommandBase{

    private Intake intake; 
    private Serializer serializer;
    private Tower tower;
    private IntakeType intakeType;


    
    public IntakeCommand(Intake intake, IntakeType intakeType, Serializer serializer, Tower tower){
        this.intake = intake;
        this.intakeType = intakeType;
        this.serializer = serializer;
        this.tower = tower;
    }


    @Override 
    public void execute (){
        switch(intakeType){
            case EXTAKE:
            intake.extakeBalls();
            serializer.unCerealBalls();
            tower.setBothTowerPower(-1);
            break;

            case INTAKE:
            intake.intakeBalls();  
            serializer.serializeBalls();          
            tower.breakBeamTower();
            break;

            case DISABLE: 
            intake.disable();
            serializer.disable();
            tower.disable();
            break; 
        }

        //intake.intakeBalls();
    }


    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        tower.disable();
        intake.disable();
        serializer.disable();
    }

    


    public enum IntakeType{
        EXTAKE,
        INTAKE,
        DISABLE
    }

}