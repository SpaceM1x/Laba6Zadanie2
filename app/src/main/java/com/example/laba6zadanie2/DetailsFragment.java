package com.example.laba6zadanie2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    private ImageView employeeImage;
    private EditText employeeName;
    private EditText employeePosition;
    private Button saveButton;
    private Button selectEmployeeButton;

    private int[] employeeImages = {R.drawable.employee1, R.drawable.employee2, R.drawable.employee3};
    private int currentEmployee = 0;

    private static final String ARG_EMPLOYEE_INDEX = "employeeIndex";
    private static final String STATE_EMPLOYEE_INDEX = "stateEmployeeIndex";

    // Фабричный метод для создания экземпляра с передачей индекса
    public static DetailsFragment newInstance(int employeeIndex) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EMPLOYEE_INDEX, employeeIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentEmployee = savedInstanceState.getInt(STATE_EMPLOYEE_INDEX, 0);
        } else if (getArguments() != null) {
            currentEmployee = getArguments().getInt(ARG_EMPLOYEE_INDEX, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        employeeImage = view.findViewById(R.id.employeeImage);
        employeeName = view.findViewById(R.id.employeeName);
        employeePosition = view.findViewById(R.id.employeePosition);
        saveButton = view.findViewById(R.id.saveButton);
        selectEmployeeButton = view.findViewById(R.id.selectEmployeeButton);

        // Загружаем данные из MainActivity
        updateDetailsFromActivity();

        // Кнопка "Сохранить"
        saveButton.setOnClickListener(v -> {
            // Сохранение данных в MainActivity
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.setEmployeeName(currentEmployee, employeeName.getText().toString());
                activity.setEmployeePosition(currentEmployee, employeePosition.getText().toString());
            }

            // Логирование для проверки
            Log.d("DetailsFragment", "Saved changes for employee index: " + currentEmployee);
            Log.d("DetailsFragment", "New name: " + employeeName.getText().toString());
            Log.d("DetailsFragment", "New position: " + employeePosition.getText().toString());
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            selectEmployeeButton.setVisibility(View.GONE);
        } else {
            selectEmployeeButton.setVisibility(View.VISIBLE);
            selectEmployeeButton.setOnClickListener(v -> {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.detailsFragmentContainer, new SelectionFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }
        return view;
    }

    // Метод для загрузки данных из MainActivity
    private void updateDetailsFromActivity() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null && currentEmployee >= 0 && currentEmployee < employeeImages.length) {
            employeeImage.setImageResource(employeeImages[currentEmployee]);
            employeeName.setText(activity.getEmployeeName(currentEmployee));
            employeePosition.setText(activity.getEmployeePosition(currentEmployee));
        }
    }

    // Метод для установки выбранного сотрудника через Bundle
    public void setEmployee(int index) {
        currentEmployee = index;
        updateDetailsFromActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_EMPLOYEE_INDEX, currentEmployee);
    }
}
