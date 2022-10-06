package com.example.contacts;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contact_Page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contact_Page extends Fragment implements Adapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Contact_Page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contact_Page.
     */
    // TODO: Rename and change types and number of parameters
    public static Contact_Page newInstance(String param1, String param2) {
        Contact_Page fragment = new Contact_Page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button Add;
    private ViewModel viewModel;
    RecyclerView recyclerView;
    Adapter adapter;
    MainActivity mainActivity;
    public List<Data> data = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact__page, container, false);
        Add = view.findViewById(R.id.add_Contacts);
        // Move to Add_Contact Page on button click
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.Container, new AddContacts()).addToBackStack(null).commit();
            }
        });

        viewModel = new ViewModelProvider(this.getActivity()).get(ViewModel.class);
        MyDB db = new MyDB(getContext());
        data = db.GetContacts();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter(data,Contact_Page.this::onItemClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mainActivity=(MainActivity)getActivity();
        mainActivity.toolbar.setTitle("Contact list");
        //Toast.makeText(getContext(),"implemented",Toast.LENGTH_SHORT).show();
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        // implementing view model from here on
        // this will prevent data loss/ restarting om orientation change
        viewModel.getLiveData().observe(requireActivity(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
            }
        });
        /*View model implementation ends*/

        return view;
    }

    @Override
    public void onItemClick(Data data) {
        String id = String.valueOf(data.getId());
        Fragment fragment = ContactDetails.newInstance(data.getName(), data.getNumber(), data.getEmail(),id,data.getNumber2(),data.getNumber3());
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Container,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
