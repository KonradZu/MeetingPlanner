package com.akobets.meetingplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

/**
 * Created by akobets on 18.08.2015.
 */
public class DateInfoAdapter extends ArrayAdapter<PojoTimeSlot> {

//public class MyLazyAdapter extends ArrayAdapter<String> {


    private Context context;
    private int resource;
    private List<PojoTimeSlot> pojoTimeSlotList;

    public DateInfoAdapter(Context context, int resource, List<PojoTimeSlot> pojoTimeSlotList) {
        super(context, resource, pojoTimeSlotList);

        this.context = context;
        this.resource = resource;
        this.pojoTimeSlotList = pojoTimeSlotList;
    }

//    @Override
//    public int getCount() {
//        // ���������� ���������� ��������� ������
//        return pojoTimeSlotList.size();
//    }

    @Override
    public PojoTimeSlot getItem(int position) {
        // ��������� �� ������� ������ �������� �� ������� ��������
        return pojoTimeSlotList.get(position);
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    // ���������� View ���������� ��������� ������
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // ������ ��� ������������������ �������� ������
        View view = inflater.inflate(resource, parent, false); //����������� � INFLATER-�� !!!

//        ��������� ���� � ����������� ��������
        TableRow tr0 = (TableRow) view.findViewById(R.id.tr0);
        TableRow tr1 = (TableRow) view.findViewById(R.id.tr1);
        TableRow tr2 = (TableRow) view.findViewById(R.id.tr2);
        TableRow tr3 = (TableRow) view.findViewById(R.id.tr3);

        TextView tvRtr0 = (TextView) view.findViewById(R.id.tvRtr0);
        TextView tvRtr1 = (TextView) view.findViewById(R.id.tvRtr1);
        TextView tvRtr2 = (TextView) view.findViewById(R.id.tvRtr2);
        TextView tvRtr3 = (TextView) view.findViewById(R.id.tvRtr3);
        TextView tvLtr0 = (TextView) view.findViewById(R.id.tvLtr0);
        TextView tvLtr1 = (TextView) view.findViewById(R.id.tvLtr1);
        TextView tvLtr2 = (TextView) view.findViewById(R.id.tvLtr2);
        TextView tvLtr3 = (TextView) view.findViewById(R.id.tvLtr3);


//        // ����������� ������ ��� ���������
//        TextView title = (TextView)view.findViewById(R.id.title);
//        TextView time = (TextView)view.findViewById(R.id.time);
//        ImageView thumbImage = (ImageView)view.findViewById(R.id.imageView);

//        // �������� ������� �� ������
//        ObjectItem objectItem = pojoTimeSlotList.get(position);
        // �������� ������� �� ������
        PojoTimeSlot pojoCurrent  = pojoTimeSlotList.get(position);

        // ������������� �������� ����������� ������ ��������� ������
//        title.setText(objectItem.getTitle());
//        time.setText(objectItem.getDate().toString());
//        thumbImage.setImageDrawable(objectItem.getImage());

        // ������������� �������� ����������� ������ ��������� ������

        tr0.setBackgroundColor(pojoCurrent.getColor(0));
        tr1.setBackgroundColor(pojoCurrent.getColor(1));
        tr2.setBackgroundColor(pojoCurrent.getColor(2));
        tr3.setBackgroundColor(pojoCurrent.getColor(3));

        tvLtr0.setText(pojoCurrent.getDataField(PojoTimeSlot.MINUTE00));
        tvRtr0.setText(pojoCurrent.getDataField(PojoTimeSlot.MEETING_CONTACT));
        tvLtr1.setText(pojoCurrent.getDataField(PojoTimeSlot.MINUTE15));
        tvRtr1.setText(pojoCurrent.getDataField(PojoTimeSlot.MEETING_DURATION));
        tvLtr2.setText(pojoCurrent.getDataField(PojoTimeSlot.MINUTE30));
        tvRtr2.setText(pojoCurrent.getDataField(PojoTimeSlot.MEETING_RESERVED1));
        tvLtr3.setText(pojoCurrent.getDataField(PojoTimeSlot.MINUTE45));
        tvRtr3.setText(pojoCurrent.getDataField(PojoTimeSlot.MEETING_RESERVED2));

        return view;
    }
}
