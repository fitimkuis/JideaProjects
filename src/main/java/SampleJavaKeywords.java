import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class SampleJavaKeywords {
    @RobotKeyword
    public String fooJavaKeyword(){
        return "foo";
    }
}
