package cronExpression.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Arjun Das
 * @since : 08-03-2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error<S>{

    S error;
}
