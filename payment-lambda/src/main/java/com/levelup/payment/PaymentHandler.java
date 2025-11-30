package com.levelup.payment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * AWS Lambda function for processing payment orders from SQS queue
 * Integrado con el sistema LevelUp E-Commerce
 */
public class PaymentHandler implements RequestHandler<SQSEvent, Map<String, Object>> {
    
    private static final Logger logger = LogManager.getLogger(PaymentHandler.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public Map<String, Object> handleRequest(SQSEvent event, Context context) {
        logger.info("Processing {} SQS messages for LevelUp payments", event.getRecords().size());
        
        Map<String, Object> response = new HashMap<>();
        int successCount = 0;
        int failureCount = 0;
        
        for (SQSMessage message : event.getRecords()) {
            try {
                processPayment(message);
                successCount++;
            } catch (Exception e) {
                logger.error("Error processing message: {}", message.getMessageId(), e);
                failureCount++;
            }
        }
        
        response.put("totalMessages", event.getRecords().size());
        response.put("successCount", successCount);
        response.put("failureCount", failureCount);
        response.put("timestamp", Instant.now().toString());
        response.put("system", "LevelUp E-Commerce");
        
        logger.info("Processing complete: {} successful, {} failed", successCount, failureCount);
        return response;
    }
    
    private void processPayment(SQSMessage message) {
        String messageBody = message.getBody();
        logger.info("Processing payment message: {}", message.getMessageId());
        
        try {
            // Parse order from message
            PaymentOrder order = gson.fromJson(messageBody, PaymentOrder.class);
            
            logger.info("Order details - ID: {}, User: {}, Amount: {}", 
                       order.getOrderId(), order.getUserId(), order.getTotalAmount());
            
            // Simulate payment processing
            boolean paymentSuccess = processPaymentTransaction(order);
            
            if (paymentSuccess) {
                logger.info("Payment processed successfully for order: {}", order.getOrderId());
                // Aquí se integraría con:
                // 1. Actualizar estado del carrito
                // 2. Notificar al microservicio de usuarios
                // 3. Enviar email de confirmación
                // 4. Actualizar inventario en core_negocio
            } else {
                logger.warn("Payment failed for order: {}", order.getOrderId());
                // Manejar fallo de pago
            }
            
        } catch (Exception e) {
            logger.error("Error parsing or processing payment order", e);
            throw new RuntimeException("Payment processing failed", e);
        }
    }
    
    private boolean processPaymentTransaction(PaymentOrder order) {
        logger.info("Initiating payment transaction for order: {}", order.getOrderId());
        
        try {
            // Simular llamada a payment gateway
            Thread.sleep(1000);
            
            // Validar datos
            if (order.getTotalAmount() <= 0) {
                logger.error("Invalid amount: {}", order.getTotalAmount());
                return false;
            }
            
            if (order.getUserId() == null || order.getUserId().isEmpty()) {
                logger.error("Invalid user ID");
                return false;
            }
            
            // Simular 95% tasa de éxito
            boolean success = Math.random() < 0.95;
            
            if (success) {
                logger.info("Payment gateway approved transaction for order: {}", order.getOrderId());
            } else {
                logger.warn("Payment gateway declined transaction for order: {}", order.getOrderId());
            }
            
            return success;
            
        } catch (InterruptedException e) {
            logger.error("Payment processing interrupted", e);
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error during payment processing", e);
            return false;
        }
    }
    
    /**
     * Payment Order data model
     */
    private static class PaymentOrder {
        private String orderId;
        private String userId;
        private Double totalAmount;
        private String currency;
        private String paymentMethod;
        private Map<String, Object> items;
        
        public String getOrderId() {
            return orderId;
        }
        
        public String getUserId() {
            return userId;
        }
        
        public Double getTotalAmount() {
            return totalAmount;
        }
        
        public String getCurrency() {
            return currency;
        }
        
        public String getPaymentMethod() {
            return paymentMethod;
        }
        
        public Map<String, Object> getItems() {
            return items;
        }
    }
}
