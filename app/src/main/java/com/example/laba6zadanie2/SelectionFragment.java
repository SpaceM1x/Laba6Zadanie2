// SelectionFragment.java

package com.example.laba6zadanie2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection, container, false);

        // Кнопки для выбора сотрудников
        Button employee1Button = view.findViewById(R.id.employee1Button);
        Button employee2Button = view.findViewById(R.id.employee2Button);
        Button employee3Button = view.findViewById(R.id.employee3Button);

        // Выбираем сотрудника и передаем в DetailsFragment
        employee1Button.setOnClickListener(v -> selectEmployee(0));
        employee2Button.setOnClickListener(v -> selectEmployee(1));
        employee3Button.setOnClickListener(v -> selectEmployee(2));

        return view;
    }

    private void selectEmployee(int index) {
        DetailsFragment newFragment = new DetailsFragment();

        // Передаем индекс сотрудника через Bundle
        Bundle args = new Bundle();
        args.putInt("employeeIndex", index);
        newFragment.setArguments(args);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detailsFragmentContainer, newFragment)
                .addToBackStack(null)
                .commit();
    }

}
