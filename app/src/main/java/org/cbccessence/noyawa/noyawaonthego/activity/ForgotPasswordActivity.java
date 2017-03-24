package org.cbccessence.noyawa.noyawaonthego.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.cbccessence.noyawa.noyawaonthego.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mac on 1/21/16.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    @Bind(R.id.input_email)
    EditText _usernameText;

    @Bind(R.id.btn_submit)
    Button _submitButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ButterKnife.bind(this);

        _submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });


    }


    public void forgotPassword(){
         // TODO: 1/25/16
    }


}
