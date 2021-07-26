package Activities;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myapp.contractdrafter.R;

import org.w3c.dom.Text;

import Models.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfile extends AppCompatActivity
{
    protected CircleImageView profilepicture, cameraicon;
    protected EditText first_name, lastname, usertype, phone_number, bio, EMAIL;
    protected RadioGroup genderGroup;
    protected Button savebtn;
    protected ProgressBar progressBar;
    protected Uri imageUri;
    protected int GALLERY_PICK = 1;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    protected String email_ID, password, confirm_password;
    private FirebaseAuth signupMe;
    private static final String USER = "USER";
    User user;
    private String gender;
    private RadioButton radioButton;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        email_ID = (String) getIntent().getStringExtra("email");
        password = (String) getIntent().getStringExtra("password");
        confirm_password = (String) getIntent().getStringExtra("confirm_password");
        EMAIL = (EditText) findViewById(R.id.EmailIDUser);
        EMAIL.setText(email_ID);
        profilepicture = (CircleImageView) findViewById(R.id.circularImageCreateProfile);
        cameraicon = (CircleImageView) findViewById(R.id.cameracircularicon);
        first_name = (EditText) findViewById(R.id.firstNameFieldCreateProfile);
        lastname = (EditText) findViewById(R.id.lastNameFieldCreateProfile);
        usertype = (EditText) findViewById(R.id.UserType);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroupCreateProfile);
        phone_number = (EditText) findViewById(R.id.phoneNoFieldCreateProfile);
        bio = (EditText) findViewById(R.id.bioFieldCreateProfile);
        savebtn = (Button) findViewById(R.id.saveButtonCreateProfile);
        progressBar = (ProgressBar) findViewById(R.id.progressBarCreateProfile);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        signupMe = FirebaseAuth.getInstance();

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedID = genderGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedID);
                gender = radioButton.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                savebtn.setVisibility(View.GONE);
                Upload();
            }
        });

        PickImageFromGallery();
    }

    public void RegisterUser(String user_email, String password1)
    {
        signupMe.createUserWithEmailAndPassword(user_email, password1).addOnCompleteListener(CreateProfile.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "User already exists with this email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    savebtn.setVisibility(View.VISIBLE);
                    HideKeyboard(CreateProfile.this);
                }
                else
                {
                    FirebaseUser user = signupMe.getCurrentUser();
                    CreateUserProfile(user);
                    HideKeyboard(getApplicationContext());
                    progressBar.setVisibility(View.GONE);
                    savebtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void CreateUserProfile(FirebaseUser User)
    {
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(user);
        Intent signupbtnclicked = new Intent(CreateProfile.this, SignInScreen.class);
        startActivity(signupbtnclicked);
        finish();
    }

    private void PickImageFromGallery()
    {
        cameraicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent();
                galleryintent.setType("image/*");
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryintent, "Select Image"),GALLERY_PICK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            assert data != null;
            imageUri = data.getData();//for storing in firebase
            profilepicture.setImageURI(imageUri);

        }
    }

    public void Upload()
    {
        if(imageUri != null)
        {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            storageReference = storageReference.child("Images/" + System.currentTimeMillis() + phone_number.getText().toString());
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    String img = uri.toString();
                                    user = new User (first_name.getText().toString(), lastname.getText().toString(), EMAIL.getText().toString(),
                                            password, usertype.getText().toString(), gender,
                                            phone_number.getText().toString(), bio.getText().toString(), img, "Offline");

                                    RegisterUser(EMAIL.getText().toString(), password);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                             savebtn.setEnabled(true);
                                            //bck.setEnabled(true);
                                            savebtn.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            savebtn.setVisibility(View.VISIBLE);
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(), "Upload All Images", Toast.LENGTH_SHORT).show();
            savebtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void HideKeyboard(Context c)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(c);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}