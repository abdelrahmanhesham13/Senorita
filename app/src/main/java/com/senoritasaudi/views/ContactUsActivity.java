package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityContactUsBinding;
import com.senoritasaudi.events.OnClickListener;
import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.storeutils.StoreManager;
import com.senoritasaudi.viewmodels.SendFeedbackViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

import java.util.ArrayList;

public class ContactUsActivity extends BaseActivityWithViewModel<SendFeedbackViewModel,ActivityContactUsBinding> implements OnClickListener {

    public int messageType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.call_us));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivityBinding().setClickHandler(this);
        setupSpinner();
    }

    private void setupSpinner() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(getString(R.string.message_type));
        strings.add(getString(R.string.complaints));
        strings.add(getString(R.string.proposals));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, strings) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(ContextCompat.getColor(ContactUsActivity.this,R.color.colorPrimary));
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getActivityBinding().departmentsSpinner.setAdapter(spinnerArrayAdapter);
        getActivityBinding().departmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(ContactUsActivity.this,R.color.colorPrimary));
                if (position != 0) {
                    if (position == 1) {
                        messageType = 1;
                    } else if (position == 2) {
                        messageType = 2;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected SendFeedbackViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(SendFeedbackViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        String name = getActivityBinding().editText6.getText().toString().trim();
        String phoneNumber = getActivityBinding().editText8.getText().toString().trim();
        String message = getActivityBinding().editText9.getText().toString().trim();
        switch (v.getId()) {
            case R.id.next_button:
                if (name.isEmpty()) {
                    getActivityBinding().editText6.setError(getString(R.string.enter_your_name));
                    getActivityBinding().editText6.requestFocus();
                } else if (phoneNumber.isEmpty()) {
                    getActivityBinding().editText8.setError(getString(R.string.enter_phone));
                    getActivityBinding().editText8.requestFocus();
                } else if (message.isEmpty()) {
                    getActivityBinding().editText9.setError(getString(R.string.enter_message));
                    getActivityBinding().editText9.requestFocus();
                } else if (messageType == -1) {
                    Toast.makeText(this, "من فضلك ادخل نوع الرساله", Toast.LENGTH_SHORT).show();
                }else {
                    getActivityBinding().editText6.setError(null);
                    getActivityBinding().editText8.setError(null);
                    getActivityBinding().editText9.setError(null);
                    getActivityBinding().progressParent.setVisibility(View.VISIBLE);
                    sendFeedback(name,phoneNumber,message, String.valueOf(messageType));
                }
                break;
        }
    }

    private void sendFeedback(String name, String phoneNumber, String message,String type) {
        getActivityViewModel().sendFeedBack(name,phoneNumber,message,type).observe(this, new Observer<FeedbackResponse>() {
            @Override
            public void onChanged(FeedbackResponse feedbackResponse) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (feedbackResponse != null && feedbackResponse.getStatus()) {
//                    if (StoreManager.getAppLanguage(ContactUsActivity.this).equals("ar")) {
//                        Toast.makeText(ContactUsActivity.this, feedbackResponse.getMessageAr(), Toast.LENGTH_LONG).show();
//                    } else {
                        Toast.makeText(ContactUsActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_LONG).show();
                    //}
                    finish();
                } else if (feedbackResponse != null && !feedbackResponse.getStatus()) {
//                    if (StoreManager.getAppLanguage(ContactUsActivity.this).equals("ar")) {
//                        Toast.makeText(ContactUsActivity.this, feedbackResponse.getMessageAr(), Toast.LENGTH_LONG).show();
//                    } else {
                        Toast.makeText(ContactUsActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_LONG).show();
                    //}
                } else {
                    Toast.makeText(ContactUsActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
