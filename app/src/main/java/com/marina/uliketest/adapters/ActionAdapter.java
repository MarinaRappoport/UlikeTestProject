package com.marina.uliketest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marina.uliketest.R;
import com.marina.uliketest.classes.Action;
import com.marina.uliketest.common.CommonData;

import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Created by Marina on 21.04.2017.
 *
 * adapter for ActionListActivity
 */
public class ActionAdapter extends BaseAdapter{

    private Context context;

    public ActionAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return CommonData.getInstance().getActions().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout;
        if (convertView != null) {
            layout = (LinearLayout)convertView;
        }
        else {
            layout = (LinearLayout) View.inflate(context, R.layout.action_lv_item, null);
        }

        Action action = CommonData.getInstance().getActions().get(position);

        TextView tvType = (TextView) layout.findViewById(R.id.tvType);
        tvType.setText(action.getType().toString());

        TextView tvEnabled = (TextView) layout.findViewById(R.id.tvEnabled);
        tvEnabled.setText(action.isEnabled()? "Yes" : "No");

        TextView tvPriority = (TextView) layout.findViewById(R.id.tvPriority);
        tvPriority.setText(action.getPriority()+"");

        TextView tvDays = (TextView) layout.findViewById(R.id.tvDays);
        tvDays.setText(Arrays.toString(action.getValidDays()));

        TextView tvCooldown = (TextView) layout.findViewById(R.id.tvCooldown);
        tvCooldown.setText(action.getCoolDown()/1000+"");

        TextView tvLastcall = (TextView) layout.findViewById(R.id.tvLastcall);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String lastCall = (action.getLastCall()==null? "Never" : dateFormat.format(action.getLastCall()));
        tvLastcall.setText(lastCall);

        return layout;
    }
}
