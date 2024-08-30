package com.pruebasUnitarias.Class;

import com.pruebasUnitarias.Class.Entity.Order;
import com.pruebasUnitarias.Class.Repository.OrderRepository;
import com.pruebasUnitarias.Class.Service.OrderService;
import com.pruebasUnitarias.Class.Service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private Order testOrder;

    @Mock
    private PaymentService paymentService;

    @Mock
    private OrderRepository orderRepository;
    private Order order2;

    @InjectMocks
    private OrderService orderService;


    @BeforeEach
    void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);

        testOrder=new Order();
        // asignacion de valores a parametros
        testOrder.setId(1L);
        testOrder.setAmount(100.50);
        // testOrder = new Order(testOrderId, testOrderAmount);

        order2 = new Order();
        order2.setId(2L);
        order2.setAmount(200.0);

    }

    @Test
    void placeOrder() {
        Order order = new Order();
        order.setId(10);
        order.setAmount(1000.0);

        Mockito.when(paymentService.processPayment(order.getAmount())).thenReturn(true);
        boolean result = orderService.placeOrder(order);
        //valida que se haya consulatado al servicio
        Mockito.verify(orderRepository, times(1)).save(order);

         System.out.println("Datos guardados ID: " + order.getId() + ", Amount: " + order.getAmount());


    }

    @Test
    void getOrderById() {

        long orderId = 1L;
        double amount=1300;
        Order expectedOrder = new Order();
        expectedOrder.setId(orderId);
        expectedOrder.setAmount(amount);
        Mockito.when(orderRepository.findById(expectedOrder.getId())).thenReturn(Optional.of(testOrder));

        // valor actual
        Order result = orderService.getOrderById(orderId);

        //verificar que se llame al metodo
        Mockito.verify(orderRepository, times(1)).findById(1L);

        // valida que la busqueda no sea nula
        assertNotNull(result);
        assertEquals(expectedOrder, result);
        System.out.println("Registro encontrado --> ID: " + expectedOrder.getId() + ", Amount: " + expectedOrder.getAmount());


    }


    @Test
    void cancelOrder() {
        long orderId = 1L;
        double amount=1300;
        Order cancelOrder = new Order();
        cancelOrder.setId(orderId);
        cancelOrder.setAmount(amount);

        //Mockito.when(orderRepository.findById(cancelOrder.getId())).thenReturn(Optional.of(testOrder));
        Mockito.when(orderRepository.findById(cancelOrder.getId())).thenReturn(Optional.of(testOrder));

        // Para que de la excpecion de Order notfound
        // Mockito.when(orderRepository.findById(cancelOrder.getId())).thenReturn(Optional.empty());

        // Llama al método bajo prueba
        orderService.cancelOrder(testOrder.getId());

        // Verifica que el método delete del repositorio fue llamado con el pedido correcto
        Mockito.verify(orderRepository, times(1)).delete(testOrder);

        System.out.println("Dato Eliminado: Id: " + cancelOrder.getId() + ", Amount: " + cancelOrder.getAmount());

    }

    @Test
    void listAllOrders() {

        // Simula la respuesta del repositorio
        List<Order> orders = Arrays.asList(testOrder, order2);
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        // Llama al método del servicio
        List<Order> result = orderService.listAllOrders();

        // Verifica que el repositorio fue llamado una vez
        Mockito.verify(orderRepository, times(1)).findAll();

        // Verifica que el resultado es correcto
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(testOrder));
        assertTrue(result.contains(order2));
        result.forEach(order -> System.out.println("Lista de datos ->ID: " + order.getId() + ", Amount: " + order.getAmount()));


    }


}