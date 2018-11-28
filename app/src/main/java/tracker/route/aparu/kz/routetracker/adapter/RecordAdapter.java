package tracker.route.aparu.kz.routetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import tracker.route.aparu.kz.routetracker.R;
import tracker.route.aparu.kz.routetracker.activity.RouteMapActivity;
import tracker.route.aparu.kz.routetracker.model.Record;

import static tracker.route.aparu.kz.routetracker.util.Constant.RECORD_ID;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private List<Record> recordList;
    private DateFormat format = DateFormat.getDateInstance();

    public RecordAdapter(List<Record> recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecordViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int i) {
        Record record = recordList.get(i);
        Context context = holder.itemView.getContext();
        holder.date.setText(context.getString(R.string.date, format.format(record.getDate())));
        holder.pointNumber.setText(context.getString(R.string.point_number, record.getPointNumber()));
        holder.id = record.getId();
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public void notifyDataSetChanged(List<Record> records) {
        this.recordList = records;
        notifyDataSetChanged();
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {

        int id;
        TextView date = itemView.findViewById(R.id.date);
        TextView pointNumber = itemView.findViewById(R.id.pointNumber);

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, RouteMapActivity.class);
                intent.putExtra(RECORD_ID, id);
                context.startActivity(intent);
            });
        }
    }
}
