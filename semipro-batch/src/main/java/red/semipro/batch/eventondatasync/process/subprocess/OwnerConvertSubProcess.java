package red.semipro.batch.eventondatasync.process.subprocess;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import red.semipro.domain.eventon.model.Owner;
import red.semipro.domain.model.eventon.EventonSeminarOwner;

@Component
@RequiredArgsConstructor
public class OwnerConvertSubProcess {

    public List<EventonSeminarOwner> execute(
        @Nonnull final Integer eventId,
        @Nullable final List<Owner> ownerList) {

        if (CollectionUtils.isEmpty(ownerList)) {
            return Collections.emptyList();
        }

        final List<EventonSeminarOwner> eventonSeminarOwnerList = Lists.newArrayList();
        ownerList.forEach(owner -> eventonSeminarOwnerList.add(EventonSeminarOwner.builder()
            .eventId(eventId)
            .id(owner.getId())
            .name(owner.getName())
            .url(owner.getUrl())
            .build()));

        return eventonSeminarOwnerList;
    }

}
