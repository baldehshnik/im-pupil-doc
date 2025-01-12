package im.pupil.api.dto.lesson;

import java.io.Serializable;

public class GetLessonWithPassStatusDto implements Serializable {

    private GetLessonDto getLessonDto;
    private GetPassDto getPassDto;

    public GetLessonWithPassStatusDto() {}

    public GetLessonWithPassStatusDto(
            GetLessonDto getLessonDto,
            GetPassDto getPassDto
    ) {
        this.getLessonDto = getLessonDto;
        this.getPassDto = getPassDto;
    }

    public GetLessonDto getGetLessonDto() {
        return getLessonDto;
    }

    public void setGetLessonDto(GetLessonDto getLessonDto) {
        this.getLessonDto = getLessonDto;
    }

    public GetPassDto getGetPassDto() {
        return getPassDto;
    }

    public void setGetPassDto(GetPassDto getPassDto) {
        this.getPassDto = getPassDto;
    }
}

























