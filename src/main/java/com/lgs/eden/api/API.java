package com.lgs.eden.api;

import com.lgs.eden.api.auth.AuthAPI;
import com.lgs.eden.api.callback.CallBackAPI;
import com.lgs.eden.api.games.GameAPI;
import com.lgs.eden.api.local.LocalHandler;
import com.lgs.eden.api.news.NewsAPI;
import com.lgs.eden.api.nexus.NexusHandler;
import com.lgs.eden.api.profile.ProfileAPI;

/**
 * API that will be documented here
 * https://lgsnexus.docs.apiary.io/.
 */
public interface API extends AuthAPI, GameAPI, ProfileAPI, NewsAPI, CallBackAPI {

    // we are creating this variable to reduce the number of letters (NexusHandler.getInstance() vs API.imp)
    // and because we may want some sort of factory pattern.
    //
    // Using that, we can have a local API for tests and our server API.
    @SuppressWarnings("ConstantConditionalExpression")
    APIHandler imp = false ? LocalHandler.getInstance() : NexusHandler.getInstance();

}
