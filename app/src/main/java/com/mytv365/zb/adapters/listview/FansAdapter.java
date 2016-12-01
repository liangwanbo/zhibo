package com.mytv365.zb.adapters.listview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fhrj.library.base.impl.BaseViewHolder;
import com.mytv365.zb.R;
import com.mytv365.zb.model.Concern;
import com.mytv365.zb.presenters.AudienceHelper;
import com.mytv365.zb.presenters.viewinface.Audience;
import com.mytv365.zb.presenters.viewinface.ConcernView;
import com.mytv365.zb.views.activity.MyzhuyeActivity;
import com.mytv365.zb.views.activity.user.FansActivity;
import com.tencent.TIMUserProfile;

import java.util.List;

/**
 *
 * Created by zhangguohao on 16/9/12.
 * yang : Concern adapter
 */
public class FansAdapter extends BaseViewHolder<Concern> implements Audience {

    private ImageView persontal_concern_people_headimg;
    private TextView persontal_concern_people_name, persontal_concern_people_description, persontal_concern_btn;
    private String type;
    private AudienceHelper audienceHelper;
    private ConcernView concernView;
    private int mPosition;
    private LinearLayout concern_item_choose;

    private Context context;

    public FansAdapter(Context mContext, String type, ConcernView concernView) {
        super(mContext);
        this.type = type;
        this.concernView = concernView;
        this.context=mContext;
        audienceHelper = new AudienceHelper(mContext, this);

    }

    @Override
    public BaseViewHolder<Concern> createViewHolder() {
        return new FansAdapter(mContext, type, concernView);
    }


    @Override
    public int bindLayout() {
        return R.layout.item_fans;
    }


    @Override
    public void initView(View view) {
        persontal_concern_people_headimg = (ImageView) view.findViewById(R.id.persontal_concern_people_headimg);
        persontal_concern_people_description = (TextView) view.findViewById(R.id.persontal_concern_description);
        persontal_concern_people_name = (TextView) view.findViewById(R.id.persontal_concern_people_name);
        persontal_concern_btn = (TextView) view.findViewById(R.id.persontal_concern_btn);
        concern_item_choose= (LinearLayout) findViewById(R.id.concern_item_choose);

    }

    @Override
    public void fillData(final Concern rowData, final int position) {
        persontal_concern_people_name.setText(rowData.getNickName());
        persontal_concern_people_description.setText(rowData.getSign());
        mPosition = position;

        Log.i("fanss", "头像地址: "+rowData.getHeadImages());
        Glide.with(mContext).load(rowData.getHeadImages()).into(persontal_concern_people_headimg);

        if (type.equals(FansActivity.FANCE)){
            persontal_concern_btn.setVisibility(View.INVISIBLE);
        }else{

            persontal_concern_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    audienceHelper.CancelAudience(Integer.valueOf(rowData.getFollower()));
                }
            });


            concern_item_choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, MyzhuyeActivity.class);
                    intent.putExtra(MyzhuyeActivity.LIVETEACHERID,rowData.getFollower());
                    intent.putExtra("names",rowData.getNickName());
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public void cancelaudien(int type) {
        if (type == 100) {
            concernView.getPosition(mPosition);
            Toast.makeText(mContext, "取消关注", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "操作失败", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void addaudience(int type){
    }



    @Override
    public void audienguanx(int type){
    }



    @Override
    public void getConcernList(List<TIMUserProfile> concernList) {
    }


    @Override
    public void getByConernList(List<TIMUserProfile> concernList) {
    }

}
