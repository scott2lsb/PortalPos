package cn.burgeon.core.ui.allot;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotOutInAdapter;
import cn.burgeon.core.ui.BaseActivity;

public class AllotOutInActivity extends BaseActivity {

    private GridView allotOutInGV;
    private AllotOutInAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_allot_out_in);

        init();
    }

    private void init() {
        allotOutInGV = (GridView) findViewById(R.id.allotOutInGV);
        mAdapter = new AllotOutInAdapter(this);
        allotOutInGV.setAdapter(mAdapter);
        allotOutInGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) parent.getItemAtPosition(position);
                if (itemValue != null && Constant.allotOutInTextValues[0].equals(itemValue)) {
                    forwardActivity(AllotInActivity.class);
                } else if (itemValue != null && Constant.allotOutInTextValues[1].equals(itemValue)) {
                    forwardActivity(AllotOutActivity.class);
                } else if (itemValue != null && Constant.allotOutInTextValues[2].equals(itemValue)) {
                    forwardActivity(AllotInQueryActivity.class);
                } else if (itemValue != null && Constant.allotOutInTextValues[3].equals(itemValue)) {
                    forwardActivity(AllotOutQueryActivity.class);
                }
            }
        });
    }
}
