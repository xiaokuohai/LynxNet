package cn.udday.lnyx;

public interface CallBack<T> {
    void onResponse(Call<T> call,Response<T> response);
    void onFailure(Call<T> call,Throwable e);
}
