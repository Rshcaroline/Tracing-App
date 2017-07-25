package com.fdwireless.trace.ui.team;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fdwireless.trace.mapmodule.R;

import java.util.List;

/**
 * Created by caroline on 2017/2/23.
 */

public class FourTeamAdapter extends RecyclerView.Adapter<FourTeamAdapter.ViewHolder> {

    private List<FourTeam> mFourTeamList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fourteamView;
        ImageView fourteamImage;
        ImageView userone;
        ImageView usertwo;
        ImageView userthree;
        ImageView userfour;
        TextView numberrate;

        public ViewHolder(View view) {
            super(view);
            fourteamView = view;
            fourteamImage = (ImageView) view.findViewById(R.id.fourteam_image);
            numberrate = (TextView) view.findViewById(R.id.numberrate);
            userone = (ImageView) view.findViewById(R.id.userone);
            usertwo = (ImageView) view.findViewById(R.id.usertwo);
            userthree = (ImageView) view.findViewById(R.id.userthree);
            userfour = (ImageView) view.findViewById(R.id.userfour);
        }
    }

    public FourTeamAdapter(List<FourTeam> fourteamList) {
        mFourTeamList = fourteamList;
    }

    @Override
    public FourTeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fourteam_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fourteamView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                FourTeam fourTeam = mFourTeamList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + fourTeam.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        /*
        holder.fourteamImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                FourTeam fourTeam = mFourTeamList.get(position);
                Toast.makeText(v.getContext(), "you clicked image " + fourTeam.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        */
        return holder;
    }

    @Override
    public void onBindViewHolder(FourTeamAdapter.ViewHolder holder, int position) {
        FourTeam fourTeam = mFourTeamList.get(position);
        holder.fourteamImage.setImageResource(fourTeam.getImageId());
        holder.userone.setImageResource(fourTeam.getUserone());
        holder.usertwo.setImageResource(fourTeam.getUsertwo());
        holder.userthree.setImageResource(fourTeam.getUserthree());
        holder.userfour.setImageResource(fourTeam.getUserfour());
        holder.numberrate.setText(fourTeam.getNumberrate());
    }

    @Override
    public int getItemCount() {
        return mFourTeamList.size();
    }
}
