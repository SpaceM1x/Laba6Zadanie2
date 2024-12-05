package com.example.laba6zadanie2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            setupFragments();  // Инициализация фрагментов при первом запуске
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setupFragments();  // Переустанавливаем фрагменты и обновляем видимость кнопок при изменении ориентации
    }

    private String[] employeeNames = {"Иван Иванов", "Петр Петров", "Мария Смирнова"};
    private String[] employeePositions = {"Менеджер", "Разработчик", "Тестировщик"};

    public String getEmployeeName(int index) {
        return employeeNames[index];
    }

    public void setEmployeeName(int index, String name) {
        employeeNames[index] = name;
    }

    public String getEmployeePosition(int index) {
        return employeePositions[index];
    }

    public void setEmployeePosition(int index, String position) {
        employeePositions[index] = position;
    }

    private void setupFragments() {
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        if (mainLayout == null) {
            Log.e("MainActivity", "mainLayout is null");
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Получаем текущую ориентацию экрана
        int currentOrientation = getResources().getConfiguration().orientation;

        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Альбомная ориентация: оба фрагмента на одном экране
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Заменяем оба фрагмента
            transaction.replace(R.id.selectionFragmentContainer, new SelectionFragment());
            transaction.replace(R.id.detailsFragmentContainer, new DetailsFragment());

            // Отображаем контейнер выбора
            findViewById(R.id.selectionFragmentContainer).setVisibility(View.VISIBLE);

        } else {
            // Портретная ориентация: один фрагмент с возможностью переключения
            mainLayout.setOrientation(LinearLayout.VERTICAL);


            transaction.replace(R.id.detailsFragmentContainer, new DetailsFragment());
            transaction.replace(R.id.selectionFragmentContainer, new SelectionFragment());

            // Скрываем контейнер выбора
            findViewById(R.id.selectionFragmentContainer).setVisibility(View.GONE);
        }

        // Подтверждаем изменения фрагментов
        transaction.commit();
    }
}
