package cn.burgeon.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.burgeon.core.R;
import cn.burgeon.core.bean.AllotOut;

/**
 * Created by Simon on 2014/4/16.
 */
public class AllotOutLVAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AllotOut> list;
    private int mLayoutRes;

    public AllotOutLVAdapter(Context c, ArrayList<AllotOut> l, int layoutRes) {
        this.mContext = c;
        this.list = l;
        this.mLayoutRes = layoutRes;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutRes, null);
            holder = new ViewHolder();
            holder.idTV = (TextView) convertView.findViewById(R.id.idTV);
            holder.docnoTV = (TextView) convertView.findViewById(R.id.docnoTV);
            holder.uploadStateTV = (TextView) convertView.findViewById(R.id.uploadStateTV);
            holder.docStateTV = (TextView) convertView.findViewById(R.id.docStateTV);
            holder.billdateTV = (TextView) convertView.findViewById(R.id.billdateTV);
            holder.cdestidTV = (TextView) convertView.findViewById(R.id.cdestidTV);
            holder.totqtyoutTV = (TextView) convertView.findViewById(R.id.totqtyoutTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 赋值
        holder.idTV.setText(String.valueOf(list.get(position).getID()));
        holder.docnoTV.setText(list.get(position).getDOCNO());
        holder.billdateTV.setText(list.get(position).getBILLDATE());
        holder.cdestidTV.setText(list.get(position).getC_DEST_ID());
        holder.totqtyoutTV.setText(list.get(position).getTOT_QTYOUT());
        return convertView;
    }

    static class ViewHolder {
        TextView idTV;
        TextView docnoTV;
        TextView uploadStateTV;
        TextView docStateTV;
        TextView billdateTV;
        TextView cdestidTV;
        TextView totqtyoutTV;
    }
}
