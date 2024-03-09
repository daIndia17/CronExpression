package cronExpression;

import com.fasterxml.jackson.databind.ObjectMapper;
import cronExpression.payload.CronExpressionRequest;
import cronExpression.payload.ParserResponse;
import cronExpression.service.CronExpressionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CronExpressionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CronExpressionService cronExpressionService;

    @Test
    void testGetCronParser() throws Exception {
        // Mocking request and response objects
        CronExpressionRequest request = new CronExpressionRequest();
        request.setCronExpression("*/15 0 1,15 * 1-5 /usr/bin/find");

        ParserResponse response = new ParserResponse();
        response.setMinutes(Arrays.asList("0", "15", "30", "45"));
        response.setHours(Arrays.asList("0"));
        response.setDaysOfMonth(Arrays.asList("1", "15"));
        response.setMonths(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        response.setDaysOfWeek(Arrays.asList("1", "2", "3", "4", "5"));
        response.setCommand("/usr/bin/find");

        String response1 = response.toString();

        // Mocking service method call
        when(cronExpressionService.getCronParserResult(request.getCronExpression())).thenReturn(response);


        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8090/cron/expressionParser")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(response1));


        verify(cronExpressionService).getCronParserResult(request.getCronExpression());
    }

    @Test
    void testGetCronParserInvalidRequest() throws Exception {
        // Performing POST request with empty body
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8090/cron/expressionParser")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
