package com.sberbank.bankapi;

import com.sberbank.bankapi.controller.ManagerController;
import com.sberbank.bankapi.utils.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class BankApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManagerController personController;

    @Test
    public void testControllerIsPresent() throws  Exception {
        assertThat(personController).isNotNull();
    }

    @Test
    public void testGetPerson() throws Exception {
        this.mockMvc.perform(get("/api/persons/getPerson/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/person.json")));
    }

    @Test
    public void testGetAccount() throws Exception {
        this.mockMvc.perform(get("/api/accounts/getAccounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/accounts.json")));
    }

    @Test
    public void testGetCard() throws Exception {
        this.mockMvc.perform(get("/api/cards/getCards/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/cards.json")));
    }

    @Test
    public void testGetAccountByAccountNumber() throws Exception {
        this.mockMvc.perform(get("/api/accounts/getAccountByAccountNumber/7788665544332211"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/accountByNumber.json")));
    }

    @Test
    public void testGetAllPersons() throws Exception {
        this.mockMvc.perform(get("/api/persons/getAllPersons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/allPersons.json")));
    }

    @Test
    public void testGetBalance() throws Exception {
        this.mockMvc.perform(get("/api/cards/getBalanceForCard/8899009988776655"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("9500.0"));
    }

    @Test
    public void testCreatePerson() throws Exception {
        this.mockMvc.perform(post("/api/persons/createPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ivan\",\"surname\":\"Ivanov\",\"phone\":\"89807776655\",\"passport\":\"4518999777\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":5,\"name\":\"Ivan\",\"surname\":\"Ivanov\",\"phone\":\"89807776655\",\"passport\":\"4518999777\"}"));
    }

    @Test
    public void testAddMoneyToCard() throws Exception {
        this.mockMvc.perform(post("/api/cards/addMoneyToCard/4400354676542233/20000.00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/addMoney.json")));
    }

    @Test
    public void testDeleteCard() throws Exception {
        this.mockMvc.perform(delete("/api/cards/deleteCard/8899009988776655"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/deleteCard.json")));
    }

    @Test
    public void testTransferMoney() throws Exception {
        this.mockMvc.perform(post("/api/accounts/transferMoney/4058354637462435/7356454734625343/1000.00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/transferMoney.json")));
    }

}
