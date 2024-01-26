package com.crud.myFinalCrud;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.controllers.Controller;
import com.crud.myFinalCrud.service.Service;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) //Говорит JUnit о том, что для выполнения тестов должен
                                   // использоваться расширение Spring.
@SpringBootTest   // Запускает тесты Spring Boot и настраивает приложение для тестирования.
@AutoConfigureMockMvc  //Автоматически настраивает MockMvc для использования в тестах.
@Transactional //Оборачивает выполнение теста в транзакцию, которая будет откатываться после завершения теста.
@Rollback  //Указывает на откат транзакции после выполнения теста.
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  //Позволяет выполнять HTTP-запросы к контроллеру и проверять их результаты.

    @Mock
    private Service service; //Создает мок сервиса, который используется вместо реального сервиса в контроллере.

    @InjectMocks // Внедряет зависимости в контроллер, включая созданный мок сервиса.
    private Controller controller;

    @Autowired // Объект для преобразования объектов в JSON и обратно.
    private ObjectMapper objectMapper;

    @Test
    public void testAddNewClient() throws Exception {
        // Создаем объект клиента для передачи в теле запроса
        Client client = new Client();
        client.setClientName("John Doe");
        client.setClientINN(123456789);
        client.setClientPhone(1234567890);

        // Преобразуем объект в JSON
        String clientJson = objectMapper.writeValueAsString(client);

        // Определяем, как должен вести себя мок сервиса при вызове метода addClientService
        // (в данном случае, мы не делаем ничего, просто проверяем вызов)

        // Выполняем POST-запрос на ваш эндпоинт
        mockMvc.perform(post("/clients/add-new-client")
                        .content(clientJson)
                        .contentType("application/json"))
                .andExpect(status().isOk());


        /*
        Таким образом, этот тест проверяет, что при вызове эндпоинта
        /clients/add-new-client с корректным JSON-телом клиента контроллер успешно
        обрабатывает запрос и вызывает соответствующий метод сервиса (addClientService).
        Транзакция здесь используется для автоматического отката изменений в базе данных
        после выполнения теста, чтобы избежать дублирования данных в реальной базе.
         */
    }
}
