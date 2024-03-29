package com.danny.javablogaggregatorspringboot.controller.admin;

import com.danny.javablogaggregatorspringboot.entity.Configuration;
import com.danny.javablogaggregatorspringboot.service.ConfigurationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@AllArgsConstructor

@RequestMapping("/admin/configuration")
@Controller
public class AdminConfigurationController {

    private ConfigurationService configurationService;

    @RequestMapping
    public String show(Model model) {
        model.addAttribute("configuration", configurationService.find());
        model.addAttribute("current", "configuration");
        return "configuration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public View save(@ModelAttribute Configuration configuration) {
        configurationService.save(configuration);
        RedirectView redirectView = new RedirectView("configuration?success=true");
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }

    @RequestMapping(value = "/upload-icon", method = RequestMethod.POST)
    public View uploadIcon(@RequestParam MultipartFile icon) {
        if (!icon.isEmpty()) {
            try {
                log.info("save icon");
                configurationService.saveIcon(icon.getBytes());
            } catch (Exception e) {
                log.error("could not upload icon", e);
            }
        } else {
            log.error("could not upload icon");
        }
        RedirectView redirectView = new RedirectView("?success=true");
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }

    @RequestMapping(value = "/upload-favicon", method = RequestMethod.POST)
    public View uploadFavicon(@RequestParam MultipartFile favicon) {
        if (!favicon.isEmpty()) {
            try {
                log.info("save favicon");
                configurationService.saveFavicon(favicon.getBytes());
            } catch (Exception e) {
                log.error("could not upload favicon", e);
            }
        } else {
            log.error("could not upload favicon");
        }
        RedirectView redirectView = new RedirectView("?success=true");
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }

    @RequestMapping(value = "/upload-appleTouchIcon", method = RequestMethod.POST)
    public View uploadAppleTouchIcon(@RequestParam MultipartFile appleTouchIcon) {
        if (!appleTouchIcon.isEmpty()) {
            try {
                log.info("save apple touch icon");
                configurationService.saveAppleTouchIcon(appleTouchIcon.getBytes());
            } catch (Exception e) {
                log.error("could not upload appleTouchIcon", e);
            }
        } else {
            log.error("could not upload appleTouchIcon");
        }
        RedirectView redirectView = new RedirectView("?success=true");
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }

}