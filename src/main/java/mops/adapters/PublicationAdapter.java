package mops.adapters;

import mops.adapters.dtos.PublicationDto;
import mops.application.services.GroupService;
import mops.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

//@Service
//@PropertySource(value = "classpath:errormappings/datepollmappings.properties", encoding = "UTF-8")
public class PublicationAdapter {

    private final transient Environment errorEnvironment;
    private final transient GroupService groupService;
    private final transient UserService userService;

    //@Autowired
    public PublicationAdapter(Environment env, GroupService groupService, UserService userService) {
        this.errorEnvironment = env;
        this.groupService = groupService;
        this.userService = userService;
    }

    public boolean addUserOrGroup(PublicationDto publicationDto, String userOrGroup, MessageContext context) {

        return false;
    }
}
