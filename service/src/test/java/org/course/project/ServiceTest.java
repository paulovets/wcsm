package org.course.project;

import org.apache.commons.lang.ArrayUtils;
import org.course.project.model.system.*;
import org.course.project.service.interfaces.*;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.course.project.utility.exceptions.ParametersException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class ServiceTest {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    @Qualifier(value = "componentTemplateService")
    public IEntityService<ComponentTemplate> componentTemplateService;

    @Autowired
    @Qualifier(value = "containerTemplateService")
    public IEntityService<ContainerTemplate> containerTemplateService;

    @Autowired
    @Qualifier(value = "alpacaService")
    public IEntityService<AlpacaEntity> alpacaService;

    @Autowired
    @Qualifier(value = "componentService")
    public IEntityService<Component> componentService;

    @Autowired
    public IDataService dataService;

    @Autowired
    public IDynamicEntityService dynamicEntityService;

    @Autowired
    public IPageService pageService;

    @Autowired
    @Qualifier("siteService")
    public IEntityService<Site> siteService;

    @Autowired
    @Qualifier("containerService")
    public IEntityService<Container> containerService;

    @Autowired
    public IUserService userService;

    public static Boolean isInited = false;

    public final static String TEST_WORD = "test";
    public final static String TEST_CONFIG = "{\"test\":\"test\"}";
    private final static String DYNAMIC_NAME = "DemoDynamicEntity";


    @Before
    @Transactional
    public void init() throws NoSuchMethodException, ParametersException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException {

        if( !ServiceTest.isInited ) {

            ServiceTest.isInited = true;

            final Integer numberOfRecords = 10;

            final Field[] configurableClassFieldsArray = AConfigurableComponent.class.getDeclaredFields();
            final Field[] clientAdminFieldsArray = ClientAdmin.class.getDeclaredFields();
            final Field[] siteFieldsArray = (Field[]) ArrayUtils.addAll(Site.class.getDeclaredFields(), configurableClassFieldsArray);
            final Field[] pageFieldsArray = (Field[]) ArrayUtils.addAll(Page.class.getDeclaredFields(), configurableClassFieldsArray);
            final Field[] containerTemplateFieldsArray = (Field[]) ArrayUtils.addAll(ContainerTemplate.class.getDeclaredFields(), configurableClassFieldsArray);
            final Field[] containerFieldsArray = (Field[]) ArrayUtils.addAll(Container.class.getDeclaredFields(), configurableClassFieldsArray);
            final Field[] componentFieldsArray = (Field[]) ArrayUtils.addAll(Component.class.getDeclaredFields(), configurableClassFieldsArray);
            final Field[] componentTemplateFieldsArray = (Field[]) ArrayUtils.addAll(ContainerTemplate.class.getDeclaredFields(), configurableClassFieldsArray);
            final Field[] alpacaFieldsArray = (Field[]) ArrayUtils.addAll(AlpacaEntity.class.getDeclaredFields(), configurableClassFieldsArray);

            final List<Site> sitesList = new ArrayList<Site>();
            final List<ComponentTemplate> componentTemplatesList = new ArrayList<ComponentTemplate>();
            final List<ContainerTemplate> containerTemplatesList = new ArrayList<ContainerTemplate>();
            Map<String, String> fieldToValueMap = null;
            for (Integer i = 0; i < numberOfRecords; i++) {

                String column = ServiceTest.TEST_WORD + i;

                fieldToValueMap = ServiceTest.getFieldsMap(clientAdminFieldsArray, i);
                ClientAdmin clientAdmin = this.userService.persist(fieldToValueMap);

                fieldToValueMap = ServiceTest.getFieldsMap(siteFieldsArray, i);
                fieldToValueMap.put("adminid", clientAdmin.getId().toString());
                Site s = this.siteService.persist(fieldToValueMap);

                fieldToValueMap = ServiceTest.getFieldsMap(containerTemplateFieldsArray, i);
                ContainerTemplate conT = this.containerTemplateService.persist(fieldToValueMap);

                fieldToValueMap = ServiceTest.getFieldsMap(componentTemplateFieldsArray, i);
                ComponentTemplate comT = this.componentTemplateService.persist(fieldToValueMap);

                if (i < 2) {
                    sitesList.add(s);
                    componentTemplatesList.add(comT);
                    containerTemplatesList.add(conT);
                }

            }
            em.flush();

            final List<Page> pagesList = new ArrayList<Page>();
            for (Integer j = 0; j < sitesList.size(); j++) {
                Page page = null;
                for (Integer i = 0; i < numberOfRecords; i++) {

                    fieldToValueMap = ServiceTest.getFieldsMap(pageFieldsArray, i);
                    fieldToValueMap.put("siteid", sitesList.get(j).getId().toString());
                    Page p = this.pageService.persist(fieldToValueMap);

                    if (i == 0) {
                        page = p;
                    }

                }
                pagesList.add(page);
            }
            em.flush();

            final List<Container> containersList = new ArrayList<Container>();
            Container container = null;
            Integer k = null;
            for (Integer j = 0; j < pagesList.size(); j++) {
                for (k = 0; k < numberOfRecords - 3; k++) {

                    fieldToValueMap = ServiceTest.getFieldsMap(containerFieldsArray, k);
                    fieldToValueMap.put("containertemplateid", containerTemplatesList.get(j).getId().toString());
                    fieldToValueMap.put("pageid", pagesList.get(j).getId().toString());
                    Container c = this.containerService.persist(fieldToValueMap);

                    if (k == 0) {
                        container = c;
                    }

                }
                containersList.add(container);
                em.flush();

                for (; k < numberOfRecords; k++) {

                    fieldToValueMap = ServiceTest.getFieldsMap(containerFieldsArray, k);
                    fieldToValueMap.put("containertemplateid", containerTemplatesList.get(j).getId().toString());
                    fieldToValueMap.put("pageid", pagesList.get(j).getId().toString());
                    fieldToValueMap.put("containerid", container.getId().toString());
                    Container c = this.containerService.persist(fieldToValueMap);

                }
            }
            em.flush();

            final List<Component> componentsList = new ArrayList<Component>();
            for (Integer j = 0; j < containersList.size(); j++) {
                Component component = null;
                for (Integer i = 0; i < numberOfRecords; i++) {

                    fieldToValueMap = ServiceTest.getFieldsMap(componentFieldsArray, i);
                    fieldToValueMap.put("componenttemplateid", componentTemplatesList.get(j).getId().toString());
                    fieldToValueMap.put("containerid", containersList.get(j).getId().toString());
                    Component c = this.componentService.persist(fieldToValueMap);

                    if (i == 0) {
                        component = c;
                    }

                }
                componentsList.add(component);
            }
            em.flush();

            try (final BufferedReader alpacaSchemaReader = new BufferedReader(new FileReader("target/test-classes/alpacaschema.txt"));
                 final BufferedReader alpacaOptionsReader = new BufferedReader(new FileReader("target/test-classes/alpacaschema.txt"));
                 final BufferedReader alpacaDataReader = new BufferedReader(new FileReader("target/test-classes/alpacadata.txt"));) {

                String alpacaData = alpacaDataReader.readLine();
                String alpacaSchema = alpacaSchemaReader.readLine();
                String alpacaOptions = alpacaOptionsReader.readLine();
                for (Integer j = 0; j < componentsList.size(); j++) {

                    fieldToValueMap = new HashMap<String, String>();
                    for (Field field : alpacaFieldsArray) {
                        if (field.getType() == String.class) {
                            if (field.getName().equals("configSchema")) {
                                fieldToValueMap.put(field.getName(), alpacaSchema);
                            } else if (field.getName().equals("configOptions")) {
                                fieldToValueMap.put(field.getName(), alpacaOptions);
                            } else {
                                fieldToValueMap.put(field.getName(), ServiceTest.TEST_WORD);
                            }
                        }
                    }
                    AlpacaEntity alpacaEntity = this.alpacaService.persist(fieldToValueMap);

                    fieldToValueMap = new HashMap<String, String>();
                    fieldToValueMap.put("alpacaid", alpacaEntity.getId().toString());
                    fieldToValueMap.put("componentid", componentsList.get(j).getId().toString());
                    fieldToValueMap.put("classname", ServiceTest.TEST_WORD);
                    Data data = this.dataService.persist(fieldToValueMap);

                    em.flush();

                    alpacaData = alpacaData.replaceAll("\\}$", ",\"parentid\":\"" + data.getId() + "\"}");
                    Map<String, String> dynamicMap = new HashMap<String, String>();
                    dynamicMap.put("alpacadata", alpacaData);
                    dynamicMap.put("classname", ServiceTest.TEST_WORD);

                    this.dynamicEntityService.persist(dynamicMap);

                }

            }
        }
    }

    private static Map<String,String> getFieldsMap(final Field[] fieldsArray, final Integer counter) {

        String column = ServiceTest.TEST_WORD + counter;

        Map<String,String> fieldToValueMap = new HashMap<String,String>();
        for(Field field : fieldsArray) {
            if (field.getType() == String.class) {
                if(field.getName().indexOf("config") > -1) {
                    fieldToValueMap.put(field.getName(), ServiceTest.TEST_CONFIG);
                } else {
                    fieldToValueMap.put(field.getName(), column);
                }
            }
        }

        return fieldToValueMap;

    }

    @Transactional
    @Test(expected = ParametersException.class)
    public void testBaseUpdateFail() throws ParametersException {

        ClientAdmin clientAdmin = this.userService.get(new HashMap<String, String>()).get(0);

        Map<String,String> fieldsForUpdate = new HashMap<String,String>();
        final String testWord = "password";
        fieldsForUpdate.put(TEST_WORD, testWord);

        this.userService.update(fieldsForUpdate);

    }

    @Transactional
    @Test(expected = ParametersException.class)
    public void testBaseRemoveFail() throws ParametersException {

        this.userService.remove(new HashMap<String, String>());

    }

    @Transactional
    @Test
    public void testBaseUpdateAndRemove() throws ParametersException {

        Page page = null;
        List<Page> pagesList = this.pageService.get(new HashMap<String, String>());
        for(Page p : pagesList) {
            if(p.getContainers().size() != 0) {
                page = p;
                break;
            }
        }

        Map<String,String> fieldsForUpdate = new HashMap<String,String>();
        final String testWord = "configOptions";
        fieldsForUpdate.put(testWord, testWord);
        fieldsForUpdate.put("id", page.getId().toString());

        this.pageService.update(fieldsForUpdate);
        Assert.assertTrue(page.getConfigOptions().equals(testWord));

        fieldsForUpdate.put("id", page.getId().toString());
        this.pageService.remove(fieldsForUpdate);
        pagesList = this.pageService.get(fieldsForUpdate);
        Assert.assertTrue(pagesList.size() == 0);

    }

    @Transactional
    @Test
    public void testDataService() throws ParametersException {

        List<Data> datasList = this.dataService.get(new HashMap<String, String>());
        final Long componentId = datasList.get(0).getComponent().getId();

        final Map<String,String> relationshipsMap = new HashMap<String,String>();
        relationshipsMap.put("componentid", componentId.toString());
        datasList = this.dataService.getByParents(relationshipsMap);
        Assert.assertTrue(datasList.size() > 0);

    }

    @Transactional
    @Test
    public void testDynamicService() throws ParametersException, IOException {

        this.dynamicEntityService.initDynamicClasses();

        final Data data = this.dataService.get(new HashMap<String, String>()).get(0);
        final Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("classname", ServiceTest.TEST_WORD);
        paramsMap.put("parentid", data.getId().toString());
        final List<DynamicEntity> dynamicsList = this.dynamicEntityService.getRecordsByParent(paramsMap);
        Assert.assertTrue(dynamicsList.size() > 0);

        paramsMap.put("id", String.valueOf(dynamicsList.get(0).get("id")));
        DynamicEntity dynamicEntity = this.dynamicEntityService.get(paramsMap);
        Assert.assertTrue(dynamicEntity.get("id") != null);

        final Map<String,String> fieldsForUpdateMap = new HashMap<String,String>();
        fieldsForUpdateMap.put("id", String.valueOf(dynamicsList.get(0).get("id")));
        fieldsForUpdateMap.put("classname", ServiceTest.TEST_WORD);
        final String oldFocus = dynamicEntity.<String>get("focus");
        final String newFocus = "ololo";
        fieldsForUpdateMap.put("focus", newFocus);
        dynamicEntity = this.dynamicEntityService.update(fieldsForUpdateMap);

        Assert.assertTrue(!oldFocus.equals(dynamicEntity.<String>get("focus")));

        final String queryStr = new String("DROP TABLE " + ServiceTest.TEST_WORD + ";");
        final Query query = em.createNativeQuery(queryStr);
        query.executeUpdate();

    }

    @Transactional
    @Test
    public void testUserService() throws ParametersException {

        final String existedUsername = ServiceTest.TEST_WORD + 0;
        final String existedPassword = ServiceTest.TEST_WORD + 0;

        final Map<String,String> credentialsMap = new HashMap<String,String>();
        credentialsMap.put("username", existedUsername);
        credentialsMap.put("password", existedPassword);

        final ClientAdmin clientAdmin = this.userService.doLogin(credentialsMap);
        Assert.assertTrue(clientAdmin.getId() != null);

    }

    @Transactional
    @Test
    public void testPageService() throws ParametersException, IOException {

        Page page = null;
        List<Page> pagesList = this.pageService.get(new HashMap<String, String>());
        for(Page p : pagesList) {
            if(p.getContainers().size() > 0) {
                page = p;
                break;
            }
        }

        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("pageid", page.getId().toString());
        final String resultOutput = this.pageService.getPageConfig(paramsMap);
        Assert.assertTrue(resultOutput.length() > 0);

        Site site = null;
        List<Site> sitesList = this.siteService.get(new HashMap<String, String>());
        for(Site s : sitesList) {
            if(s.getPages().size() > 0) {
                site = s;
                break;
            }
        }

        paramsMap = new HashMap<String,String>();
        paramsMap.put("siteid", site.getId().toString());
        paramsMap.put("type", ServiceTest.TEST_WORD + 0);
        page = this.pageService.getPageByType(paramsMap);
        Assert.assertTrue(page.getId() != null);

    }

}
