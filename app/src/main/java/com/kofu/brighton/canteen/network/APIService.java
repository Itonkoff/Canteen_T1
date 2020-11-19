package com.kofu.brighton.canteen.network;

import com.kofu.brighton.canteen.models.Login;
import com.kofu.brighton.canteen.models.Meal;
import com.kofu.brighton.canteen.models.MealBill;
import com.kofu.brighton.canteen.models.Register;
import com.kofu.brighton.canteen.models.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    /*
     * Authentication
     *
     * */
    @POST("Authentication/SignIn")
    Call<Token> login(@Body Login login);

    @POST("Authentication/SignUp/Canteen-Help")
    Call<Void> register(@Body Register details);

    /*
     * Canteen
     *
     * */
    @GET("Canteen/Meals")
    Call<List<Meal>> getAvailableMeals();

    @POST("Canteen/Bill")
    Call<Void> mealbill(@Body MealBill mealBill);
}
