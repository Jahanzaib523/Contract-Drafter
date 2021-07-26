package Fragments;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myapp.contractdrafter.R;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Home_Fragment extends Fragment
{
    private CircleImageView mic;
    private TextView output;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.home_fragment, container, false);

        mic = root.findViewById(R.id.getvoiceinput);
        output = root.findViewById(R.id.ouputtext);

        mic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getInput(root);
                return true;
            }
        });
        return root;
    }

    public void getInput(View v)
    {
        Intent voice_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        if(voice_intent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            startActivityForResult(voice_intent, 10);
        }
        else
        {
            Toast.makeText(getContext(), "Recognizer Does Not Support Here.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 10:
                if(resultCode == RESULT_OK && data !=null)
                {
                    ArrayList<String> arr = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    output.setText(arr.get(0));
                }
                break;
        }
    }
}
