package com.example.nowmoneytask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nowmoneytask.R;
import com.example.nowmoneytask.api.Status;
import com.example.nowmoneytask.databinding.FragmentLoginBinding;
import com.example.nowmoneytask.di.Injectable;
import com.example.nowmoneytask.repository.model.LoginResponse;
import com.example.nowmoneytask.repository.model.UserBody;
import com.example.nowmoneytask.util.SessionManager;
import com.example.nowmoneytask.util.validation.FieldsValidation.IFieldValidation;
import com.example.nowmoneytask.util.validation.FieldsValidation.IValidationHandler;
import com.example.nowmoneytask.util.validation.FieldsValidation.ValidateFields;
import com.example.nowmoneytask.util.validation.ValidationRules.ValidateNameNew;
import com.example.nowmoneytask.util.validation.ValidationViews.ViewFieldEditText;
import com.example.nowmoneytask.util.validation.error.ValidationError;
import com.example.nowmoneytask.viewmodel.LoginViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;


public class LoginFragment extends Fragment implements Injectable {


    private FragmentLoginBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private LoginViewModel viewModel;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);


        binding.signInBtn.setOnClickListener(v -> {

            // ahmed
            // AhmeD123

            String username = Objects.requireNonNull(binding.usernameEd.getText()).toString();
            String password = Objects.requireNonNull(binding.passwordEd.getText()).toString();


            IFieldValidation[] iFieldValidations = new IFieldValidation[]{

                    new ValidateNameNew(requireContext(), new ViewFieldEditText(binding.usernameEd), username, getString(R.string.please_enter_your_name), 5, 50),
                    new ValidateNameNew(requireContext(), new ViewFieldEditText(binding.passwordEd), password, getString(R.string.please_enter_your_password), 5, 50),

            };

            ValidateFields fields = new ValidateFields(iFieldValidations);

            fields.validate(requireContext(), new IValidationHandler() {
                @Override
                public void onValidationSuccess() {

                    UserBody body = new UserBody(username, password);

                    // For UI Test Only
                    if (((MainActivity) requireActivity()).mIdlingResource != null) {
                        Objects.requireNonNull(((MainActivity) requireActivity()).mIdlingResource).setIdleState(false);

                    }

                    viewModel.setUserBody(body);

                }

                @Override
                public void onValidationFailed(List<ValidationError> validationErrors) {
                    for (ValidationError error : validationErrors) {
                        error.displayError();
                    }
                }
            });


        });


        viewModel.getLoginResponse().observe(getViewLifecycleOwner(), resource -> {


            if (resource != null) {


                if (resource.status == Status.LOADING) {
                    binding.progressbar.setVisibility(View.VISIBLE);
                } else if (resource.status == Status.ERROR) {
                    binding.progressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "network failure." + resource.message, Toast.LENGTH_SHORT).show();

                } else if (resource.status == Status.SUCCESS && resource.data != null) {

                    // For UI Test Only
                    if (((MainActivity) requireActivity()).mIdlingResource != null) {
                        Objects.requireNonNull(((MainActivity) requireActivity()).mIdlingResource).setIdleState(true);

                    }


                    binding.progressbar.setVisibility(View.GONE);

                    LoginResponse response = resource.data;

                    Log.e("LoginFragment", response.getToken());

                    SessionManager sessionManager = new SessionManager(getActivity());
                    sessionManager.createLoginSession(response.getToken());

                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    requireActivity().finish();

                }


            }


        });


        return binding.getRoot();
    }


}