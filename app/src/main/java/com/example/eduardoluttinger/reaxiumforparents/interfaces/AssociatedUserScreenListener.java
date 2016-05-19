package com.example.eduardoluttinger.reaxiumforparents.interfaces;

import com.example.eduardoluttinger.reaxiumforparents.beans.User;

/**
 * Created by Eduardo Luttinger on 10/05/2016.
 */
public interface AssociatedUserScreenListener {

    void trackUser(User user);

    void showAccessInformation(User user);

}
