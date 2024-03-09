package cronExpression.controller;

import cronExpression.exception.InvalidRequestException;
import cronExpression.payload.BaseResponse;
import cronExpression.payload.CronExpressionRequest;
import cronExpression.payload.ParserResponse;
import cronExpression.service.CronExpressionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author : Arjun Das
 * @since : 08-03-2024
 */
@RestController
@RequestMapping("/cron")
@Description("SorterPackerController class for Saveo-Med")
public class CronExpressionController {

    @Autowired
    private CronExpressionService cronExpressionService;

    /**
     * @param cronExpressionRequest
     * @return ParserResponse
     */
    @PostMapping("/expressionParser")
    public String getCronParser(@RequestBody CronExpressionRequest cronExpressionRequest){
        if(Objects.isNull(cronExpressionRequest))
            throw new InvalidRequestException("Cron Expression request body is empty!! Please provide a valid expression to process");

        if(StringUtils.isBlank(cronExpressionRequest.getCronExpression()))
            throw new InvalidRequestException("Cron Expression input is empty!! Please provide a valid expression to process");

        ParserResponse parserResponse = cronExpressionService.getCronParserResult(cronExpressionRequest.getCronExpression());
        return parserResponse.toString();
    }
}
