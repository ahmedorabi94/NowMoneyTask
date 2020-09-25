package com.example.nowmoneytask.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nowmoneytask.R;
import com.example.nowmoneytask.api.Status;
import com.example.nowmoneytask.databinding.FragmentAddDialogBinding;
import com.example.nowmoneytask.di.Injectable;
import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.repository.model.StatusResponse;
import com.example.nowmoneytask.viewmodel.ReceiversViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;


public class AddDialogFragment extends DialogFragment implements Injectable {

    private FragmentAddDialogBinding binding;


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ReceiversViewModel viewModel;

    private String _Id, name, number, address;


    public AddDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            _Id = getArguments().getString("Id");
            name = getArguments().getString("name");
            number = getArguments().getString("number");
            address = getArguments().getString("address");

        }

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_dialog, container, false);


        if (_Id != null) {
            binding.nameEd.setText(name);
            binding.numberEd.setText(number);
            binding.addressEd.setText(address);

            binding.nameEd.setEnabled(false);
            binding.numberEd.setEnabled(false);
            binding.addressEd.setEnabled(false);

            binding.addBtn.setVisibility(View.GONE);
        } else {
            binding.deleteBtn.setVisibility(View.GONE);
        }


        binding.addBtn.setOnClickListener(v -> {
            String name = Objects.requireNonNull(binding.nameEd.getText()).toString();
            String number = Objects.requireNonNull(binding.numberEd.getText()).toString();
            String address = Objects.requireNonNull(binding.addressEd.getText()).toString();

            User user = new User(name, number, address);

            if (((HomeActivity) requireActivity()).mIdlingResource != null){
                Objects.requireNonNull(((HomeActivity) requireActivity()).mIdlingResource).setIdleState(false);

            }


            viewModel.setUser(user);

        });


        binding.deleteBtn.setOnClickListener(v -> {

            if (_Id != null) {

                if (((HomeActivity) requireActivity()).mIdlingResource != null){
                    Objects.requireNonNull(((HomeActivity) requireActivity()).mIdlingResource).setIdleState(false);

                }
                viewModel.setId(_Id);

            }

        });


        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(ReceiversViewModel.class);

        viewModel.getAddResponseLiveData().observe(getViewLifecycleOwner(), resource -> {

            if (resource != null) {

                if (resource.status == Status.LOADING) {
                    binding.progressbar.setVisibility(View.VISIBLE);
                } else if (resource.status == Status.ERROR) {
                    binding.progressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "network failure. " + resource.message, Toast.LENGTH_SHORT).show();

                } else if (resource.status == Status.SUCCESS && resource.data != null) {

                    if (((HomeActivity) requireActivity()).mIdlingResource != null){
                        Objects.requireNonNull(((HomeActivity) requireActivity()).mIdlingResource).setIdleState(true);

                    }

                    binding.progressbar.setVisibility(View.GONE);

                    StatusResponse response = resource.data;

                    Log.e("AddDialogFragment Add", response.getStatus());


                  //  resource = null;
                    viewModel.setUser(null);
                    viewModel.setForceUpdate(true);

                    dismiss();

                    //  Navigation.findNavController(binding.getRoot()).popBackStack();


                }


            }

        });


        viewModel.getDeleteResponseLiveData().observe(getViewLifecycleOwner(), resource -> {


            if (resource != null) {

                if (resource.status == Status.LOADING) {
                    binding.progressbar.setVisibility(View.VISIBLE);
                } else if (resource.status == Status.ERROR) {
                    binding.progressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "network failure. " + resource.message, Toast.LENGTH_SHORT).show();

                } else if (resource.status == Status.SUCCESS && resource.data != null) {

                    if (((HomeActivity) requireActivity()).mIdlingResource != null){
                        Objects.requireNonNull(((HomeActivity) requireActivity()).mIdlingResource).setIdleState(true);

                    }

                    binding.progressbar.setVisibility(View.GONE);

                    StatusResponse response = resource.data;

                    Log.e("AddDialoFragment Delete", response.getStatus());


                  //  resource = null;
                    viewModel.setId(null);

                    viewModel.setForceUpdate(true);

                    // Navigation.findNavController(binding.getRoot()).popBackStack();


                    dismiss();

                }


            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        WindowManager.LayoutParams params = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).getAttributes();

        params.width = 1100;
        params.height = 1200;

        Objects.requireNonNull(getDialog().getWindow()).setAttributes(params);

    }
}