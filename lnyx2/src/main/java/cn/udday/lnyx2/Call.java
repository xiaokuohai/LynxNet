package cn.udday.lnyx2;

public interface Call {
    Response execute();
    void enqueue(Callback callback);
}
