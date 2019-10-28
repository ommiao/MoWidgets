package cn.ommiao.mowidgets.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.Path;

public class PathSelectAdapter extends RecyclerView.Adapter<PathSelectAdapter.VH> {

    public static final String PAYLOAD_SELECTED = "selected";

    private ArrayList<Path> list;

    public PathSelectAdapter(ArrayList<Path> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_path_select, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Path path = list.get(position);
        holder.tvFileName.setText(path.getFileName());
        holder.tvFileName.setSelected(true);
        holder.ivSelected.setVisibility(path.isSelected() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        if(payloads.size() == 0){
            super.onBindViewHolder(holder, position, payloads);
        } else {
            String payload = (String) payloads.get(0);
            Path path = list.get(position);
            if(payload.contains(PAYLOAD_SELECTED)){
                holder.ivSelected.setVisibility(path.isSelected() ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private TextView tvFileName;
        private ImageView ivSelected;

        private VH(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            ivSelected = itemView.findViewById(R.id.iv_selected);
            tvFileName.setOnClickListener(v -> {
                if(onFileClickListener != null){
                    onFileClickListener.onFileClick(getAdapterPosition());
                }
            });
        }
    }

    private OnFileClickListener onFileClickListener;

    public void setOnFileClickListener(OnFileClickListener onFileClickListener) {
        this.onFileClickListener = onFileClickListener;
    }

    public interface OnFileClickListener {
        void onFileClick(int pos);
    }

}
