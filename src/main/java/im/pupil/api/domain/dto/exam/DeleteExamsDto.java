package im.pupil.api.domain.dto.exam;

import java.io.Serializable;
import java.util.List;

public class DeleteExamsDto implements Serializable {

    private List<Integer> ids;

    public DeleteExamsDto() {
    }

    public DeleteExamsDto(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}



















