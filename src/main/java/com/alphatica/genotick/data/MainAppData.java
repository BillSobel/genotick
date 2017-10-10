package com.alphatica.genotick.data;

import com.alphatica.genotick.timepoint.TimePoint;
import com.alphatica.genotick.timepoint.TimePoints;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

// TODO change nulls to optional
public class MainAppData {
    
    private final Map<DataSetName, DataSet> sets;
    private final TimePoints timePoints;

    public MainAppData() {
        sets = new HashMap<>();
        timePoints = new TimePoints(false);
    }

    public void put(DataSet set) {
        this.sets.put(set.getName(), set);
        set.fetchMergedTimePoints(this.timePoints);
    }

    public void put(DataSetName name, DataLines tohlcLines) {
        DataSet set = new DataSet(name, tohlcLines);
        set.fetchMergedTimePoints(timePoints);
        sets.put(name, set);
    }

    public TimePoint getFirstTimePoint() {
        return timePoints.getOldest();
    }

    public TimePoint getLastTimePoint() {
        return timePoints.getNewest();
    }

    public TimePoint getTimePoint(int bar) {
        return isValidBar(bar) ? timePoints.get(bar) : null;
    }

    public int getBar(TimePoint timePoint) {
        return timePoints.getIndex(timePoint);
    }

    public int getNearestBar(TimePoint timePoint) {
        return timePoints.getNearestIndex(timePoint);
    }

    private boolean isValidBar(int bar) {
        return timePoints.isValidIndex(bar);
    }

    public int getTimePointCount() {
        return timePoints.size();
    }
    
    public TimePoints createTimePointsCopy(TimePoint startTime, TimePoint endTime) {
        return timePoints.createSelectionCopy(startTime, endTime);
    }
    
    public Stream<TimePoint> getTimePoints(TimePoint startTime, TimePoint endTime) {
        return timePoints.getSelection(startTime, endTime);
    }
    
    public Set<DataSetName> getDataSetNames() {
        return sets.keySet();
    }

    public Collection<DataSet> getDataSets() {
        return sets.values();
    }

    public DataSet getDataSet(DataSetName name) {
        return sets.get(name);
    }

    public boolean containsDataSet(DataSetName name) {
        return sets.containsKey(name);
    }

    public boolean isEmpty() {
        return sets.isEmpty();
    }
}
