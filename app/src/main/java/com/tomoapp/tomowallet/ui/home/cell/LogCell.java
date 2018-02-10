package com.tomoapp.tomowallet.ui.home.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.jaychang.srv.Updatable;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.helper.DateTimeUtil;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.userInfo.pojo.Log;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macbook on 12/26/17.
 */

public class LogCell extends SimpleCell<Log, LogCell.LogCellViewHolder> implements Updatable<Log> {


    private LogCellClickListener callback;
    public LogCell(@NonNull Log item, LogCellClickListener callback) {
        super(item);

        this.callback = callback;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.cell_log_2;
    }

    @NonNull
    @Override
    protected LogCellViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new LogCellViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull LogCellViewHolder logCellViewHolder, int i, @NonNull Context context, Object o) {
        logCellViewHolder.setContent(getItem());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Log log) {
        return false;
    }

    @Override
    public Object getChangePayload(@NonNull Log log) {
        return null;
    }

    public interface LogCellClickListener{
        void onLogCellClicked(Log log);
    }

    public class LogCellViewHolder extends SimpleViewHolder {


        @BindView(R.id.img_type)
        ImageView imgType;
        @BindView(R.id.txt_change)
        TextView txtChange;
        @BindView(R.id.txt_total)
        TextView txtTotal;
        @BindView(R.id.txt_transaction_type)
        TextView txtTransactionType;
        @BindView(R.id.txt_transaction_time)
        TextView txtTransactionTime;

        public LogCellViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onLogCellClicked(log);
                }
            });
        }

        private Log log;

        private void setContent(Log log) {
            try {
                this.log = log;
                txtTransactionTime.setText(DateTimeUtil.displayTime(log.getTime()));
                switch (log.getType()) {
                    case Log.TYPE_CASHOUT:
                        txtTransactionType.setText("Cash out");
                        imgType.setImageResource(R.drawable.ic_cash_out);
                        txtChange.setText(String.format(Locale.US, "%.4f TMC", Double.parseDouble(log.getChange())));
                        break;
                    case Log.TYPE_CASHIN:
                        txtTransactionType.setText("Cash in");
                        imgType.setImageResource(R.drawable.ic_cash_in);
                        txtChange.setText(String.format(Locale.US, "%.4f TMC", Double.parseDouble(log.getChange())));
                        break;
                    case Log.TYPE_REWARD:
                        txtTransactionType.setText("Reward");
                        imgType.setImageResource(R.drawable.ic_crown);
                        txtChange.setText(String.format(Locale.US, "+%.4f TMC", Double.parseDouble(log.getChange())));
                        break;
                    case Log.TYPE_TRANSFER:
                        txtTransactionType.setText("Send TMC");
                        imgType.setImageResource(R.drawable.ic_send);
                        txtChange.setText(String.format(Locale.US, "-%.4f TMC", Double.parseDouble(log.getChange())));
                        break;
                    case Log.TYPE_RECEIVE:
                        txtTransactionType.setText("Receive TMC");
                        imgType.setImageResource(R.drawable.ic_receive);
                        txtChange.setText(String.format(Locale.US, "+%.4f TMC", Double.parseDouble(log.getChange())));
                        break;
                    case Log.TYPE_MESSAGE:
                        txtTransactionType.setText("Message");
                        imgType.setImageResource(R.drawable.ic_message);
                        txtChange.setText("");
                        break;
                }
                /*txtMessage.setText(log.getMessage() == null ? "NULL" : log.getMessage());
                txtTmcMainChain.setText(String.format(Locale.US, "%.2f", log.getTmcMainchain()));
                txtTmcSideChain.setText(String.format(Locale.US, "%.2f", log.getTmcSidechain()));*/
                txtTotal.setText(String.format(Locale.US, "Total: %.4f TMC", (log.getTmcSidechain() + log.getTmcMainchain())));
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
    }
}
