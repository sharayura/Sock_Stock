package pro.sky.test_skypro.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.test_skypro.dto.SocksDto;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SocksControllerTest {

    private final String TEST_COLOR = "black";
    private final Long TEST_QUANTITY1 = 50L;
    private final Long TEST_QUANTITY2 = 30L;
    private final Byte TEST_COTTON1 = 90;
    private final String MORE_THEN = "moreThan";
    private final String LESS_THEN = "lessThan";
    private final String EQUAL = "equal";


    @Autowired
    MockMvc mockMvc;

    @Test
    void socksAPITest() throws Exception {
        SocksDto socksDto = new SocksDto();
        socksDto.setColor(TEST_COLOR);
        socksDto.setCottonPart(TEST_COTTON1);
        socksDto.setQuantity(TEST_QUANTITY1);

        mockMvc.perform(post("/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(socksDto)))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/socks").param("color", TEST_COLOR)
                                .param("operation", EQUAL)
                                .param("cottonPart", TEST_COTTON1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(TEST_QUANTITY1.toString()));

        socksDto.setQuantity(TEST_QUANTITY2);
        mockMvc.perform(post("/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(socksDto)))
                .andExpect(status().isOk());

        Long difference = TEST_QUANTITY1 - TEST_QUANTITY2;
        mockMvc.perform(
                        get("/api/socks").param("color", TEST_COLOR)
                                .param("operation", EQUAL)
                                .param("cottonPart", TEST_COTTON1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(difference.toString()));
    }

    private String asJsonString(SocksDto socksDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(socksDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
