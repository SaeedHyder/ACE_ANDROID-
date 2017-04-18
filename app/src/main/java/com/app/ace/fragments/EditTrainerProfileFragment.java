package com.app.ace.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.activities.MainActivity;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class EditTrainerProfileFragment extends BaseFragment implements View.OnClickListener ,AdapterView.OnItemSelectedListener , MainActivity.ImageSetter{

    @InjectView(R.id.btnChangeProfilePhoto)
    private Button btnChangeProfilePhoto;

    @InjectView(R.id.civ_profile_pic)
    private CircleImageView civ_profile_pic;

    @InjectView(R.id.txt_eidt_certification)
    private AnyTextView txt_eidt_certification;

    @InjectView(R.id.txt_eidt_speciality)
    private AnyTextView txt_eidt_speciality;

    @InjectView(R.id.sp_Gender)
    private Spinner sp_Gender;


    public static EditTrainerProfileFragment newInstance(){

        return new EditTrainerProfileFragment();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile_trainer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add(getString(R.string.male));
        categories.add(getString(R.string.femaile));

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_Gender.setAdapter(dataAdapter);
        sp_Gender.setSelection(0);

        setListener();
    }

    private void setListener() {
        btnChangeProfilePhoto.setOnClickListener(this);
        txt_eidt_certification.setOnClickListener(this);
        txt_eidt_speciality.setOnClickListener(this);
        getMainActivity().setImageSetter(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();

        titleBar.showSaveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });

        titleBar.setSubHeading(getResources().getString(R.string.edit_profile));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnChangeProfilePhoto:

                CameraHelper.uploadPhotoDialog(getMainActivity());

                break;

            case R.id.txt_eidt_certification:

                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

                break;

            case R.id.txt_eidt_speciality:

                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

                break;

        }

    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.CAMERA_REQUEST) {

            CameraHelper.retrieveAndDisplayPicture(requestCode,resultCode,data,getDockActivity(),civ_profile_pic);


        } else if (requestCode == AppConstants.GALLERY_REQUEST) {

            CameraHelper.retrieveAndDisplayPicture(requestCode,resultCode,data,getDockActivity(),civ_profile_pic);
        }
    }*/

    @Override
    public void setImage(String imagePath) {

        if (imagePath != null) {
            //profilePic = new File(imagePath);
            ImageLoader.getInstance().displayImage(
                    "file:///" +imagePath, civ_profile_pic);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
