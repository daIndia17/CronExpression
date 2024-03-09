package cronExpression.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import cronExpression.exception.Error;

/**
 * @author : Arjun Das
 * @since : 08-03-2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    Error error;

    T data;

    public BaseResponse(Error error) {
        this.error = error;
        this.data = null;
    }

    public BaseResponse(T data) {
        this.data = data;
        this.error = null;
    }
}
