package cn.udday.lnyxnet;

public interface Call {
    Response execute();
    void enqueue(Callback callback);
}
