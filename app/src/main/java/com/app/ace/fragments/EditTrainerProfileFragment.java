package com.app.ace.fragments;

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
import com.app.ace.entities.SpinnerDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.EditTrainerData;
import com.app.ace.interfaces.IGetLocation;
import com.app.ace.ui.dialogs.DialogFactory;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.commons.lang3.StringUtils;

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

import static com.app.ace.R.id.txt_pref_training_gym;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class EditTrainerProfileFragment extends BaseFragment implements View.OnClickListener ,AdapterView.OnItemSelectedListener , MainActivity.ImageSetter,EditTrainerData,IGetLocation{

    @InjectView(R.id.btnChangeProfilePhoto)
    private Button btnChangeProfilePhoto;

    @InjectView(R.id.civ_profile_pic)
    private CircleImageView civ_profile_pic;

    @InjectView(R.id.sp_speciality)
    private Spinner sp_speciality;

    @InjectView(R.id.sp_Certification)
    private Spinner sp_Certification;

    @InjectView(R.id.edtUserName)
    AnyEditTextView edtUserName;

    @InjectView(R.id.edtUniversity)
    AnyEditTextView edtUniversity;

    @InjectView(R.id.txtGymLocatoin)
    AnyTextView txtGymLocatoin;

    @InjectView(R.id.edtEmail)
    AnyEditTextView edtEmail;

    @InjectView(R.id.edtMobileNumber)
    AnyEditTextView edtMobileNumber;

    File profilePic;
    String profilePath;

    ImageLoader imageLoader;
    String fullname,firstName,lastName;




    @InjectView(R.id.sp_Gender)
    private Spinner sp_Gender;

    String Education="";
    String Speciality="";
    String gymLocation="";
    String lat="";
    String log="";

    ArrayList<String> EducationArray = new ArrayList<>();
    ArrayList<String> SpecialityArray = new ArrayList<>();



    public static EditTrainerProfileFragment newInstance(){

        return new EditTrainerProfileFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
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

        ShowProfile();

        setListener();
        setSpCertification();
        setSpSpeciality();






    }

    private void ShowProfile() {
        edtUserName.setText(prefHelper.getUserName());
        edtMobileNumber.setText(prefHelper.getUser().getPhone_number());
        edtEmail.setText(prefHelper.getUser().getEmail());
        edtEmail.setEnabled(false);
        edtEmail.setFocusable(false);
        edtUniversity.setText(prefHelper.getUser().getUniversity());
        txtGymLocatoin.setText(prefHelper.getUser().getGym_address());
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
        if(prefHelper.getUser().getGym_address()!=null)
        {
            gymLocation=prefHelper.getUser().getGym_address();
        }

        MultipartBody.Part profile_picture = null;
        if(profilePic != null) {
           profile_picture = MultipartBody.Part.createFormData("profile_picture", profilePath, RequestBody.create(MediaType.parse("image/*"), profilePic));
        }

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.UpdateTrainer(

                RequestBody.create(MediaType.parse("text/plain"),prefHelper.getUserId()),
               // RequestBody.create(MediaType.parse("text/plain"),password),
                RequestBody.create(MediaType.parse("text/plain"),firstName),
                RequestBody.create(MediaType.parse("text/plain"),lastName),
                RequestBody.create(MediaType.parse("text/plain"),edtMobileNumber.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),edtUniversity.getText().toString()),
                profile_picture,
                RequestBody.create(MediaType.parse("text/plain"),Education),
                RequestBody.create(MediaType.parse("text/plain"),Speciality),
                RequestBody.create(MediaType.parse("text/plain"),gymLocation),
                RequestBody.create(MediaType.parse("text/plain"),lat),
                RequestBody.create(MediaType.parse("text/plain"),log)
                 );

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {
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

    private void setListener() {
        btnChangeProfilePhoto.setOnClickListener(this);
        edtUserName.setOnClickListener(this);
        edtUniversity.setOnClickListener(this);
        edtEmail.setOnClickListener(this);
        edtMobileNumber.setOnClickListener(this);
        txtGymLocatoin.setOnClickListener(this);
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
               // setSpCertification();
                //setSpSpeciality();
                Education = StringUtils.join(EducationArray,",");
                Speciality = StringUtils.join(SpecialityArray,",");
                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
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

            case R.id.sp_Certification:

               // setSpCertification();


                break;

            case R.id.sp_speciality:

                //setSpSpeciality();

                break;

            case R.id.txtGymLocatoin:

                MapControllerFragment mapControllerFragment = MapControllerFragment.newInstance();
                mapControllerFragment.setDelegate(this);

                DialogFactory.showMapControllerDialog(getDockActivity(), mapControllerFragment);

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
    private void setSpCertification() {

        ArrayList<String> Certification= new ArrayList<String>();

        Certification.add("Select Certification");
        Certification.add("Degree ");
        Certification.add("NASM");
        Certification.add("NCSA");
        Certification.add("ACSM");
        Certification.add("CHECK");
        Certification.add("ACE");


     ArrayList<SpinnerDataItem> listVOs = new ArrayList<>();

        for (int i = 0; i < Certification.size(); i++) {
            SpinnerDataItem stateVO = new SpinnerDataItem();
            stateVO.setTitle(Certification.get(i));
            if(prefHelper.getUser().getEducation().contains(Certification.get(i))) {
                stateVO.setSelected(true);
            }
            listVOs.add(stateVO);
        }
        SpinerAdapter myAdapter = new SpinerAdapter(getDockActivity(), 0, listVOs,this);
        myAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sp_Certification.setAdapter(myAdapter);
    }

    private void setSpSpeciality() {
        ArrayList<String> Speciality= new ArrayList<String>();

        Speciality.add("Select Speciality");
        Speciality.add("Flexibility training ");
        Speciality.add("Dynamic strength training");
        Speciality.add("Static strength training");
        Speciality.add("Circuit training");
        Speciality.add("Aerobic training");
        Speciality.add("Body Building");
        Speciality.add("Loose Weight");

        ArrayList<SpinnerDataItem> listVOs = new ArrayList<>();

        for (int i = 0; i < Speciality.size(); i++) {
            SpinnerDataItem stateVO = new SpinnerDataItem();
            stateVO.setTitle(Speciality.get(i));
            if(prefHelper.getUser().getSpeciality().contains(Speciality.get(i))) {
                stateVO.setSelected(true);
            }
            listVOs.add(stateVO);
        }
        SpinerAdapter myAdapter = new SpinerAdapter(getDockActivity(),0, listVOs,this);
        myAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sp_speciality.setAdapter(myAdapter);
    }

    @Override
    public void updateEducationData(ArrayList<SpinnerDataItem> listState) {

        EducationArray.clear();


        for (SpinnerDataItem item : listState)

        {
            if (item.isSelected()) {
                EducationArray.add(item.getTitle());
            }

        }


    }

    @Override
    public void updateSpecialtyData(ArrayList<SpinnerDataItem> listState) {

        SpecialityArray.clear();


        for (SpinnerDataItem item : listState)

        {
            if (item.isSelected()) {
                SpecialityArray.add(item.getTitle());
            }

        }



    }

    @Override
    public void onLocationSet(LatLng location, String formattedAddress) {

        txtGymLocatoin.setText(formattedAddress);
        gymLocation=formattedAddress;
        lat=String.valueOf(location.latitude);
        log=String.valueOf(location.longitude);
    }
}
