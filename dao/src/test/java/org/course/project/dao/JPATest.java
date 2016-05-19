package org.course.project.dao;

import org.course.project.dao.interfaces.*;
import org.course.project.utility.DynamicHelper;
import org.course.project.model.system.*;
import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicTypeBuilder;
import org.junit.*;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public class JPATest {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    public IClientAdminDao clientAdminIDao;

    @Autowired
    public ISiteDao siteDao;

    @Autowired
    public IPageDao pageDao;

    @Autowired
    public IContainerDao containerDao;

    @Autowired
    public IComponentDao componentDao;

    @Autowired
    public IDataDao dataDao;

    @Autowired
    public IDynamicEntityDao dynamicDao;

    @Autowired
    @Qualifier(value = "alpacaDao")
    public IDao<AlpacaEntity> alpacaDao;

    @Autowired
    @Qualifier(value = "componentTemplateDao")
    public IDao<ComponentTemplate> componentTemplateDao;

    @Autowired
    @Qualifier(value = "containerTemplateDao")
    public IDao<ContainerTemplate> containerTemplateDao;

    @Autowired
    private DynamicHelper dynamicHelper;

    private static List<String> dynamicTablesList;

    public static Boolean isInited = false;

    public static final String TEST_WORD = "test";
    private final static String SEQUENCE = "DYNAMIC_SEQ";
    private final static String DYNAMIC_NAME = "DemoDynamicEntity";

    @Before
    @Transactional
    public void init(){

        if( !JPATest.isInited ) {

            JPATest.isInited = true;

            JPATest.dynamicTablesList = new ArrayList<String>();

            final Integer numberOfRecords = 10;

            final List<Site> sitesList = new ArrayList<Site>();
            final List<ComponentTemplate> componentTemplatesList = new ArrayList<ComponentTemplate>();
            final List<ContainerTemplate> containerTemplatesList = new ArrayList<ContainerTemplate>();
            for (Integer i = 0; i < numberOfRecords; i++) {

                String column = JPATest.TEST_WORD + i;
                ClientAdmin clientAdmin = new ClientAdmin(column, column, column, column);
                this.clientAdminIDao.persist(clientAdmin);

                Site s = new Site(column, column, column);
                this.siteDao.persist(clientAdmin.getId(), s);

                ComponentTemplate comT = new ComponentTemplate(column, column);
                this.componentTemplateDao.persist(comT);

                ContainerTemplate conT = new ContainerTemplate(column, column);
                this.containerTemplateDao.persist(conT);

                if(i < 2) {
                    sitesList.add(s);
                    componentTemplatesList.add(comT);
                    containerTemplatesList.add(conT);
                }

            }
            em.flush();

            final List<Page> pagesList = new ArrayList<Page>();
            for(Integer j = 0; j < sitesList.size(); j++) {
                Page page = null;
                for (Integer i = 0; i < numberOfRecords; i++) {

                    String column = JPATest.TEST_WORD + i;
                    Page p = new Page(column, column, column);
                    this.pageDao.persist(sitesList.get(j).getId(), p);

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
            for(Integer j = 0; j < pagesList.size(); j++) {
                for (k = 0; k < numberOfRecords - 3; k++) {

                    String column = JPATest.TEST_WORD + k;
                    Container c = new Container(column, column);
                    this.containerDao.persist(containerTemplatesList.get(j).getId(), pagesList.get(j).getId(), null, c);

                    if (k == 0) {
                        container = c;
                    }

                }
                containersList.add(container);
                em.flush();

                for (; k < numberOfRecords; k++) {

                    String column = JPATest.TEST_WORD + k;
                    Container c = new Container(column, column);
                    container.addContainer(c);
                    this.containerDao.persist(containerTemplatesList.get(j).getId(), pagesList.get(j).getId(), container.getId(), c);

                }
            }
            em.flush();

            final List<Component> componentsList = new ArrayList<Component>();
            for(Integer j = 0; j < containersList.size(); j++) {
                Component component = null;
                for (Integer i = 0; i < numberOfRecords; i++) {

                    String column = JPATest.TEST_WORD + i;
                    Component c = new Component(column, column);
                    this.componentDao.persist(componentTemplatesList.get(j).getId(), containersList.get(j).getId(), c);

                    if(i == 0) {
                        component = c;
                    }

                }
                componentsList.add(component);
            }
            em.flush();

            for(Integer j = 0; j < componentsList.size(); j++) {
                final Data data = new Data();
                data.setRelatedEntityName(JPATest.DYNAMIC_NAME);

                final AlpacaEntity alpacaEntity = new AlpacaEntity("", "", "");
                this.alpacaDao.persist(alpacaEntity);

                data.setAlpacaEntity(alpacaEntity);
                this.dataDao.persist(componentsList.get(j).getId(), data);
            }

        }
    }


    @Test
    @Transactional
    public void testEclipseLinkDynamic() {

        final DynamicClassLoader dClassLoader = this.dynamicHelper.getDCL();
        final JPADynamicHelper dHelper = this.dynamicHelper.getJDH();

        final String dynamicTableName = "DEMO_DYNAMIC_ENTITY";
        JPATest.dynamicTablesList.add(dynamicTableName);

        final Class<?> dynamicEntityClass = dClassLoader.createDynamicClass("org.course.project.model.dynamic." + JPATest.DYNAMIC_NAME);

        final JPADynamicTypeBuilder newType = new JPADynamicTypeBuilder(dynamicEntityClass, null, dynamicTableName);
        newType.setPrimaryKeyFields("ID");
        newType.configureSequencing(JPATest.SEQUENCE, "ID");
        newType.addDirectMapping("id", Long.class, "ID");
        newType.addDirectMapping("name", String.class, "NAME");
        newType.addDirectMapping("description", String.class, "DESCRIPTION");
        newType.addDirectMapping("parentid", String.class, "PARENT_ID");
        dHelper.addTypes(true, true, newType.getType());

        final DynamicEntity newDynamicEntity = dHelper.newDynamicEntity("org.course.project.model.dynamic." + JPATest.DYNAMIC_NAME);

        final List<Data> dataList = this.dataDao.get(null);

        newDynamicEntity.set("name", "Demo");
        newDynamicEntity.set("description", "Demo Entity");

        final String parentId = dataList.get(0).getId().toString();
        newDynamicEntity.set("parentid", parentId);

        this.dynamicDao.persist(newDynamicEntity);

        final DynamicEntity retrievedDynamicEntity = this.dynamicDao.get(JPATest.DYNAMIC_NAME, newDynamicEntity.<Long> get("id"));
        Assert.assertEquals(newDynamicEntity.<Long> get("id"), retrievedDynamicEntity.<Long> get("id"));

        final Map<String,String> fieldsToValuesMap = new HashMap<String,String>();
        fieldsToValuesMap.put("description", "Demo Entity1");
        final String oldDescription = newDynamicEntity.<String> get("description");
        this.dynamicDao.update(JPATest.DYNAMIC_NAME, fieldsToValuesMap, newDynamicEntity.<Long> get("id"));
        final String newDescription = newDynamicEntity.<String> get("description");
        Assert.assertTrue(!oldDescription.equals(newDescription));

        final List<DynamicEntity> retrievedByParentList = this.dynamicDao.getRecordsByParent(JPATest.DYNAMIC_NAME, newDynamicEntity.get("parentid"));
        Assert.assertTrue(retrievedByParentList.size() > 0);

    }

    @Test
    @Transactional
    public void testBaseDao() {

        List<Site> sitesList = this.siteDao.get(null);
        Assert.assertTrue(sitesList.size() > 0);

        Site site = null;
        Integer pagesNumber = 0;
        for (Site s : sitesList) {
            if (s.getPages().values().size() > 0) {
                site = s;
                pagesNumber += site.getPages().values().size();
                break;
            }
        }
        Assert.assertTrue(pagesNumber > 0);

        sitesList = this.siteDao.get(site.getId());
        Assert.assertTrue(sitesList.size() == 1);

        final ClientAdmin clientAdmin = this.clientAdminIDao.get(null).get(0);
        Site newSite = new Site(JPATest.TEST_WORD + 11, JPATest.TEST_WORD + 11, JPATest.TEST_WORD + 11);
        this.siteDao.persist(clientAdmin.getId(), newSite);
        em.flush();
        final Long newId = newSite.getId();

        final String oldName = newSite.getName();
        final Map<String,String> fieldsUpdateMap = new HashMap<String,String>();
        fieldsUpdateMap.put("name", JPATest.TEST_WORD + 12);
        this.siteDao.update(fieldsUpdateMap, newId);
        Assert.assertTrue(!oldName.equals(newSite.getName()));

        this.siteDao.remove(site);
        sitesList = this.siteDao.get(site.getId());
        Assert.assertTrue(sitesList.size() == 0);

    }

    @Test
    @Transactional
    public void testDataDao() {

        List<Data> datasList = this.dataDao.get(null);
        final Long componentId = datasList.get(0).getComponent().getId();

        datasList = this.dataDao.getByParent(componentId);
        Assert.assertTrue(datasList.size() > 0);

    }

    @Test
    @Transactional
    public void testPageDao() {

        final List<Site> sitesList = this.siteDao.get(null);

        Site site = new Site();
        for (Site s : sitesList) {
            if (s.getPages().values().size() > 0) {
                site = s;
                break;
            }
        }

        final String predefinedPageType = "test0";
        Page page = this.pageDao.getPageByType(site.getId(), predefinedPageType);
        Assert.assertTrue(page.getId() != null);

    }

    @Test
    @Transactional
    public void testClientAdminDao() {

        String existedUserName = "test0";
        String existedUserPassword = existedUserName;
        ClientAdmin clientAdmin = this.clientAdminIDao.getByCredentials(existedUserName, existedUserPassword);
        Assert.assertTrue(clientAdmin.getId() != null);

        existedUserName = "test11";
        existedUserPassword = existedUserName;
        clientAdmin = this.clientAdminIDao.getByCredentials(existedUserName, existedUserPassword);
        Assert.assertTrue(clientAdmin.getId() == null);

    }

    @After
    public void removeDynamicTables() {

        if (JPATest.dynamicTablesList.size() > 0) {
            final StringBuilder queryStr = new StringBuilder("DROP TABLE ");
            for (String table : JPATest.dynamicTablesList) {

                queryStr.append(table + ",");

            }
            final Query query = em.createNativeQuery(queryStr.toString().replaceFirst("\\,$", ";"));
            query.executeUpdate();

            JPATest.dynamicTablesList.clear();
        }

    }

}
