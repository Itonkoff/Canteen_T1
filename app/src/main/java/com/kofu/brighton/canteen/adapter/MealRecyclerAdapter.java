package com.kofu.brighton.canteen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kofu.brighton.canteen.MainActivityCallBacks;
import com.kofu.brighton.canteen.R;
import com.kofu.brighton.canteen.models.Meal;

import java.util.List;

public class MealRecyclerAdapter extends RecyclerView.Adapter<MealRecyclerAdapter.MealItemViewHolder> {

    private final Context mContext;
    private final List<Meal> mMeals;
    private final LayoutInflater mLayoutInflater;

    public MealRecyclerAdapter(Context context, List<Meal> meals) {
        mContext = context;
        mMeals = meals;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MealItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mealItem = mLayoutInflater.inflate(R.layout.meal_item, parent, false);
        return new MealItemViewHolder(mealItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MealItemViewHolder holder, int position) {
        holder.bind(mMeals.get(position));
    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    class MealItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView mMealTitleField;
        private final TextView mMealPriceTextField;
        private final View mItemView;

        public MealItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            mMealTitleField = itemView.findViewById(R.id.tv_meal);
            mMealPriceTextField = itemView.findViewById(R.id.tv_meal_price);
        }

        public void bind(Meal meal) {
            mMealTitleField.setText(meal.meal);
            mMealPriceTextField.setText("$ " + String.valueOf(meal.price));

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivityCallBacks activity = (MainActivityCallBacks) mContext;
                    activity.billStudent(meal);
                }
            });
        }
    }
}
