package com.alphatica.genotick.genotick;

import com.alphatica.genotick.data.MainAppData;
import com.alphatica.genotick.timepoint.TimePoint;
import com.alphatica.genotick.population.PopulationSettings;
import com.alphatica.genotick.breeder.InheritedWeightMode;
import com.alphatica.genotick.chart.GenoChartMode;

import java.lang.reflect.Field;

public class MainSettings {

    public TimePoint startTimePoint = new TimePoint(0);
    public TimePoint endTimePoint = new TimePoint(Long.MAX_VALUE);
    public int populationDesiredSize = PopulationSettings.DEFAULT_DESIRED_SIZE;
    public String populationDAO = PopulationSettings.DEFAULT_DATA_ACCESS;
    public boolean performTraining = true;
    public String dataDirectory = Main.DEFAULT_DATA_DIR;
    public int minimumRobotInstructions = 16;
    public int maximumRobotInstructions = 1024;
    public int maximumProcessorInstructionFactor = 256;
    public double maximumDeathByAge = 0.01;
    public double maximumDeathByWeight = 0.1;
    public double probabilityOfDeathByAge = 0.5;
    public double probabilityOfDeathByWeight = 0.5;
    public WeightMode weightMode = WeightMode.WIN_COUNT;
    public double weightExponent = 2.0;
    public double inheritedChildWeight = 0;
    public InheritedWeightMode inheritedChildWeightMode = InheritedWeightMode.ANCESTORS_LOG;
    public int maximumDataOffset = 256;
    public int protectRobotsUntilOutcomes = 100;
    public double newInstructionProbability = 0.01;
    public double instructionMutationProbability = 0.01;
    public double skipInstructionProbability = 0.01;
    public int minimumOutcomesToAllowBreeding = 50;
    public int minimumOutcomesBetweenBreeding = 50;
    public boolean killNonPredictingRobots = true;
    public double randomRobotsAtEachUpdate = 0.02;
    public double protectBestRobots = 0.02;
    public boolean requireSymmetricalRobots = true;
    public double resultThreshold = 1;
    public int ignoreColumns = 0;
    public long randomSeed = 0;
    public GenoChartMode chartMode = GenoChartMode.NONE;

    private MainSettings() {
    }

    public static MainSettings getSettings() {
        return new MainSettings();
    }

    public String getString() throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Field [] fields = this.getClass().getDeclaredFields();
        for(Field field: fields) {
            sb.append(field.getName()).append(" ").append(field.get(this)).append("\n");
        }
        return sb.toString();
    }

    @SuppressWarnings("WeakerAccess")
    public void validateTimePoints(MainAppData data) {
        TimePoint first = data.getFirstTimePoint();
        TimePoint last = data.getLastTimePoint();
        if(startTimePoint.compareTo(first) < 0) {
            startTimePoint = first;
        }
        if(endTimePoint.compareTo(last) > 0) {
            endTimePoint = last;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void validate() {
        ensure(startTimePoint.compareTo(endTimePoint) <= 0,
                "End Time Point must be higher or equal Start Time Point");
        ensure(populationDesiredSize > 0, greaterThanZeroString("Population desired size"));
        ensure(maximumDataOffset > 0, greaterThanZeroString("Maximum Data Offset"));
        ensure(minimumRobotInstructions > 0, greaterThanZeroString("Minimum robot instructions"));
        ensure(maximumRobotInstructions > minimumRobotInstructions, greaterThanIntegerString("Maximum robot instructions", minimumRobotInstructions));
        ensure(maximumProcessorInstructionFactor > 0, greaterThanZeroString("Maximum processor instruction factor"));
        ensure(checkZeroToOne(maximumDeathByAge), zeroToOneString("Maximum Death by Age"));
        ensure(checkZeroToOne(maximumDeathByWeight), zeroToOneString("Maximum Death by Weight"));
        ensure(checkZeroToOne(probabilityOfDeathByAge), zeroToOneString("Probability Death by Age"));
        ensure(checkZeroToOne(inheritedChildWeight), zeroToOneString("Inherited Child's Weight"));
        ensure(protectRobotsUntilOutcomes >= 0, atLeastZeroString("Protect Robots until Outcomes"));
        ensure(checkZeroToOne(newInstructionProbability), zeroToOneString("New Instruction Probability"));
        ensure(checkZeroToOne(instructionMutationProbability), zeroToOneString("Instruction Mutation Probability"));
        ensure(checkZeroToOne(skipInstructionProbability), zeroToOneString("Skip Instruction Probability"));
        ensure(minimumOutcomesToAllowBreeding >= 0, atLeastZeroString("Minimum outcomes to allow breeding"));
        ensure(minimumOutcomesBetweenBreeding >= 0, atLeastZeroString("Minimum outcomes between breeding"));
        ensure(randomRobotsAtEachUpdate >=0, zeroToOneString("Random Robots at Each Update"));
        ensure(protectBestRobots >= 0, zeroToOneString("Protect Best Robots"));
        ensure(resultThreshold >= 1,atLeastOneString("Result threshold"));
        ensure(ignoreColumns >= 0, atLeastZeroString("Ignore columns"));
    }

    private String atLeastZeroString(String s) {
        return s + " must be at least 0";
    }

    private String zeroToOneString(String s) {
        return s + " must be between 0.0 and 1.0";
    }

    private String greaterThanZeroString(String s) {
        return s + " must be greater than 0";
    }

    private String greaterThanIntegerString(String s, int value) {
        return s + " must be greater than " + value;
    }

    private String atLeastOneString(String s) {
        return s + " must be at least 1";
    }

    private boolean checkZeroToOne(double value) {
        return value >= 0 && value <= 1;
    }

    private void ensure(boolean condition, String message) {
        if(!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
