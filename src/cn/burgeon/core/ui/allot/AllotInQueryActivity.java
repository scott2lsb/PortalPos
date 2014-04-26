package cn.burgeon.core.ui.allot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotInQueryLVAdapter;
import cn.burgeon.core.bean.AllotInQuery;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;

import com.android.volley.Response;

public class AllotInQueryActivity extends BaseActivity implements OnClickListener {

	private EditText startDateET, endDateET;
	private ListView allotinqueryLV;
	private TextView recodeNumTV;
	private Button queryBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allot_in_query);

		init();

		initLVData();
	}

	private void init() {
		// 初始化门店信息
		TextView storeTV = (TextView) findViewById(R.id.storeTV);
		storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

		TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
		currTimeTV.setText(getCurrDate());
		
		startDateET = (EditText) findViewById(R.id.startDateET);
		endDateET = (EditText) findViewById(R.id.endDateET);

		HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		ViewGroup.LayoutParams params = hsv.getLayoutParams();
		params.height = (int) ScreenUtils.getAllotInLVHeight(this);

		allotinqueryLV = (ListView) findViewById(R.id.allotinqueryLV);
		recodeNumTV = (TextView) findViewById(R.id.recodeNumTV);

		queryBtn = (Button) findViewById(R.id.queryBtn);
		queryBtn.setOnClickListener(this);
	}

	private void initLVData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.queryBtn:
			/*
			 * { "table":"M_TRANSFER", "columns":["DOCNO","DATEOUT"], "params":{"column":"DATEIN","condition":"20091217~20091219"} }
			 */
			Map<String, String> params = new HashMap<String, String>();
			JSONArray array = new JSONArray();
			try {
				JSONObject transactions = new JSONObject();
				transactions.put("id", 112);
				transactions.put("command", "Query");

				JSONObject paramsTable = new JSONObject();
				paramsTable.put("table", "M_TRANSFER");
				paramsTable.put("columns", new JSONArray().put("ID").put("DOCNO").put("BILLDATE").put("C_ORIG_ID").put("TOT_QTYOUT"));
				JSONObject paramsCondition = new JSONObject();
				paramsCondition.put("column", "DATEIN");
				paramsCondition.put("condition", startDateET.getText() + "~" + endDateET.getText());
				paramsTable.put("params", paramsCondition);

				transactions.put("params", paramsTable);
				array.put(transactions);
				params.put("transactions", array.toString());

				sendRequest(params, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 取消进度条
						stopProgressDialog();

						ArrayList<AllotInQuery> lists = new ArrayList<AllotInQuery>();
						try {
							JSONArray resJA = new JSONArray(response);
							JSONObject resJO = resJA.getJSONObject(0);
							JSONArray rowsJA = resJO.getJSONArray("rows");
							int len = rowsJA.length();

							for (int i = 0; i < len; i++) {
								// [2924,"TF0912140000163",20091214,3865,13]
								String currRow = rowsJA.get(i).toString();
								String[] currRows = currRow.split(",");

								AllotInQuery allotInQuery = new AllotInQuery();
								allotInQuery.setID(currRows[0].substring(1, currRows[0].length()));
								allotInQuery.setDOCNO(currRows[1].substring(1, currRows[1].length() - 1));
								allotInQuery.setBILLDATE(currRows[2]);
								allotInQuery.setC_ORIG_ID(currRows[3]);
								allotInQuery.setTOT_QTYOUT(currRows[4].substring(0, currRows[4].length() - 1));
								lists.add(allotInQuery);
							}

							// 记录数
							recodeNumTV.setText(lists.size() + "条记录");

							AllotInQueryLVAdapter mAdapter = new AllotInQueryLVAdapter(AllotInQueryActivity.this, lists,
									R.layout.allot_in_query_item);
							allotinqueryLV.setAdapter(mAdapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
