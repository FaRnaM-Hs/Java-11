package string;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class StringShould {

    @Test
    void be_split_line_by_line() {
        String s = "\t line 1 \u2005 \n line 2 \r \n line 3 \r\n line 4";

        List<String> lines = s.lines()
                .filter(Predicate.not(String::isBlank))
                .map(String::strip)
                .collect(Collectors.toUnmodifiableList());
        String[] strings = lines.toArray(String[]::new);

        assertThat(lines).containsExactly("line 1", "line 2", "line 3", "line 4");
        assertThat(strings).containsExactly("line 1", "line 2", "line 3", "line 4");
    }
}
