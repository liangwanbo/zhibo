package com.mytv365.zb.views.activity.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.common.BaseActivity;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.utils.Constants;
import com.mytv365.zb.utils.GlideUtils;
import com.mytv365.zb.utils.UIUtils;
import com.mytv365.zb.wheelview.ChangeAddressDialog;
import com.mytv365.zb.wheelview.ChangeBirthDialog;
import com.mytv365.zb.widget.ActionSheetDialog;
import com.mytv365.zb.widget.CircleImageView;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 用户资料编辑
 * Created by yangzhi on 16/10/8.
 */
public class UserEditInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView title,riqi,gender_adress;
    /**
     * 用户头像
     */
    private CircleImageView persontal_user_head_image;
    /**
     * 用户名称
     */
    private RelativeLayout persontal_user_name;

    /**
     * 用户签名
     */
    private RelativeLayout persontal_qianming;

    private String textriqi;

    /**
     * 性别
     */
    private RelativeLayout persontal_sex,persontal_birdestry,persontal_adress,persontal_indestorc;
    private static final int IMAGE_STORE = 200;
    private static final int CROP_CHOOSE = 10;
    private Uri fileUri, cropUri;
    private static final int CAPTURE_IMAGE_CAMERA = 100;
    private File outputImage;
    private Context mcontext;
    private boolean shanchang;
    private long timelong;
    private boolean bUploading = false;
    public static final int GERENJIANJIE=5;
    public static final int USERCHANGENAME=2;
    public static final int USERCHANGEQING=3;
    public static final int USERCHANGESEX=4;
    private String userchangename;
    private String userchangeqing;
    private String userchangesex;
    private RelativeLayout relat_back;
    private boolean Imageselete;
    private Button bnt_sure;
    private boolean chouse;
    private TextView gender_text,myname,mysing,gender_indestor;

    private boolean isgaibian=false;

    /**
     * 文件路径
     */
    private String path;

    private  boolean isload=false;

    private long timeStemp;


    @Override
    public int getLayout() {
        return R.layout.activity_user_edit_info;
    }

    @Override
    public int getcolor() {
        return R.color.touming;
    }

    @Override
    public void getinit() {

        title = (TextView) findViewById(R.id.tv_titles);
        title.setText("资料编辑");
        persontal_user_head_image = (CircleImageView) findViewById(R.id.persontal_user_head_image);
        GlideUtils.loadImage(this, Constant.getUser().getHeadImages(),persontal_user_head_image);
        persontal_user_name = (RelativeLayout) findViewById(R.id.persontal_user_name);
        persontal_qianming = (RelativeLayout) findViewById(R.id.persontal_qianming);
        persontal_sex = (RelativeLayout) findViewById(R.id.persontal_sex);
        persontal_birdestry = (RelativeLayout) findViewById(R.id.persontal_birdestry);
        persontal_adress = (RelativeLayout) findViewById(R.id.persontal_adress);
        persontal_indestorc = (RelativeLayout) findViewById(R.id.persontal_indestorc);
        myname=(TextView)this.findViewById(R.id.myname) ;
        riqi=(TextView)this.findViewById(R.id.riqi);
        riqi.setText(Constant.getUser().getDate());
        myname.setText(Constant.getUser().getNickName());
        mysing=(TextView)this.findViewById(R.id.mysing) ;
        mysing.setText(Constant.getUser().getSign());
        gender_adress=(TextView)findViewById(R.id.gender_adress) ;
        gender_adress.setText(Constant.getUser().getAddress());
        gender_text=(TextView)this.findViewById(R.id.gender_text) ;
        gender_text.setText(Constant.getUser().getSex());
        persontal_user_head_image.setOnClickListener(this);
        persontal_user_name.setOnClickListener(this);
        persontal_qianming.setOnClickListener(this);
        persontal_sex.setOnClickListener(this);
        persontal_birdestry.setOnClickListener(this);
        persontal_adress.setOnClickListener(this);
        persontal_indestorc.setOnClickListener(this);
        relat_back=(RelativeLayout)this.findViewById(R.id.relat_back);
        relat_back.setOnClickListener(this);
        bnt_sure=(Button)this.findViewById(R.id.bnt_sure);
        bnt_sure.setOnClickListener(this);
        gender_indestor=(TextView)findViewById(R.id.gender_indestor);
        gender_indestor.setText(Constant.getUser().getIntroduction());

    }



    public void setname(){

        TIMFriendshipManager.getInstance().setNickName(Constant.getUser().getNickName(), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess() {

            }
        });


        TIMFriendshipManager.getInstance().setFaceUrl(Constant.getUser().getHeadImages(), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess() {

            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bnt_sure:

                if(isgaibian==true){
                    if(chouse==true){
                        if(shanchang==true){
                            getMyRoomImage();
                        }else{
                            getMyRooms();
                        }
                        uploadex();
                    }else{
                        uploadex();
                    }
                }else{
                    Toast.makeText(UserEditInfoActivity.this,"你未做任何修改",Toast.LENGTH_LONG).show();
                    UserEditInfoActivity.this.finish();
                }

                break;

            case R.id.persontal_birdestry:
                ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(
                        UserEditInfoActivity.this);
                mChangeBirthDialog.setDate(2016,11,8);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {
                        String data=year + "-" + month + "-" + day;

                        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date=simpleDateFormat .parse(data);
                             timeStemp = date.getTime();
                           // Toast.makeText(UserEditInfoActivity.this,timeStemp+"",Toast.LENGTH_SHORT).show();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        textriqi=year+month+day;
                        isgaibian=true;
                        Log.e("ceshiriqi",timeStemp+"");

                        riqi.setText(data);

                    }
                });

                break;

            case R.id.persontal_adress:
                ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(
                        UserEditInfoActivity.this);
                mChangeAddressDialog.setAddress("上海", "上海");
                mChangeAddressDialog.show();
                mChangeAddressDialog.setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city) {
                        gender_adress.setText(province + "-" + city);
                        isgaibian=true;
                    }
                });


                break;

            case R.id.persontal_indestorc:
                SharedPreferences userInfof =getSharedPreferences("content", 1);
                SharedPreferences.Editor edittb=userInfof.edit();
                edittb.putString("content",gender_indestor.getText().toString());
                edittb.commit();

                startActivityForResult(new Intent(UserEditInfoActivity.this,Userjianjie.class),GERENJIANJIE);

                break;


            case R.id.relat_back:
                UserEditInfoActivity.this.finish();
                break;

            case R.id.persontal_user_name:

                SharedPreferences userInfo =getSharedPreferences("content", 1);
                SharedPreferences.Editor edit=userInfo.edit();
                edit.putString("content",myname.getText().toString());
                edit.commit();

                startActivityForResult(new Intent(UserEditInfoActivity.this,PersontalDeaultName.class),USERCHANGENAME);
                break;
            case R.id.persontal_qianming:

                SharedPreferences userInfoc =getSharedPreferences("content", 1);
                SharedPreferences.Editor editt=userInfoc.edit();
                editt.putString("content",mysing.getText().toString());
                editt.commit();
                startActivityForResult(new Intent(UserEditInfoActivity.this,PersontalDeaultStyleDes.class),USERCHANGEQING);
                break;
            case R.id.persontal_sex:
                new ActionSheetDialog(UserEditInfoActivity.this).builder().
                        setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .setTitle("性别")
                        .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                gender_text.setText("男");
                                isgaibian=true;
                                //codeValue="1005001";
                              /*  SharedPreferences userInfo =getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = userInfo.edit();
                                editor.putString("codeValue",codeValue);
                                editor.commit();*/

                            }
                        })
                        .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                gender_text.setText("女");
                                isgaibian=true;
                               //codeValue="1005002";
                               /* SharedPreferences userInfo =getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = userInfo.edit();
                                editor.putString("codeValue",codeValue);
                                editor.commit();*/

                            }
                        }).show();

                break;

            case R.id.persontal_user_head_image:
                new ActionSheetDialog(UserEditInfoActivity.this).builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .setTitle("请选择图片来源")
                        .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                getPicFrom(IMAGE_STORE);
                                chouse=true;
                                shanchang = true;
                                isgaibian=true;

                            }
                        })
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                getPicFrom(CAPTURE_IMAGE_CAMERA);
                                chouse=true;
                                shanchang = false;
                                isgaibian=true;

                            }
                        }).show();

                break;

        }
    }


    private void getPicFrom(int type) {
        switch (type) {
            case CAPTURE_IMAGE_CAMERA:
                timelong=System.currentTimeMillis();
                String paizhao=String.valueOf(timelong);
                fileUri = createCoverUri(paizhao);
                Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent_photo, CAPTURE_IMAGE_CAMERA);
                break;
            case IMAGE_STORE:
                timelong=System.currentTimeMillis();
                String xiangce=String.valueOf(timelong);
                fileUri = createCoverUri(xiangce);
                Intent intent_album = new Intent("android.intent.action.GET_CONTENT");
                intent_album.setType("image/*");
                startActivityForResult(intent_album, IMAGE_STORE);
                break;
        }

    }



    private Uri createCoverUri(String type) {
        String filename = Constant.getUser().getId() + type + ".jpg";
        outputImage = new File(Environment.getExternalStorageDirectory(), filename);
        if (ContextCompat.checkSelfPermission(UserEditInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UserEditInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_PERMISSION_REQ_CODE);
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
                    //拍照
                    startPhotoZoom(fileUri);
                    break;
                case IMAGE_STORE:
                    //相册
                    path = UIUtils.getPath(this, data.getData());
                    if (null != path) {
                        File file = new File(path);
                        startPhotoZoom(Uri.fromFile(file));
                    }
                    break;

                case CROP_CHOOSE:
                    isload=true;

                    String paths = cropUri.getPath();
                    File fileData1 = new File(paths);
                    try {
                        InputStream ins = new FileInputStream(fileData1);
                        Bitmap bitmap = BitmapFactory.decodeStream(ins);
                        persontal_user_head_image.setImageBitmap(bitmap);
                        isgaibian=true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

//                    if (shanchang == true) {
//                        File fileData = new File(UIUtils.getPath(this, data.getData()));
//                        try {
//                            InputStream ins = new FileInputStream(fileData);
//                            Bitmap bitmap = BitmapFactory.decodeStream(ins);
//                            persontal_user_head_image.setImageBitmap(bitmap);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//                        bUploading = true;
//                    } else if (shanchang == false) {
//                    }
                   // getMyRoomImage();
                    break;

            }
        }

        switch (resultCode){
            case USERCHANGENAME:
                userchangename=data.getStringExtra("persontal_name");
                myname.setText(userchangename);
                isgaibian=true;
                break;
            case USERCHANGEQING:
                userchangeqing=data.getStringExtra("persontal_style");
                mysing.setText(userchangeqing);
                isgaibian=true;
                break;
            case USERCHANGESEX:
                userchangesex=data.getStringExtra("persontal_sex");
                gender_text.setText(userchangesex);
                isgaibian=true;
                break;

            case GERENJIANJIE:
                String contnts=data.getStringExtra("persontal_indection");
                gender_indestor.setText(contnts);
                isgaibian=true;
                break;



        }
    }


    /**
     * 返回的剪辑方法
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Log.i("tag", "startPhotoZoom: 剪辑照片");
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


    public void getMyRoomImage(){
        Log.i("tag", "onBackPressed: 点击相册上传");
        String paths=cropUri.getPath();
        Log.e("paizhao",paths);
        File fileData=new File(paths);


        OkGo.post(HttpUrl.userSendHeadfaceUrl)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("fileData",fileData)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String sb=jsonObject.getString("resultCode");
                            String userheadimg=jsonObject.getString("headImageUrl");
                            Constant.user.setHeadImages(userheadimg);
                            if (sb.equals("100")){

                                TIMFriendshipManager.getInstance().setFaceUrl(Constant.getUser().getHeadImages(), new TIMCallBack() {
                                    @Override
                                    public void onError(int i, String s) {
                                    }

                                    @Override
                                    public void onSuccess() {

                                    }

                                });
                                Toast.makeText(UserEditInfoActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();

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
        /*try {
            OkHttpClientManager.postAsyn(HttpUrl.userSendHeadfaceUrl,new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.i("tag", "onBackPressed: 点击相册上传失败");
                }

                @Override
                public void onResponse(String response) {
                    Log.i("tag", "onBackPressed: 点击相册上传成功方法");
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String s=jsonObject.getString("resultCode");
                        String userheadimg=jsonObject.getString("headImageUrl");
                        Constant.user.setHeadImages(userheadimg);
                        if (s.equals("100")){
                            Toast.makeText(UserEditInfoActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();

                            TIMFriendshipManager.getInstance().setFaceUrl(Constant.getUser().getHeadImages(), new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                }

                                @Override
                                public void onSuccess() {

                                }

                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },fileData,"fileData","fileData");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



    public void getMyRooms(){
        String paths=cropUri.getPath();
        File fileData=new File(paths);
        Log.i("tag", "onBackPressed: 点击拍照上传");
        Log.e("paizhao",paths);

        OkGo.post(HttpUrl.userSendHeadfaceUrl)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("fileData",fileData)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String sc=jsonObject.getString("resultCode");
                            String userheadimg=jsonObject.getString("headImageUrl");
                            Constant.user.setHeadImages(userheadimg);
                            if (sc.equals("100")){

                                TIMFriendshipManager.getInstance().setFaceUrl(Constant.getUser().getHeadImages(), new TIMCallBack() {
                                    @Override
                                    public void onError(int i, String s) {
                                    }

                                    @Override
                                    public void onSuccess() {

                                    }

                                });
                                Toast.makeText(UserEditInfoActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();

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




       /* try {
            OkHttpClientManager.postAsyn(HttpUrl.userSendHeadfaceUrl, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.i("tag", "onBackPressed: 点击拍照上传失败");
                }

                @Override
                public void onResponse(String response) {
                    Log.i("tag", "onBackPressed: 点击拍照上传成功方法");
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String s=jsonObject.getString("resultCode");
                        String userheadimg=jsonObject.getString("headImageUrl");
                        Constant.user.setHeadImages(userheadimg);
                        if (s.equals("100")){

                            TIMFriendshipManager.getInstance().setFaceUrl(Constant.getUser().getHeadImages(), new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                }

                                @Override
                                public void onSuccess() {

                                }

                            });
                            Toast.makeText(UserEditInfoActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },fileData,"fileData","fileData");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



    public void setInfo(){
        SharedPreferences userInfo =getSharedPreferences("MySign", 1);
        String context=userInfo.getString("content","");
        mysing.setText(context);
        SharedPreferences userInfos =getSharedPreferences("Myname", 1);
        String Myname=userInfos.getString("content","");
        myname.setText(Myname);




    }

    public void uploadex(){
        String s;
       int tis= (int)System.currentTimeMillis();
        Map<String,String> map=new HashMap<>();
        map.put("nickName",myname.getText().toString());
        map.put("sex",gender_text.getText().toString());
        map.put("address","");
        map.put("introduction",mysing.getText().toString());
        map.put("birthDate",timeStemp+"");

        if(TextUtils.isEmpty(riqi.getText().toString())){
            s=tis+"";
        }else{
            String b=riqi.getText().toString();
            s=b.replace("-","");
            Log.e("sb",s);
        }
        OkGo.post(HttpUrl.bianjiinfo)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("nickName",myname.getText().toString())
                .params("sex",gender_text.getText().toString())
                .params("sign",mysing.getText().toString())
                .params("address",gender_adress.getText().toString())
                .params("introduction",gender_indestor.getText().toString())
                .params("birthDate",timeStemp)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json=new JSONObject(s);
                            if(json.getString("resultCode").equals("100")){
                                Toast.makeText(UserEditInfoActivity.this, json.getString("resultMessage"), Toast.LENGTH_SHORT).show();
                                Constant.getUser().setNickName(myname.getText().toString());
                                Constant.getUser().setSex(gender_text.getText().toString());
                                Constant.getUser().setIntroduction(gender_indestor.getText().toString());
                                Constant.getUser().setDate(riqi.getText().toString());
                                Constant.getUser().setAddress(gender_adress.getText().toString());
                                Constant.getUser().setSign(mysing.getText().toString());

                                TIMFriendshipManager.getInstance().setNickName(Constant.getUser().getNickName(), new TIMCallBack() {
                                    @Override
                                    public void onError(int i, String s) {
                                    }

                                    @Override
                                    public void onSuccess() {

                                    }
                                });
                                UserEditInfoActivity.this.finish();

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


       /* OkHttpClientManager.postAsyn(HttpUrl.bianjiinfo, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json=new JSONObject(response);
                    if(json.getString("resultCode").equals("100")){
                        Toast.makeText(UserEditInfoActivity.this, json.getString("resultMessage"), Toast.LENGTH_SHORT).show();
                        Constant.getUser().setNickName(myname.getText().toString());
                        Constant.getUser().setSex(gender_text.getText().toString());
                        Constant.getUser().setIntroduction(mysing.getText().toString());

                        TIMFriendshipManager.getInstance().setNickName(Constant.getUser().getNickName(), new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                            }

                            @Override
                            public void onSuccess() {

                            }
                        });
                        UserEditInfoActivity.this.finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },map,"");*/

    }



}
