package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.senoritasaudi.R;
import com.senoritasaudi.adapters.DepartmentsAdapter;
import com.senoritasaudi.databinding.ActivityDepartmentsBinding;
import com.senoritasaudi.events.OnItemClicked;
import com.senoritasaudi.models.responseModels.DepartmentResponseModel;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;

public class DepartmentsActivity extends BaseActivityWithViewModel<MainViewModel, ActivityDepartmentsBinding> {

    private DepartmentsAdapter mDepartmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.departments));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDepartmentsAdapter = new DepartmentsAdapter(this, new OnItemClicked() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(DepartmentsActivity.this,DepartmentOffersActivity.class);
                intent.putExtra("departmentId",mDepartmentsAdapter.getDepartmentIdForPosition(position));
                startActivity(intent);
            }
        },2);
        getActivityBinding().departmentsRecycler.setHasFixedSize(true);
        getActivityBinding().departmentsRecycler.setLayoutManager(new GridLayoutManager(this,2));
        getActivityBinding().departmentsRecycler.setAdapter(mDepartmentsAdapter);
        getActivityViewModel().getDepartmentResponseModelLiveData().observe(this, new Observer<DepartmentResponseModel>() {
            @Override
            public void onChanged(DepartmentResponseModel departmentResponseModel) {
                getActivityBinding().progressBar.setVisibility(View.GONE);
                if (departmentResponseModel != null)
                    mDepartmentsAdapter.addAll(departmentResponseModel.getCategories());
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_departments;
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
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }
}
