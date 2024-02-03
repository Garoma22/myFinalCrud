package com.crud.myFinalCrud;
import com.crud.myFinalCrud.model.DTOs.ClientDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.controllers.Controller;
import com.crud.myFinalCrud.service.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) // Говорит JUnit о том, что для выполнения тестов должен
// использоваться расширение Spring.
//@SpringBootTest   // Запускает тесты Spring Boot и настраивает приложение для тестирования.
//@AutoConfigureMockMvc  //Автоматически настраивает MockMvc для использования в тестах.
@WebMvcTest(value = Controller.class) //тестируем только контроллеры
//@ContextConfiguration(classes = {MyFinalCrudApplication.class})

//@Transactional //Оборачивает выполнение теста в транзакцию, которая будет откатываться после завершения теста.
//@Rollback  //Указывает на откат транзакции после выполнения теста.
//@ActiveProfiles("test") - тестовая база

public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  //Библиотека позволяет выполнять
    // HTTP-запросы к контроллеру и проверять их результаты.

    @MockBean
    private Service service; //Создает мок сервиса, который используется
    // вместо реального сервиса в контроллере.

    @InjectMocks //используется для внедрения мок-объекта service в контроллер Controller.
    // Это позволяет тестировать контроллер, не обращаясь к реальному объекту Service.
    private Controller controller;

    @Autowired // Объект для преобразования объектов в JSON и обратно.
    private ObjectMapper objectMapper;

    @Test
    public void testAddNewClient() throws Exception {
        // Создаем объект клиента для передачи в теле запроса
        ClientDto clientDto = new ClientDto();
        clientDto.setClientName("John Doe");
        clientDto.setClientINN(123456789);
        clientDto.setClientPhone(1234567890);

        Client client = new Client();

        client.setClientName(clientDto.getClientName());
        client.setClientINN(clientDto.getClientINN());
        client.setClientPhone(clientDto.getClientPhone());

        // Преобразуем объект в JSON
        String clientJson = objectMapper.writeValueAsString(clientDto);

        // Определяем, как должен вести себя MOK СЕРВИСА при вызове метода сервиса addClientService
        // (в данном случае, мы не делаем ничего, просто проверяем вызов)

        when(service.convertClientDtoToClientAndAddClientToDb(clientDto)).thenReturn(client);

        // Выполняем POST-запрос на ваш эндпоинт
        mockMvc.perform(post("/clients/add-new-client")
                        .content(clientJson) //метод устанвливает тело запроса, то есть json body
                        .contentType("application/json")) //говорим серверу что тиа запроса это джейсон
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("John Doe"))
                .andExpect(jsonPath("$.clientINN").value(123456789))
                .andExpect(jsonPath("$.clientPhone").value(1234567890));





        }






    @Test
    public void testGetClientByINN() throws Exception {
        // Задаем значение INN для поиска
        long clientINN = 123456789;

        // Создаем мок объекта клиента, который будет возвращен сервисом
        Client mockClient = new Client();
        mockClient.setClientName("John Doe");
        mockClient.setClientINN(clientINN);
        mockClient.setClientPhone(1234567890);

        // Указываем, что при вызове сервиса с данным INN, он должен вернуть мок-клиента
        when(service.getClientByINN(clientINN)).thenReturn(mockClient);

        // Выполняем GET-запрос на ваш эндпоинт
        mockMvc.perform(get("/clients/get-client-by-inn")
                        .param("clientINN", String.valueOf(clientINN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("John Doe"))
                .andExpect(jsonPath("$.clientINN").value(clientINN))
                .andExpect(jsonPath("$.clientPhone").value(1234567890));
    }

    @Test
    public void testUpdateClientName() throws Exception {
        Long clientINN = 123L;
        String newName = "Kirk";
        String oldName = "Ivan";

        // Создаем фейковый клиент, предполагая, что клиент существует в базе
        Client mockClient = new Client();
        mockClient.setClientName(oldName);
        mockClient.setClientINN(clientINN);
        mockClient.setClientPhone(1234567890);

        // Когда вызывается метод getClientByINN с clientINN, вернуть созданный выше мок
        when(service.getClientByINN(clientINN)).thenReturn(mockClient);


        //пихаем этот по сути мок в другой метод сервиса
        when(service.addClientToDb(eq(mockClient))).thenReturn(mockClient);

        // Выполняем запрос на обновление имени - тут прописываем параметы из аргументов в контроллере
        mockMvc.perform(patch("/clients/update-client-name")
                        .param("clientINN", String.valueOf(clientINN))
                        .param("newName", newName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value(newName))
                .andExpect(jsonPath("$.clientINN").value(123L))
                .andExpect(jsonPath("$.clientPhone").value(1234567890));

        // Проверяем, что метод getClientByINN был вызван с переданным clientINN
        verify(service, times(1)).getClientByINN(clientINN);

        // Проверяем, что метод addClientService был вызван с фейковым клиентом, у которого обновлено имя
        verify(service, times(1)).addClientToDb(argThat(client -> client.getClientName().equals(newName)));
    }

    @Test
    public void testGetAllClients() throws Exception {

        //имитируем что в базе чето есть
        Client mockClient = new Client();
        mockClient.setClientName("Tom");
        mockClient.setClientINN(123L);
        mockClient.setClientPhone(1234567890);

        Client mockClient2 = new Client();
        mockClient2.setClientName("Bob");
        mockClient2.setClientINN(456L);
        mockClient2.setClientPhone(1234567891);

        List<Client> mockClietnsList = new ArrayList<>();
        mockClietnsList.add(mockClient);
        mockClietnsList.add(mockClient2);

        when(service.getAll()).thenReturn(mockClietnsList);

        mockMvc.perform(get("/clients/get-all-clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientName").value("Tom"))
                .andExpect(jsonPath("$[0].clientINN").value(123L))
                .andExpect(jsonPath("$[0].clientPhone").value(1234567890))
                .andExpect(jsonPath("$[1].clientName").value("Bob"))
                .andExpect(jsonPath("$[1].clientINN").value(456L))
                .andExpect(jsonPath("$[1].clientPhone").value(1234567891));


        // Проверяем случай пустого списка
        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/clients/get-all-clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testRemoveClientFullyFromBase() throws Exception {
        Client mockClient = new Client();
        mockClient.setClientName("Tom");
        mockClient.setClientINN(123L);
        mockClient.setClientPhone(1234567890);

        when(service.removeClient(mockClient)).thenReturn(null);

        mockMvc.perform(delete("/clients/remove-client-fully")
                        .param("clientINN", String.valueOf(mockClient.getClientINN())))
                .andExpect(status().isOk());
    }
}








