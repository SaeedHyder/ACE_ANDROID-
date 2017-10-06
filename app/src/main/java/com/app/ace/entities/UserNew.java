package com.app.ace.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 10/4/2017.
 */

public class UserNew {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("userType")
    @Expose
    private String usersType;
    @SerializedName("user_name")
    @Expose
    private Object userName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("university")
    @Expose
    private String university;
    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("gym_name")
    @Expose
    private String gymName;
    @SerializedName("gym_address")
    @Expose
    private String gymAddress;
    @SerializedName("gym_latitude")
    @Expose
    private String gymLatitude;
    @SerializedName("gym_longitude")
    @Expose
    private String gymLongitude;
    @SerializedName("speciality")
    @Expose
    private String speciality;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("primary_reason")
    @Expose
    private String primaryReason;
    @SerializedName("gym_days")
    @Expose
    private String gymDays;
    @SerializedName("gym_timing_from")
    @Expose
    private String gymTimingFrom;
    @SerializedName("gym_timing_to")
    @Expose
    private String gymTimingTo;
    @SerializedName("resume")
    @Expose
    private String resume;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("social_media_id")
    @Expose
    private String socialMediaId;
    @SerializedName("social_media_platform")
    @Expose
    private String socialMediaPlatform;
    @SerializedName("notification_status")
    @Expose
    private Integer notificationStatus;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("is_verified")
    @Expose
    private Integer isVerified;
    @SerializedName("private_account")
    @Expose
    private Integer privateAccount;
    @SerializedName("mute_conversation")
    @Expose
    private Integer muteConversation;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("positive_review")
    @Expose
    private Integer positiveReview;
    @SerializedName("negative_review")
    @Expose
    private Integer negativeReview;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUsersType() {
        return usersType;
    }

    public void setUsersType(String usersType) {
        this.usersType = usersType;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getGymAddress() {
        return gymAddress;
    }

    public void setGymAddress(String gymAddress) {
        this.gymAddress = gymAddress;
    }

    public String getGymLatitude() {
        return gymLatitude;
    }

    public void setGymLatitude(String gymLatitude) {
        this.gymLatitude = gymLatitude;
    }

    public String getGymLongitude() {
        return gymLongitude;
    }

    public void setGymLongitude(String gymLongitude) {
        this.gymLongitude = gymLongitude;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPrimaryReason() {
        return primaryReason;
    }

    public void setPrimaryReason(String primaryReason) {
        this.primaryReason = primaryReason;
    }

    public String getGymDays() {
        return gymDays;
    }

    public void setGymDays(String gymDays) {
        this.gymDays = gymDays;
    }

    public String getGymTimingFrom() {
        return gymTimingFrom;
    }

    public void setGymTimingFrom(String gymTimingFrom) {
        this.gymTimingFrom = gymTimingFrom;
    }

    public String getGymTimingTo() {
        return gymTimingTo;
    }

    public void setGymTimingTo(String gymTimingTo) {
        this.gymTimingTo = gymTimingTo;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSocialMediaId() {
        return socialMediaId;
    }

    public void setSocialMediaId(String socialMediaId) {
        this.socialMediaId = socialMediaId;
    }

    public String getSocialMediaPlatform() {
        return socialMediaPlatform;
    }

    public void setSocialMediaPlatform(String socialMediaPlatform) {
        this.socialMediaPlatform = socialMediaPlatform;
    }

    public Integer getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Integer notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(Integer privateAccount) {
        this.privateAccount = privateAccount;
    }

    public Integer getMuteConversation() {
        return muteConversation;
    }

    public void setMuteConversation(Integer muteConversation) {
        this.muteConversation = muteConversation;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getPositiveReview() {
        return positiveReview;
    }

    public void setPositiveReview(Integer positiveReview) {
        this.positiveReview = positiveReview;
    }

    public Integer getNegativeReview() {
        return negativeReview;
    }

    public void setNegativeReview(Integer negativeReview) {
        this.negativeReview = negativeReview;
    }
}
