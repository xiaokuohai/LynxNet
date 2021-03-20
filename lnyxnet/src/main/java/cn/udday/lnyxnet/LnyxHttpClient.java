package cn.udday.lnyxnet;

public class LnyxHttpClient {
    private Config config;

    public LnyxHttpClient(Builder builder) {
        this.config = new Config(builder);
    }

    public Call newCall(Request request) {
        return new HttpCall(config, request);
    }

    static class Config {
        final int connTimeout;
        final int readTimeout;
        final int writeTimeout;

        public Config(Builder builder) {
            this.connTimeout = builder.connTimeout;
            this.readTimeout = builder.connTimeout;
            this.writeTimeout = builder.writeTimeout;
        }
    }

    public static final class Builder {
        private int connTimeout;
        private int readTimeout;
        private int writeTimeout;

        public Builder() {
            this.connTimeout = 10 * 1000;
            this.readTimeout = 10 * 1000;
            this.writeTimeout = 10 * 1000;
        }


        public Builder readTimeOut(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder connTimeOut(int connTimeout) {
            this.connTimeout = connTimeout;
            return this;
        }

        public Builder writeTimeOut(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public LnyxHttpClient build() {
            return new LnyxHttpClient(this);
        }

    }
}
