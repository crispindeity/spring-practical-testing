package study.cafekiosk.learning;


import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class GuavaLearningTest {

    @DisplayName("구아바 라이브러리 리스트 파티션 학습 테스트")
    @Test
    void guavaListPartition() throws Exception {
        //given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        //when
        List<List<Integer>> partition = Lists.partition(integers, 3);
        

        //then
        Assertions.assertThat(partition).hasSize(2);
    }
}
