package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import retrofit.RestAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.android.sabbir.navdrawer.Constants.Constants;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.interfaces.GitHubDataInterface;
import test.android.sabbir.navdrawer.models.GitHubUser;

/**
 * @author sabbir
 *
 *
 */
public class GitHubFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GitHubFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GitHubFragment newInstance(String param1, String param2) {
        GitHubFragment fragment = new GitHubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.imageView2)
    ImageView profileImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.GITHUB_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        GitHubDataInterface gitHubDataInterface=retrofit.create(GitHubDataInterface.class);
        Call<GitHubUser> call=gitHubDataInterface.getUser("himelhimu");
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                GitHubUser gitHubUser=response.body();
                tvName.setText(gitHubUser.getName());
                tvUserName.setText(gitHubUser.getLogin());
                Picasso.with(getActivity()).load(gitHubUser.getAvatarUrl()).into(profileImageView);
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {

            }
        });



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @BindView(R.id.textView5)
    TextView tvName;
    @BindView(R.id.textView6)
    TextView tvUserName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_git_hub, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
}
