package org.omscs.ml.a4burlap.experiments;

import org.omscs.ml.a4burlap.mdp.MDPBlockDude;
import org.omscs.ml.a4burlap.mdp.MDPGridWorld;
import org.omscs.ml.a4burlap.mdp.ProblemSize;
import org.omscs.ml.a4burlap.qlearn.QSettings;
import org.omscs.ml.a4burlap.utils.CSVWriterGeneric;
import org.omscs.ml.a4burlap.vipi.PISettings;
import org.omscs.ml.a4burlap.vipi.VISettings;

import static org.omscs.ml.a4burlap.experiments.RunnerVIPI.NAME_BLOCKDUDE;
import static org.omscs.ml.a4burlap.experiments.RunnerVIPI.NAME_GRIDWORLD;
import static org.omscs.ml.a4burlap.utils.Utils.printExerimentStartBlurb;

import java.util.Set;

public class RunExperiments {

    public static void main( String[] args ){

        Set<String> expDirs = Set.of(NAME_BLOCKDUDE, NAME_GRIDWORLD);
        CSVWriterGeneric csvWriter = new CSVWriterGeneric("output", expDirs, "My Small Problem Experiments", "smprob");

        //All MDPs
        MDPBlockDude mdpBlockDudeSM = new MDPBlockDude(ProblemSize.SMALL);
//        MDPBlockDude mdpBlockDudeLRG = new MDPBlockDude(ProblemSize.LARGE);
        MDPGridWorld mdpGridWorldSM = new MDPGridWorld(0.8, ProblemSize.SMALL);
//        MDPGridWorld mdpGridWorldLRG = new MDPGridWorld(ProblemSize.LARGE);


        printExerimentStartBlurb("VI - Small BlockDude");
       //Value Iteration with small BlockDude
        VISettings viSettings01 = new VISettings(0.99f, 0.001f, 1000, "vi_sm_bd_01" );
        csvWriter.appendToExperimentCatalog(viSettings01);
        BlockDudeVIExperiment viBlockDudeExperiment = new BlockDudeVIExperiment(mdpBlockDudeSM, viSettings01, csvWriter);
        viBlockDudeExperiment.runAndSave(false);
        mdpBlockDudeSM.reset();

        // Policy Iterateion with Small Blockdude
        printExerimentStartBlurb("PI - Small BlockDude");
        PISettings piSettings01 = new PISettings(0.99f, 0.001f,
                0.001f, 1000,
                100, "pi_sm_bd_01");
        csvWriter.appendToExperimentCatalog(piSettings01);
        BlockDudePIExperiment piBlockDudeExperiment = new BlockDudePIExperiment(mdpBlockDudeSM,piSettings01, csvWriter);
        piBlockDudeExperiment.runAndSave(false);
        mdpBlockDudeSM.reset();


        //Small BlockDude with Q-Learner
        printExerimentStartBlurb("Q-Learner - Small BlockDude");
        QSettings qSettingsBD01 = new QSettings("q_sm_bd_01", 0.99, 0.3, 0.2, 2000, 0.90, 0.99 );
        csvWriter.appendToExperimentCatalog(qSettingsBD01);
        BlockDudeQLearnerExperiment bdqlExp01 = new BlockDudeQLearnerExperiment(mdpBlockDudeSM,qSettingsBD01, csvWriter);
        bdqlExp01.runWithEpisodesAndSave(3,300);
        mdpBlockDudeSM.reset();


        //Small gridworld with Vi
        printExerimentStartBlurb("VI GridWorld Small");
        VISettings viSettingsGW02 = new VISettings(0.99f, 0.0001f,
                1000,"vi_gw_small");
        csvWriter.appendToExperimentCatalog(viSettingsGW02);
        GridWorldVIExperiment gwSmVi02 = new GridWorldVIExperiment(mdpGridWorldSM,viSettingsGW02, csvWriter);
        gwSmVi02.runAndSaveMultiWithVisual(5,0);
        mdpGridWorldSM.reset();

        //Small gridworld with Q-Learner
        printExerimentStartBlurb("Q-Learner - Small Gridworld");
        QSettings qSettingsGW01 = new QSettings("q_sm_gw_01", 0.99, 0.3, 0.1, 2000, 0.8, 0.0 );
        csvWriter.appendToExperimentCatalog(qSettingsGW01);
        GridWorldQLearnerExperiment gwQExp01 = new GridWorldQLearnerExperiment(mdpGridWorldSM,qSettingsGW01, csvWriter);
        gwQExp01.tooggleVisual(true, 0);
        gwQExp01.runWithEpisodesAndSave(3,2000);
        mdpGridWorldSM.reset();


    }
}
