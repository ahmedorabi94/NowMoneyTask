package com.example.nowmoneytask.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nowmoneytask.R;
import com.example.nowmoneytask.api.Status;
import com.example.nowmoneytask.databinding.FragmentReceiversBinding;
import com.example.nowmoneytask.di.Injectable;
import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.util.SessionManager;
import com.example.nowmoneytask.viewmodel.ReceiversViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;


public class ReceiversFragment extends Fragment implements Injectable {


    private FragmentReceiversBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ReceiversViewModel viewModel;

    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    private String token;

    private UserCallback callback = new UserCallback() {
        @Override
        public void onUserClick(User user) {

            Bundle args = new Bundle();
            args.putString("Id", user.get_id());
            args.putString("name", user.getName());
            args.putString("number", user.getNumber());
            args.putString("address", user.getAddress());


            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_receiversFragment2_to_addDialogFragment2, args);
        }
    };


    public ReceiversFragment() {
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receivers, container, false);

        initViews();

        SessionManager sessionManager = new SessionManager(getContext());

        token = sessionManager.getUSerDetails().get(SessionManager.KEY_TOKEN);


        binding.addReceiverBtn.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_receiversFragment2_to_addDialogFragment2));


        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(ReceiversViewModel.class);

        viewModel.setToken(token);

        viewModel.getAllReceivers().observe(getViewLifecycleOwner(), resource -> {

            if (resource != null) {


                if (resource.status == Status.LOADING) {
                    binding.progressbar.setVisibility(View.VISIBLE);
                } else if (resource.status == Status.ERROR) {
                    binding.progressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "network failure. " + resource.message, Toast.LENGTH_SHORT).show();

                } else if (resource.status == Status.SUCCESS && resource.data != null) {
                    binding.progressbar.setVisibility(View.GONE);

                    List<User> response = resource.data;

                    Log.e("ReceiversFragment", response.size() + "");

                    if (response.size() == 0) {
                        binding.textVewEmpty.setVisibility(View.VISIBLE);
                    } else {
                        binding.textVewEmpty.setVisibility(View.GONE);
                        adapter = new TaskAdapter(callback);
                        adapter.submitList(response);
                        recyclerView.setAdapter(adapter);
                    }

                }

            }

        });

    }

    private void initViews() {
        recyclerView = binding.recyclerView;
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}