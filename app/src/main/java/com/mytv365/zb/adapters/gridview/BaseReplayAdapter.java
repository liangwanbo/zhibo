package com.mytv365.zb.adapters.gridview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mytv365.zb.R;
import com.mytv365.zb.model.WonderfulVideo;

import java.util.ArrayList;

/**
 * Created by 郑士成 on 2016/10/20.
 */
public class BaseReplayAdapter extends BaseAdapter {
    private Context context;
    public boolean isShowChoice;
    public ArrayList<Boolean> ischoice;
    public boolean isShowCheck;
    private int checkposition;
    private boolean isAllChoice;
    public ArrayList<String> chiiceids = new ArrayList<>();
    public ArrayList<Boolean>delete =new ArrayList<>();
    ViewHolder holder =null;

    private ArrayList<WonderfulVideo>lists =new ArrayList<>();
    public BaseReplayAdapter(Context context, ArrayList<WonderfulVideo>lists,ArrayList<Boolean>ischoice){
        this.context =context;
        this.lists =lists;
        this.ischoice =ischoice;

    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if(convertView ==null){
            checkposition =position;
            holder =new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.persontal_replay_item,null);
            holder.replay_time =(TextView) convertView.findViewById(R.id.replay_time);
            holder.replay_id =(TextView)convertView.findViewById(R.id.replay_theme);
            holder.fileId =(TextView)convertView.findViewById(R.id.replay_name);
            holder.coverImage=(ImageView)convertView.findViewById(R.id.replay_title_pic_layout);
            holder.choiceImage =(ImageView)convertView.findViewById(R.id.replay_choice_pic);
            convertView.setTag(holder);

        }else {
           holder =(ViewHolder)convertView.getTag();
        }
        if(isShowChoice){
            holder.choiceImage.setVisibility(View.VISIBLE);
            if(ischoice.get(position)){
                holder.choiceImage.setImageResource(R.drawable.choice_pressed);
            }else {
                holder.choiceImage.setImageResource(R.drawable.choice_normal);
            }
        }else {
            for(int i=0;i<ischoice.size();i++){
              if(ischoice.get(i) ==true){
                  ischoice.set(i,false) ;
              }
            }
            holder.choiceImage.setVisibility(View.INVISIBLE);
        }
        if(!TextUtils.isEmpty(lists.get(position).getFileName())){
            holder.fileId .setText(lists.get(position).getFileName());
        }

//        holder.replay_time.setText(lists.get(position).getFileId());
        holder.replay_id.setText("直播主题");
        Glide.with(context).load(lists.get(position).getCoverImage()).into(holder.coverImage);
//        Picasso.with(context).load(lists.get(position).getCoverImage()).centerCrop().resize(50,50).into(holder.coverImage);
        return convertView;
    }
    public void chiceState(int post) {
        ischoice.set(post,ischoice.get(post) == true ? false : true) ;
        this.notifyDataSetChanged();

    }


    // 点击进行全选和不全选
    public void chiceAll() {
        if (!isAllChoice) {
            for (int i = 0; i<ischoice.size(); i++) {
                if (ischoice.get(i) == false) {
                    ischoice.set(i,true) ;
                }
                this.notifyDataSetChanged();

            }
            isAllChoice = true;
        } else {

            for (int i = 0; i <ischoice.size(); i++) {
                if (ischoice.get(i) == true) {
                    ischoice.set(i,false) ;
                }
                this.notifyDataSetChanged();
            }

            isAllChoice = false;
        }

    }

    // 点击进行全选和不全选
    public ArrayList<String>Ischiceid() {

        if (chiiceids != null) {
            chiiceids.clear();
        }

        for (int i = 0; i <ischoice.size(); i++) {
            if (ischoice.get(i) == true) {
                chiiceids.add(lists.get(i).getId());
            }
        }
        this.notifyDataSetChanged();
        return chiiceids;
    }


    public class ViewHolder{
        ImageView coverImage;
        ImageView choiceImage;
        TextView fileId;
        TextView replay_time;
        TextView replay_id;
    }

}
