package com.app.ace.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.AnyEditTextView;

import roboguice.inject.InjectView;

/**
 * Created on 10/4/2017.
 */
public class FeedBackFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.btn_negative)
    private RelativeLayout btn_negative;
    @InjectView(R.id.btn_positive)
    private RelativeLayout btn_positive;
    @InjectView(R.id.img_plus)
    private ImageView img_plus;
    @InjectView(R.id.img_minus)
    private ImageView img_minus;
    @InjectView(R.id.edt_feedback)
    private AnyEditTextView edt_feedback;
    @InjectView(R.id.btn_Submit)
    private Button btn_Submit;
    private String positive = "positive";
    private String negative = "negative";
    private String selectedFeedback = positive;

    public static FeedBackFragment newInstance() {
        Bundle args = new Bundle();

        FeedBackFragment fragment = new FeedBackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlisteners();
    }

    private void setlisteners() {
        btn_positive.setOnClickListener(this);
        btn_negative.setOnClickListener(this);
        btn_Submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_positive:
                btn_negative.setBackground(getResources().getDrawable(R.drawable.negative_border));
                img_minus.setImageResource(R.drawable.minus_neg_plug);
                img_plus.setImageResource(R.drawable.addd);
                btn_positive.setBackground(getResources().getDrawable(R.drawable.red_border));
                selectedFeedback = positive;
                break;
            case R.id.btn_negative:
                selectedFeedback = negative;
                btn_negative.setBackground(getResources().getDrawable(R.drawable.red_border));
                img_minus.setImageResource(R.drawable.minus_plug);
                img_plus.setImageResource(R.drawable.addd1);
                btn_positive.setBackground(getResources().getDrawable(R.drawable.negative_border));
                break;
            case R.id.btn_Submit:
                if (validated())
                    break;

        }

    }

    private boolean validated() {
        if (edt_feedback.getText().toString().trim().equals("")) {
            edt_feedback.setError(getString(R.string.feedback_error));
            return false;
        } else {
            return true;
        }
    }
}