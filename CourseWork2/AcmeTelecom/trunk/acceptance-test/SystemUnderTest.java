 

import com.acmetelecom.BillGenerator;
import com.acmetelecom.Biller;
import com.acmetelecom.ConfigurableBillingSystem;
import com.acmetelecom.Generator;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.fakes.FakeClock;
import com.acmetelecom.fakes.FakeCustomerDatabase;
import com.acmetelecom.fakes.FakePrinter;
import com.acmetelecom.fakes.FakeTariffLibrary;
import com.acmetelecom.platform.Printer;
import com.acmetelecom.rates.RateSelector;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander
 * Date: 30/11/2011
 * Time: 3:16 μμ
 * To change this template use File | Settings | File Templates.
 */
public class SystemUnderTest
{
    public static ByteArrayOutputStream os = new ByteArrayOutputStream();
    public static PrintStream out = new PrintStream(os);
    public static TariffData data = new TariffData();
    public static TariffLibrary tariffDataBase;
    public static FakeCustomerDatabase customerDataBase = new FakeCustomerDatabase();
    public static Printer printer = new FakePrinter(out);
    public static final Generator bGenerator = new BillGenerator(printer);
    public static RateSelector selector;
    public static final FakeClock clock= new FakeClock(Calendar.getInstance());

    public static Biller bs;

    public SystemUnderTest()
    {
    //void
    }

    public static void setBs()
    {
      bs = new ConfigurableBillingSystem(customerDataBase, tariffDataBase,  bGenerator, selector, clock );
    }

    public static void setTar()
    {
        tariffDataBase = new FakeTariffLibrary(data.boff,data.bon,data.loff,data.lon,data.soff,data.son);
    }


}
