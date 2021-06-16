package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;


public class SqlUtils {
    @SneakyThrows
    public static Connection getConnection() {
        String dbUrl = System.getProperty("db.url");
        String login = System.getProperty("login");
        String password = System.getProperty("password");
        final Connection connection = DriverManager.getConnection(dbUrl, login, password);
        return connection;
    }

    @SneakyThrows
    public static String getPaymentId() {
        String paymentId = null;
        val idSQL = "SELECT payment_id FROM order_entity order by created desc limit 1;";
        try (val conn = getConnection();
             val statusStmt = conn.prepareStatement(idSQL)) {
            try (val rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    paymentId = rs.getString("payment_id");
                }
            }
        }
        return paymentId;
    }

    @SneakyThrows
    public static String getStatusForPaymentByDebitCard(String paymentId) {
        String statusSQL = "SELECT status FROM payment_entity WHERE transaction_id =?; ";
        String status = null;
        try (val conn = getConnection();
             val statusStmt = conn.prepareStatement(statusSQL)) {
            statusStmt.setString(1, paymentId);
            try (val rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        }
        return status;
    }

    @SneakyThrows
    public static String getPaymentAmount(String paymentId) {
        String amountSQL = "SELECT amount FROM payment_entity WHERE transaction_id =?; ";
        String amount = null;
        try (val conn = getConnection();
             val statusStmt = conn.prepareStatement(amountSQL)) {
            statusStmt.setString(1, paymentId);
            try (val rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    amount = rs.getString("amount");
                }
            }
        }
        return amount;
    }

    @SneakyThrows
    public static String getStatusForPaymentByCreditCard(String paymentId) {
        String statusSQL = "SELECT status FROM credit_request_entity WHERE bank_id =?; ";
        String status = null;
        try (val conn = getConnection();
             val statusStmt = conn.prepareStatement(statusSQL)) {
            statusStmt.setString(1, paymentId);
            try (val rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        }
        return status;
    }
}
