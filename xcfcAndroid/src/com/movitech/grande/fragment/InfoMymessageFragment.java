package com.movitech.grande.fragment;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.MyMessageListAdapter;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcMyMessage;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcMyMessageResult;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 2, 2014 8:19:22 PM
 * @Description: This is David Wu's property.
 *
 **/
@EFragment(R.layout.layout_listview_fragment_info)
public class InfoMymessageFragment extends BaseFragment {

	@ViewById(R.id.lv_fragment_info)
	LoadDataListView lvFragmentInfo;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;

	XcfcMyMessage[] myMessages = null;

	View myMessageLoadMore = null;

	MyMessageListAdapter myMessageAdapter = null;

	int myMessageCurrPage = 1;

	int myMessageTotalPage;

	List<View> views = null;
	List<TextView> textViews = null;
	ProcessingDialog processingDialog = null;
	boolean isLoading = false;
	@AfterViews
	void afterViews() {
		// 初始化ListView
		initializeListView();
	}

	public void loadMyMessageData() {
		// 载入数据
		doLoadDataAndBindData();
	}

	public void reDoLoad() {
		if (!isLoading) {
			showProgressDialog();
			doLoadDataAndBindData();
		}
	}
	private void initializeListView() {
		myMessageLoadMore = getActivity().getLayoutInflater().inflate(R.layout.item_loading, null);

		lvFragmentInfo.addFooterView(myMessageLoadMore);

		lvFragmentInfo.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
			public void toBottom() {
				loadNewForMyMessage();
				
			}
		});
	}

	XcfcMyMessageResult resultMessages;
	@Background
	void loadNewForMyMessage() {
		myMessageCurrPage = lvFragmentInfo.getCurrentPage() + 1;
		resultMessages = netHandler.postForGetMyMessages(myMessageCurrPage, mApp.getCurrUser().getId());

		boolean ret = resultMessages == null || !resultMessages.getResultSuccess();
		if (!ret) {
			addMessageItems();
		}
	}

	@UiThread
	void addMessageItems() {
		myMessageAdapter.addItems(resultMessages.getPageResult().getMessages());
		lvFragmentInfo.setCurrentPage(myMessageCurrPage);
	}

	@Background
	void doLoadDataAndBindData() {
		XcfcMyMessageResult result = netHandler.postForGetMyMessages(myMessageCurrPage, mApp.getCurrUser().getId());

		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		myMessages = result.getPageResult().getMessages();
		myMessageTotalPage = result.getPageResult().getPageNo();

		goBackMainThread(result.getResultMsg(), true);
	}

	private void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(getActivity(), msg);

		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		dismissProcessingDialog();
		myMessageAdapter = new MyMessageListAdapter(getActivity(), myMessages, R.layout.item_mine_message);
		lvFragmentInfo.setAdapter(myMessageAdapter);
		lvFragmentInfo.setTotalPageCount(myMessageTotalPage);
		lvFragmentInfo.setCurrentPage(1);

		/* Click 事件，暂时不做事情
		lvFragmentInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcMyMessage message = ((MyMessageListAdapter.ViewCache) view.getTag()).info;
			}
		});
		*/
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void showProgressDialog() {
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				//BackgroundExecutor.cancelAll("queryData", false);
			}
		});
		processingDialog.show();
	}
	
	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}
}
