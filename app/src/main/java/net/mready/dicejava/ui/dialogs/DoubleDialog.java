package net.mready.dicejava.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import net.mready.dicejava.R;

public class DoubleDialog extends DialogFragment {
    private Button btnContinue;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_double, null);

        getViewReferences(dialogView);

        builder.setView(dialogView);

        AlertDialog ad = builder.create();

        if (ad.getWindow() != null) {
            ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.getWindow().requestFeature(Window.FEATURE_NO_TITLE);


            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        return ad;
    }

    private void getViewReferences(View dialogView) {
        btnContinue = dialogView.findViewById(R.id.btn_continue);
    }


}
