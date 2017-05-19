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

import com.app.ace.R;
import com.app.ace.activities.MainActivity;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;




/**
 * Created by khan_muhammad on 3/22/2017.
 */

public class EditTraineeProfileFragment extends BaseFragment implements View.OnClickListener ,AdapterView.OnItemSelectedListener , MainActivity.ImageSetter{

    @InjectView(R.id.btnChangeProfilePhoto)
    private Button btnChangeProfilePhoto;

    @InjectView(R.id.civ_profile_pic)
    private CircleImageView civ_profile_pic;

    @InjectView(R.id.sp_Gender)
    private Spinner sp_Gender;




    @InjectView(R.id.edtUserName)
    AnyEditTextView edtUserName;

    @InjectView(R.id.edtTagLine)
    AnyEditTextView edtTagLine;

    @InjectView(R.id.edtEmail)
    AnyEditTextView edtEmail;

    @InjectView(R.id.edtMobileNumber)
    AnyEditTextView edtMobileNumber;

    String fullname,firstName,lastName;
    File profilePic;
    String profilePath;
    ImageLoader imageLoader;



    public static EditTraineeProfileFragment newInstance(){

        return new EditTraineeProfileFragment();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile_trainee, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageLoader = ImageLoader.getInstance();
        sp_Gender();
        sp_Certification();
        SetTraineeProfile();
        setListener();
    }


    private void SetTraineeProfile() {

        edtUserName.setText(prefHelper.getUserName());
        edtEmail.setText(prefHelper.getUser().getEmail());
        edtMobileNumber.setText(prefHelper.getUser().getPhone_number());
        edtTagLine.setText(prefHelper.getUser().getUser_status());
        imageLoader.displayImage(prefHelper.getUser().getProfile_image(), civ_profile_pic);



    }

    private void EditProfile() {

        fullname=edtUserName.getText().toString();
        if(fullname.contains(" ")) {
            String[] name = fullname.split(" ");
            firstName = name[0];
            lastName = name[1];
        }
        else
        {
            firstName=fullname;
            lastName=" ";
        }
        MultipartBody.Part profile_picture = null;
        if(profilePic != null) {
            profile_picture = MultipartBody.Part.createFormData("profile_picture", profilePath,
                    RequestBody.create(MediaType.parse("image/*"), profilePic));
        }
        Call<ResponseWrapper<RegistrationResult>> callBack = webService.UpdateTrainee(
                RequestBody.create(MediaType.parse("text/plain"),prefHelper.getUserId()),
                RequestBody.create(MediaType.parse("text/plain"),firstName),
                RequestBody.create(MediaType.parse("text/plain"),lastName),
                RequestBody.create(MediaType.parse("text/plain"),edtTagLine.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),edtMobileNumber.getText().toString()),
                profile_picture
        );

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call,
                                   Response<ResponseWrapper<RegistrationResult>> response) {

                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
                else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResult>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });

}

    private void sp_Gender() {
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

    }

    private void sp_Certification() {

        List<String> categories = new ArrayList<>();
        categories.add(getString(R.string.male));
        categories.add(getString(R.string.femaile));
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

    }

    private void setListener() {
        btnChangeProfilePhoto.setOnClickListener(this);
        getMainActivity().setImageSetter(this);
        edtUserName.setOnClickListener(this);
        edtTagLine.setOnClickListener(this);
        edtMobileNumber.setOnClickListener(this);
        edtEmail.setOnClickListener(this);
        edtEmail.setEnabled(false);
        edtEmail.setFocusable(false);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();

        titleBar.showSaveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                EditProfile();
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

        }

    }

    /*@Override
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
            profilePic = new File(imagePath);
            profilePath=imagePath;
            ImageLoader.getInstance().displayImage(
                    "file:///" +imagePath, civ_profile_pic);
        }
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath) {

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
