package com.mybury.waver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybury.waver.common.code.PushType;
import com.mybury.waver.domain.Alarm;
import com.mybury.waver.repository.AlarmRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.PartTree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlarmServiceTest {

    @Test
    void queryRestrictsUserAndOrdersByCreationThenIdDescending() throws Exception {
        String queryMethod = AlarmRepository.class
                .getMethod("findByUserIdOrderByCreatedAtDescIdDesc", long.class).getName();
        PartTree query = new PartTree(queryMethod, Alarm.class);

        assertThat(query.getParts().toList()).hasSize(1);
        assertThat(query.getParts().iterator().next().getProperty().toDotPath()).isEqualTo("userId");
        assertThat(query.getSort()).isEqualTo(Sort.by(Sort.Direction.DESC, "createdAt", "id"));
    }

    @Test
    void responsePreservesOrderedResultsForRequestedUser() {
        AlarmRepository repository = mock(AlarmRepository.class);
        when(repository.findByUserIdOrderByCreatedAtDescIdDesc(7L)).thenReturn(List.of(
                Alarm.builder().id(20L).userId(7L).type(PushType.NOTICE).message("newest").build(),
                Alarm.builder().id(10L).userId(7L).type(PushType.NOTICE).message("older").build()));

        JsonNode response = new ObjectMapper().valueToTree(new AlarmService(repository).getList(7L));

        assertThat(response.path("alarms").findValuesAsText("message")).containsExactly("newest", "older");
        verify(repository).findByUserIdOrderByCreatedAtDescIdDesc(7L);
    }

    @Test
    void emptyHistoryReturnsEmptyList() {
        AlarmRepository repository = mock(AlarmRepository.class);

        assertThat(new AlarmService(repository).getList(7L).alarms()).isEmpty();
    }
}
