// Generated by view binder compiler. Do not edit!
package com.chhating.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.chhating.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegistationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText rejEmail;

  @NonNull
  public final TextView rejLogin;

  @NonNull
  public final EditText rejName;

  @NonNull
  public final EditText rejPassword;

  @NonNull
  public final CircleImageView rejProfile;

  @NonNull
  public final EditText rejRePassword;

  @NonNull
  public final Button rejSignup;

  private ActivityRegistationBinding(@NonNull ConstraintLayout rootView, @NonNull EditText rejEmail,
      @NonNull TextView rejLogin, @NonNull EditText rejName, @NonNull EditText rejPassword,
      @NonNull CircleImageView rejProfile, @NonNull EditText rejRePassword,
      @NonNull Button rejSignup) {
    this.rootView = rootView;
    this.rejEmail = rejEmail;
    this.rejLogin = rejLogin;
    this.rejName = rejName;
    this.rejPassword = rejPassword;
    this.rejProfile = rejProfile;
    this.rejRePassword = rejRePassword;
    this.rejSignup = rejSignup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegistationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegistationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_registation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegistationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.rej_email;
      EditText rejEmail = ViewBindings.findChildViewById(rootView, id);
      if (rejEmail == null) {
        break missingId;
      }

      id = R.id.rej_login;
      TextView rejLogin = ViewBindings.findChildViewById(rootView, id);
      if (rejLogin == null) {
        break missingId;
      }

      id = R.id.rej_name;
      EditText rejName = ViewBindings.findChildViewById(rootView, id);
      if (rejName == null) {
        break missingId;
      }

      id = R.id.rej_password;
      EditText rejPassword = ViewBindings.findChildViewById(rootView, id);
      if (rejPassword == null) {
        break missingId;
      }

      id = R.id.rej_profile;
      CircleImageView rejProfile = ViewBindings.findChildViewById(rootView, id);
      if (rejProfile == null) {
        break missingId;
      }

      id = R.id.rej_re_password;
      EditText rejRePassword = ViewBindings.findChildViewById(rootView, id);
      if (rejRePassword == null) {
        break missingId;
      }

      id = R.id.rej_signup;
      Button rejSignup = ViewBindings.findChildViewById(rootView, id);
      if (rejSignup == null) {
        break missingId;
      }

      return new ActivityRegistationBinding((ConstraintLayout) rootView, rejEmail, rejLogin,
          rejName, rejPassword, rejProfile, rejRePassword, rejSignup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
