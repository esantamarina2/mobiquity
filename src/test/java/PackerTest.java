import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;
import com.mobiquityinc.packer.PackerInstance;

public class PackerTest {

    private static final String NO_VALUE_RETURNED = "-";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    public void whenFileExistsThenValidResult() {
        String expected = Packer.pack("test-data.txt");
        assertThat("4" + LINE_SEPARATOR + NO_VALUE_RETURNED + LINE_SEPARATOR + "2,7" + LINE_SEPARATOR + "8,9", is(equalTo(expected)));
    }

    @Test(expected = APIException.class)
    public void whenFileNotExistsThenExceptionExpected() {
        Packer.pack("test-invalid-data.txt");
    }

    @Test
    public void whenNoItemsFitsThenEmptySolution() {
        String solution = Packer.solvePackerInstance(new PackerInstance("8 : (1,15.3,€34)"));
        assertThat(NO_VALUE_RETURNED, is(equalTo(solution)));
    }

    @Test
    public void whenNoItemsProvidedThenEmptySolution() {
        String solution = Packer.solvePackerInstance(new PackerInstance("8 : "));
        assertThat(NO_VALUE_RETURNED, is(equalTo(solution)));
    }

    @Test
    public void whenItemsProvidedThenSingleItemInBag() {
        String solution = Packer.solvePackerInstance((new PackerInstance("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)")));
        assertThat("4", is(equalTo(solution)));
    }

    @Test
    public void whenItemsProvidedThenMultipleItemInBag() {
        String solution = Packer.solvePackerInstance(new PackerInstance("75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)"));
        assertThat("2,7", is(equalTo(solution)));
    }

    @Test(expected = APIException.class)
    public void whenLineItemSyntaxErrorThenExceptionExpected() {
        Packer.solvePackerInstance(new PackerInstance("52:(1.7.50,$10)"));
    }

}
