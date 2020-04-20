package io.github.thang86.searchview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BikeStationAdapter extends RecyclerView.Adapter<BikeStationAdapter.StationViewHolder> implements Filterable {
    private List<Result> mStationList;
    private List<Result> mFilteredStationList;
    private LayoutInflater inflater;
    private ItemClickListener mItemClickListener;

    public BikeStationAdapter(Context context, List<Result> stations) {
        inflater = LayoutInflater.from(context);
        this.mStationList = stations;
        this.mFilteredStationList = stations;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_station_card, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Result station = mFilteredStationList.get(position);
        holder.setData(station, position);
    }

    @Override
    public int getItemCount() {
        return mFilteredStationList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();
                if (searchString.isEmpty()) {
                    mFilteredStationList = mStationList;
                } else {
                    ArrayList<Result> tempFilteredList = new ArrayList<>();
                    for (Result station : mStationList) {
                        // search for station name
                        if (station.getOriginalTitle().toLowerCase().contains(searchString)) {
                            tempFilteredList.add(station);
                        }
                    }
                    mFilteredStationList = tempFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredStationList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredStationList = (ArrayList<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void updateSearchResults(List<Result> results) {
        this.mFilteredStationList = results;
    }

    public class StationViewHolder extends RecyclerView.ViewHolder {
        TextView stationName, fullSlot, emptySlot;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);
            stationName = itemView.findViewById(R.id.stationName);
            fullSlot = itemView.findViewById(R.id.fullSlot);
            emptySlot = itemView.findViewById(R.id.emptySlot);
        }

        @SuppressLint("SetTextI18n")
        public void setData(final Result station, final int position) {
            this.stationName.setText(station.getOriginalTitle());
            this.fullSlot.setText("Full Slot: " + station.getOriginalLanguage());
            this.emptySlot.setText("Empty Slot: " + station.getTitle());
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mItemClickListener.onItemClick(station, position);
//                }
//            });
        }
    }
}