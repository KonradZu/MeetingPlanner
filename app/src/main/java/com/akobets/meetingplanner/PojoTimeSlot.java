package com.akobets.meetingplanner;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by akobets on 18.08.2015.
 */
public class PojoTimeSlot {

    public static final int MINUTE00 = 0;
    public static final int MINUTE15 = 2;
    public static final int MINUTE30 = 4;
    public static final int MINUTE45 = 6;

    public static final int MEETING_CONTACT = 1;
    public static final int MEETING_DURATION = 3;
    public static final int MEETING_RESERVED1 = 5;
    public static final int MEETING_RESERVED2 = 7;

    private ArrayList<String> timeSlotData;
    private ArrayList<Integer> timeSlotColor;

    {
        timeSlotData = new ArrayList<>();
        Collections.addAll(timeSlotData,
                " ", " ", " ", " ", " ", " ", " ", " ");
        timeSlotColor = new ArrayList<>();
        Collections.addAll(timeSlotColor,
                ActivityDateInfo.FREE_COLOR_LIGHT,
                ActivityDateInfo.FREE_COLOR_LIGHT,
                ActivityDateInfo.FREE_COLOR_LIGHT,
                ActivityDateInfo.FREE_COLOR_LIGHT);
    }


    public ArrayList<String> getDataArray() {
        return timeSlotData;
    }

    public void setDataArray(ArrayList<String> timeSlotData) {
        this.timeSlotData = timeSlotData;
    }

    public String getDataField(int index) {
        return timeSlotData.get(index);
    }

    public void setDataField(String value, int index) {
        timeSlotData.set(index, value);
    }

    public ArrayList<Integer> getColorArray() {
        return timeSlotColor;
    }

    public void setColorArray(ArrayList<Integer> timeSlotColor) {
        this.timeSlotColor = timeSlotColor;
    }

    public Integer getColor(int index) {
        return timeSlotColor.get(index);
    }

    public void setColor(int color, int index, int stripQuantity) {

        for (int i = 0; i < stripQuantity; i++) {
            timeSlotColor.set(index + i, color);
        }
    }

}
