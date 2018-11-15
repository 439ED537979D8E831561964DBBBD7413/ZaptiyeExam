package com.praxistechinc.pts.http;

/**
 * Created by Mitesh on 03/11/16.
 */


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface HttpCallback {

    void onFailure(Call call, IOException e);

    void onSuccess(Call call, Response response) throws IOException;

}
