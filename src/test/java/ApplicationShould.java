import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationShould {

    @Test
    void work() {
        Application app = new Application();

        boolean isWork = app.isWork();

        Assertions.assertThat(isWork).isTrue();
    }
}
