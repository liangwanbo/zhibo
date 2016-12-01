package com.mytv365.zb.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mytv365.zb.R;
import com.mytv365.zb.model.WonderfulVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 阳志 on 2016/8/25 0025.
 * 精彩回放
 */
public class PersontalReplayAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<WonderfulVideo> replayVideos;
    private List<Drawable> drawables;
    public boolean isChice[]; // 是否选择boolean 集合
    private boolean isAllchioce;
    public boolean isShowChice;

    public List<String> chiiceids = new ArrayList<>();


    public PersontalReplayAdapter(Context context, List<WonderfulVideo> replayVideos, List<Drawable> drawables) {
        this.context = context;
        this.inflater = inflater.from(context);
        this.replayVideos = replayVideos;
        this.drawables = drawables;


        Log.i("hck", replayVideos.size() + "lenght");
        isChice = new boolean[replayVideos.size()]; // 设置图片集合个boolean值
        for (int i = 0; i < replayVideos.size(); i++) { // for循环为boolean值集合一一赋值为false
            isChice[i] = false;
        }

        Log.i("PersontalUserReplay", "PersontalReplayAdapter: 适配器中isChice的选择" + isChice.length);

    }


    @Override
    public int getCount() {
        if(replayVideos!=null){
            return replayVideos.size();
        }
        return 0;

    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        WonderfulVideo reVideo = replayVideos.get(i);
        if (view == null) {

            view = inflater.inflate(R.layout.persontal_replay_item, null);
            viewHolder = new ViewHolder();
            viewHolder.layout = (ImageView) view.findViewById(R.id.replay_title_pic_layout);
            viewHolder.replay_choice_pic = (ImageView) view.findViewById(R.id.replay_choice_pic);
            viewHolder.replay_name = (TextView) view.findViewById(R.id.replay_name);
            viewHolder.replay_time = (TextView) view.findViewById(R.id.replay_time);
            viewHolder.replay_title_pic = (ImageView) view.findViewById(R.id.replay_title_pic);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (isShowChice) {
            Log.i("tag", "点击编辑显示所以预选");
            viewHolder.replay_choice_pic.setVisibility(View.VISIBLE);

            viewHolder.replay_choice_pic.setImageResource(R.drawable.choice_normal);

            if (isChice[i]) {
                viewHolder.replay_choice_pic.setImageResource(R.drawable.choice_pressed);
            } else {
                viewHolder.replay_choice_pic.setImageResource(R.drawable.choice_normal);
            }
        } else {
            Log.i("tag", "点击编辑隐藏所以预选");
            for (int a = 0; a < isChice.length; a++) {
                if (isChice[a] == true) {
                    isChice[a] = false;
                }
            }

            viewHolder.replay_choice_pic.setVisibility(View.INVISIBLE);
        }

//            Drawable dw=drawables.get(i);
//            viewHolder.layout.setBackgroundDrawable(dw);

        viewHolder.replay_name.setText(reVideo.getFileName());
        viewHolder.replay_time.setText(reVideo.getId() + " ");
        RequestManager rew = Glide.with(context);
       // rew.load(reVideo.).into(viewHolder.layout);

            //viewHolder.replay_title_pic.setImageResource(R.drawable.errou);

        return view;

    }


    // 点击选择或取消选择
    public void chiceState(int post) {
        isChice[post] = isChice[post] == true ? false : true;
        this.notifyDataSetChanged();
    }

    // 点击进行全选和不全选
    public void chiceAll() {
        if (!isAllchioce) {
            for (int i = 0; i < isChice.length; i++) {
                if (isChice[i] == false) {
                    isChice[i] = true;
                }
                this.notifyDataSetChanged();
            }
            isAllchioce = true;
        } else {

            for (int i = 0; i < isChice.length; i++) {
                if (isChice[i] == true) {
                    isChice[i] = false;
                }
                this.notifyDataSetChanged();
            }

            isAllchioce = false;
        }

    }


    // 点击进行全选和不全选
    public List<String> Ischiceid() {

        if (chiiceids != null) {
            chiiceids.clear();
        }

        for (int i = 0; i < isChice.length; i++) {
            if (isChice[i] == true) {
                chiiceids.add(replayVideos.get(i).getFileName());
            }
//                this.notifyDataSetChanged();
        }

        return chiiceids;

    }


    public boolean[] getIsChice() {
        return isChice;
    }

    static class ViewHolder {

        ImageView layout;
        ImageView replay_title_pic;
        ImageView replay_choice_pic;
        TextView replay_time;
        TextView replay_name;
    }

}
