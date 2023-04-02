package com.doc24x7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.doc24x7.Utils.FragmentActivityMessage;
import com.doc24x7.Utils.GlobalBus;
import com.doc24x7.Utils.Loader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

     Button btnOk;

    private EditText etOldPass;
    private EditText etNewPass;
    private EditText etCnfPass;

    public TextInputLayout tilOldPass;
    public TextInputLayout tilNewPass;
    public TextInputLayout tilCnfPass;

    Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getIds();

     }

    private void getIds() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Change Password");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


       loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        tilOldPass=(TextInputLayout)findViewById(R.id.til_old_pass);
        tilNewPass=(TextInputLayout)findViewById(R.id.til_new_pass);
        tilCnfPass=(TextInputLayout)findViewById(R.id.til_cnf_pass);

        etOldPass=(EditText)findViewById(R.id.et_old_pass);
        etNewPass=(EditText)findViewById(R.id.et_new_pass);
        etCnfPass=(EditText)findViewById(R.id.et_cnf_pass);

        btnOk=(Button)findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);

        etOldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validateOldPass()) {
                    return;
                }
            }
        });

        etNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validateNewPass()) {
                    return;
                }
            }
        });

        etCnfPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validateCnfPass()) {
                    return;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==btnOk)
        {
           /* if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilMethods.INSTANCE.changePassword(this, etOldPass.getText().toString().trim(), etCnfPass.getText().toString().trim(),loader);

            } else {
                UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }*/
        }
    }

    private boolean validateOldPass() {
        if (etOldPass.getText().toString().trim().isEmpty()) {
            tilOldPass.setError(getString(R.string.err_msg_old_pass));
            etOldPass.requestFocus();
            btnOk.setEnabled(false);
            return false;
        }
        else {
            tilOldPass.setErrorEnabled(false);
            btnOk.setEnabled(true);
        }
        return true;
    }

    private boolean validateNewPass() {
        if (etNewPass.getText().toString().trim().isEmpty()) {
            tilNewPass.setError(getString(R.string.err_msg_new_pass));
            etNewPass.requestFocus();
            btnOk.setEnabled(false);
            return false;
        }
        else {
            tilNewPass.setErrorEnabled(false);
            btnOk.setEnabled(true);
        }
        return true;
    }

    private boolean validateCnfPass() {
        if (etCnfPass.getText().toString().trim().isEmpty()) {
            tilCnfPass.setError(getString(R.string.err_msg_cnf_pass));
            etCnfPass.requestFocus();
            btnOk.setEnabled(false);
            return false;
        }
        else if (!etCnfPass.getText().toString().equals(etNewPass.getText().toString())) {
            tilCnfPass.setError(getString(R.string.err_msg_match_pass));
            etCnfPass.requestFocus();
            btnOk.setEnabled(false);
            return false;
        }else {
            tilCnfPass.setErrorEnabled(false);
            btnOk.setEnabled(true);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {

        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();

    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);

        super.onDestroy();
    }

    @Subscribe
    public void onActivityActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("refreshpass")) {
            etOldPass.setText("");
            etNewPass.setText("");
            etCnfPass.setText("");
            etOldPass.requestFocus();
            tilNewPass.setErrorEnabled(false);
            tilCnfPass.setErrorEnabled(false);
        }
    }

}