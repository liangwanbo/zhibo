package com.mytv365.zb.views.zhibolive;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.CurLiveInfo;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.model.User;
import com.mytv365.zb.presenters.LiveHelper;
import com.mytv365.zb.presenters.LocationHelper;
import com.mytv365.zb.presenters.UploadHelper;
import com.mytv365.zb.presenters.viewinface.LocationView;
import com.mytv365.zb.presenters.viewinface.UploadView;
import com.mytv365.zb.utils.Constants;
import com.mytv365.zb.utils.LogConstants;
import com.mytv365.zb.utils.SxbLog;
import com.mytv365.zb.utils.UIUtils;
import com.mytv365.zb.views.LiveActivity;
import com.mytv365.zb.views.zhibolive.livebase.BaseActivity;
import com.mytv365.zb.widget.ActionSheetDialog;
import com.mytv365.zb.widget.AlertDialog;
import com.mytv365.zb.widget.LoadingDialog;
import com.mytv365.zb.widget.MyDialogs;
import com.mytv365.zb.widget.mydialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMGroupManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/8/25.
 *
 * @liangwwanbo
 * @主播界面
 */
public class zhuboActivity extends BaseActivity implements View.OnClickListener, UploadView, LocationView {

    private ImageView image_cha, image_add_headface, pinlun, mic, xiangji, gift, share_pt;
    private EditText edit_title;
    private LinearLayout layout_dl;
    private RelativeLayout Relative_headface, Relative_state, Relat_zb_l, Relate_top, reaktive_meili, Relative_title;
    private Button bnt_zb, bnt_zb_kb;
    private ListView listviews;
    //private PinLunAdepter adepter;
    // private PinLunPesor pesor;
    //private List<PinLunPesor> list=new ArrayList<>();
    private static final int CAPTURE_IMAGE_CAMERA = 100;
    private static final int IMAGE_STORE = 200;
    private static final int CROP_CHOOSE = 10;
    private boolean bPermission = false;
    private Uri fileUri, cropUri;
    private static final String TAG = zhuboActivity.class.getSimpleName();
    private boolean bUploading = false;
    private UploadHelper mPublishLivePresenter;
    private LocationHelper mLocationHelper;
    private LiveHelper mLiveHelper;
    private boolean shanchang;
    private String path;
    private ImageView backs;
    private int uploadPercent = 0;
    private File outputImage;
    private String type;
    private String isfirst;
    private TextView yues;
    private MyBroadcastReciver Myreciver;
    private boolean iszhubo = false;
    private boolean isjianji = false;
    private User user;
    private long timelong;
    private ImageView imageback;
    private TextView meili_number, zhubo_name, zhubo_number, live_background_notice;
    private boolean isbackground = false;
    private boolean  istitle=false;
    private MyDialogs dialogs;
    private mydialog mydialog;
    private LoadingDialog dialog;
    private boolean ischoosepic=false;



    private Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        ;
    };


    @Override
    public int getLayout() {
        return R.layout.zhubo_ac_layout;
    }

    @Override
    public void getinit() {
        SharedPreferences shas = getSharedPreferences("XIANZHI", 1);
        isfirst = shas.getString("type", "");
        if (!TextUtils.isEmpty(isfirst)) {
            iszhubo = true;
        }

        SharedPreferences sha = getSharedPreferences("TOKEN", 0);
        isfirst = sha.getString("type", "");
        if (isfirst.equals("主播")) {
            iszhubo = true;
        }

        quanxian();
        init();
        // ShowDialog();

        bPermission = checkPublishPermission();
        mPublishLivePresenter.updateSig();

    }


    public void quanxian() {

        Myreciver = new MyBroadcastReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yanhuang.xiuchang.xiuchang.zhuboroom");
        zhuboActivity.this.registerReceiver(Myreciver, intentFilter);

    }


    private class MyBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.yanhuang.xiuchang.xiuchang.zhuboroom")) {
                type = intent.getStringExtra("iszhubo");
                if (!TextUtils.isEmpty(type)) {
                    iszhubo = true;
                }
                Toast.makeText(zhuboActivity.this, type, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public int getcolor() {
        return R.color.quantouming;
    }


    public void init() {
        String indicator = "";
        mLocationHelper = new LocationHelper(this);
        backs = (ImageView) this.findViewById(R.id.backs);
        live_background_notice = (TextView) findViewById(R.id.live_background_notice);
        backs.setOnClickListener(this);
        mPublishLivePresenter = new UploadHelper(this, this);

        // share_pt=(ImageView)this.findViewById(R.id.share_pt);

        Relative_title = (RelativeLayout) this.findViewById(R.id.Relative_title);
        edit_title = (EditText) this.findViewById(R.id.edit_title);

        // imageback=(ImageView)this.findViewById(R.id.imageback);

        Resources r = this.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(r, R.drawable.errou);
        //imageback.setImageBitmap(Blur.apply(bitmap));

//        bnt_zb=(Button)this.findViewById(R.id.bnt_zb);
        bnt_zb_kb = (Button) this.findViewById(R.id.bnt_zb_kb);
        bnt_zb_kb.setVisibility(View.VISIBLE);
        image_add_headface = (ImageView) this.findViewById(R.id.image_add_headface);
        Relative_headface = (RelativeLayout) this.findViewById(R.id.Relative_headface);
        Relat_zb_l = (RelativeLayout) this.findViewById(R.id.Relat_zb_l);
        image_add_headface.setOnClickListener(this);
        bnt_zb_kb.setOnClickListener(this);
        mydialog= new mydialog(zhuboActivity.this);

        //MyDialogs dialogs=new MyDialogs(zhuboActivity.this);

        /*dialog = new LoadingDialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);*/
       // dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
        edit_title.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Log.d("null", "afterTextChanged");
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                Log.d("null", "beforeTextChanged:" + s + "-" + start + "-" + count + "-" + after);
                if (s.length()==0&&count==0) {
                    bnt_zb_kb.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_btn));
                } else {
                    istitle=true;
                    if (isbackground==true)
                        bnt_zb_kb.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_btn_pressed));
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                Log.d("null", "onTextChanged:" + s + "-" + "-" + start + "-" + before + "-" + count);
                if (s.length()==0&&count==0){
                    bnt_zb_kb.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_btn));
                }else{
                    istitle=true;
                    if (isbackground==true)
                        bnt_zb_kb.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_btn_pressed));
                }
            }
        });

//        Log.i(TAG, "onResponse:  获取手机的图片地址是："+Constant.CopyURI);
//        if (Constant.CopyURI != null) {
//            isbackground=true;
//            isjianji=true;
//            image_add_headface.setImageURI(Constant.CopyURI);
//        }

    }


    public void ShowDialog() {
        new AlertDialog(zhuboActivity.this).builder().setTitle("请您开播时遵守")
                .setMsg(null)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhuboActivity.this.finish();

            }
        }).setTextlistener("管理条例协议", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(zhuboActivity.this, "tiaozhuang", Toast.LENGTH_LONG).show();
            }
        }).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_add_headface:
                getImages();
                break;
//            case R.id.image_mic:
//                //开麦
//                break;
//            case R.id.image_camera:
//                //相机
//                break;
//            case R.id.image_shares:
//                //分享
//                break;

            case R.id.bnt_zb_kb:
                //开始直播
                if (TextUtils.isEmpty(edit_title.getText().toString())) {
                    Toast.makeText(zhuboActivity.this, "请输入直播主题", Toast.LENGTH_LONG).show();
                } else {
//                        if (isbackground){

                    if (isjianji==true){
                        mydialog.show();

                        if (shanchang == true) {
                            getMyRoomImage();
                        } else if (shanchang == false) {
                            getMyRooms();
                        }
                    }else {
                        Toast.makeText(zhuboActivity.this, "请添加封面和主题", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.backs:
                zhuboActivity.this.finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void Dismiss() {
        mHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onDestroy() {
        mPublishLivePresenter.onDestory();
        //mLocationHelper.onDestory();
        unregisterReceiver(Myreciver);
        super.onDestroy();
    }


    /**
     * 图片选择对话框
     */
    public void getImages() {
        new ActionSheetDialog(this).builder().setTitle("请选择图片来源")
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        getPicFrom(IMAGE_STORE);
                        shanchang = true;

                    }
                })
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        getPicFrom(CAPTURE_IMAGE_CAMERA);
                        shanchang = false;
                    }
                }).show();
    }


    /**
     * 获取图片资源
     *
     * @param type
     */

    private void getPicFrom(int type) {
        if (!bPermission) {
            Toast.makeText(this, "权限不足", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (type) {
            case CAPTURE_IMAGE_CAMERA:
                timelong = System.currentTimeMillis();
                String paizhao = String.valueOf(timelong);
                fileUri = createCoverUri(paizhao);
                Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent_photo, CAPTURE_IMAGE_CAMERA);
                break;
            case IMAGE_STORE:
                timelong = System.currentTimeMillis();
                String xiangce = String.valueOf(timelong);
                fileUri = createCoverUri(xiangce);
                Intent intent_album = new Intent("android.intent.action.GET_CONTENT");
                intent_album.setType("image/*");
                startActivityForResult(intent_album, IMAGE_STORE);
                break;
        }
    }


    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(zhuboActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(zhuboActivity.this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(zhuboActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(zhuboActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        Constants.WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }


    private Uri createCoverUri(String type) {
        String filename = Constant.getUser().getId() + type + ".jpg";
        outputImage = new File(Environment.getExternalStorageDirectory(), filename);
        if (ContextCompat.checkSelfPermission(zhuboActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(zhuboActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_PERMISSION_REQ_CODE);
            return null;
        }
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(outputImage);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAPTURE_IMAGE_CAMERA:
                    startPhotoZoom(fileUri);
                    break;
                case IMAGE_STORE:
                    path = UIUtils.getPath(this, data.getData());
                    if (null != path) {
                        SxbLog.d(TAG, "startPhotoZoom->path:" + path);
//                        Toast.makeText(zhuboActivity.this,path,Toast.LENGTH_LONG).show();
                        Log.e("lujing", path);
                        File file = new File(path);
                        startPhotoZoom(Uri.fromFile(file));
                    }
                    break;
                case CROP_CHOOSE:

                    isbackground = true;
                    isjianji=true;
                    Log.i(TAG, "编辑完成后的返回图片地址" + cropUri.toString());

//                    //赋值图片 阳
//                    Constant.CopyURI=cropUri;

                    image_add_headface.setImageURI(Constant.CopyURI);
                    Log.i(TAG, "编辑完成后的返回图片地址设置完成" );

                    ischoosepic=true;

                    live_background_notice.setText("更换封面");
                    live_background_notice.setTextSize((float) 20.0);
                    live_background_notice.setGravity(Gravity.CENTER);
                    bUploading = true;
                    Relative_title.setVisibility(View.VISIBLE);
                    bnt_zb_kb.setVisibility(View.VISIBLE);
                    //mPublishLivePresenter.uploadCover(cropUri.getPath());
                    // SheZhizhibo();
                    if (isjianji&&istitle){
                        bnt_zb_kb.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_btn_pressed));
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (cropUri != null) {
            image_add_headface.setImageURI(cropUri);
        }

    }

    public void startPhotoZoom(Uri uri) {
        cropUri = createCoverUri("_crop");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 500);
        intent.putExtra("aspectY", 309);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 309);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_CHOOSE);
    }

    @Override
    public void onUploadProcess(int percent) {
        uploadPercent = percent;
    }

    @Override
    public void onUploadResult(int code, String url) {

        if (0 == code) {
            CurLiveInfo.setCoverurl(url);
            Toast.makeText(this, "上传封面成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "上传封面失败", Toast.LENGTH_SHORT).show();
        }
        bUploading = false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.LOCATION_PERMISSION_REQ_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!mLocationHelper.getMyLocation(getApplicationContext(), this)) {

                    }
                }
                break;
            case Constants.WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                break;
            default:
                break;
        }

    }

    @Override
    public void onLocationChanged(int code, double lat1, double long1, String location) {
    }

    public void SheZhizhibo() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("liveUrl", Constant.getUser().getId());
//        //map.put("toKen", Constant.getUser().getToKen());
//        map.put("roomName",edit_title.getText().toString());
        OkGo.post(HttpUrl.statrtlive)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("liveUrl", Constant.getUser().getId())
                .params("roomName", edit_title.getText().toString())
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json = null;
                            json = new JSONObject(s);
                            if (json.getString("resultCode").equals("100")) {
//                                Toast.makeText(zhuboActivity.this, "开播成功", Toast.LENGTH_SHORT).show();
                                Intent intents = new Intent(zhuboActivity.this, LiveActivity.class);
                                intents.putExtra(Constants.ID_STATUS, Constants.HOST);
                                MySelfInfo.getInstance().setIdStatus(Constants.HOST);
                                MySelfInfo.getInstance().setJoinRoomWay(true);
                                CurLiveInfo.setHostName(Constant.getUser().getNickName());
                                CurLiveInfo.setTitle(edit_title.getText().toString());
                                CurLiveInfo.setHostID(Constant.getUser().getId());
//                        CurLiveInfo.setRoomNum(MySelfInfo.getInstance().getMyRoomNum());
                                CurLiveInfo.setRoomNum(Integer.valueOf(Constant.getUser().getId()));
                                CurLiveInfo.setTitles(edit_title.getText().toString());
                                CurLiveInfo.setImageUrl(CurLiveInfo.getImageUrl());
                                CurLiveInfo.setTexts("");
                                String roomName=edit_title.getText().toString();
                                intents.putExtra("roomName",roomName);
                                startActivity(intents);
                               // Dismiss();
                                mydialog.dismiss();
                                //dialogs.builder().Dismiss();
                                zhuboActivity.this.finish();
                            } else {
                                mydialog.dismiss();
                              // Dismiss();
                               // dialogs.builder().Dismiss();

                                Toast.makeText(zhuboActivity.this, "开播失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Dismiss();
                        super.onError(call, response, e);
                    }
                });


//        OkHttpClientManager.postAsyn(HttpUrl.statrtlive, new OkHttpClientManager.StringCallback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Dismiss();
//                Log.e(TAG+"error,error",request.body().toString()+e.getMessage());
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.e("TAGTAGTAGTAGTAGs",response);
//                try {
//                    JSONObject json= null;
//                        json = new JSONObject(response);
//                    if(json.getString("resultCode").equals("100")) {
//                        Toast.makeText(zhuboActivity.this,"开播成功",Toast.LENGTH_SHORT).show();
//                        Intent intents = new Intent(zhuboActivity.this, LiveActivity.class);
//                        intents.putExtra(Constants.ID_STATUS, Constants.HOST);
//                        MySelfInfo.getInstance().setIdStatus(Constants.HOST);
//                        MySelfInfo.getInstance().setJoinRoomWay(true);
//                        CurLiveInfo.setHostName(Constant.getUser().getNickName());
//                        CurLiveInfo.setTitle(edit_title.getText().toString());
//                        CurLiveInfo.setHostID(Constant.getUser().getId());
////                        CurLiveInfo.setRoomNum(MySelfInfo.getInstance().getMyRoomNum());
//                        CurLiveInfo.setRoomNum(Integer.valueOf(Constant.getUser().getId()) );
//                        startActivity(intents);
//                        Dismiss();
//                        zhuboActivity.this.finish();
//                    }else{
//                        Dismiss();
//                        Toast.makeText(zhuboActivity.this,"开播失败",Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },map,"");


//        OkGo.post(HttpUrl.statrtlive)
//                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
//                .headers("header1", "headerValue1")
//                .params("liveUrl",Constant.getUser().getId())
//                .params("roomName",edit_title.getText().toString())
//                .setCertificates()
//                .execute(new StringDialogCallback(this) {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        try {
//                            JSONObject json=new JSONObject(s);
//
//                            if(json.getString("resultCode").equals("100")) {
//                                Intent intents = new Intent(zhuboActivity.this, LiveActivity.class);
//                                intents.putExtra(Constants.ID_STATUS, Constants.HOST);
//                                MySelfInfo.getInstance().setIdStatus(Constants.HOST);
//                                MySelfInfo.getInstance().setJoinRoomWay(true);
//                                CurLiveInfo.setHostName(Constant.getUser().getNickName());
//                                CurLiveInfo.setTitle(edit_title.getText().toString());
//                                CurLiveInfo.setHostID(Constant.getUser().getId());
////                        CurLiveInfo.setRoomNum(MySelfInfo.getInstance().getMyRoomNum());
//                                CurLiveInfo.setRoomNum(Integer.valueOf(Constant.getUser().getId()) );
//                                startActivity(intents);
//                                zhuboActivity.this.finish();
//
//                            }else{
//                                Toast.makeText(zhuboActivity.this,"你未登录，请登录",Toast.LENGTH_LONG).show();
//                                Intent inten=new Intent(zhuboActivity.this, LoginActivity.class);
//                                startActivity(inten);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                    }
//                });

    }


    //Uri uri;

    /**
     * 相册
     */
    public void getMyRoomImage() {

        String paths=cropUri.getPath();
        File fileData = new File(paths);
        OkGo.post(HttpUrl.updateImage)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("fileData", fileData)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        CurLiveInfo.setImageUrl(s);
                        Log.i(TAG, "onError: 相册图片上传成功 response   "+response+" 返回信息  "+s);
                        SharedPreferences share = getSharedPreferences("photo", 0);
                        SharedPreferences.Editor edit = share.edit();
                        edit.putString("url", s);
                        edit.commit();
                        Log.i("photo", s);
                        if (!TextUtils.isEmpty(s)) {
//                            kaibo = true;
                            deleteguroid();
                            SheZhizhibo();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Log.i(TAG, "onError: 相册图片上传失败 response   "+response+" exeception  "+e);
                        super.onError(call, response, e);
                    }
                });

       /*try {
           OkHttpClientManager.postAsyn(HttpUrl.updateImage,new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
//                    Toast.makeText(zhuboActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e("msgttttt",e.getMessage());
                }

                @Override
                public void onResponse(String response) {

                    SharedPreferences share=getSharedPreferences("photo",0);
                    SharedPreferences.Editor edit=share.edit();
                    edit.putString("url",response);
                    edit.commit();
                    Log.i("photo",response);
                    if(!TextUtils.isEmpty(response)){
                        kaibo=true;
                        SheZhizhibo();
                    }
                }
            },fileData,"fileData","fileData");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    public void getMyRooms() {
        String paths;
//        if (!cropUri.equals("")){

        paths = cropUri.getPath();

//        }else {
//            paths=Constant.CopyURI.getPath();
//        }

        Log.e("paizhao", paths);
        File fileData = new File(paths);
        OkGo.post(HttpUrl.updateImage)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("fileData", fileData)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        CurLiveInfo.setImageUrl(s);
                        Log.e("photoppp", s);
                        SharedPreferences share = getSharedPreferences("photo", 0);
                        SharedPreferences.Editor edit = share.edit();
                        edit.putString("url", s);
                        edit.commit();
                        if (!TextUtils.isEmpty(s)) {
//                            kaibo = true;
                            if (Constant.getUser().getRoleId().equals("2")) {

                                deleteguroid();
                                SheZhizhibo();
                            } else {
                                Toast.makeText(zhuboActivity.this, "你不是老师，无法开播", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });



        /*try {
            OkHttpClientManager.postAsyn(HttpUrl.updateImage, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(String response) {
                    Log.e("photoppp",response);
                    SharedPreferences share=getSharedPreferences("photo",0);
                    SharedPreferences.Editor edit=share.edit();
                    edit.putString("url",response);
                    edit.commit();
                    if(!TextUtils.isEmpty(response)){
                        kaibo=true;
                        if(Constant.getUser().getRoleId().equals("2")){
                            SheZhizhibo();
                        }else{
                            Toast.makeText(zhuboActivity.this, "你不是老师，无法开播", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            },fileData,"fileData","fileData");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }



    public void deleteguroid(){

        TIMGroupManager.getInstance().deleteGroup("" + Constant.getUser().getId(), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                SxbLog.standardQuiteRoomLog(TAG, "delete im room", "" + LogConstants.STATUS.FAILED, "code:" + i + " msg:" + s);
            }

            @Override
            public void onSuccess() {
                SxbLog.standardQuiteRoomLog(TAG, "delete im room", "" + LogConstants.STATUS.SUCCEED, "room id " + CurLiveInfo.getRoomNum());
                //isInChatRoom = false;
            }
        });
    }
}
