package com.mytv365.zb.views.fragment.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhrj.library.MApplication;
import com.fhrj.library.base.impl.BaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.ListSelfAdapter;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.common.Globals;
import com.mytv365.zb.model.ItemMyStock;
import com.mytv365.zb.model.ItemStock;
import com.mytv365.zb.model.User;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.Parse;
import com.mytv365.zb.utils.UtilHttp;
import com.mytv365.zb.views.activity.stock.GgxqActivity;
import com.mytv365.zb.views.activity.stock.HshqActivity;
import com.mytv365.zb.views.activity.stock.SearchActivity;
import com.mytv365.zb.views.activity.user.LoginActivity;
import com.mytv365.zb.widget.stock.SlideListView.MyOnItemOnClickListener;
import com.mytv365.zb.widget.stock.XSlideListView;
import com.mytv365.zb.widget.webview.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @ 自选股
 * Created by zhangguohao on 16/9/8.
 */
public class SelfFragment extends BaseFragment implements OnClickListener{

    private ListSelfAdapter mSelfAdapter;
    private XSlideListView lvMyStockList;
    private TextView tv_bianji;
    private RelativeLayout ll_search;
    private RelativeLayout rl_center;
    private RelativeLayout rl_centers;
    private LinearLayout ll_center;

    LinearLayout layout_index_1;
    LinearLayout layout_index_2;
    LinearLayout layout_index_3;

    TextView tv_index_1;
    TextView tv_index_1_zs;
    TextView tv_index_1_zdf;
    TextView tv_index_2;
    TextView tv_index_2_zs;
    TextView tv_index_2_zdf;
    TextView tv_index_3;
    TextView tv_index_3_zs;
    TextView tv_index_3_zdf;
    ImageView iv_serachs;
    TextView tv_serachs;
    /*标题*/
    private TextView title;

    /** Dialog */
    private MyProgressDialog pd;// 进度条

    private boolean isFirst = false;
    /** 数据相关 */
    private boolean isSend;// 是否发送到handler
    private boolean isSendHshq;// 是否发送到handler

    private boolean isSendMsg = false;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        // 当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Globals.HANDLE_GET_SELF:
                    findSelf();
                    break;
                case 0x2000:
                    User user = Constant.getUser();
                    if(MApplication.isNetworkReady()==true) {
                        if (user == null) {
                            ll_center.setVisibility(View.GONE);
                            rl_center.setVisibility(View.GONE);
                            rl_centers.setVisibility(View.VISIBLE);
                            MyUtils.getInstance().showToast(getActivity(),
                                    "请登录!");
                        } else {
                            pd.show();
                            iv_serachs.setBackgroundResource(R.drawable.addphoto);
                            tv_serachs.setText("暂无股票，请去添加");
                            runSelf();
                        }
                    }else{

                        MyUtils.getInstance().showToast(getActivity(),
                                "网络已关闭");
                        iv_serachs.setBackgroundResource(R.drawable.icon_no_wifi);
                        tv_serachs.setText("请求失败，重试看看");
                        ll_center.setVisibility(View.GONE);
                        rl_center.setVisibility(View.GONE);
                        rl_centers.setVisibility(View.VISIBLE);

                    }

//                    if(!isSendMsg){
//                        handler.sendEmptyMessageDelayed(0x2000,100);
//                        isSendMsg = ! isSendMsg;
//                    }

                    break;

                case 0x000:
                    MyUtils.getInstance().showToast(getActivity(),
                            "暂无股票,请去添加!");
                    break;
                case Globals.HANDLE_GGHQ:
                    Bundle bundle = msg.getData();
                    Intent intent = new Intent(getActivity(),
                            GgxqActivity.class);
                    intent.putExtra("code", bundle.getString("code"));
                    startActivity(intent);
                    // DataCenter.getInstance().parserGgxq();
                    // DataCenter.getInstance().parserGgxqTime();
                    // DataCenter.getInstance().parseMX();
                    // Intent intent = new Intent(SelfActivity.this,
                    // GgxqActivity.class);
                    // startActivity(intent);
                    break;
                case Globals.HANDLE_HSHQ:
                    intent = new Intent(getActivity(), HshqActivity.class);
                    startActivity(intent);
                    break;
                case 0x999:
                    MyUtils.getInstance().showToast(getActivity(),
                            "网络请求异常");
                    break;
                default:
                    break;
            }
            if (pd.isShowing())
                pd.dismiss();
        }
    };

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        Log.i("i","  Fragment  执行onAttach");

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        pd = new MyProgressDialog(getActivity(),R.style.CustomProgressDialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_my_stock, null);
        DataCenter.getInstance().initDB(getActivity());
        findLayout(view);
        isSend = true;
        Log.i("test","-onCreateView-");

        pd.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                if (!pd.isShowing()) {
                    isSend = false;
                    isSendHshq = false;
                }
            }
        });
        initView(view);

        lvMyStockList.setPullRefreshEnable(true);
        lvMyStockList.setPullLoadEnable(true);
        lvMyStockList.setXListViewListener(new XSlideListView.IXListViewListener() {

            @Override
            public void onRefresh() {

                CountDownTimer time = new CountDownTimer(2000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFinish() {
                        lvMyStockList.stopRefresh();
//                                    initView();
                        handler.sendEmptyMessage(0x2000);
                    }
                };

                time.start();

            }

            @Override
            public void onLoadMore() {
                CountDownTimer time = new CountDownTimer(2000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFinish() {
                        lvMyStockList.stopLoadMore();
                    }
                };
                time.start();
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//        handler.removeMessages(0x2000);
        handler.sendEmptyMessage(0x2000);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        handler.removeMessages(0x2000);
    }

    private void findLayout(View view) {
        LinearLayout back = (LinearLayout) view.findViewById(R.id.ll_back);
        back.setOnClickListener(this);
        ImageView ivSearch = (ImageView) view.findViewById(R.id.title_search);
        ivSearch.setOnClickListener(this);
        ImageView ivMarket = (ImageView) view.findViewById(R.id.title_market);
        ivMarket.setOnClickListener(this);

        tv_bianji = (TextView)view.findViewById(R.id.bianji);
        tv_bianji.setOnClickListener(this);
        tv_index_1 = (TextView) view.findViewById(R.id.tv_index_1);
        tv_index_1_zs = (TextView) view.findViewById(R.id.tv_index_1_zs);
        tv_index_1_zdf = (TextView) view.findViewById(R.id.tv_index_1_zdf);
        tv_index_2 = (TextView) view.findViewById(R.id.tv_index_2);
        tv_index_2_zs = (TextView) view.findViewById(R.id.tv_index_2_zs);
        tv_index_2_zdf = (TextView) view.findViewById(R.id.tv_index_2_zdf);
        tv_index_3 = (TextView) view.findViewById(R.id.tv_index_3);
        tv_index_3_zs = (TextView) view.findViewById(R.id.tv_index_3_zs);
        tv_index_3_zdf = (TextView) view.findViewById(R.id.tv_index_3_zdf);
        layout_index_1 = (LinearLayout) view.findViewById(R.id.layout_index_1);
        // layout_index_1.setOnClickListener(this);
        layout_index_2 = (LinearLayout) view.findViewById(R.id.layout_index_2);
        // layout_index_2.setOnClickListener(this);
        layout_index_3 = (LinearLayout) view.findViewById(R.id.layout_index_3);
        // layout_index_3.setOnClickListener(this);
        lvMyStockList = (XSlideListView) view.findViewById(R.id.list);
        ll_search = (RelativeLayout) view.findViewById(R.id.search_ll);
        ll_search.setOnClickListener(this);


        rl_center = (RelativeLayout) view.findViewById(R.id.rl_center);
        rl_centers = (RelativeLayout) view.findViewById(R.id.rl_centers);
        ll_center = (LinearLayout) view.findViewById(R.id.ll_center);
        iv_serachs = (ImageView) view.findViewById(R.id.iv_searchs);
        tv_serachs = (TextView) view.findViewById(R.id.tv_searchs);
        iv_serachs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_searchs:
                if(Constant.getUser()==null){
                    intent.setClass(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }else {
                    intent.setClass(getActivity(), SearchActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.search_ll:
                intent.setClass(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.bianji:
                if (lvMyStockList.isCanSlide()) {
                    lvMyStockList.setCanSlide(false);
                    tv_bianji.setText("完成");
                    if ( mSelfAdapter!= null) {
                        mSelfAdapter.notifyDataSetChanged();
                    }
                } else {
                    lvMyStockList.setCanSlide(true);
                    tv_bianji.setText("编辑");
                }
                break;
            case R.id.ll_back://
                Log.i("SelfActivity", "===back");
                getActivity().finish();
                break;
            case R.id.title_search:
                Log.i("SelfActivity", "===title_search");
                intent.setClass(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                // this.finish();
                break;
            case R.id.title_market:
                Log.i("SelfActivity", "===title_market");
                isSendHshq = true;
                pd.show();
                runHshq();
                break;
            case R.id.layout_index_1:
                Log.i("SelfActivity", "===layout_index_1");
                break;
            case R.id.layout_index_2:
                Log.i("SelfActivity", "===layout_index_2");
                break;
            case R.id.layout_index_3:
                Log.i("SelfActivity", "===layout_index_3");
                break;
            default:
                break;
        }
    }

    /**
     * 沪深行情
     */
    private void runHshq() {
        new Thread() {
            @Override
            public void run() {
//				DataCenter.getInstance().mStrHshq = DataCenter.getInstance().http
//						.getHshq();
//				if (DataCenter.getInstance().mStrHshq != null
//						&& DataCenter.getInstance().mStrHshq.length() > 0) {
                parserHshq();
//					if (isSendHshq)
                handler.sendEmptyMessage(Globals.HANDLE_HSHQ);
//				} else {
//					handler.sendEmptyMessage(0x999);
//				}
            }
        }.start();
    }

    /**
     * 自选股列表
     */
    private void runSelf() {
        new Thread() {
            @Override
            public void run() {
               getSelf("2");


            }
        }.start();
    }

    /**
     * 个股行情
     */
    private void runGghq(final String code) {
        new Thread() {
            @Override
            public void run() {

                DataCenter.getInstance().mStrGghq = DataCenter.getInstance().http
                        .getGghq(code);
                if (DataCenter.getInstance().mStrGghq != null
                        && DataCenter.getInstance().mStrGghq.length() > 20) {
                    DataCenter.getInstance().parserGgxq();
                    DataCenter.getInstance().parserGgxqTime();
                    DataCenter.getInstance().parseMX();
                    // DataCenter.parseKLine(DataCenter.getInstance().mStrGghq);

                    Message muest = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("code", String.valueOf(code));
                    muest.setData(bundle);
                    muest.what = Globals.HANDLE_GGHQ;
                    if (isSend)
                        handler.sendMessage(muest);
                }


//				 DataCenter.getInstance().mStrGghq =
//				 DataCenter.getInstance().http
//				 .getGghq(code);
//				 if (DataCenter.getInstance().mStrGghq.length() > 20) {
//				 handler.sendEmptyMessage(com.weitou.stock.Globals.HANDLE_GGHQ);
//				 }
            }
        }.start();

    }

    /**
     * 生成自选股列表
     */
    // public void findSelf() {
    // JSONObject jsonData = null;
    // JSONObject jsonObject = null;
    // JSONArray jsonArray;
    // try {
    // if (DataCenter.getInstance().mStrList != null) {
    // jsonData = new JSONObject(DataCenter.getInstance().mStrList);
    // jsonObject = jsonData.getJSONObject("data");
    //
    // jsonArray = jsonObject.optJSONArray("codelist");
    // if (jsonArray != null) {
    // parserSelfList();
    // mSelfAdapter = new ListSelfAdapter(this, DataCenter.getInstance().list);
    // lvSelfList.setAdapter(mSelfAdapter);
    // lvSelfList.setOnItemClickListener(new OnItemClickListener() {
    // @Override
    // public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long
    // arg3) {
    // Log.i("SelfActivity", "===onItemClick id:"+arg2);
    // String code = DataCenter.getInstance().list.get(arg2).stock_id;
    // Log.i("SelfActivity", "===code:"+code);
    // runGghq(code);
    // }
    // });
    // }
    // }
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    // }


    public void findSelf() {
		JSONObject jsonData = null;
		JSONArray jsonArray;
		try {
			if (DataCenter.getInstance().mStrList != null) {
				jsonData = new JSONObject(DataCenter.getInstance().mStrList);
				jsonArray = jsonData.optJSONArray("resultData");
				if (jsonArray != null) {
					parserMyStock();
					setAdapter();
                    lvMyStockList.setMyOnItemOnClickListener(new MyOnItemOnClickListener() {

                        @Override
                        public void onMyItemClick(View view, int position) {
                            pd.show();
                            isSend = true;
                            String code = DataCenter.getInstance().listMyStock
                                    .get(position-1).code;
                            runGghq(code);
                        }
                    });

				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


    }




    private void parserMyStock() {
        ItemMyStock item;
		try {
			JSONObject jsondata = new JSONObject(
					DataCenter.getInstance().mStrList);
            Log.i("selfFragment","jsonData--"+jsondata);
//			JSONObject jsonObject = jsondata.getJSONObject("data");
			// 进行中的任务
			JSONArray array = jsondata.optJSONArray("resultData");
			ArrayList<ItemMyStock> listMyStock = new ArrayList<ItemMyStock>();
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					item = new ItemMyStock();
					item.name = array.getJSONObject(i).getString("name");
					item.code = array.getJSONObject(i).getString("zqdm");
					item.close = Parse.getInstance().parse2String(
							array.getJSONObject(i).getString("zjcj"));
//					item.change = Parse.getInstance().parse2String(
//							array.getJSONObject(i).getString("change"));
					item.changep = array.getJSONObject(i).getString("changep");
					listMyStock.add(item);
				}
			}
			DataCenter.getInstance().listMyStock = listMyStock;
		} catch (JSONException e) {
			e.printStackTrace();
		}

    }


    /**
     * 解析沪深行情
     */
    private void parserHshq() {
        ItemStock item;
//		try {
//			JSONObject jsondata = new JSONObject(
//					DataCenter.getInstance().mStrHshq);
//			JSONObject jsonObject = jsondata.getJSONObject("block");
//			// 热门板块
//			JSONArray array = jsonObject.optJSONArray("list");
//			if (array != null) {
//				DataCenter.getInstance().listHot.clear();
//				for (int i = 0; i < array.length(); i++) {
//					item = new ItemStock();
//					item.name = array.getJSONObject(i).getString("name");
//					item.code = array.getJSONObject(i).getString("code");
//					item.close = array.getJSONObject(i).getString("close");
//					item.change = array.getJSONObject(i).getString("change");
//					item.changep = array.getJSONObject(i).getString("changep");
//					item.cjl = array.getJSONObject(i).getString("cjl");
//					item.cje = array.getJSONObject(i).getString("cje");
//					item.market = array.getJSONObject(i).getString("market");
//
//					DataCenter.getInstance().listHot.add(item);
//					Log.i("SelfActivity", "=== hot item.name：" + item.name);
//				}
//			}
        String []name = {"传媒","机械设备","交通运输","商业运输","农林牧渔","招商轮船"};
        String []code = {"720000","640000","420000","450000","110000","601872"};
        String []close = {"7.880","9.090","23.000","23.680","17.320","11.080"};
        String []change = {"2.410","0.830","2.100","2.160","1.580","1.010"};
        String []changep = {"0.4406","0.1005","0.1005","0.1004","0.1004","0.1003"};
        String []cjl = {"25178500","11722900","1248171756","16650800","1116080","397200794"};;
        String []cje = {"1974826","1065612","14118833106","1971454","966526","42397200794"};
        String [] market = {"1","1","1","1","1","1"};
        for(int i =0; i< name.length; i++){
            item = new ItemStock();
            item.name = name[i];
            item.code = code[i];
            item.close= close[i];
            item.change = change[i];
            item.changep = changep[i];
            item.cjl = cjl[i];
            item.cje = cje[i];
            item.market = market[i];
            DataCenter.getInstance().listHot.add(item);

        }

        DataCenter.getInstance().listStockUp.clear();

        ItemStock itemUpHotm = new ItemStock();
        itemUpHotm.index = 2;
        itemUpHotm.item = 1;

        DataCenter.getInstance().listStockUp.add(itemUpHotm);

        ItemStock itemUpHot = new ItemStock();
        itemUpHot.index = 2;
        itemUpHot.item = 0;

        DataCenter.getInstance().listStockUp.add(itemUpHot);

        ItemStock itemUpHead = new ItemStock();
        itemUpHead.index = 1;
        itemUpHead.item = 1;
        DataCenter.getInstance().listStockUp.add(itemUpHead);
        // 涨幅榜
        String []nameZ = {"N有线","海伦哲","中海集运","茂业物流","湘潭电化"};
        String []codeZ = {"600959","300201","601866","000889","002125"};
        String []closeZ = {"7.880","9.090","11.500","11.840","15.460"};
        String []changeZ = {"2.410","0.830","1.050","1.080","1.410"};
        String []changepZ = {"0.4406","0.1005","0.1005","0.1004","0.1004"};
        String []cjlZ = {"251785","117229","624085878","83254","1133752"};;
        String []cjeZ = {"1974826","1065612","7059416553","985727","17527806"};
        String [] marketZ = {"1","1","1","1","1"};
        for(int i =0; i< nameZ.length; i++){
            item = new ItemStock();
            item.name = nameZ[i];
            item.code = codeZ[i];
            item.close= closeZ[i];
            item.change = changeZ[i];
            item.changep = changepZ[i];
            item.cjl = cjlZ[i];
            item.cje = cjeZ[i];
            item.market = marketZ[i];
            item.item = 0;
            item.index = 1;
            DataCenter.getInstance().listStockUp.add(item);
        }
//			jsonObject = jsondata.getJSONObject("stockup");
//			array = jsonObject.optJSONArray("list");
//			if (array != null && array.length() > 0) {
//				DataCenter.getInstance().listStockUp.clear();
//
//				ItemStock itemUpHotm = new ItemStock();
//				itemUpHotm.index = 2;
//				itemUpHotm.item = 1;
//
//				DataCenter.getInstance().listStockUp.add(itemUpHotm);
//
//				ItemStock itemUpHot = new ItemStock();
//				itemUpHot.index = 2;
//				itemUpHot.item = 0;
//
//				DataCenter.getInstance().listStockUp.add(itemUpHot);
//
//				ItemStock itemUpHead = new ItemStock();
//				itemUpHead.index = 1;
//				itemUpHead.item = 1;
//				DataCenter.getInstance().listStockUp.add(itemUpHead);
//				for (int i = 0; i < array.length(); i++) {
//					item = new ItemStock();
//					item.name = array.getJSONObject(i).getString("name");
//					item.code = array.getJSONObject(i).getString("code");
//					item.close = array.getJSONObject(i).getString("close");
//					item.change = array.getJSONObject(i).getString("change");
//					item.changep = array.getJSONObject(i).getString("changep");
//					item.cjl = array.getJSONObject(i).getString("cjl");
//					item.cje = array.getJSONObject(i).getString("cje");
//					item.market = array.getJSONObject(i).getString("market");
//					item.item = 0;
//					item.index = 1;
//					DataCenter.getInstance().listStockUp.add(item);
//					Log.i("SelfActivity", "=== stockup item.name：" + item.name);
//				}
//			}
        // 跌幅榜
        DataCenter.getInstance().listStockDown.clear();
        ItemStock itemDownHead = new ItemStock();
        itemDownHead.index = -1;
        itemDownHead.item = 1;
        DataCenter.getInstance().listStockUp.add(itemDownHead);
        String []nameD = {"深大通","中润资源","绿景控股","四环生物","英特集团"};
        String []codeD = {"000038","000506","000502","000518","002125"};
        String []closeD = {"0.000","0.000","0.000","0.000","0.000"};
        String []changeD = {"-20.410","-10.830","-6.950","-13.080","-5.410"};
        String []changepD = {"-2.4406","1.1205","-0.1000","-1.1004","-1.1004"};
        String []cjlD = {"0","0","0","0","0"};;
        String []cjeD = {"0","0","0","0","0"};
        String [] marketD = {"2","2","2","2","2"};
        for(int i =0; i< nameD.length; i++){
            item = new ItemStock();
            item.name = nameD[i];
            item.code = codeD[i];
            item.close= closeD[i];
            item.change = changeD[i];
            item.changep = changepD[i];
            item.cjl = cjlD[i];
            item.cje = cjeD[i];
            item.market = marketD[i];
            item.index = -1;
            item.item = 0;
            DataCenter.getInstance().listStockUp.add(item);
        }

//			jsonObject = jsondata.getJSONObject("stockdown");
//			array = jsonObject.optJSONArray("list");
//			if (array != null && array.length() > 0) {
//				DataCenter.getInstance().listStockDown.clear();
//				ItemStock itemDownHead = new ItemStock();
//				itemDownHead.index = -1;
//				itemDownHead.item = 1;
//				DataCenter.getInstance().listStockUp.add(itemDownHead);
//				for (int i = 0; i < array.length(); i++) {
//					item = new ItemStock();
//					item.name = array.getJSONObject(i).getString("name");
//					item.code = array.getJSONObject(i).getString("code");
//					item.close = array.getJSONObject(i).getString("close");
//					item.change = array.getJSONObject(i).getString("change");
//					item.changep = array.getJSONObject(i).getString("changep");
//					item.cjl = array.getJSONObject(i).getString("cjl");
//					item.cje = array.getJSONObject(i).getString("cje");
//					item.market = array.getJSONObject(i).getString("market");
//					item.index = -1;
//					item.item = 0;
//					// DataCenter.getInstance().listStockDown.add(item);
//					DataCenter.getInstance().listStockUp.add(item);
//					Log.i("SelfActivity", "=== stockdown item.name："
//							+ item.name);
//				}
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
    }
    public void getSelf(String uid) {
        OkGo.get(String.format(UtilHttp.URL_GET_SELF, uid))
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(s);
                            int resultcode = object.getInt("resultCode");
                            JSONArray resultData = object.optJSONArray("resultData");
                            if (resultcode == 100) {
                                if (resultData.length() != 0) {
                                    getSelf2(s);
                                } else {
                                    if(isFirst==false) {
                                        isFirst = true;
                                        ll_center.setVisibility(View.GONE);
                                        rl_center.setVisibility(View.GONE);
                                        rl_centers.setVisibility(View.VISIBLE);
                                    }
                                    handler.sendEmptyMessage(0x000);
                                }
                            }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }

                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }

                });

    }
    public void getSelf2(String data) {
        try {
            String stocklist="";
            String str = data;
            JSONObject jsob = new JSONObject(str == null ? "" : str);
            JSONArray jsonarray = jsob.optJSONArray("resultData");
            Log.i("test","resultData--"+jsonarray);
            if (jsonarray != null) {
                int length = jsonarray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsobs = jsonarray.optJSONObject(i);
                    String code = jsobs.optString("stockNo");
                    stocklist += code + ",";
                }
                stocklist = stocklist.substring(0, stocklist.length() - 1);
                Log.i("test","stocklist"+stocklist);
                getSelf2Date(stocklist);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void getSelf2Date(String stocklist){
        OkGo.get(String.format(UtilHttp.URL_GET_SELF2,
                stocklist))
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("test", "--onSuccess-" + s);
                        DataCenter.getInstance().mStrList = s;
                        JSONObject object = null;

                        try {
                            object = new JSONObject(s);
                            int resultcode = object.getInt("resultCode");
                            JSONArray resultData = object.optJSONArray("resultData");
                            if (resultcode == 100) {
                                if (resultData.length() != 0) {
                                    ll_center.setVisibility(View.VISIBLE);
                                    rl_center.setVisibility(View.VISIBLE);
                                    rl_centers.setVisibility(View.GONE);
                                    handler.sendEmptyMessage(Globals.HANDLE_GET_SELF);
                                }
                            } else {
                                handler.sendEmptyMessage(0x999);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
    /**
     * 设置listview适配器
     */
    private void setAdapter() {
        if (lvMyStockList.getAdapter() == null) {
            if (mSelfAdapter == null) {
                mSelfAdapter = new ListSelfAdapter(getActivity(),
                        DataCenter.getInstance().listMyStock,lvMyStockList);
                Log.i("SelfActivity", "mList=" +DataCenter.getInstance().listMyStock.size());
            } else {
                Log.i("SelfActivity", "mLists=" +DataCenter.getInstance().listMyStock.size());
                mSelfAdapter.setData(DataCenter.getInstance().listMyStock);
            }
            lvMyStockList.setAdapter(mSelfAdapter);
        } else {
            mSelfAdapter.setData(DataCenter.getInstance().listMyStock);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_my_stock;
    }

    @Override
    public void initParams(Bundle params) {
    }

    @Override
    public void initView(View view) {
        title = (TextView) view.findViewById(R.id.tv_title);
        title.setText("自选股");
    }

    @Override
    public void doBusiness(Context mContext) {
    }


}
