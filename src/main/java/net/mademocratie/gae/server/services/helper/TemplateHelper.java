package net.mademocratie.gae.server.services.helper;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.mademocratie.gae.server.exception.MaDemocratieException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class TemplateHelper {
    private final static Logger LOGGER = Logger.getLogger(TemplateHelper.class.getName());

    public static String processTemplate(Object data, String curTemplate) throws IOException, TemplateException, MaDemocratieException {
        Map root = new HashMap();
        root.put("data", data);
        Configuration configuration = new Configuration();
        // configuration.setClassForTemplateLoading(TemplateHelper.class.getClass(), "/");
        URL resource = TemplateHelper.class.getResource("/" + curTemplate);
        if (resource == null) {
            throw new MaDemocratieException("unable to find template " + curTemplate);
        }
        String resourceDir = calculateResourceDir(curTemplate, resource);
        configuration.setDirectoryForTemplateLoading(new File(resourceDir));
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        // URL templateResource = TemplateHelper.class.getResource("/" + curTemplate);
        // String templatePath = templateResource != null ? templateResource.getPath().substring(1) : null;
        // LOGGER.info("template : " + templatePath);
        Template temp = configuration.getTemplate(curTemplate);

        StringBufferWriter sbw = new TemplateHelper.StringBufferWriter();
        temp.process(root, sbw);
        return sbw.getStringBuffer().toString();
    }

    private static String calculateResourceDir(String curTemplate, URL resource) {
        String resourceDir = resource.getPath().substring(1);
        resourceDir = resourceDir.substring(0, resourceDir.length()-curTemplate.length());
        LOGGER.info("resourceDir : " + resourceDir);
        return resourceDir;
    }

    private static class StringBufferWriter extends Writer
    {
        /* ------------------------------------------------------------ */
        private StringBuffer _buffer;

    /* ------------------------------------------------------------ */
        /** Constructor.
         */
        public StringBufferWriter()
        {
            _buffer=new StringBuffer();
        }

    /* ------------------------------------------------------------ */
        /** Constructor.
         * @param buffer
         */
        public StringBufferWriter(StringBuffer buffer)
        {
            _buffer=buffer;
        }

        /* ------------------------------------------------------------ */
        public void setStringBuffer(StringBuffer buffer)
        {
            _buffer=buffer;
        }

        /* ------------------------------------------------------------ */
        public StringBuffer getStringBuffer()
        {
            return _buffer;
        }

        /* ------------------------------------------------------------ */
        public void write(char c)
                throws IOException
        {
            _buffer.append(c);
        }

        /* ------------------------------------------------------------ */
        public void write(char[] ca)
                throws IOException
        {
            _buffer.append(ca);
        }


        /* ------------------------------------------------------------ */
        public void write(char[] ca,int offset, int length)
                throws IOException
        {
            _buffer.append(ca,offset,length);
        }

        /* ------------------------------------------------------------ */
        public void write(String s)
                throws IOException
        {
            _buffer.append(s);
        }

        /* ------------------------------------------------------------ */
        public void write(String s,int offset, int length)
                throws IOException
        {
            for (int i=0;i<length;i++)
                _buffer.append(s.charAt(offset+i));
        }

        /* ------------------------------------------------------------ */
        public void flush()
        {}

        /* ------------------------------------------------------------ */
        public void reset()
        {
            _buffer.setLength(0);
        }

        /* ------------------------------------------------------------ */
        public void close()
        {}

    }
}

