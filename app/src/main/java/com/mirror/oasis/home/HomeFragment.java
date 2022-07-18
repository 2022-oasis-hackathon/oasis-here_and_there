package com.mirror.oasis.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;

public class HomeFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("home");

    String myKey;
    String myId;
    String myProfile;

    private EditText search;
    private TextView userId;

    private RecyclerView homeRecyclerView;
    private HomeAdapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;


    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        myKey = MainActivity.myKey;
        myId = MainActivity.myId;
        myProfile = MainActivity.myProfile;

        search = (EditText) v.findViewById(R.id.search);
        userId = (TextView) v.findViewById(R.id.userId);

        userId.setText(myId + "님을 위한 추천지");

        search.setImeOptions(EditorInfo.IME_ACTION_DONE);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(getActivity(), textView.getText().toString() + " ", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        return v;
    }
}

