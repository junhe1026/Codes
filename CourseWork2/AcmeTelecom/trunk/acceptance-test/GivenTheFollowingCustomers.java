 

import com.acmetelecom.customer.Customer;
import fit.ColumnFixture;

import static com.acmetelecom.builders.CustomerBuilder.aCustomer;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander
 * Date: 30/11/2011
 * Time: 4:28 μμ
 * To change this template use File | Settings | File Templates.
 */
public class GivenTheFollowingCustomers extends ColumnFixture
{
    public String Name;
	public String PhoneNumber;
    public String TariffPlan;

    @Override
	public void reset() {
		Name = null;
		PhoneNumber = null;
        TariffPlan = null;
	}

	@Override
	public void execute() {
        Customer customer = aCustomer().
						    named(Name).
						withPhoneNumber(PhoneNumber).
                        andPricePlan(TariffPlan).build();

        SystemUnderTest.customerDataBase.addCustomer(customer);
	}
}
