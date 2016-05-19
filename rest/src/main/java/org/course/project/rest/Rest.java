package org.course.project.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.course.project.model.system.*;
import org.course.project.service.interfaces.*;
import org.course.project.utility.exceptions.ParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Controller()
@RequestMapping(value = "/wcsm")
public final class Rest {

    private final static Integer ERROR = 400;
    private final static Integer SUCCES = 200;
    private final static String RESPONCE_TYPE = "application/json";
    private final static String DEFAULT_RESPONCE = "{}";

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

    private AsyncContext asyncContext;

    @ResponseBody
    @RequestMapping( value = "{serviceName}/{action}", method = RequestMethod.POST)
    public void doHandle(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response,
                         @PathVariable( "serviceName" ) final String serviceName,
                         @PathVariable( "action" ) final String action) {

        request.setAttribute( "org.apache.catalina.ASYNC_SUPPORTED", true );

        this.asyncContext = request.startAsync();

        final Rest reference = this;
        this.asyncContext.start( new Runnable() {
            public void run() {

                try {

                    final Field classField = ReflectionUtils.findField(reference.getClass(), serviceName);
                    ReflectionUtils.makeAccessible(classField);

                    final Object service = ReflectionUtils.getField(classField, reference);
                    final Method method = ReflectionUtils.findMethod(classField.getType(), action, Map.class);

                    final Map<String, String> paramsMap = new HashMap<String, String>();
                    for (String key : request.getParameterMap().keySet()) {
                        paramsMap.put(key, request.getParameterMap().get(key)[0]);
                    }

                    final Object intermediateResult = ReflectionUtils.invokeMethod(method, service, paramsMap);

                    String result = intermediateResult instanceof String ? (String)intermediateResult : new ObjectMapper().writeValueAsString(intermediateResult);
                    result = result.length() == 0 ? Rest.DEFAULT_RESPONCE : result;

                    reference.sendSucces(result);

                } catch (final Exception ex) {
                    reference.sendError(ex);
                }

            }
        });

    }

    public void sendError( final Exception ex ) {

        try {

            StringBuilder exToStr = new StringBuilder();
            exToStr.append( "Exception: " + ex.getCause() + " : " + ex.getMessage() + "\n" );

            for ( StackTraceElement el : ex.getStackTrace() ) {

                exToStr.append( "STACKTRACE ITEM: " + el.toString() + "\n" );

            }

            HttpServletResponse res = (HttpServletResponse)this.asyncContext.getResponse();

            res.sendError( Rest.ERROR, "{ \"error\": \"" + exToStr.toString() + "\" }");
            this.asyncContext.complete();

        } catch ( final IOException exx ) {

            //ignore

        }

    }

    public void sendSucces( final String content ) {

        try {

            HttpServletResponse res = (HttpServletResponse)this.asyncContext.getResponse();

            res.setContentType(Rest.RESPONCE_TYPE);
            final byte[] responseBytes = content.getBytes();
            res.setContentLength(responseBytes.length);
            res.setStatus(Rest.SUCCES);
            final PrintWriter writer = res.getWriter();
            writer.println(content);
            writer.flush();
            writer.close();
            this.asyncContext.complete();

        } catch ( final IOException ex ) {

            //ignore

        }

    }

    @PostConstruct
    public void initDynamicEntities() throws ParametersException, IOException {
        this.dynamicEntityService.initDynamicClasses();
    }

}
