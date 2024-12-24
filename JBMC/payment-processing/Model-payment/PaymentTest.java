public class PaymentTest {
    public static void main(String[] args) {
        // 创建Payment对象并进行测试
        Payment payment = new Payment();
        
        // 测试卡号验证 - 添加详细的错误信息
        payment.setCardNumber("1234567890123456");
        if (payment.getCardNumber() == null || payment.getCardNumber().length() != 16) {
            throw new RuntimeException("Card number validation failed: length must be 16");
        }
        
        // 测试CVV验证
        payment.setCvv("123");
        if (payment.getCvv() == null || payment.getCvv().length() != 3) {
            throw new RuntimeException("CVV validation failed: length must be 3");
        }
        
        // 测试过期月份验证
        payment.setExpiryMonth(12);
        if (payment.getExpiryMonth() < 1 || payment.getExpiryMonth() > 12) {
            throw new RuntimeException("Expiry month must be between 1 and 12");
        }
        
        // 测试过期年份验证
        payment.setExpiryYear(2025);
        if (payment.getExpiryYear() < 2023) {
            throw new RuntimeException("Expiry year must be 2023 or later");
        }
        
        // 测试支付类型
        payment.setType("CREDIT_CARD");
        if (!"CREDIT_CARD".equals(payment.getType())) {
            throw new RuntimeException("Payment type must be CREDIT_CARD");
        }

        // 打印验证成功信息
        System.out.println("All validations passed successfully!");
    }
}