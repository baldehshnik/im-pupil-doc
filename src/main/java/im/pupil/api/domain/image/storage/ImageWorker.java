package im.pupil.api.domain.image.storage;

import im.pupil.api.domain.exception.UnexpectedException;
import im.pupil.api.domain.exception.storage.FailedStorageConnectionException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class ImageWorker {
    private final String supabaseUrl;
    private final String supabaseKey;
    private final String bucketName;

    private final ImageDirectoryProvider directoryProvider;

    public ImageWorker(
            @Value("${S3_ENDPOINT_URL}") String supabaseUrl,
            @Value("${S3_ACCESS_KEY}") String supabaseKey,
            @Value("${S3_BUCKET_NAME}") String bucketName
    ) {
        this.supabaseUrl = supabaseUrl;
        this.supabaseKey = supabaseKey;
        this.bucketName = bucketName;
        this.directoryProvider = new ImageDirectoryProvider(ImageUtils.UPLOAD_DIRECTORY);
    }

    public String saveImage(
            MultipartFile image, ImageWorker.ImageType imageType
    ) throws FailedStorageConnectionException, UnexpectedException {
        String fileName = UUID.randomUUID() + ".png";
        String directoryName = directoryProvider.getDirectoryPath(imageType);

        uploadImageToSupabase(image, bucketName, directoryName + "/" + fileName);
        return supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + directoryName + "/" + fileName;
    }

    private void uploadImageToSupabase(
            MultipartFile image, String bucketName, String filePath
    ) throws FailedStorageConnectionException, UnexpectedException {
        String apiUrl = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + filePath;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(apiUrl);
            post.setHeader("Authorization", "Bearer " + supabaseKey);
            post.setHeader("Content-Type", image.getContentType());

            try (InputStream inputStream = image.getInputStream()) {
                post.setEntity(new InputStreamEntity(inputStream, image.getSize()));
                HttpResponse response = httpClient.execute(post);
                String responseString = EntityUtils.toString(response.getEntity());

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("Failed to upload file: " + responseString);
                }
            }
        } catch (IOException e) {
            throw new FailedStorageConnectionException();
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected storage exception");
        }
    }


    public enum ImageType {

        ACCOUNT("accounts"),
        ICON("icons"),
        ABOUT("about"),
        EVENT("event"),
        PRACTICE("practice"),
        SECTION("section");

        private final String directoryName;

        ImageType(String directoryName) {
            this.directoryName = directoryName;
        }

        public String getDirectoryName() {
            return directoryName;
        }
    }
}
