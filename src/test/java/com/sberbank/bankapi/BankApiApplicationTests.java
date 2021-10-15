package com.sberbank.bankapi;

import com.sberbank.bankapi.controller.AccountController;
import com.sberbank.bankapi.controller.CardController;
import com.sberbank.bankapi.controller.PersonController;
import com.sberbank.bankapi.utils.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithMockUser(username = "manager", roles = {"MANAGER"})
public class BankApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonController personController;

    @Autowired
    private AccountController accountController;

    @Autowired
    private CardController cardController;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    public void testPersonControllerIsPresent() throws Exception {
        assertThat(personController).isNotNull();
    }

    @Test
    public void testAccountControllerIsPresent() throws Exception {
        assertThat(accountController).isNotNull();
    }

    @Test
    public void testCardControllerIsPresent() throws Exception {
        assertThat(cardController).isNotNull();
    }

    @Test
    public void testGetPerson() throws Exception {
        this.mockMvc.perform(get("/api/manager/getPerson/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/person.json")));
    }

    @Test
    public void testGetAccount() throws Exception {
        this.mockMvc.perform(get("/api/clients/getAccounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/accounts.json")));
    }

    @Test
    public void testGetCard() throws Exception {
        this.mockMvc.perform(get("/api/clients/getCards/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/cards.json")));
    }

    @Test
    public void testGetAccountByAccountNumber() throws Exception {
        this.mockMvc.perform(get("/api/clients/getAccountByAccountNumber/7788654324567845"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/accountByNumber.json")));
    }

    @Test
    public void testGetAllPersons() throws Exception {
        this.mockMvc.perform(get("/api/manager/getAllPersons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/allPersons.json")));
    }

    @Test
    public void testGetBalance() throws Exception {
        this.mockMvc.perform(get("/api/clients/getBalanceForCard/9876342625342628"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("7000.0"));
    }

    @Test
    public void testCreatePerson() throws Exception {
        this.mockMvc.perform(post("/api/manager/createPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ivan\",\"surname\":\"Ivanov\",\"phone\":\"89807776655\",\"passport\":\"4518999777\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":6,\"name\":\"Ivan\",\"surname\":\"Ivanov\",\"phone\":\"89807776655\",\"passport\":\"4518999777\"}"));
    }

    @Test
    public void testAddMoneyToCard() throws Exception {
        this.mockMvc.perform(post("/api/clients/addMoneyToCard/8599009988776657/20000.00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/addMoney.json")));
    }

    @Test
    public void testDeleteCard() throws Exception {
        this.mockMvc.perform(delete("/api/manager/deleteCard/8899009988776655"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/deleteCard.json")));
    }

    @Test
    public void testTransferMoney() throws Exception {
        this.mockMvc.perform(post("/api/clients/transferMoney/2277556677443344/8765374652343847/1000.00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSONParser.parseJsonToString("jsons/transferMoney.json")));
    }

    @Test
    public void testRequestToCreateAccount() throws Exception {
        this.mockMvc.perform(post("/api/clients/addRequestToCreateAccount/5673673567"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().json("{\"message\":\"Заявка на открытие счета подана\"}"));
    }

    @Test
    public void testConfirmRequestToCreateAccount() throws Exception {
        this.mockMvc.perform(post("/api/manager/confirmRequestToCreateAccount/5673673567"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().json("{\"message\":\"Заявка на открытие счета подтверждена\"}"));
    }

}
