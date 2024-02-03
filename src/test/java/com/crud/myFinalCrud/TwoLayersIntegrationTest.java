package com.crud.myFinalCrud;


import com.crud.myFinalCrud.controllers.Controller;
import com.crud.myFinalCrud.model.Booking;
import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.model.DTOs.BookingRequestWithDTOs;
import com.crud.myFinalCrud.repositories.BookingRepository;
import com.crud.myFinalCrud.repositories.ClientRepository;
import com.crud.myFinalCrud.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TwoLayersIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Service service; //Создает мок сервиса, который используется
    // вместо реального сервиса в контроллере.

    @InjectMocks //используется для внедрения мок-объекта service в контроллер Controller.
    // Это позволяет тестировать контроллер, не обращаясь к реальному объекту Service.
    private Controller controller;

    @Autowired // Объект для преобразования объектов в JSON и обратно.
    private ObjectMapper objectMapper;

    @MockBean
    private Service Service;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ClientRepository clientRepository;


    @RunWith(SpringRunner.class)
    @WebMvcTest(Controller.class)
    public class YourControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private Service Service;

        @MockBean
        private BookingRepository bookingRepository;

        @MockBean
        private ClientRepository clientRepository;

        @Test
        public void testAddOrder() throws Exception {
            // Настройка тестового контекста и мокирование

            // Создание тестовых данных
            BookingRequestWithDTOs testData = createTestData();

            // Имитация поведения репозиториев
            Mockito.when(clientRepository.findAll()).thenReturn(Arrays.asList(/* ваш список клиентов */));
            Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(/* ваш сохраненный клиент */);
            Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(/* ваш сохраненный букинг */);

            // Вызов тестируемого метода
            mockMvc.perform(post("/clients/add-booking")
                            .content(/* преобразование testData в JSON */)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            // Проверка результата
//            Mockito.verify(yourService, times(1)).addBooking(/* ожидаемые аргументы */);
//            Mockito.verify(clientRepository, times(1)).save(/* ожидаемый клиент */);
//            Mockito.verify(bookingRepository, times(1)).save(/* ожидаемый букинг */);
        }

        private BookingRequestWithDTOs createTestData() {
            // Создание и возврат объекта с тестовыми данными


        }
    }




}
}