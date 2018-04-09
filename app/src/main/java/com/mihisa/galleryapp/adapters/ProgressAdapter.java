package com.mihisa.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mihisa.galleryapp.R;
import com.mihisa.galleryapp.util.file.DeleteException;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;

import org.horaapps.liz.ThemeHelper;
import org.horaapps.liz.ThemedViewHolder;
import org.horaapps.liz.ui.ThemedIcon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by insight on 06.04.18.
 */

public class ProgressAdapter extends BaseAdapter<ProgressAdapter.ListItem, ProgressAdapter.ViewHolder> {

    public static class ListItem {
        String name;
        Exception ex;

        public ListItem(DeleteException ex) {
            this.name = ex.getMedia().getName();
            this.ex = ex;
        }

        public ListItem(String name) {
            this.name = name;
            ex = new Exception("No error message");
        }

        public String getName() {
            return name;
        }
    }

    public interface OnProgress {
        void onNewItem(ListItem item);
    }

    public void add(ListItem item, boolean hasErrors) {
        if (hasErrors)
            this.add(item);
        if (listener != null) {
            listener.onNewItem(item);
        }
    }

    private OnProgress listener;

    public void setListener(OnProgress listener) {
        this.listener = listener;
    }

    public ProgressAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_progress_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem element = getElement(position);
        holder.name.setText(element.name);

        holder.icon.setIcon(CommunityMaterial.Icon.cmd_alert_octagram);
        holder.cont.setOnClickListener(view -> Toast.makeText(holder.cont.getContext(), element.ex.getMessage(), Toast.LENGTH_SHORT).show());
    }

    static class ViewHolder extends ThemedViewHolder {

        @BindView(R.id.folder_icon_bottom_sheet_item)
        ThemedIcon icon;
        @BindView(R.id.name_folder)
        TextView name;
        @BindView(R.id.ll_album_bottom_sheet_item)
        LinearLayout cont;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void refreshTheme(ThemeHelper themeHelper) {

        }
    }
}
