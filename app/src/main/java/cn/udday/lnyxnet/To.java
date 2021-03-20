package cn.udday.lnyxnet;

import cn.udday.lnyx2.Call;
import cn.udday.lnyx2.http.GET;
import cn.udday.lnyx2.http.Headers;
import cn.udday.lnyx2.http.POST;
import cn.udday.lnyx2.http.Query;;

public interface To {
    @Headers("token:a337xZZFXIn7SqD1")
    @GET("qinghua")
    Call toGet();
    @Headers("token:a337xZZFXIn7SqD1")
    @POST("weather/hourly")
    Call toPost(@Query("location") String string);
}
