package com.akobets.meetingplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by akobets on 19.08.2015.
 */
public class ActivityMeetingAdd extends Activity implements View.OnClickListener {
//        implements View.OnClickListener{

    public static final int NAME = 0;
    public static final int TIME = 1;
    public static final int TYPE = 2;
    public static final int PLACE = 3;
    public static final int MIN_MEETING_DURATION = 2;
    public static final int RESULT_CODE = 1;
    public static final String NEW_MEETING_TRANSFER_DATA = "???????? ? ????? ???????";


    private Spinner sName;
    private Spinner sTimeFrom;
    private Spinner sTimeTo;
    private Spinner sMeetingType;
    private Spinner sMeetingPlace;

    private ArrayList<String> sNameData;
    private ArrayList<String> sTimeData;
    private ArrayList<String> sMeetingTypeData;
    private ArrayList<String> sMeetingPlaceData;

    private int sNamePosition;
    private int sTimeFromPosition;
    private int sTimeToPosition;
    private int sMeetingTypePosition;
    private int sMeetingPlacePosition;

    private ArrayAdapter<String> sNameAdapter;
    private ArrayAdapter<String> sTimeFromAdapter;
    private ArrayAdapter<String> sTimeToAdapter;
    private ArrayAdapter<String> sMeetingTypeAdapter;
    private ArrayAdapter<String> sMeetingPlaceAdapter;

    private Button bCancel;
    private Button bSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_add);

        bCancel = (Button) findViewById(R.id.bCancel);
        bSave = (Button) findViewById(R.id.bSave);

        bCancel.setOnClickListener(this);
        bSave.setOnClickListener(this);

        // ??????? ??? ?????? ?????
        sNameData = sInit(NAME);
        sNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sNameData);
        sNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sName = (Spinner) findViewById(R.id.sName);
        sName.setAdapter(sNameAdapter);

        // ???????? ???????
        sNamePosition = 0;
        sName.setSelection(sNamePosition);

        // ????????????? ?????????? ???????
        sName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ??????????? ????????? ??????? ? ??????? ????
                sNamePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // ??????? ??? ?????? ??? ???????
        sMeetingTypeData = sInit(TYPE);
        sMeetingTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sMeetingTypeData);
        sMeetingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMeetingType = (Spinner) findViewById(R.id.sMeetingType);
        sMeetingType.setAdapter(sMeetingTypeAdapter);

        // ???????? ???????
        sMeetingTypePosition = 0;
        sMeetingType.setSelection(sMeetingTypePosition);

        // ????????????? ?????????? ???????
        sMeetingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ??????????? ????????? ??????? ? ??????? ????
                sMeetingTypePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // ??????? ??? ?????? ????? ???????
        sMeetingPlaceData = sInit(PLACE);
        sMeetingPlaceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sMeetingPlaceData);
        sMeetingPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMeetingPlace = (Spinner) findViewById(R.id.sMeetingPlace);
        sMeetingPlace.setAdapter(sMeetingPlaceAdapter);

        // ???????? ???????
        sMeetingPlacePosition = 0;
        sMeetingPlace.setSelection(sMeetingPlacePosition);

        // ????????????? ?????????? ???????
        sMeetingPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ??????????? ????????? ??????? ? ??????? ????
                sMeetingPlacePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // ??????? ??? ?????? ???????
        sTimeFromAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sInit(TIME));
        sTimeFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTimeFrom = (Spinner) findViewById(R.id.sTimeFrom);
        sTimeFrom.setAdapter(sTimeFromAdapter);

        // ???????? ???????
        sTimeFromPosition = 0;
        sTimeFrom.setSelection(sTimeFromPosition);

        // ????????????? ?????????? ???????
        sTimeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ??????????? ????????? ??????? ? ??????? ????
                sTimeFromPosition = position;
                meetingDurationValidator(R.id.sTimeFrom);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // ??????? ??? ?????? ???????
        sTimeData = sInit(TIME);
        sTimeToAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sTimeData);
        sTimeToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTimeTo = (Spinner) findViewById(R.id.sTimeTo);
        sTimeTo.setAdapter(sTimeToAdapter);

        // ???????? ???????
        sTimeToPosition = sTimeFromPosition + MIN_MEETING_DURATION;
        sTimeTo.setSelection(sTimeToPosition);

        // ????????????? ?????????? ???????
        sTimeTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ??????????? ????????? ???????
//                meetingDurationValidator();
                sTimeToPosition = position;
                meetingDurationValidator(R.id.sTimeTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private ArrayList<String> sInit(int flag) {
        ArrayList<String> tempArray = new ArrayList<>();
        int resourceId = R.array.Name;
        switch (flag) {
            case NAME:
                resourceId = R.array.Name;
                break;
            case TYPE:
                resourceId = R.array.MeetingType;
                break;
            case PLACE:
                resourceId = R.array.MeetingPlace;
                break;
            case TIME:
                String time = "";
                String hour = "";
                String minute = "";
                for (int i = 0; i < ActivityDateInfo.VISIBLE_HOURS; i++) {
                    for (int j = 0; j < 60; j = j + 15) {
                        if (i < 10) {
                            hour = "0" + i;
                        } else {
                            hour = "" + i;
                        }
                        if (j == 0) {
                            minute = "0" + j;
                        } else {
                            minute = "" + j;
                        }
                        time = hour + ":" + minute;
                        tempArray.add(time);
                    }
                }
                ;
                break;
        }
        if (flag != TIME) {
            for (String i : getResources().getStringArray(resourceId)) {
                tempArray.add(i);
            }
        }
        return tempArray;
    }

    private void meetingDurationValidator(int idResources) {
        if (sTimeToPosition - sTimeFromPosition < 2) {
            sTimeToPosition = sTimeFromPosition + 2;
            sTimeTo.setSelection(sTimeToPosition);

            if (idResources == R.id.sTimeTo) {
                Toast.makeText(getApplicationContext(), R.string.Meeting_duration_alarm, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bCancel:
                finish();
                break;
            case R.id.bSave:
//                Toast.makeText(getApplicationContext(), "SAVE button pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putStringArrayListExtra(NEW_MEETING_TRANSFER_DATA, initNewMeetingData());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private ArrayList<String> initNewMeetingData() {
        ArrayList<String> newMeetingData = new ArrayList<>();
        newMeetingData.add(sNameAdapter.getItem(sNamePosition));
        newMeetingData.add(sTimeFromAdapter.getItem(sTimeFromPosition));
        newMeetingData.add(sTimeToAdapter.getItem(sTimeToPosition));
        newMeetingData.add(sMeetingTypeAdapter.getItem(sMeetingTypePosition));
        newMeetingData.add(sMeetingPlaceAdapter.getItem(sMeetingPlacePosition));
        return newMeetingData;
    }

}



