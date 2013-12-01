package nz.co.yellow.vault.content.test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;
import nz.co.yellow.vault.content.data.repository.MturkFieldsRepositoryCustom;
import nz.co.yellow.vault.content.data.repository.MturkFieldsRepositoryImpl;
import nz.co.yellow.vault.content.ds.SiliconeMturkFieldsDS;
import nz.co.yellow.vault.content.ds.impl.SiliconeMturkFieldsDSImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

@ContextConfiguration(classes = TestAppConfig.class)
public class RepositoryMturkFieldsMockTest {

  private SiliconeMturkFieldsDS siliconeMturkFieldsDs;

  private MturkFieldsRepositoryCustom repositoryMock;

  @Before
  public void setUp()
      throws Exception {
    siliconeMturkFieldsDs = new SiliconeMturkFieldsDSImpl();
    repositoryMock = mock(MturkFieldsRepositoryImpl.class);
    ReflectionTestUtils.setField(siliconeMturkFieldsDs,
        "mturkFieldsCustRepository", repositoryMock);
  }

  @Test
  public void testCreateOrMergeSiliconeMturkFields()
      throws Exception {

    SiliconeMturkModel added = buildCreatedMturkFields();

    siliconeMturkFieldsDs.createOrMergeSiliconeMturkFields(added);

    ArgumentCaptor<SiliconeMturkModel> fieldsArgument = ArgumentCaptor
        .forClass(SiliconeMturkModel.class);

    verify(repositoryMock, times(1)).procedureProcess(
        fieldsArgument.capture());
    verifyNoMoreInteractions(repositoryMock);

    SiliconeMturkModel actual = fieldsArgument.getValue();
    assertSiliconeMturkModel(added, actual);
  }

  private SiliconeMturkModel buildCreatedMturkFields() {
    SiliconeMturkModel siliconeMturkModel = new SiliconeMturkModel();
    siliconeMturkModel.setYellowListId(123);
    siliconeMturkModel.setEmail("test@yellow.co.nz");
    siliconeMturkModel.setFax("12345");
    return siliconeMturkModel;
  }

  public static void assertSiliconeMturkModel(
      SiliconeMturkModel modelAdded, SiliconeMturkModel modelActual) {
    assertEquals(modelAdded.getYellowListId(),
        modelActual.getYellowListId());
    assertEquals(modelAdded.getEmail(), modelActual.getEmail());
    assertEquals(modelAdded.getFax(), modelActual.getFax());
  }

}
