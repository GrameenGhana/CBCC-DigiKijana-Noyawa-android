package org.cbccessence.noyawa.noyawaonthego.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import org.cbccessence.noyawa.noyawaonthego.R;

/**
 * Created by aangjnr on 06/02/2017.
 */
public class RegistrationFragment extends Fragment {

    Integer year, month, day, presentYear;
    RelativeLayout selectDate;
    View rootView;


    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.registration_fragment_layout, container, false);

        return rootView;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);




        rootView.findViewById(R.id.select_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDatePickerDialog();



            }
        });


    }



    public void showDatePickerDialog() {

        DatePickerDialog date_picker_dialog = new DatePickerDialog(getActivity(), datePickerListener, 1970, 01, 01);
        date_picker_dialog.show();


    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

        }
    };

}