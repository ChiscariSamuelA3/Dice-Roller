package net.mready.dicejava.ui.dice;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.seismic.ShakeDetector;

import net.mready.dicejava.MainActivity;
import net.mready.dicejava.R;
import net.mready.dicejava.model.History;
import net.mready.dicejava.ui.dialogs.DoubleDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DiceFragment extends Fragment implements ShakeDetector.Listener {
    FrameLayout containerHistoryPreview;
    Button btnRoll;
    LottieAnimationView ivDiceLeft, ivDiceRight;
    TextView previousScore;
    private static final String HISTORY_LIST_KEY = "history_list_key";
    private static ArrayList<History> historyList;

    Vibrator vibratorDouble;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViewReferences(view);

        // delete history
        /*SharedPreferences settings = view.getContext().getSharedPreferences("history_storage", Context.MODE_PRIVATE);
        settings.edit().clear().apply();*/

        loadData(view);

        if (historyList.isEmpty()) {
            // istoricul este gol => redirectionare catre empty state
            containerHistoryPreview.setOnClickListener((v) -> ((MainActivity) requireActivity()).navigateToHistoryEmpty());
        } else
            containerHistoryPreview.setOnClickListener((v) -> ((MainActivity) requireActivity()).navigateToHistory());

        btnRoll.setOnClickListener(v -> getRollResult());


        // TASK 6.1
        SensorManager sm = (SensorManager) view.getContext().getSystemService(Context.SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sm);
    }

    private void getViewReferences(View view) {
        containerHistoryPreview = view.findViewById(R.id.container_history_preview);

        btnRoll = view.findViewById(R.id.btn_roll);
        ivDiceLeft = view.findViewById(R.id.iv_dice_left);
        ivDiceRight = view.findViewById(R.id.iv_dice_right);
        previousScore = view.findViewById(R.id.previous_score);
    }

    // TASK 1
    private void getRollResult() {

        ivDiceLeft.setAnimation(R.raw.dice1animation);
        ivDiceRight.setAnimation(R.raw.dice2animation);

        ivDiceLeft.playAnimation();
        ivDiceRight.playAnimation();

        ivDiceLeft.addAnimatorListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("ONEND", "onAnimationEnd: HERE");
                int randomRoll1 = (int) (Math.random() * 6 + 1);
                int randomRoll2 = (int) (Math.random() * 6 + 1);
                int dice1, dice2;

                dice1 = getRandomDice1(randomRoll1);
                dice2 = getRandomDice2(randomRoll2);

                if (dice1 == 0 || dice2 == 0) {
                    // for the sake of clarity
                    Toast.makeText(requireContext(), "Please try again!", Toast.LENGTH_SHORT).show();
                } else {
                    // odata ce a avut loc o aruncare de zaruri, istoricul nu va mai fi gol
                    containerHistoryPreview.setOnClickListener((v) -> ((MainActivity) requireActivity()).navigateToHistory());

                    int result = dice1 + dice2;

                    Toast.makeText(requireContext(), dice1 + " : " + dice2, Toast.LENGTH_SHORT).show();

                    boolean isDouble = false;
                    if (dice1 == dice2) {
                        isDouble = true;

                        // TASK 5
                        openCustomDialog();
                    }

                    previousScore.setText(dice1 + "-" + dice2);

                    historyList.add(new History(dice1, dice2, result, isDouble));

                    saveData();

                    ivDiceLeft.removeAllAnimatorListeners();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void openCustomDialog() {
        DoubleDialog dialog = new DoubleDialog();
        dialog.show(requireActivity().getSupportFragmentManager(), "show dialog");


        initVibratorDouble();
    }

    // TASK 7
    private void initVibratorDouble() {
        vibratorDouble = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibratorDouble.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private int getRandomDice1(int randomRoll) {
        int dice;
        switch (randomRoll) {
            case 1:
                dice = 1;
                ivDiceLeft.setImageResource(R.drawable.illustration_dice_face_1);
                break;
            case 2:
                dice = 2;
                ivDiceLeft.setImageResource(R.drawable.illustration_dice_face_2);
                break;
            case 3:
                dice = 3;
                ivDiceLeft.setImageResource(R.drawable.illustration_dice_face_3);
                break;
            case 4:
                dice = 4;
                ivDiceLeft.setImageResource(R.drawable.illustration_dice_face_4);
                break;
            case 5:
                dice = 5;
                ivDiceLeft.setImageResource(R.drawable.illustration_dice_face_5);
                break;
            case 6:
                dice = 6;
                ivDiceLeft.setImageResource(R.drawable.illustration_dice_face_6);
                break;
            default:
                dice = 0;
        }
        return dice;
    }

    private int getRandomDice2(int randomRoll) {
        int dice;
        switch (randomRoll) {
            case 1:
                dice = 1;
                ivDiceRight.setImageResource(R.drawable.illustration_dice_face_1);
                break;
            case 2:
                dice = 2;
                ivDiceRight.setImageResource(R.drawable.illustration_dice_face_2);
                break;
            case 3:
                dice = 3;
                ivDiceRight.setImageResource(R.drawable.illustration_dice_face_3);
                break;
            case 4:
                dice = 4;
                ivDiceRight.setImageResource(R.drawable.illustration_dice_face_4);
                break;
            case 5:
                dice = 5;
                ivDiceRight.setImageResource(R.drawable.illustration_dice_face_5);
                break;
            case 6:
                dice = 6;
                ivDiceRight.setImageResource(R.drawable.illustration_dice_face_6);
                break;
            default:
                dice = 0;
        }
        return dice;
    }

    // TASK 2
    private void saveData() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("history_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // convert history list to a string -> json file (serialize data with Gson library)
        Gson gsonHistory = new Gson();
        String convertedHistoryList = gsonHistory.toJson(historyList);

        // add string to shared preferences
        editor.putString(HISTORY_LIST_KEY, convertedHistoryList);

        //apply changes to editor
        editor.apply();
    }

    private void loadData(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("history_storage", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        // type token of the objects
        Type type = new TypeToken<ArrayList<History>>() {
        }.getType();
        // get history list from shared preferences and convert to ArrayList - deserialize
        historyList = gson.fromJson(sharedPreferences.getString(HISTORY_LIST_KEY, null), type);

        if (historyList == null) {
            historyList = new ArrayList<>();
        } else {
            //Task 3.3
            int lastDiceIndex;
            lastDiceIndex = historyList.size() - 1;
            previousScore.setText(historyList.get(lastDiceIndex).getDice1() + "-" + historyList.get(lastDiceIndex).getDice2());
        }
    }

    public ArrayList<History> getHistoryList() {
        return historyList;
    }

    public static DiceFragment newInstance() {
        return new DiceFragment();
    }

    // TASK 6.2
    @Override
    public void hearShake() {
        getRollResult();
        Log.d("SHAKE", "hearShake: SHAAKKKEEEEEEEEEEEEEEEEEEEEE");
    }
}