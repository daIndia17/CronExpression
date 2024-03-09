package cronExpression.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Arjun Das
 * @since : 08-03-2024
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParserResponse {

    private List<String> minutes;
    private List<String> hours;
    private List<String> daysOfMonth;
    private List<String> months;
    private List<String> daysOfWeek;
    private String command;


    @Override
    public String toString() {
        return "minute " + formatList(minutes) +
                "\nhour " + formatList(hours) +
                "\nday of month " + formatList(daysOfMonth) +
                "\nmonth " + formatList(months) +
                "\nday of week " + formatList(daysOfWeek) +
                "\ncommand " + command;
    }

    private String formatList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}
