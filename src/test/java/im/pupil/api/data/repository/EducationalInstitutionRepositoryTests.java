package im.pupil.api.data.repository;

import im.pupil.api.data.entity.institution.EducationalInstitution;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EducationalInstitutionRepositoryTests {

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    private EducationalInstitution institution1;
    private EducationalInstitution institution2;

    @BeforeEach
    public void setUp() {
        institution1 = EducationalInstitution.builder()
                .name("Test Institution One")
                .abbreviation("TI1")
                .type(1)
                .address("123 Test St")
                .phone("123-456-7890")
                .build();

        institution2 = EducationalInstitution.builder()
                .name("Test Institution Two")
                .abbreviation("TI2")
                .type(2)
                .address("456 Another St")
                .phone("987-654-3210")
                .build();

        educationalInstitutionRepository.save(institution1);
        educationalInstitutionRepository.save(institution2);
    }

    @AfterEach
    public void tearDown() {
        educationalInstitutionRepository.deleteAll();
    }

    @Test
    public void testFindByName() {
        Optional<EducationalInstitution> foundInstitution = educationalInstitutionRepository.findByName(institution1.getName());

        assertThat(foundInstitution).isPresent();

        //noinspection OptionalGetWithoutIsPresent
        assertThat(foundInstitution.get().getId()).isEqualTo(institution1.getId());
    }

    @Test
    public void testFindByAbbreviation() {
        Optional<EducationalInstitution> foundInstitution = educationalInstitutionRepository.findByAbbreviation(institution2.getAbbreviation());

        assertThat(foundInstitution).isPresent();

        //noinspection OptionalGetWithoutIsPresent
        assertThat(foundInstitution.get().getId()).isEqualTo(institution2.getId());
    }

    @Test
    public void testFindByName_NotFound() {
        Optional<EducationalInstitution> foundInstitution = educationalInstitutionRepository.findByName("Nonexistent Institution");

        assertThat(foundInstitution).isNotPresent();
    }

    @Test
    public void testFindTop8ByNameContaining() {
        List<EducationalInstitution> institutions = educationalInstitutionRepository.findTop8ByNameContaining("Test");

        assertThat(institutions).isNotEmpty();
        assertThat(institutions).hasSize(2);
        assertThat(institutions)
                .extracting(EducationalInstitution::getName)
                .contains("Test Institution One", "Test Institution Two");
    }

    @Test
    public void testFindTop8ByNameContaining_NoResults() {
        List<EducationalInstitution> institutions = educationalInstitutionRepository.findTop8ByNameContaining("Nonexistent");

        assertThat(institutions).isEmpty();
    }

    @Test
    public void testSaveInstitution() {
        EducationalInstitution newInstitution = EducationalInstitution.builder()
                .name("New Institution")
                .abbreviation("NI")
                .type(3)
                .address("789 New St")
                .phone("555-555-5555")
                .build();

        EducationalInstitution savedInstitution = educationalInstitutionRepository.save(newInstitution);

        assertThat(savedInstitution).isNotNull();
        assertThat(savedInstitution.getId()).isNotNull();
        assertThat(savedInstitution.getName()).isEqualTo(newInstitution.getName());
    }

    @Test
    public void testFindByName_CaseInsensitive() {
        Optional<EducationalInstitution> foundInstitution = educationalInstitutionRepository.findByName("test institution one".toUpperCase());

        assertThat(foundInstitution).isPresent();

        //noinspection OptionalGetWithoutIsPresent
        assertThat(foundInstitution.get().getId()).isEqualTo(institution1.getId());
    }

    @Test
    public void testInvalidInstitution() {
        EducationalInstitution invalidInstitution = EducationalInstitution.builder()
                .name(null)
                .abbreviation("II")
                .type(0)
                .address("Invalid Address")
                .phone("Invalid Phone")
                .build();

        assertThrows(ConstraintViolationException.class, () -> educationalInstitutionRepository.save(invalidInstitution));
    }

    @Test
    public void testDeleteInstitution() {
        educationalInstitutionRepository.delete(institution1);

        Optional<EducationalInstitution> deletedInstitution = educationalInstitutionRepository.findById(institution1.getId());
        assertThat(deletedInstitution).isNotPresent();
    }

    @Test
    public void testUpdateInstitution() {
        institution1.setName("Updated Institution");
        EducationalInstitution updatedInstitution = educationalInstitutionRepository.save(institution1);

        assertThat(updatedInstitution.getName()).isEqualTo("Updated Institution");
    }

    @Test
    public void testUniqueName() {
        EducationalInstitution institution1 = EducationalInstitution.builder()
                .name("Unique Institution 1")
                .abbreviation("UI1")
                .type(1)
                .address("123 Institution St")
                .phone("123-456-7890")
                .build();

        EducationalInstitution institution2 = EducationalInstitution.builder()
                .name("Unique Institution 2")
                .abbreviation("UI2")
                .type(2)
                .address("456 Institution St")
                .phone("987-654-3210")
                .build();

        educationalInstitutionRepository.save(institution1);
        EducationalInstitution savedInstitution2 = educationalInstitutionRepository.save(institution2);

        assertThat(savedInstitution2).isNotNull();
        assertThat(savedInstitution2.getName()).isEqualTo(institution2.getName());
    }

    @Test
    public void testDuplicateNameThrowsException() {
        EducationalInstitution institution1 = EducationalInstitution.builder()
                .name("Duplicate Institution")
                .abbreviation("DI1")
                .type(1)
                .address("123 Dup St")
                .phone("111-222-3333")
                .build();

        EducationalInstitution institution2 = EducationalInstitution.builder()
                .name("Duplicate Institution")
                .abbreviation("DI2")
                .type(2)
                .address("456 Dup St")
                .phone("444-555-6666")
                .build();

        educationalInstitutionRepository.save(institution1);

        assertThrows(DataIntegrityViolationException.class, () -> educationalInstitutionRepository.save(institution2));
    }

    @Test
    public void testUpdateToExistingNameThrowsException() {
        EducationalInstitution institution1 = EducationalInstitution.builder()
                .name("Institution A")
                .abbreviation("IA")
                .type(1)
                .address("123 A St")
                .phone("000-000-0000")
                .build();

        EducationalInstitution institution2 = EducationalInstitution.builder()
                .name("Institution B")
                .abbreviation("IB")
                .type(2)
                .address("123 B St")
                .phone("111-111-1111")
                .build();

        educationalInstitutionRepository.save(institution1);
        educationalInstitutionRepository.save(institution2);

        institution1.setName("Institution B");

        assertThrows(DataIntegrityViolationException.class, () -> educationalInstitutionRepository.save(institution1));
    }
}
























