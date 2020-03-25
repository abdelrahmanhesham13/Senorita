package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
import com.senoritasaudi.databinding.ActivitySearchBinding;
import com.senoritasaudi.models.responseModels.ClinicsResponseModel;
import com.senoritasaudi.models.responseModels.DepartmentResponseModel;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

import java.util.ArrayList;

public class SearchActivity extends BaseActivityWithViewModel<MainViewModel, ActivitySearchBinding> {

    DepartmentResponseModel departmentResponseModel;
    ClinicsResponseModel clinicsResponseModel;
    String selectedDepartmentId = "0";
    String selectedClinicId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ابحث عن العروض");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getActivityViewModel().getDepartmentResponseModelLiveData().observe(SearchActivity.this, new Observer<DepartmentResponseModel>() {
            @Override
            public void onChanged(DepartmentResponseModel departmentResponseModel) {
                if (departmentResponseModel != null) {
                    SearchActivity.this.departmentResponseModel = departmentResponseModel;
                    setupSpinner();
                }
                getActivityViewModel().getClinics().observe(SearchActivity.this, new Observer<ClinicsResponseModel>() {
                    @Override
                    public void onChanged(ClinicsResponseModel clinicsResponseModel) {
                        getActivityBinding().progressParent.setVisibility(View.GONE);
                        if (clinicsResponseModel != null) {
                            SearchActivity.this.clinicsResponseModel = clinicsResponseModel;
                            setupSpinner2();
                        }
                    }
                });
            }
        });

        getActivityBinding().searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String offerId = getActivityBinding().editText.getText().toString().trim();
                if (offerId.isEmpty() && selectedClinicId.equals("0") && selectedDepartmentId.equals("0")) {
                    Toast.makeText(SearchActivity.this, R.string.one_filter, Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(SearchActivity.this, DepartmentOffersActivity.class).
                            putExtra("type", "search").
                            putExtra("departmentId", selectedDepartmentId).
                            putExtra("clinicId", selectedClinicId).
                            putExtra("offerId",offerId));
                }
            }
        });
    }

    private void setupSpinner2() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("اختر القسم");
        for (int i = 0; i < clinicsResponseModel.getClinics().size(); i++) {
            strings.add(clinicsResponseModel.getClinics().get(i).getName());
        }
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
                    tv.setTextColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimary));
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getActivityBinding().clinicsSpinner.setAdapter(spinnerArrayAdapter);
        getActivityBinding().clinicsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimary));
                if (position != 0) {
                    selectedClinicId = clinicsResponseModel.getClinics().get(position - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSpinner() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("اختر مقدم الخدمه");
        for (int i = 0; i < departmentResponseModel.getCategories().size(); i++) {
            strings.add(departmentResponseModel.getCategories().get(i).getName());
        }
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
                    tv.setTextColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimary));
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
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimary));
                if (position != 0) {
                    selectedDepartmentId = departmentResponseModel.getCategories().get(position - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search;
    }

    @Override
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
