package com.mytv365.zb.adapters.listview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseViewHolder;
import com.fhrj.library.tools.ToolImage;
import com.mytv365.zb.R;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.model.Live;
import com.mytv365.zb.views.activity.MyzhuyeActivity;
import com.mytv365.zb.views.activity.user.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 直播
 * Created by zhangguohao on 16/9/12.
 *
 */
public class LiveAdapter  extends BaseViewHolder<Live> {
    private ImageLoader imageLoader= ToolImage.getImageLoader();
    private TextView name,zhiboing,zhiboend;
    private TextView title,positions;
    private RelativeLayout roomIoc;
    private ImageView room_ioc,headimages,tubiao;
    private Context context;

    public LiveAdapter(Context mContext) {
        super(mContext);
        this.context=mContext;
    }

    @Override
    public BaseViewHolder<Live> createViewHolder() {
        return new LiveAdapter(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_live;
    }

    @Override
    public void initView(View view) {
        name=(TextView)findViewById(R.id.name);
        title=(TextView)findViewById(R.id.title);
        positions=(TextView)findViewById(R.id.position);
        zhiboing=(TextView)findViewById(R.id.zhiboing);
        zhiboend=(TextView)findViewById(R.id.zhiboend);
        roomIoc=(RelativeLayout)findViewById(R.id.roomIoc);
        room_ioc=(ImageView)findViewById(R.id.room_ioc);
        tubiao=(ImageView)findViewById(R.id.tubiao);
        headimages=(ImageView)findViewById(R.id.headimages);

//        ViewGroup.LayoutParams para = room_ioc.getLayoutParams();
//        para.height = SysEnv.SCREEN_HEIGHT / 5;
//        para.width = SysEnv.SCREEN_WIDTH;
//        room_ioc.setLayoutParams(para);



    }

    @Override
    public void fillData(final Live rowData, int position) {
        name.setText(rowData.getName());
        title.setText(rowData.getTitle());
        positions.setText(rowData.getPosition());
        if(rowData.getState().equals("0")){
            zhiboend.setVisibility(View.VISIBLE);
            zhiboing.setVisibility(View.GONE);
        }else{
            zhiboing.setVisibility(View.VISIBLE);
            zhiboend.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(rowData.getIoc())){
            imageLoader.displayImage(rowData.getIoc(),headimages);
        }

        if(!TextUtils.isEmpty(rowData.getRoomIoc())){
            imageLoader.displayImage(rowData.getRoomIoc(),room_ioc);
        }else{
            room_ioc.setImageResource(R.drawable.errou);
        }

        if(!TextUtils.isEmpty(rowData.getPostionImage())){
            imageLoader.displayImage(rowData.getPostionImage(),tubiao);

        }

        headimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constant.getUser()==null){
                    Intent intent=new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }else{

                    Intent intent=new Intent(context, MyzhuyeActivity.class);
                    intent.putExtra(MyzhuyeActivity.LIVETEACHERID,rowData.getUserid());
                    intent.putExtra("names",rowData.getName());
                    context.startActivity(intent);
                }
            }
        });

    }
}
