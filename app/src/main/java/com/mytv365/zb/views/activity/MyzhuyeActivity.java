package com.mytv365.zb.views.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mytv365.zb.R;
import com.mytv365.zb.common.BaseActivity;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.model.Teachers;
import com.mytv365.zb.presenters.AudienceHelper;
import com.mytv365.zb.presenters.TeacherHelper;
import com.mytv365.zb.presenters.viewinface.Audience;
import com.mytv365.zb.presenters.viewinface.TeacherView;
import com.mytv365.zb.utils.GlideUtils;
import com.mytv365.zb.views.activity.user.ReviewActivity;
import com.mytv365.zb.widget.CircleImageView;
import com.tencent.TIMUserProfile;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * <p/>
 * 老师主页
 */
public class MyzhuyeActivity extends BaseActivity implements View.OnClickListener, TeacherView, Audience {
    private RelativeLayout back, teacher_info, review, tzbj;
    private TextView name;
    private ImageView gz, iocs, concern_img_btn;
    private TeacherHelper teacherHelper;
    private TextView jiangshi, guanzhunumber, teacherinfo;
    private CircleImageView ioc;
    private Teachers teache;
    private boolean type;
    public static String LIVETEACHERID = "liveteacher";
    private static final String TAG = "MyzhuyeActivity";
    private String names;
    private AudienceHelper audienceHelper;
    private String id;
    private boolean isConcern = false;


    @Override
    public int getLayout() {
        return R.layout.myzhuye_layout;
    }

    @Override
    public int getcolor() {
        return R.color.title;
    }

    @Override
    public void getinit() {

        names = getIntent().getStringExtra("names");
        audienceHelper = new AudienceHelper(this, this);
        teacherHelper = new TeacherHelper(this, this);

        id = getIntent().getStringExtra(MyzhuyeActivity.LIVETEACHERID);

        if (id != null) {
            teacherHelper.getteacherdata(id);
        }

        back = (RelativeLayout) this.findViewById(R.id.back);
        teacher_info = (RelativeLayout) this.findViewById(R.id.teacher_info);
        review = (RelativeLayout) this.findViewById(R.id.review);
        ioc = (CircleImageView) this.findViewById(R.id.ioc);
        tzbj = (RelativeLayout) this.findViewById(R.id.tzbj);
        name = (TextView) this.findViewById(R.id.name);
        gz = (ImageView) this.findViewById(R.id.gz);
        iocs = (ImageView) this.findViewById(R.id.iocs);
        jiangshi = (TextView) this.findViewById(R.id.jiangshi);
        guanzhunumber = (TextView) this.findViewById(R.id.guanzhunumber);
        teacherinfo = (TextView) this.findViewById(R.id.teacherinfo);
//        concern_img_btn=findViewById(R.id.)

        back.setOnClickListener(this);
        teacher_info.setOnClickListener(this);
        review.setOnClickListener(this);
        tzbj.setOnClickListener(this);
        gz.setOnClickListener(this);
        Log.i(TAG, "getinit:  id 是" + id);
        audienceHelper.SelectAudience(Integer.valueOf(id));
        // audienceHelper.getByConcernlist(Constant.CONCERNPAGE,Constant.CONCERNPAGESIZE);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                MyzhuyeActivity.this.finish();
                break;
            case R.id.teacher_info:
                if (teache != null) {
                        Intent intent = new Intent(this, TeacherJianJie.class);
                        intent.putExtra("jianjie", teache.getIntroduction());
                        startActivity(intent);
                }
                break;

            case R.id.review:
                Intent inten = new Intent(MyzhuyeActivity.this, ReviewActivity.class);
                inten.putExtra("userId", id);
                Constant.isFor = "1";
                startActivity(inten);
                break;

            case R.id.tzbj:
                Intent intens = new Intent(MyzhuyeActivity.this, PersontalNotice.class);
                startActivity(intens);
                break;

            case R.id.gz:
                if (!isConcern) {
                    audienceHelper.addaudience(Integer.valueOf(id));
                    isConcern = true;
                } else {
                    audienceHelper.CancelAudience(Integer.valueOf(id));
                    isConcern = false;
                }
                break;
        }

    }


    @Override
    public void callback(Teachers teachers) {

        teache = teachers;
        jiangshi.setText(teachers.getPosition());
        guanzhunumber.setText(teachers.getFollowerNumber());
        teacherinfo.setText(teachers.getIntroduction());
        GlideUtils.loadImage(this, teachers.getHeadImages(), ioc);
        GlideUtils.loadImage(this, teachers.getRoleIcon(), iocs);
        name.setText(names);
        if (teachers.getFollowStatus().equals("1")) {
            gz.setImageResource(R.drawable.yiguanzhu);
        } else if (teachers.getFollowStatus().equals("2")) {
            gz.setImageResource(R.drawable.guanzhus);
        }


    }


    @Override
    public void show(String imfo) {
        Toast.makeText(this, imfo, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        teacherHelper.onDestory();
    }

    @Override
    public void addaudience(int type) {
        if (type == 100) {
            Toast.makeText(MyzhuyeActivity.this, "成功关注", Toast.LENGTH_SHORT).show();
            gz.setImageResource(R.drawable.yiguanzhu);
            int concernNumber = Integer.valueOf(guanzhunumber.getText().toString());
            guanzhunumber.setText(String.valueOf(concernNumber + 1));

//            audienceHelper.getByConcernlist(Constant.CONCERNPAGE,Constant.CONCERNPAGESIZE);
            isConcern = true;
        } else if (type == 301) {
            Toast.makeText(MyzhuyeActivity.this, "已经关注", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MyzhuyeActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void cancelaudien(int type) {
        if (type == 100) {
            Toast.makeText(MyzhuyeActivity.this, "取消关注", Toast.LENGTH_SHORT).show();
            gz.setImageResource(R.drawable.guanzhus);
            int concernNumber = Integer.valueOf(guanzhunumber.getText().toString());
            if (concernNumber < 1) {
                guanzhunumber.setText(String.valueOf(0));
            } else {
                guanzhunumber.setText(String.valueOf(concernNumber - 1));
            }


//            audienceHelper.getByConcernlist(Constant.CONCERNPAGE,Constant.CONCERNPAGESIZE);
            isConcern = false;
        } else {
            Toast.makeText(MyzhuyeActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void audienguanx(int type) {
        if (type == 304) {
            isConcern = true;
            gz.setImageResource(R.drawable.yiguanzhu);
        } else if (type == 306) {
            isConcern = true;
            gz.setImageResource(R.drawable.yiguanzhu);
        } else if (type == 100) {
            isConcern = false;
            gz.setImageResource(R.drawable.guanzhus);
        }
    }


    @Override
    public void getConcernList(List<TIMUserProfile> concernList) {
    }


    @Override
    public void getByConernList(List<TIMUserProfile> concernList) {
        Log.i(TAG, "getByConernList:  回调大小是" + concernList.size());
        if (concernList != null) {
            guanzhunumber.setText(String.valueOf(concernList.size()));
        }
    }

}
