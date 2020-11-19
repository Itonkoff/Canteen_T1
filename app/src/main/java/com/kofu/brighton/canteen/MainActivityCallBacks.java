package com.kofu.brighton.canteen;

import com.kofu.brighton.canteen.models.Meal;

public interface MainActivityCallBacks {
    void setToken(String token);
    String getToken();
    void hideFab();
    void showFab();

    void billStudent(Meal meal);
}
