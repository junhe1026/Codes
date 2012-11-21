 

import fit.ColumnFixture;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander
 * Date: 30/11/2011
 * Time: 4:23 μμ
 * To change this template use File | Settings | File Templates.
 */
public class GivenTheFollowingTariffPlans extends ColumnFixture
{
     public String Name;
	public double OffPeakChargePer10SecondsInPence;
    public double OnPeakChargePer10SecondsInPence;


    @Override
	public void reset() {
		Name = null;
		OffPeakChargePer10SecondsInPence = 0;
        OnPeakChargePer10SecondsInPence = 0;
	}

	@Override
	public void execute() {

        if(Name.equals("Business"))
        {
            SystemUnderTest.data.boff = new BigDecimal(OffPeakChargePer10SecondsInPence/10);
            SystemUnderTest.data.bon = new BigDecimal(OnPeakChargePer10SecondsInPence/10);
        }
        if(Name.equals("Leisure"))
        {
            SystemUnderTest.data.loff = new BigDecimal(OffPeakChargePer10SecondsInPence/10);
            SystemUnderTest.data.lon = new BigDecimal(OnPeakChargePer10SecondsInPence/10);
        }
        if(Name.equals("Standard"))
        {
            SystemUnderTest.data.soff = new BigDecimal(OffPeakChargePer10SecondsInPence/10);
            SystemUnderTest.data.son = new BigDecimal(OnPeakChargePer10SecondsInPence/10);
        }

	}


}
