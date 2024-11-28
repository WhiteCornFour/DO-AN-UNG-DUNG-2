package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.SharedViewModel_User;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;
import com.google.android.material.textfield.TextInputEditText;

public class User_Two_Step_Verification extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    SharedViewModel_User sharedViewModel_user;
    ImageView imgBackToPrivacyMainPage;
    Switch switchVerificationMode;
    TextView tvVerificationCode;
    LinearLayout layoutEditVerificationCode;
    UserHandler userHandler;
    User user = new User();
    String maNguoiDung = "";
    String cheDoXacNhan= "";
    String maXacNhan= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_two_step_verification);
        addControl();
        sharedViewModel_user = new ViewModelProvider(this).get(SharedViewModel_User.class);
        userHandler = new UserHandler(User_Two_Step_Verification.this, DB_NAME, null, DB_VERSION);
        user = getIntentUser();
        maNguoiDung = user.getMaNguoiDung();
        cheDoXacNhan = user.getCheDoXacNhan();
        Log.d("cheDoXacNhan", cheDoXacNhan);
        maXacNhan = user.getMaXacNhan();

        sharedViewModel_user.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                setUpTextViewData();
            }
        });
        setUpSwitchData(cheDoXacNhan);
        setUpTextViewData();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        super.onBackPressed();
    }

    void addControl() {
        imgBackToPrivacyMainPage = findViewById(R.id.imgBackToPrivacyMainPage);
        switchVerificationMode = findViewById(R.id.switchVerificationMode);
        tvVerificationCode = findViewById(R.id.tvVerificationCode);
        layoutEditVerificationCode = findViewById(R.id.layoutEditVerificationCode);
    }

    void addEvent() {
        imgBackToPrivacyMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Two_Step_Verification.this, User_Privacy.class);
                intent.putExtra("userBackFromPageToPrivacy", user);
                startActivity(intent);
                finish();
            }
        });

        switchVerificationMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (maXacNhan != null) {
                    //neu ma xac nhan co thi thuc hien
                    if (isChecked){
                        userHandler.updateCheDoXacNhan("On", maNguoiDung);
                        user.setCheDoXacNhan("On");
                        Log.d("cheDoXacNhanSwitch", user.getCheDoXacNhan());
                        switchVerificationMode.setText("On");
                        sharedViewModel_user.setUser(user);
                        Toast.makeText(User_Two_Step_Verification.this, "Set to On", Toast.LENGTH_SHORT).show();
                    } else {
                        userHandler.updateCheDoXacNhan("Off", maNguoiDung);
                        user.setCheDoXacNhan("Off");
                        Log.d("cheDoXacNhanSwitch", user.getCheDoXacNhan());
                        switchVerificationMode.setText("Off");
                        sharedViewModel_user.setUser(user);
                        Toast.makeText(User_Two_Step_Verification.this, "Set to Off", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //neu ma khong co ma thi gat switch sang off va gui thong bao
                    switchVerificationMode.setOnCheckedChangeListener(null);
                    switchVerificationMode.setChecked(!isChecked);
                    switchVerificationMode.setOnCheckedChangeListener(this);
                    showWarningHavingVerificationCodeFirst();
                }
            }
        });

        layoutEditVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVerificationPasswordDialog();
            }
        });
    }

    private User getIntentUser() {
        Intent intent = getIntent();
        User userIntent = (User) intent.getSerializableExtra("userFromPrivacyToTwoStepVerification");
        return userIntent;
    }

    private void setUpSwitchData(String cheDoXacNhan) {
        if (cheDoXacNhan.equals("On"))
        {
            switchVerificationMode.setChecked(true);
            switchVerificationMode.setText("On");
        } else {
            switchVerificationMode.setChecked(false);
            switchVerificationMode.setText("Off");
        }
    }

    private void setUpTextViewData() {
        if(maXacNhan != null) {
            String maskedCode = maXacNhan.substring(0, maXacNhan.length() - 3) + "***"; //set up cho 3 so cuoi cua ma xac nhan thanh 3 dau sao
            tvVerificationCode.setText(maskedCode);
        } else {
            tvVerificationCode.setText("");
        }
    }

    private void showWarningHavingVerificationCodeFirst() {
        ConstraintLayout successConstraintLayout = findViewById(R.id.warningHavingVerificationCodeConstraintLayout);
        View view = LayoutInflater.from(User_Two_Step_Verification.this).inflate(R.layout.custom_warning_having_verification_code_dialog, successConstraintLayout);

        Button btnOkHavingVerificationCode = view.findViewById(R.id.btnOkHavingVerificationCode);

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Two_Step_Verification.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnOkHavingVerificationCode.findViewById(R.id.btnOkHavingVerificationCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showVerificationPasswordDialog() {
        ConstraintLayout successConstraintLayout = findViewById(R.id.verificationPasswordConstraintLayout);
        View view = LayoutInflater.from(User_Two_Step_Verification.this).inflate(R.layout.custom_enter_password_verification_code_dialog, successConstraintLayout);

        TextInputEditText edtVerificationPassword = view.findViewById(R.id.edtVerificationPassword);
        Button btnEnterVerificationPassword = view.findViewById(R.id.btnEnterVerificationPassword);
        Button btnVerificationPasswordComeback = view.findViewById(R.id.btnVerificationPasswordComeback);

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Two_Step_Verification.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnEnterVerificationPassword.findViewById(R.id.btnEnterVerificationPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationPassword = edtVerificationPassword.getText().toString().trim();
                boolean verifiedPasswordStatus = false;
                verifiedPasswordStatus = userHandler.checkEnterOldPasswordIsRight(user.getMaNguoiDung(), verificationPassword);
                if(verifiedPasswordStatus) {
                    if(maXacNhan != null) {
                        showCheckingOldVerificationCodeDialog();
                    } else {
                        showCreateNewVerificationCodeDialog();
                    }
                } else {
                    Toast.makeText(User_Two_Step_Verification.this, "Wrong password! Try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
            }
        });

        btnVerificationPasswordComeback.findViewById(R.id.btnVerificationPasswordComeback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showCheckingOldVerificationCodeDialog() {
        ConstraintLayout successConstraintLayout = findViewById(R.id.verificationCodeConstraintLayout);
        View view = LayoutInflater.from(User_Two_Step_Verification.this).inflate(R.layout.custom_enter_verification_code_dialog, successConstraintLayout);

        EditText edtFirstNumber = view.findViewById(R.id.edtFirstNumber);
        EditText edtSecondNumber = view.findViewById(R.id.edtSecondNumber);
        EditText edtThirdNumber = view.findViewById(R.id.edtThirdNumber);
        EditText edtFourthNumber = view.findViewById(R.id.edtFourthNumber);
        EditText edtFifthNumber = view.findViewById(R.id.edtFifthNumber);
        Button btnEnterCode = view.findViewById(R.id.btnEnterCode);
        Button btnComeback = view.findViewById(R.id.btnComeback);

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Two_Step_Verification.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnEnterCode.findViewById(R.id.btnEnterCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldVerificationCode = edtFirstNumber.getText().toString().trim() +
                        edtSecondNumber.getText().toString().trim() +
                        edtThirdNumber.getText().toString().trim() +
                        edtFourthNumber.getText().toString().trim() +
                        edtFifthNumber.getText().toString().trim();
                if (oldVerificationCode.isEmpty())
                {
                    Toast.makeText(User_Two_Step_Verification.this, "Please enter new code!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (maXacNhan == null & !maXacNhan.equals(oldVerificationCode)) {
                    Toast.makeText(User_Two_Step_Verification.this, "Verification code is not valid!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(User_Two_Step_Verification.this, "Please enter your new verification code!", Toast.LENGTH_SHORT).show();
                    showCreateNewVerificationCodeDialog();
                    alertDialog.dismiss();
                }
            }

        });

        btnComeback.findViewById(R.id.btnComeback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showCreateNewVerificationCodeDialog() {
        ConstraintLayout successConstraintLayout = findViewById(R.id.verificationNewCodeConstraintLayout);
        View view = LayoutInflater.from(User_Two_Step_Verification.this).inflate(R.layout.custom_enter_new_verification_code_dialog, successConstraintLayout);

        EditText edtFirstNumber = view.findViewById(R.id.edtFirstNumber);
        EditText edtSecondNumber = view.findViewById(R.id.edtSecondNumber);
        EditText edtThirdNumber = view.findViewById(R.id.edtThirdNumber);
        EditText edtFourthNumber = view.findViewById(R.id.edtFourthNumber);
        EditText edtFifthNumber = view.findViewById(R.id.edtFifthNumber);
        Button btnEnterNewCode = view.findViewById(R.id.btnEnterNewCode);
        Button btnNewCodeComeback = view.findViewById(R.id.btnNewCodeComeback);

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Two_Step_Verification.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnEnterNewCode.findViewById(R.id.btnEnterNewCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newVerificationCode = edtFirstNumber.getText().toString().trim() +
                        edtSecondNumber.getText().toString().trim() +
                        edtThirdNumber.getText().toString().trim() +
                        edtFourthNumber.getText().toString().trim() +
                        edtFifthNumber.getText().toString().trim();
                if (newVerificationCode.isEmpty())
                {
                    Toast.makeText(User_Two_Step_Verification.this, "Please enter new code!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    userHandler.updateUserVerificationCode(newVerificationCode, maNguoiDung);
                    user.setMaXacNhan(newVerificationCode);
                    maXacNhan = newVerificationCode;
                    sharedViewModel_user.setUser(user);
                    Toast.makeText(User_Two_Step_Verification.this, "You have set new verification code successfully!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }

                alertDialog.dismiss();
            }

        });

        btnNewCodeComeback.findViewById(R.id.btnNewCodeComeback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

}