package net.mademocratie.gae.server.controllers;

import org.springframework.web.servlet.ModelAndView;

/**
 * AbstractController
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class AbstractController {

    protected void populateCommons(ModelAndView modelAndView) {
        modelAndView.addObject("jquery_version", "jquery-1.8.1");
        modelAndView.addObject("bootstrap_version", "bootstrap-2-2-2");
    }
}
