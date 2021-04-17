import java.sql.ResultSet;
import java.sql.*;

public class CustomerService
{
    //Search for a title in the book table in database based on list of data
    public Customer searchCustomerName(Customer customer) {
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            String select = "SELECT * FROM online_bookstore.customer WHERE customer_name LIKE '%" + customer.getName() + "%';";
            ResultSet rs = statement.executeQuery(select);
            Customer foundCustomer = new Customer();

            while (rs.next()) {
                foundCustomer.setId(rs.getInt("customer_id"));
                foundCustomer.setPhone(rs.getString("customer_phone"));
                foundCustomer.setName(rs.getString("customer_name"));
                foundCustomer.setAddress(rs.getString("customer_address"));
                return foundCustomer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Customer();
    }


}
