package com.example.nguyen.multihopdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {

    private View rootView;
    private Button joinButton;
    private EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login_fragment, container, false);

        joinButton = rootView.findViewById(R.id.joinButton);
        editText = rootView.findViewById(R.id.editText);



        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);

                BroadCastFragment fragment = new BroadCastFragment();


                fragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction
                        .replace(R.id.frame_main,fragment,fragment.getTag())
                        .addToBackStack(fragment.getTag())
                        .commit();
            }
        });

        return rootView;
    }
}
