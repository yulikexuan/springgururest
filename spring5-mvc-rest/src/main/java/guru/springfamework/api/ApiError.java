//: guru.springfamework.api.ApiError.java


package guru.springfamework.api;


import lombok.Getter;

import java.util.Date;


@Getter
public final class ApiError {

    private final Date timestamp;
    private final String message;
    private final String details;

    private ApiError(Date timestamp, String message, String details) {
        this.timestamp = new Date(timestamp.getTime());
        this.message = message;
        this.details = details;
    }

    public final Date getTimestamp() {
        return new Date(this.timestamp.getTime());
    }

    public static final class ApiErrorBuilder {

        private Date timestamp;
        private String message;
        private String details;

        public ApiErrorBuilder setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public static ApiErrorBuilder getInstance() {
            return new ApiErrorBuilder();
        }

        public ApiErrorBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiErrorBuilder setDetails(String details) {
            this.details = details;
            return this;
        }

        public ApiError createApiError() {
            return new ApiError(timestamp, message, details);
        }
    }

}///:~