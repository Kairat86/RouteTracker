package tracker.route.aparu.kz.routetracker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tracker.route.aparu.kz.routetracker.R;
import tracker.route.aparu.kz.routetracker.adapter.RecordAdapter;
import tracker.route.aparu.kz.routetracker.model.Record;

public class MyRoutesFragment extends Fragment {

    private static final String TAG = MyRoutesFragment.class.getSimpleName();
    @Inject

    @BindView(R.id.rvRecords)
    RecyclerView rvRecords;
    private RecordAdapter adapter;

    public static MyRoutesFragment newInstance() {
        return new MyRoutesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_routes_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rvRecords.setHasFixedSize(true);
    }

    public void updateUI(List<Record> records) {
        if (adapter == null) {
            adapter = new RecordAdapter(records);
            rvRecords.setAdapter(adapter);
        } else {

            adapter.notifyDataSetChanged(records);
        }
    }
}
