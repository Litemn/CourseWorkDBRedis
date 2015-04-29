import org.junit.Test;

/**
 * Created by litemn on 29.04.15.
 */
public class TestEditDistance {
    
    @Test
    public void testDistance(){
        assert(EditDistance.distance("aaaa","aaaa")==0);
        assert(EditDistance.distance("","")==0);
        assert(EditDistance.distance("qwer","asdf")==8);
        assert(EditDistance.distance("a","b")==2);
        assert(EditDistance.distance("aa","b")==3);
    }
}
