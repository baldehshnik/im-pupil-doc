package im.pupil.api.domain.image.storage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageDirectoryProvider {

    private final String baseDirectory;

    public ImageDirectoryProvider(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getDirectoryPath(ImageWorker.ImageType imageType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        String datePath = dateFormat.format(new Date());
        return baseDirectory + "/" + imageType.getDirectoryName() + "/" + datePath;
    }
}
