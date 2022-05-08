package net.mready.dicejava.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import net.mready.dicejava.R;
import net.mready.dicejava.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.DiceHistoryViewHolder> {

    private List<History> historyList = new ArrayList<>();

    public HistoryAdapter() {
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;

        // refresh recycler view for new data
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiceHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dice_history, parent, false);

        return new DiceHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiceHistoryViewHolder holder, int position) {

        holder.bindDiceHistory(historyList.get(position));

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class DiceHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDiceHistorySum;
        TextView tvDiceHistoryNumbers;
        TextView tvDiceHistoryDouble;

        public DiceHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDiceHistorySum = itemView.findViewById(R.id.tv_dice_history_sum);
            tvDiceHistoryNumbers = itemView.findViewById(R.id.tv_dice_history_numbers);
            tvDiceHistoryDouble = itemView.findViewById(R.id.tv_dice_history_double);
        }

        public void bindDiceHistory(History diceHistory) {
            int dice1 = diceHistory.getDice1();
            int dice2 = diceHistory.getDice2();
            int sum = dice1 + dice2;

            tvDiceHistorySum.setText(String.valueOf(sum));
            tvDiceHistoryNumbers.setText(dice1 + "-" + dice2);

            if (diceHistory.isDouble()) {
                tvDiceHistorySum.setTextColor(ContextCompat.getColor(tvDiceHistorySum.getContext(), R.color.red));
                tvDiceHistoryNumbers.setTextColor(ContextCompat.getColor(tvDiceHistoryNumbers.getContext(), R.color.red));
                tvDiceHistoryDouble.setVisibility(View.VISIBLE);
            } else {
                tvDiceHistorySum.setTextColor(ContextCompat.getColor(tvDiceHistorySum.getContext(), R.color.belge));
                tvDiceHistoryNumbers.setTextColor(ContextCompat.getColor(tvDiceHistoryNumbers.getContext(), R.color.belge));
                tvDiceHistoryDouble.setVisibility(View.GONE);
            }

        }
    }


}
