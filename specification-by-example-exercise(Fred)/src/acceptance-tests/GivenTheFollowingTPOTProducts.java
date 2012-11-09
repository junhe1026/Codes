import fit.ColumnFixture;
import fit.Parse;

/**
 * Created with IntelliJ IDEA.
 * User: Juno HE
 * Date: 05/11/12
 * Time: 13:26
 */
public class GivenTheFollowingTPOTProducts extends ColumnFixture {

    public String Name;
    public String Group;

    @Override
    public void doRows(Parse rows) {
        SystemUnderTest.tpotProductRange.deleteAll();
        super.doRows(rows);
    }

    @Override
    public void reset() throws Exception {
        Name = null;
        Group = null;
    }

    @Override
    public void execute() throws Exception {
        SystemUnderTest.tpotProductRange.addProduct(Name,Group);
    }
}
