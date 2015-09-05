package com.akobets.meetingplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by akobets on 17.08.2015.
 */
public class ActivityDateInfo extends Activity implements View.OnClickListener {

    public static final int VISIBLE_HOURS = 24;
    public static final int START_HOURS = 9;
    public static final int END_HOURS = 22;
    public static final int MAX_MEETING_QUANTITY = 7;
    public static final int MIN_MEETING_QUANTITY = 3;
    public static final int FREE_COLOR_LIGHT = 0x56ffff32;
    public static final int FREE_COLOR_DARK = 0x86ffff32;
    public static final int BUSY_COLOR_LIGHT = 0x56FF8C00;
    public static final int BUSY_COLOR_DARK = 0x86FF8C00;
    public static final int NEW_MEETING_DATA = 1;

    //    private LinearLayout llDateInfo;
//    private TableRow trTop;
//    private TableRow trBottom;
//    private TextView tvDateLabel;
    private TextView tvSelectedDate;

    private Button bBackDateInfo;
    private Button bRefreshDateInfo;
    private Button bAddDateInfo;

    private ListView lvDateInfo;
    private ArrayList<PojoTimeSlot> pojoData = new ArrayList<>();
    private DateInfoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_info);

//        llDateInfo = (LinearLayout) findViewById(R.id.llDateInfo);
//        trTop = (TableRow) findViewById(R.id.trTop);
//        trBottom = (TableRow) findViewById(R.id.trBottom);
//        tvDateLabel = (TextView) findViewById(R.id.tvDateLabel);
        tvSelectedDate = (TextView) findViewById(R.id.tvSelectedDate);

        bBackDateInfo = (Button) findViewById(R.id.bBackDateInfo);
        bRefreshDateInfo = (Button) findViewById(R.id.bRefreshDateInfo);
        bAddDateInfo = (Button) findViewById(R.id.bAddDateInfo);

        bBackDateInfo.setOnClickListener(this);
        bRefreshDateInfo.setOnClickListener(this);
        bAddDateInfo.setOnClickListener(this);

        lvDateInfo = (ListView) findViewById(R.id.lvDateInfo);


        initDateInfoList(pojoData);
        adapter = new DateInfoAdapter(this, R.layout.time_slot, pojoData);
        lvDateInfo.setAdapter(adapter);

        lvDateInfo.setSelection(START_HOURS);

        lvDateInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Selected time: " + adapter.getItem(position).getDataField(0), Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        ArrayList<String> receivedDate;
        String[] month = getResources().getStringArray(R.array.Month_RUS_R);
        receivedDate = intent.getStringArrayListExtra(MainActivity.TRANSFER_DATE);
        tvSelectedDate.setText(receivedDate.get(0) + " " + month[Integer.parseInt(receivedDate.get(1))] + " " + receivedDate.get(2));
    }


    private boolean upDateInfoList(ArrayList<PojoTimeSlot> resultArray, String Contact, String timeFrom, String timeTo, String meetingPlace, String meetingType) {

        boolean success = true;

        int timeFromHour;
        int timeFromMinute;
        int timeToHour;
        int timeToMinute;
        int meetingDuration;

        ArrayList<PojoTimeSlot> pojoDataCheck = new ArrayList<>();

//        ?????? ?????? ? ??????????? ? ???????
        timeFromHour = Integer.parseInt(timeFrom.substring(0, 1));
        timeFromMinute = Integer.parseInt(timeFrom.substring(3, 4));
        timeToHour = Integer.parseInt(timeTo.substring(0, 1));
        timeToMinute = Integer.parseInt(timeTo.substring(3, 4));

//      ????????? ??? ?? ??????????????? ?????? ? ????????? ????????? ??????????
        meetingDuration = (timeToHour - timeFromHour) * 4 + (timeToMinute - timeFromMinute) / 15;
        for (int i = 0; i < meetingDuration; i++) {
            if (resultArray.get(timeFromHour).getDataField(timeFromMinute / 15) != " ") {
                success = false;
                i = meetingDuration;
            } else {
                timeFromMinute += 15;
                if (timeFromMinute == 0) {
                    timeFromHour++;
                }
            }
        }
        return success;
    }

    private void initDateInfoList(ArrayList<PojoTimeSlot> resultArray) {
        resultArray.clear();
        PojoTimeSlot[] PojoTimeSlotArray = new PojoTimeSlot[VISIBLE_HOURS];
        int color;
        for (int i = 0; i < VISIBLE_HOURS; i++) {
            PojoTimeSlotArray[i] = new PojoTimeSlot();
            PojoTimeSlotArray[i].setDataField(timeIntToString(i, 0), PojoTimeSlot.MINUTE00);
            if (i % 2 != 0) {
                color = FREE_COLOR_LIGHT;
            } else {
                color = FREE_COLOR_DARK;
            }
            PojoTimeSlotArray[i].setColor(color, 0, 4);
            resultArray.add(i, PojoTimeSlotArray[i]);
        }
    }

    private void refreshDateInfoList(ArrayList<PojoTimeSlot> refreshArray) {

        initDateInfoList(refreshArray);

        int meetingQuantity;
        int contactId;
        String timeFrom;
        String timeTo;
        String meetingContact;
        String meetingDuration;
        String meetingType;
        String meetingPlace;
        int[][][] meetingTime;

//Рандомно определяем количество встреч на текущий  день
        for (meetingQuantity = 0; meetingQuantity < MIN_MEETING_QUANTITY; )
            meetingQuantity = (int) (Math.random() * MAX_MEETING_QUANTITY);
        Log.d("MyLog", "meetingQuantity = " + meetingQuantity);

//        Рандомно формируем массив часы х минуты х начало/конец встречи
        meetingTime = new int[2][meetingQuantity][2];
        for (int i = 0; i < meetingQuantity; i++) {
            for (; meetingTime[0][i][0] < START_HOURS; ) {
                meetingTime[0][i][0] = START_HOURS + (int) (Math.random() * (END_HOURS - START_HOURS));
            }
            meetingTime[1][i][0] = ((int) (Math.random() * 4)) * 15;
        }
//        "Упорядочиваем сформированный массив по возрастанию
        int temp;
        for (int i = 0; i < meetingQuantity; i++) {
            for (int j = i + 1; j < meetingQuantity; j++) {
//                Log.d("MyLog", " До проверки:  meetingTime [0][" + i + "][0] = " + meetingTime[0][i][0] + " meetingTime [0][" + j + "][0] = " + meetingTime[0][j][0]);
                if (meetingTime[0][i][0] > meetingTime[0][j][0]) {
                    temp = meetingTime[0][j][0];
                    meetingTime[0][j][0] = meetingTime[0][i][0];
                    meetingTime[0][i][0] = temp;
                }
            }
        }
//        Задаем интервал между началами встреч не менее 2-х часов"
        for (int i = 0; i < meetingQuantity; i++) {
            for (int j = i + 1; j < meetingQuantity; j++) {

                if (meetingTime[0][i][0] == meetingTime[0][j][0]) {
                    meetingTime[0][j][0] += 2;
                }
                if (meetingTime[0][j][0] - meetingTime[0][i][0] == 1) {
                    meetingTime[0][j][0] += 1;
                }
            }
        }
//Убираем встречи с началом 23-00 и позже
        for (int i = 0; i < meetingQuantity; i++) {
            if (meetingTime[0][i][0] > END_HOURS) {
                meetingQuantity = i;
            }
        }

// Задаем продолжительность каждой встречи в пределах времени между всречами,
// встречи не могут заканчиваться и начинаться в пределах одного часа
        int tempMeetingDuration;
        for (int i = 0; i < meetingQuantity; i++) {
            for (tempMeetingDuration = 0; tempMeetingDuration == 0; ) {

                if (i < meetingQuantity - 1) {
                    tempMeetingDuration = (int) ((
                            (meetingTime[0][i + 1][0] - meetingTime[0][i][0]) * 4 +
                                    (meetingTime[1][i + 1][0] - meetingTime[1][i][0]) / 15
                    ) * Math.random());
                } else {
                    tempMeetingDuration = (int) ((
                            (VISIBLE_HOURS - meetingTime[0][i][0]) * 4 -
                                    meetingTime[1][i][0] / 15
                    ) * Math.random());
                }
                if (tempMeetingDuration < 2) tempMeetingDuration = 2;

            }
            meetingTime[0][i][1] = meetingTime[0][i][0] + (tempMeetingDuration + meetingTime[1][i][0] / 15) / 4;


            if ((tempMeetingDuration + meetingTime[1][i][0] / 15) < 4) {
                meetingTime[1][i][1] = (tempMeetingDuration + meetingTime[1][i][0] / 15) * 15;
            } else {
                meetingTime[1][i][1] = ((tempMeetingDuration + meetingTime[1][i][0] / 15) % 4) * 15;
            }

            if (i >= 1) {
                if (meetingTime[0][i][0] == meetingTime[0][i - 1][1]) {
                    meetingTime[1][i - 1][1] = 0;
                }
            }
        }

//        Отладочный цикл. Проверка на корректность временных интервалов
        for (int i = 0; i < meetingQuantity; i++) {
            Log.d("MyLog",
                    " Встреча №:" + (i + 1) +
                            "состоится с " + meetingTime[0][i][0] + ":" + meetingTime[1][i][0] +
                            " до " + meetingTime[0][i][1] + ":" + meetingTime[1][i][1]);
        }


// Переносим данные из сформированного массива времен встреч в поля списка pojo
//  добавляем данные - имя контакта и  длительность встречи

        ArrayList<String> contactList = new ArrayList<>();

        for (String j : getResources().getStringArray(R.array.Name)) {
            contactList.add(j);
        }

        for (int i = 0; i < meetingQuantity; i++) {

// set meeting contact
            contactId = (int) (Math.random() * (contactList.size() - 1));
            meetingContact = contactList.get(contactId);
            contactList.remove(contactId);
            refreshArray.get(meetingTime[0][i][0]).setDataField(meetingContact, meetingTime[1][i][0] / 15 * 2 + 1);

// set meeting TimeFrom time
//            timeFrom = timeIntToString(meetingTime[0][i][0], meetingTime[1][i][0]);
//            pojoData.get(meetingTime[0][i][0]).setDataField(timeFrom, meetingTime[1][i][0] / 15 * 2);

// set meeting duration,  for example - 2 hours 30 minutes
            tempMeetingDuration = (meetingTime[0][i][1] - meetingTime[0][i][0]) * 4 +
                    (meetingTime[1][i][1] - meetingTime[1][i][0]) / 15;
            meetingDuration = timeToWorld(tempMeetingDuration / 4, (tempMeetingDuration - (tempMeetingDuration / 4) * 4) * 15);
            if (meetingTime[1][i][0] == 45) {
                refreshArray.get(meetingTime[0][i][0] + 1).setDataField(meetingDuration, 1);
            } else {
                refreshArray.get(meetingTime[0][i][0]).setDataField(meetingDuration, meetingTime[1][i][0] / 15 * 2 + 3);
            }

//painting time-slot in busy colors
            busyTimePainting(refreshArray, meetingTime[0][i][0], meetingTime[1][i][0], meetingTime[0][i][1], meetingTime[1][i][1]);

// set meeting TimeTo time
//            timeTo = timeIntToString(meetingTime[0][i][1], meetingTime[1][i][1]);
//            pojoData.get(meetingTime[0][i][1]).setDataField(timeTo, meetingTime[1][i][1] / 15 * 2);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bBackDateInfo:
                finish();
                break;

            case R.id.bRefreshDateInfo:
                refreshDateInfoList(pojoData);
                adapter.notifyDataSetChanged();
                for (int i = START_HOURS; i < END_HOURS; i++) {
                    if (pojoData.get(i).getDataField(1) != " " ||
                            pojoData.get(i).getDataField(3) != " " ||
                            pojoData.get(i).getDataField(5) != " " ||
                            pojoData.get(i).getDataField(7) != " ") {
                        lvDateInfo.setSelection(i);
                        i = END_HOURS;
                    }
                }


                break;
            case R.id.bAddDateInfo:
                Intent intent = new Intent(getApplicationContext(), ActivityMeetingAdd.class);
                startActivityForResult(intent, NEW_MEETING_DATA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) {
            return;
        }
        ArrayList<String> receivedMeeting = intent.getStringArrayListExtra(ActivityMeetingAdd.NEW_MEETING_TRANSFER_DATA);

        Toast.makeText(getApplicationContext(), "Планируется встреча с " + "Имя: " + receivedMeeting.get(0) +
                ", С " + receivedMeeting.get(1) + " До " + receivedMeeting.get(2) + ", На тему: " + receivedMeeting.get(3) +
                ", Место: " + receivedMeeting.get(4), Toast.LENGTH_SHORT).show();

    }


    private String timeIntToString(int timeHour, int timeMinute) {
        String time;
        if (timeHour < 10) {
            time = "0" + timeHour + ":";
        } else {
            time = timeHour + ":";
        }
        if (timeMinute == 0) {
            time = time + "0" + timeMinute;
        } else {
            time = time + timeMinute;
        }
        return time;
    }


    private String timeToWorld(int hour, int minute) {
        String time = "";
        String hourString;
        String minuteString;

        hourString = "" + hour;
//        if (hour < 10) {
//            hourString = "0" + hour;
//        } else {
//            hourString = "" + hour;
//        }
        if (minute == 0) {
            minuteString = "0" + minute;
        } else {
            minuteString = "" + minute;
        }
        ArrayList<String> timeToWord = new ArrayList<>();
        for (String i : getResources().getStringArray(R.array.TimeToWorD)) {
            timeToWord.add(i);
        }
        if (hour < 0 || hour > 24 || (minute != 0 && minute != 15 && minute != 30 && minute != 45)) {
            time = "Ошибка временного диапазона";
            return time;
        }
        if (hour >= 5 && hour <= 20 || hour == 0) {
            time = hourString + " " + timeToWord.get(1) + " " + minuteString + " " + timeToWord.get(3);
        } else {
            if (hour == 1 || hour == 21) {
                time = hourString + " " + timeToWord.get(0) + " " + minuteString + " " + timeToWord.get(3);
            } else {
                time = hourString + " " + timeToWord.get(2) + " " + minuteString + " " + timeToWord.get(3);
            }
        }
        return time;
    }


    void busyTimePainting(ArrayList<PojoTimeSlot> pojoArray, int startHour, int startMinute, int endHour, int endMinute) {
        int tempTimeDuration = (endHour - startHour) * 4 + (endMinute - startMinute) / 15;
        int cntHour = startHour;
        int cntMinute = startMinute / 15;
        int color;
        for (int i = 0; i < tempTimeDuration; i++) {
            if (pojoArray.get(cntHour).getColor(cntMinute) == FREE_COLOR_LIGHT) {
                color = BUSY_COLOR_LIGHT;
            } else {
                color = BUSY_COLOR_DARK;
            }
            pojoData.get(cntHour).setColor(color, cntMinute, 1);
            cntMinute++;
            if (cntMinute == 4) {
                cntMinute = 0;
                cntHour++;
            }
        }
    }


}


