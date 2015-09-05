package com.akobets.meetingplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends Activity {

    public static final int YEAR_RANGE = 11;
    public static final String TRANSFER_DATE = "ƒень,мес€ц,год";

    private ArrayList<String> sDayData;
    private ArrayList<String> sMonthData;
    private ArrayList<String> sYearData;

    private Spinner sDay;
    private Spinner sMonth;
    private Spinner sYear;

    private int sDayPosition;
    private int sMonthPosition;
    private int sYearPosition;

    private Calendar calendarCurrent = new GregorianCalendar();

    private static ArrayAdapter<String> sDayAdapter;
    private static ArrayAdapter<String> sMonthAdapter;
    private static ArrayAdapter<String> sYearAdapter;

    private Button bViewPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // адаптер дл€ списка ƒни
        sDayData = sDayDataInit();
        sDayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sDayData);
        sDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDay = (Spinner) findViewById(R.id.sDay);
        sDay.setAdapter(sDayAdapter);

        // выдел€ем элемент
        sDayPosition = calendarCurrent.get(Calendar.DAY_OF_MONTH) - 1;
        sDay.setSelection(sDayPosition);

        // устанавливаем обработчик нажати€
        sDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                запоминаеем выбранную позицию в массиве дней
                sDayPosition = position;
                calendarCurrent.set(Calendar.DAY_OF_MONTH, sDayPosition + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // адаптер дл€ списка ћес€цы
        sMonthData = sMonthDataInit();
        sMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sMonthData);
        sMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMonth = (Spinner) findViewById(R.id.sMonth);
        sMonth.setAdapter(sMonthAdapter);

        // выдел€ем элемент
        sMonthPosition = calendarCurrent.get(Calendar.MONTH);
        sMonth.setSelection(sMonthPosition);

        // устанавливаем обработчик нажати€
        sMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sMonthPosition = position;
                dayValidator();
                calendarCurrent.set(Calendar.MONTH, sMonthPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // адаптер дл€ списка √оды
        sYearData = sYearDataInit();

        sYearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sYearData);
        sYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sYear = (Spinner) findViewById(R.id.sYear);
        sYear.setAdapter(sYearAdapter);

        // выдел€ем элемент
        sYearPosition = YEAR_RANGE / 2;
        sYear.setSelection(sYearPosition);
        // устанавливаем обработчик нажати€
        sYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                sYearPosition = position;
                dayValidator();
                calendarCurrent.set(Calendar.YEAR, Integer.parseInt(sYearData.get(sYearPosition)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        bViewPost = (Button) findViewById(R.id.bViewPost);

        bViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> transferDate = new ArrayList<>();
                transferDate.add(String.valueOf(calendarCurrent.get(Calendar.DAY_OF_MONTH)));
                transferDate.add(String.valueOf(calendarCurrent.get(Calendar.MONTH)));
                transferDate.add(String.valueOf(calendarCurrent.get(Calendar.YEAR)));

                Intent intent = new Intent(getApplicationContext(), ActivityDateInfo.class);
                intent.putStringArrayListExtra(TRANSFER_DATE, transferDate);
                startActivity(intent);
//                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //ћетод возвращает строковый массив, в котором каждый элемент это день мес€ца - 1,2,...,31.
    private ArrayList<String> sDayDataInit() {
        int dayInMonth = calendarCurrent.getActualMaximum(Calendar.DAY_OF_MONTH);
        ArrayList<String> DayDataArray = new ArrayList<>();
        for (int i = 0; i < dayInMonth; i++) {
            DayDataArray.add(String.valueOf(i + 1));
        }
        return DayDataArray;
    }

    //ћетод возвращает строковый массив, в котором каждый элемент это год - 2010, 2011 и т.д.
    private ArrayList<String> sYearDataInit() {
        int year = calendarCurrent.get(Calendar.YEAR) - YEAR_RANGE / 2;
        ArrayList<String> YearDataArray = new ArrayList<>();
        for (int i = 0; i < YEAR_RANGE; i++) {
            YearDataArray.add(String.valueOf(year));
            year++;
        }
        return YearDataArray;
    }

    //ћетод возвращает строковый массив, в котором каждый элемент это наименование мес€ца январь,‘евраль и т.д.
    private ArrayList<String> sMonthDataInit() {
        ArrayList<String> MonthDataArray = new ArrayList<>();
        for (String i : getResources().getStringArray(R.array.Month_RUS)) {
            MonthDataArray.add(i);
        }
        return MonthDataArray;
    }

    //ћетод провер€ет выбранную комбинацию год+мес€ц+число на корректнось, позвол€ет избежать "31 апрел€", учитывает високосные года.
    private void dayValidator() {
        Calendar calendarTemp = new GregorianCalendar(Integer.parseInt(sYearData.get(sYearPosition)), sMonthPosition, 1);
        int newDayInMonth = calendarTemp.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDayInMonth = calendarCurrent.getActualMaximum(Calendar.DAY_OF_MONTH);
        int residualDayInMonth = newDayInMonth - currentDayInMonth;
        if (residualDayInMonth != 0) {
            for (int i = 0; i < Math.abs(residualDayInMonth); i++) {
                if (residualDayInMonth > 0) {
                    currentDayInMonth++;
                    sDayData.add(String.valueOf(currentDayInMonth));
                } else {
                    currentDayInMonth--;
                    sDayData.remove(currentDayInMonth);
                }
                sDayAdapter.notifyDataSetChanged();
            }
        }
        if (sDayPosition > newDayInMonth - 1) {
            sDayPosition = newDayInMonth - 1;
            sDay.setSelection(sDayPosition);
        }
    }

}


