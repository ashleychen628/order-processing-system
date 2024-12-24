public class CustomerIdNotFoundExceptionTest {
    // 内部定义异常类
    static class CustomerIdNotFoundException extends Exception {
        private int statusCode;

        public CustomerIdNotFoundException(int statusCode, String message) {
            super(message);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    // JBMC测试入口
    public static void main(String[] args) {
        boolean exceptionThrown = false;
        
        // 测试异常创建
        try {
            throwCustomException(404, "Test Message");
        } catch (CustomerIdNotFoundException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Exception was not thrown";
        
        // 测试异常消息
        try {
            String testMessage = "Customer ID 123 not found";
            throwCustomException(404, testMessage);
        } catch (CustomerIdNotFoundException e) {
            assert e.getMessage().equals("Customer ID 123 not found") : "Wrong message";
        }
        
        // 测试状态码
        try {
            throwCustomException(404, "Test");
        } catch (CustomerIdNotFoundException e) {
            assert e.getStatusCode() == 404 : "Wrong status code";
        }
        
        System.out.println("All tests passed");
    }
    
    private static void throwCustomException(int statusCode, String message) throws CustomerIdNotFoundException {
        throw new CustomerIdNotFoundException(statusCode, message);
    }
}