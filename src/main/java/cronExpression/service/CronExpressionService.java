package cronExpression.service;


import cronExpression.constant.MasterConstants;
import cronExpression.exception.InvalidRequestException;
import cronExpression.payload.ParserResponse;
import cronExpression.utility.CommonUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author : Arjun Das
 * @since : 08-03-2024
 */
@Service
public class CronExpressionService {

    @Autowired
    CommonUtilService commonUtilService;

    /**
     * @param expression
     * @return ParserResponse
     */
    public ParserResponse getCronParserResult(String expression) {
        /*
            1. Check whether the cron expression is valid or not
            2. If valid parse the express and return the result
            3. If not, throw exception
         */

        boolean isValidExpression =  commonUtilService.isValidCronExpression(expression);
        if (!isValidExpression) {
            throw new InvalidRequestException("Cron expression is not valid");
        }

        ParserResponse parserResponse = parseExpression(expression);
        return parserResponse;
    }

    private ParserResponse parseExpression(String expression){
        String[] cronParts = expression.split("\\s+");
        int length = cronParts.length;
        Map<String, List<String>> parsedFields = new HashMap<>();
        parsedFields.put(MasterConstants.MINUTE, expandField(cronParts[0], 0, 59));
        parsedFields.put(MasterConstants.HOUR, expandField(cronParts[1], 0, 23));
        parsedFields.put(MasterConstants.DAY_OF_MONTH, expandField(cronParts[2], 1, 31));
        parsedFields.put(MasterConstants.MONTH, expandField(cronParts[3], 1, 12));
        parsedFields.put(MasterConstants.DAY_OF_WEEK, expandField(cronParts[4], 0, 6));

        String command = length > 5 ? cronParts[5] : "NA";


        ParserResponse parserResponse = ParserResponse.builder().minutes(parsedFields.get(MasterConstants.MINUTE))
                                        .hours(parsedFields.get(MasterConstants.HOUR))
                                        .daysOfMonth(parsedFields.get(MasterConstants.DAY_OF_MONTH))
                                        .months(parsedFields.get(MasterConstants.MONTH))
                                        .daysOfWeek(parsedFields.get(MasterConstants.DAY_OF_WEEK))
                                        .command(command)
                                        .build();

        return parserResponse;
    }


    private static List<String> expandField(String field, int minValue, int maxValue) {
        List<String> expandedValues = new ArrayList<>();
        if (field.equals("*")) {
            expandedValues = IntStream.rangeClosed(minValue, maxValue)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.toList());
        } else if (field.equals("?")) {
            // "?" is treated as "*" for our purpose
            expandedValues = expandField("*", minValue, maxValue);
        } else {
            String[] parts = field.split(",");
            for (String part : parts) {
                if (part.contains("/")) {
                    String[] split = part.split("/");
                    int start = split[0].contains("-") ? Integer.parseInt(split[0].split("-")[0]) : minValue;
                    int end = split[0].contains("-") ? Integer.parseInt(split[0].split("-")[1]) : maxValue;
                    int step = Integer.parseInt(split[1]);
                    for (int i = start; i <= end; i += step) {
                        expandedValues.add(String.valueOf(i));
                    }
                } else if (part.contains("-")) {
                    String[] split = part.split("-");
                    int start = Integer.parseInt(split[0]);
                    int end = Integer.parseInt(split[1]);
                    expandedValues.addAll(IntStream.rangeClosed(start, end)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.toList()));
                } else {
                    expandedValues.add(part);
                }
            }
        }
        return expandedValues;
    }

}
