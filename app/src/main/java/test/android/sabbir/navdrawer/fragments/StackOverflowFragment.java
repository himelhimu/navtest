package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

import test.android.sabbir.navdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StackOverflowFragment extends Fragment {


    public StackOverflowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        java.lang.Process process;
        try{
            process=Runtime.getRuntime().exec(new String[]{"su"});

            DataOutputStream dataOutputStream=new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("echo \"Do I have root?\" >/system/sd/temporary.txt\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            try{
                process.waitFor();
                if (process.exitValue()!=255){
                    Toast.makeText(getActivity(),"Root",Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),"Not Root",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Not Root",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stack_overflow, container, false);
    }

}
