package com.example.nowmoneytask.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nowmoneytask.R;
import com.example.nowmoneytask.databinding.RecyclerItemBinding;
import com.example.nowmoneytask.repository.db.User;

public class TaskAdapter extends ListAdapter<User, TaskAdapter.MyViewHolder> {

    private UserCallback mCallback;


    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getNumber().equals(newItem.getName()) &&
                    oldItem.getAddress().equals(newItem.getAddress());
        }
    };


    protected TaskAdapter(UserCallback callback) {
        super(DIFF_CALLBACK);
        this.mCallback = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_item, parent, false);

        binding.setCallback(mCallback);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = getItem(position);
        holder.binding.setUser(user);
        holder.binding.executePendingBindings();


    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        final RecyclerItemBinding binding;

        MyViewHolder(RecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
