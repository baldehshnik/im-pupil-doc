package im.pupil.api.data.repository;

import im.pupil.api.data.entity.about.About;
import im.pupil.api.data.entity.institution.EducationalInstitution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class AboutRepositoryTest {

    @Autowired
    private AboutRepository aboutRepository;

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    private EducationalInstitution institution;

    @BeforeEach
    public void setUp() {
        String uniqueName = "Test Institution " + System.nanoTime();

        institution = EducationalInstitution.builder()
                .name(uniqueName)
                .abbreviation("TI")
                .type(1)
                .address("123 Test St")
                .phone("123-456-7890")
                .build();

        institution = educationalInstitutionRepository.save(institution);

        About about1 = new About();
        about1.setInstitution(institution);
        about1.setDescription("Description 1");
        about1.setIcon("Icon1.png");
        aboutRepository.save(about1);

        About about2 = new About();
        about2.setInstitution(institution);
        about2.setDescription("Description 2");
        about2.setIcon("Icon2.png");
        aboutRepository.save(about2);
    }

    @AfterEach
    public void clearAll() {
        aboutRepository.deleteAll();
        educationalInstitutionRepository.deleteAll();
    }

    @Test
    public void testCreateAndFindAbout() {
        About about = new About();
        about.setInstitution(institution);
        about.setDescription("New Description");
        about.setIcon("NewIcon.png");

        aboutRepository.save(about);
        List<About> retrieved = aboutRepository.readAboutBlocks(institution.getId());

        assertThat(retrieved).hasSize(3);
        assertThat(retrieved)
                .extracting(About::getDescription)
                .contains("New Description");
    }

    @Test
    public void testUpdateAbout() {
        About about = new About();
        about.setInstitution(institution);
        about.setDescription("Old Description");
        about.setIcon("OldIcon.png");
        about = aboutRepository.save(about);

        about.setDescription("Updated Description");
        aboutRepository.save(about);

        List<About> retrieved = aboutRepository.readAboutBlocks(institution.getId());
        assertThat(retrieved)
                .extracting(About::getDescription)
                .contains("Updated Description");
    }

    @Test
    public void testDeleteAbout() {
        About about = new About();
        about.setInstitution(institution);
        about.setDescription("To be deleted");
        about.setIcon("IconToDelete.png");
        about = aboutRepository.save(about);

        aboutRepository.delete(about);
        List<About> retrieved = aboutRepository.readAboutBlocks(institution.getId());

        assertThat(retrieved)
                .extracting(About::getDescription)
                .doesNotContain("To be deleted");
    }

    @Test
    public void testReadAboutBlocks() {
        List<About> aboutBlocks = aboutRepository.readAboutBlocks(institution.getId());

        assertThat(aboutBlocks).isNotEmpty();
        assertThat(aboutBlocks.size()).isEqualTo(2);
        assertThat(aboutBlocks.get(0).getDescription()).isEqualTo("Description 1");
        assertThat(aboutBlocks.get(1).getDescription()).isEqualTo("Description 2");
    }

    @Test
    public void testReadAboutBlocks_NoResults() {
        List<About> aboutBlocks = aboutRepository.readAboutBlocks(999);

        assertThat(aboutBlocks).isEmpty();
    }
}











