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


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.StationViewHolder> implements Filterable {
    private List<City> mStationList;
    private List<City> mFilteredStationList;
    private LayoutInflater inflater;
    private ItemClickListener mItemClickListener;

    public CityAdapter(Context context, List<City> stations) {
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
        City station = mFilteredStationList.get(position);
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
                    ArrayList<City> tempFilteredList = new ArrayList<>();
                    for (City station : mStationList) {
                        // search for station name
                        if (station.getLocalizedName().toLowerCase().contains(searchString)) {
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
                mFilteredStationList = (ArrayList<City>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void updateSearchResults(List<City> results) {
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
        public void setData(final City station, final int position) {
            this.stationName.setText(station.getLocalizedName());
            this.fullSlot.setText("Full Slot: " + station.getType());
            this.emptySlot.setText("Empty Slot: " + station.getRank());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(station, position);
                }
            });
        }
    }
}