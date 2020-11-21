package com.kofu.brighton.canteen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kofu.brighton.canteen.MainActivityCallBacks;
import com.kofu.brighton.canteen.R;
import com.kofu.brighton.canteen.adapter.MealRecyclerAdapter;
import com.kofu.brighton.canteen.models.Meal;
import com.kofu.brighton.canteen.network.APIService;
import com.kofu.brighton.canteen.network.APIServiceBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private APIService mApiService;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mApiService = APIServiceBuilder.buildService(APIService.class);

        RecyclerView mealRecyclerView = view.findViewById(R.id.rv_meals);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mealRecyclerView.setLayoutManager(layoutManager);

        Call<List<Meal>> availableMealsCall = mApiService.getAvailableMeals();

        availableMealsCall.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (response.code() == 200) {
                    MealRecyclerAdapter mealRecyclerAdapter =
                            new MealRecyclerAdapter(getActivity(), response.body());
                    mealRecyclerView.setAdapter(mealRecyclerAdapter);
                } else {
                    Toast.makeText(getActivity(),
                            "Something went wrong in trying to fetch available meals",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "Failed to fetch available meals",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.canteen_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_logout) {
            logout();
        }
//        else {
//            logout();
//        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        MainActivityCallBacks activity = (MainActivityCallBacks) getActivity();
        activity.setToken(null);
        Navigation
                .findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                .navigate(FirstFragmentDirections
                        .actionFirstFragmentToLoginFragment());
    }
}